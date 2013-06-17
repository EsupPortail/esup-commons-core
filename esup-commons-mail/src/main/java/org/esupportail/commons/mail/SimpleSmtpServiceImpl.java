/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail;

import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static javax.mail.internet.MimeUtility.encodeText;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 * A simple implementation of SmtpService.
 */
public class SimpleSmtpServiceImpl implements SmtpService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 1306585947271009432L;

	/**
	 * The default encoding charset.
	 */
	private static final String DEFAULT_CHARSET = "utf-8";

	/**
	 * A logger.
	 */
	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * The smtpServers to use.
	 */
	private SmtpServer server;

	/**
	 * The 'From' address to use.
	 */
	private InternetAddress fromAddress;

	/**
	 * True to intercept all the outgoing emails.
	 */
	private Boolean interceptAll;

	/**
	 * The address to which _all_ the emails should be sent (if null, all the
	 * emails are sent normally).
	 */
	private InternetAddress interceptAddress;

	/**
	 * The recipient of the test emails.
	 */
	private InternetAddress testAddress;

	/**
	 * The charset used to encode the headers.
	 */
	private String charset;

	/**
	 * The addresses that are never intercepted.
	 */
	private List<String> notInterceptedAddresses;

	/**
	 * Constructor.
	 */
	private SimpleSmtpServiceImpl() {
		this.notInterceptedAddresses = new ArrayList<String>();
	}

	public final static SimpleSmtpServiceImpl createInstance(
			final InternetAddress fromAddress,
			final InternetAddress interceptAddress,
			final InternetAddress testAddress, final boolean interceptAll,
			final String notInterceptAdresses, final String charset,
			final SmtpServer server) {
		SimpleSmtpServiceImpl service = new SimpleSmtpServiceImpl()
				.withFromAddress(fromAddress)
				.withInterceptAddress(interceptAddress)
				.withTestAddress(testAddress).withInterceptAll(interceptAll)
				.withNotInterceptedAddresses(notInterceptAdresses)
				.withCharset(charset).withServer(server);
		assert service.fromAddress != null : "property fromAddress can not be null";
		if (service.server == null) {
			final SmtpServer defaultServer = SmtpServer.createInstance();
			service = service.withServer(defaultServer);
		}
		if (service.charset == null || "".equals(service.charset.trim())) {
			service.setDefaultCharset();
		}
		return service;
	}

	public final static SimpleSmtpServiceImpl createInstance(
			final InternetAddress fromAddress,
			final InternetAddress interceptAddress,
			final InternetAddress testAddress, final boolean interceptAll,
			final String notInterceptAdresses) {
		return createInstance(fromAddress, interceptAddress, testAddress,
				interceptAll, notInterceptAdresses, null, null);
	}

	@Override
	public Future<TransportEvent> send(final MessageTemplate messageTemplate) {

		MessageTemplate template = messageTemplate;
		
		// Interception of messages
		List<InternetAddress> tmpTos = new ArrayList<>();
		for (InternetAddress to : template.getTos()) {
			InternetAddress recipient = getRealRecipient(to, template.isIntercept());
			tmpTos.add(recipient);
			if (template.isIntercept()) break;
		}

		List<InternetAddress> tmpCcs = new ArrayList<>();
		if (template.getCcs() != null && !interceptAll) {
			for (InternetAddress to : template.getCcs()) {
				InternetAddress recipient = getRealRecipient(to, template.isIntercept());
				tmpCcs.add(recipient);
			}
		}

		List<InternetAddress> tmpBccs = new ArrayList<>();
		if (template.getBccs() != null && !interceptAll) {
			for (InternetAddress to : template.getBccs()) {
				InternetAddress recipient = getRealRecipient(to, template.isIntercept());
				tmpBccs.add(recipient);
			}
		}
		
		template = template.withTos(tmpTos.toArray(new InternetAddress[] {}))
				.withCcs(tmpCcs.toArray(new InternetAddress[] {}))
				.withBccs(tmpBccs.toArray(new InternetAddress[] {}));

		try {
			template = template.withSubject(encodeText(template.getSubject(), charset, null));
		} catch (UnsupportedEncodingException e) {
			throw new SmtpException(e);
		}

		if (logger.isDebugEnabled()) {
			for (final InternetAddress iAdr : template.getTos()) {
				logger.debug("preparing an email for to '" + iAdr.getAddress()
						+ "'...");
			}
		}
		
		final Future<TransportEvent> result = sendMessage(template, server);
		
		for (final InternetAddress iAdr : template.getTos()) {
			logger.info("an email has been sent to '" + iAdr.getAddress() + "'...");
		}
		
		return result;
	}

	@Override
	public Future<TransportEvent> sendDoNotIntercept(final MessageTemplate template) {
		return send(template.withIntercept(false));
	}

	@Override
	public boolean supportsTest() {
		return true;
	}

	@Override
	public Future<TransportEvent> test() {
		assert testAddress != null : "can not test the SMTP connection when property testAddress is not set, check your configuration.";
		final MessageTemplate template = MessageTemplate.createInstance(
				"SMTP test", "<p>This is a <b>test</b>.</p>",
				"This is a test.", testAddress);
		return sendDoNotIntercept(template);
	}

	/**
	 * @return the real recipient of an email.
	 * @param to
	 * @param intercept
	 */
	protected InternetAddress getRealRecipient(final InternetAddress to, final boolean intercept) {
		InternetAddress recipient;
		if (intercept 
				&& interceptAll
				&& interceptAddress != null
				&& !notInterceptedAddresses.contains(to.getAddress()
						.toLowerCase())) {
			try {
				recipient = new InternetAddress(interceptAddress.getAddress(),
						interceptAddress.getPersonal() + " (normally sent to "
								+ to.getAddress() + ")");
			} catch (UnsupportedEncodingException e) {
				throw new SmtpException("could not send mail to '"
						+ to.getAddress() + "'", e);
			}
		} else {
			recipient = to;
		}
		return recipient;
	}

	private Future<TransportEvent> sendMessage(final MessageTemplate template, final SmtpServer smtpServer) {
		return Executors.newSingleThreadExecutor().submit(
				new Callable<TransportEvent>() {
					@SuppressWarnings("synthetic-access")
					public TransportEvent call() throws MessagingException {
						
						Properties props = new Properties();
						props.put("mail.smtp.host", smtpServer.getHost());
						props.put("mail.smtp.port",
								Integer.toString(smtpServer.getPort()));
						Session session = Session.getInstance(props, null);
						MimeMessage message = new MessageIdChangingMimeMessage(
								session, template.getMessageId());

						// if files not null send multipart
						if (template.getFiles() != null) {
							Multipart multipart = 
									addFilesAttach(template.getHtmlBody(), template.getTextBody(), template.getFiles());
							// Associate multi-part with message and
							// attach
							message.setContent(multipart);
						} else {
							// fill in the content
							final String htmlbody = template.getHtmlBody();
							final String textbody = template.getTextBody();
							if (htmlbody != null && textbody != null) {
								// Create a multi-part to combine the
								// parts
								Multipart multipart = new MimeMultipart("alternative");
								// Create your text message part
								BodyPart textBodyPart = new MimeBodyPart();
								textBodyPart.setText(textbody);
								multipart.addBodyPart(textBodyPart);
								// Create your html message part
								BodyPart htmlBodyPart = new MimeBodyPart();
								htmlBodyPart.setContent(htmlbody,
									"text/html; charset=\"" + charset + "\"");
								multipart.addBodyPart(htmlBodyPart);
								// Associate multi-part with message
								message.setContent(multipart);
							} else if (htmlbody != null) {
								message.setContent(template.getHtmlBody(),
									"text/html; charset=\"" + charset + "\"");
							} else {
								message.setText(textbody);
							}
						}
						// Fill in header
						message.setFrom(fromAddress);
						// direct recipients
						message.addRecipients(TO, template.getTos());
						// carbon copy recipients
						if (template.getCcs() != null) {
							message.addRecipients(CC, template.getCcs());
						}
						// blind carbon copy recipients
						if (template.getBccs() != null) {
							message.addRecipients(BCC, template.getBccs());
						}
						message.addHeader("Subject", template.getSubject());

						// Send message
						Transport transport = session.getTransport("smtp");

						final List<TransportEvent> events = new ArrayList<>();
						events.add(new TransportEvent(transport,
								TransportEvent.MESSAGE_NOT_DELIVERED,
								new InternetAddress[0],
								message.getAllRecipients(),
								new InternetAddress[0], message));

						transport.addTransportListener(new TransportListener() {
							public void messagePartiallyDelivered(TransportEvent e) {
								events.set(0, e);
							}

							public void messageNotDelivered(TransportEvent e) {
								events.set(0, e);
							}

							public void messageDelivered(TransportEvent e) {
								events.set(0, e);
							}
						});
						if (smtpServer.getUser() != null) {
							transport.connect(smtpServer.getHost(),
									smtpServer.getPort(),
									smtpServer.getUser(),
									smtpServer.getPassword());
						} else {
							transport.connect();
						}
						message.saveChanges();
						transport.sendMessage(message,
								message.getAllRecipients());
						transport.close();

						return events.iterator().next();
					}
				});
	}

	/**
	 * add a message and files in a Multipart.
	 * 
	 * @param htmlBody
	 * @param textBody
	 * @param files
	 * @return Multipart
	 * @throws MessagingException
	 */
	private static Multipart addFilesAttach(final String htmlBody,
			final String textBody, final List<File> files)
			throws MessagingException {

		Multipart multipart;
		// fill in the content
		if (htmlBody != null && textBody != null) {
			// Create a multi-part to combine the parts
			multipart = new MimeMultipart("mixed");
			// Create your text message part
			BodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(textBody);
			multipart.addBodyPart(textBodyPart);
			// Create your html message part
			BodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setContent(htmlBody, "text/html");
			multipart.addBodyPart(htmlBodyPart);

		} else if (htmlBody != null) {
			multipart = new MimeMultipart("mixed");
			// Create your html message part
			BodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setContent(htmlBody, "text/html");
			multipart.addBodyPart(htmlBodyPart);

		} else {
			multipart = new MimeMultipart("mixed");
			// Create your text message part
			BodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(textBody);
			multipart.addBodyPart(textBodyPart);
		}

		BodyPart fileBodyPart;
		for (File file : files) {
			fileBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(file);
			fileBodyPart.setDataHandler(new DataHandler(source));
			fileBodyPart.setFileName(file.getName());
			multipart.addBodyPart(fileBodyPart);
		}

		return multipart;
	}

	/**
	 * @return The servers.
	 */
	public SmtpServer getServer() {
		return this.server;
	}

	/**
	 * @param servers
	 *            The servers to set.
	 */
	public void setServer(final SmtpServer server) {
		this.server = server;
	}

	/**
	 * @param servers
	 *            The servers to set.
	 * @return
	 */
	public SimpleSmtpServiceImpl withServer(final SmtpServer server) {
		this.server = server;
		return this;
	}

	/**
	 * @return The fromAddress.
	 */
	public InternetAddress getFromAddress() {
		return this.fromAddress;
	}

	/**
	 * @param fromAddress
	 *            The fromAddress to set.
	 */
	public void setFromAddress(final InternetAddress fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * @param fromAddress
	 *            The fromAddress to set.
	 * @return
	 */
	public SimpleSmtpServiceImpl withFromAddress(
			final InternetAddress fromAddress) {
		this.fromAddress = fromAddress;
		return this;
	}

	/**
	 * @return The interceptAddress.
	 */
	public InternetAddress getInterceptAddress() {
		return this.interceptAddress;
	}

	/**
	 * @param interceptAddress
	 *            The interceptAddress to set.
	 */
	public void setInterceptAddress(final InternetAddress interceptAddress) {
		this.interceptAddress = interceptAddress;
	}

	/**
	 * @param interceptAddress
	 *            The interceptAddress to set.
	 * @return
	 */
	public SimpleSmtpServiceImpl withInterceptAddress(
			final InternetAddress interceptAddress) {
		this.interceptAddress = interceptAddress;
		return this;
	}

	/**
	 * @return The charset.
	 */
	public String getCharset() {
		return this.charset;
	}

	/**
	 * @param charset
	 *            The charset to set.
	 */
	public void setCharset(final String charset) {
		this.charset = charset;
	}

	/**
	 * Set the default charset.
	 */
	public void setDefaultCharset() {
		setCharset(DEFAULT_CHARSET);
	}

	/**
	 * Set the default charset.
	 * 
	 * @return
	 */
	public SimpleSmtpServiceImpl withDefaultCharset() {
		setDefaultCharset();
		return this;
	}

	/**
	 * @param charset
	 *            The charset to set.
	 * @return
	 */
	public SimpleSmtpServiceImpl withCharset(final String charset) {
		this.charset = charset;
		return this;
	}

	/**
	 * @return the testAddress
	 */
	public InternetAddress getTestAddress() {
		return testAddress;
	}

	/**
	 * @param testAddress
	 *            the testAddress to set
	 */
	public void setTestAddress(final InternetAddress testAddress) {
		this.testAddress = testAddress;
	}

	/**
	 * @param testAddress
	 *            the testAddress to set
	 * @return
	 */
	public SimpleSmtpServiceImpl withTestAddress(
			final InternetAddress testAddress) {
		this.testAddress = testAddress;
		return this;
	}

	/**
	 * @return the notInterceptedAddresses
	 */
	protected List<String> getNotInterceptedAddresses() {
		return notInterceptedAddresses;
	}

	/**
	 * @param addresses
	 *            the addresses not to intercept, comma-separated.
	 */
	public void setNotInterceptedAddresses(final String addresses) {
		if (addresses == null) {
			return;
		}
		notInterceptedAddresses = new ArrayList<String>();
		for (String address : addresses.split(",")) {
			notInterceptedAddresses.add(address.toLowerCase());
		}
	}

	/**
	 * @param addresses
	 *            the addresses not to intercept, comma-separated.
	 * @return
	 */
	public SimpleSmtpServiceImpl withNotInterceptedAddresses(
			final String addresses) {
		setNotInterceptedAddresses(addresses);
		return this;
	}

	/**
	 * @return the interceptAll
	 */
	protected Boolean getInterceptAll() {
		return interceptAll;
	}

	/**
	 * @param interceptAll
	 *            the interceptAll to set
	 */
	public void setInterceptAll(final Boolean interceptAll) {
		this.interceptAll = interceptAll;
	}

	/**
	 * @param interceptAll
	 *            the interceptAll to set
	 * @return
	 */
	public SimpleSmtpServiceImpl withInterceptAll(final Boolean interceptAll) {
		this.interceptAll = interceptAll;
		return this;
	}
	
}
