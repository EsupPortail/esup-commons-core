/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail;

import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static javax.mail.internet.MimeUtility.encodeText;
import static org.esupportail.commons.mail.model.MailStatus.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

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
import org.esupportail.commons.mail.exceptions.SmtpException;
import org.esupportail.commons.mail.model.MailStatus;
import org.esupportail.commons.mail.model.MessageTemplate;
import org.esupportail.commons.mail.model.MimeMessageWithId;
import org.esupportail.commons.mail.model.SmtpServerData;

/**
 * A simple implementation of SmtpService.
 */
public final class SimpleSmtpService implements SmtpService {

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
    private final SmtpServerData serverData;

    /**
     * The charset used to encode the headers.
     */
    private final String charset;

    /**
     * The 'From' address to use.
     */
    private final InternetAddress fromAddress;

    /**
     * The recipient of the test emails.
     */
    private final InternetAddress testAddress;

    /**
     * The address to which _all_ the emails should be sent (if null, all the
     * emails are sent normally).
     */
    private final InternetAddress interceptAddress;

    /**
     * True to intercept all the outgoing emails.
     */
    private final Boolean interceptAll;

    /**
     * The addresses that are never intercepted.
     */
    private final List<String> notInterceptedAddresses;

    private final ExecutorService executorService;

    private SimpleSmtpService(
            final InternetAddress fromAddress,
            final InternetAddress interceptAddress,
            final InternetAddress testAddress,
            final boolean interceptAll,
            final String charset,
            final SmtpServerData serverData,
            final ExecutorService executorService) {
        this.fromAddress = fromAddress;
        this.interceptAddress = interceptAddress;
        this.testAddress = testAddress;
        this.interceptAll = interceptAll;
        this.charset = charset;
        this.serverData = serverData;
        this.notInterceptedAddresses = new ArrayList<>();
        this.executorService = executorService;
    }

    @Override
    public Future<MailStatus> send(final MessageTemplate messageTemplate) throws MessagingException {

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

        if (logger.isDebugEnabled())
            for (final InternetAddress iAdr : template.getTos())
                logger.debug("preparing an email for to '" + iAdr.getAddress() + "'...");

        final Future<MailStatus> result = sendMessage(template, serverData);

        for (final InternetAddress iAdr : template.getTos())
            logger.info("an email has been sent to '" + iAdr.getAddress() + "'...");

        return result;
    }

    @Override
    public Future<MailStatus> sendDoNotIntercept(final MessageTemplate template) throws MessagingException {
        return send(template.withIntercept(false));
    }

    @Override
    public boolean supportsTest() {
        return true;
    }

    @Override
    public Future<MailStatus> test() throws MessagingException {
        assert testAddress != null : "can not test the SMTP connection when property testAddress is not set, check your configuration.";
        final MessageTemplate template = MessageTemplate.createInstance(
                "SMTP test", "<p>This is a <b>test</b>.</p>",
                "This is a test.", testAddress);
        return sendDoNotIntercept(template);
    }

    /**
     * @return the real recipient of an email.
     */
    private InternetAddress getRealRecipient(final InternetAddress to, final boolean intercept) {
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

    private Future<MailStatus> sendMessage(final MessageTemplate template, final SmtpServerData smtpServerData)
            throws MessagingException {
        final Properties props = new Properties() {{
            put("mail.smtp.host", smtpServerData.getHost());
            put("mail.smtp.port", Integer.toString(smtpServerData.getPort()));
        }};
        final Session session = Session.getInstance(props, null);
        final MimeMessage message = buildMessage(template, session);
        final Transport transport = session.getTransport("smtp");

        abstract class Sending implements Callable<MailStatus> {
            final AtomicReference<MailStatus> mailStatus = new AtomicReference<>(Empty);
            final CountDownLatch latch = new CountDownLatch(1);
        }

        final Sending sending = new Sending() {
            public MailStatus call() throws Exception {
                if (smtpServerData.getUser() != null)
                    transport.connect(smtpServerData.getHost(),
                            smtpServerData.getPort(),
                            smtpServerData.getUser(),
                            smtpServerData.getPassword());
                else
                    transport.connect();

                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

                latch.await(); // blocking until mail status is set by the TransportListener

                return mailStatus.get();
            }
        };

        transport.addTransportListener(new TransportListener() {
            public void messageDelivered(TransportEvent e) {
                sending.mailStatus.set(Delivered);
                sending.latch.countDown();
            }

            public void messageNotDelivered(TransportEvent e) {
                sending.mailStatus.set(NotDelivered);
                sending.latch.countDown();
            }

            public void messagePartiallyDelivered(TransportEvent e) {
                sending.mailStatus.set(PartiallyDelivered);
                sending.latch.countDown();
            }
        });

        return executorService.submit(sending);
    }

    private MimeMessage buildMessage(MessageTemplate template, Session session) throws MessagingException {
        final MimeMessage message = new MimeMessageWithId(session, template.getMessageId());
        // if files not null send multipart
        if (template.getFiles() != null && !template.getFiles().isEmpty()) {
            final Multipart multipart =
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
                MimeBodyPart textBodyPart = new MimeBodyPart();
                textBodyPart.setText(textbody, charset);
                multipart.addBodyPart(textBodyPart);
                // Create your html message part
                BodyPart htmlBodyPart = new MimeBodyPart();
                htmlBodyPart.setContent(htmlbody, "text/html; charset=\"" + charset + "\"");
                multipart.addBodyPart(htmlBodyPart);
                // Associate multi-part with message
                message.setContent(multipart);
            } else if (htmlbody != null)
                message.setContent(template.getHtmlBody(), "text/html; charset=\"" + charset + "\"");
            else
                message.setText(textbody, charset);
        }
        // Fill in header
        message.setFrom(fromAddress);
        // direct recipients
        message.addRecipients(TO, template.getTos());
        // carbon copy recipients
        if (template.getCcs() != null)
            message.addRecipients(CC, template.getCcs());
        // blind carbon copy recipients
        if (template.getBccs() != null)
            message.addRecipients(BCC, template.getBccs());

        message.addHeader("Subject", template.getSubject());

        message.saveChanges();
        return message;
    }

    /**
     * add a message and files in a Multipart.
     */
    private Multipart addFilesAttach(final String htmlBody,
                                     final String textBody,
                                     final List<File> files) throws MessagingException {
        Multipart multipart;
        // fill in the content
        if (htmlBody != null && textBody != null) {
            // Create a multi-part to combine the parts
            multipart = new MimeMultipart("mixed");
            // Create your text message part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(textBody, charset);
            multipart.addBodyPart(textBodyPart);
            // Create your html message part
            BodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(htmlBody, "text/html; charset=\"" + charset + "\"");
            multipart.addBodyPart(htmlBodyPart);

        } else if (htmlBody != null) {
            multipart = new MimeMultipart("mixed");
            // Create your html message part
            BodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(htmlBody, "text/html; charset=\"" + charset + "\"");
            multipart.addBodyPart(htmlBodyPart);

        } else {
            multipart = new MimeMultipart("mixed");
            // Create your text message part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(textBody, charset);
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

    public void shutdown() {
        executorService.shutdown();
    }

    public static Builder builder(InternetAddress fromAddress,
                                  InternetAddress testAddress,
                                  InternetAddress interceptAddress) {
        return new Builder(fromAddress, testAddress, interceptAddress);
    }

    public static final class Builder {
        private SmtpServerData server = SmtpServerData.builder().build();
        private String charset = "";
        private final InternetAddress fromAddress;
        private final InternetAddress testAddress;
        private final InternetAddress interceptAddress;
        private Boolean interceptAll =false;
        private List<String> notInterceptedAddresses;
        private ExecutorService executorService = Executors.newSingleThreadExecutor();

        private Builder(InternetAddress fromAddress,
                        InternetAddress testAddress,
                        InternetAddress interceptAddress) {
            this.fromAddress = fromAddress;
            this.testAddress = testAddress;
            this.interceptAddress = interceptAddress;
        }

        public Builder server(final SmtpServerData server) {
            this.server = server;
            return this;
        }

        public Builder charset(final String charset) {
            this.charset = charset;
            return this;
        }

        public Builder fromAddress(final InternetAddress fromAddress) {
            return new Builder(fromAddress, testAddress, interceptAddress)
                    .server(server)
                    .charset(charset)
                    .interceptAll(interceptAll)
                    .notInterceptedAddresses(notInterceptedAddresses)
                    .executorService(executorService);
        }

        public Builder testAddress(final InternetAddress testAddress) {
            return new Builder(fromAddress, testAddress, interceptAddress)
                    .server(server)
                    .charset(charset)
                    .interceptAll(interceptAll)
                    .notInterceptedAddresses(notInterceptedAddresses)
                    .executorService(executorService);
        }

        public Builder interceptAddress(final InternetAddress interceptAddress) {
            return new Builder(fromAddress, testAddress, interceptAddress)
                    .server(server)
                    .charset(charset)
                    .interceptAll(interceptAll)
                    .notInterceptedAddresses(notInterceptedAddresses)
                    .executorService(executorService);
        }

        public Builder interceptAll(final Boolean interceptAll) {
            this.interceptAll = interceptAll;
            return this;
        }

        public Builder notInterceptedAddresses(final List<String> notInterceptedAddresses) {
            this.notInterceptedAddresses = notInterceptedAddresses;
            return this;
        }

        public Builder executorService(final ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public SimpleSmtpService build() {
            return new SimpleSmtpService(
                    fromAddress, interceptAddress, testAddress,
                    interceptAll, charset, server, executorService);
        }
    }

    public SmtpServerData getServerData() { return serverData; }

    public String getCharset() { return charset; }

    public InternetAddress getFromAddress() { return fromAddress; }

    public InternetAddress getTestAddress() { return testAddress; }

    public InternetAddress getInterceptAddress() { return interceptAddress; }

    public Boolean getInterceptAll() { return interceptAll; }

    public List<String> getNotInterceptedAddresses() { return notInterceptedAddresses; }

}
