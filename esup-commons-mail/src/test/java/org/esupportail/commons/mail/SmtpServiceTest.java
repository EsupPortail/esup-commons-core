package org.esupportail.commons.mail;

import static org.esupportail.commons.mail.MessageTemplate.createInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import javax.mail.internet.InternetAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

public class SmtpServiceTest {

	private SimpleSmtpServiceImpl smtpService;

	private final String TITLE = "Title";
	private final String HTML_BODY = "<html><body>Hello</body></html>";
	private final String TEXT_BODY = "Hello";
	private final String FROM = "me@test.com";
	private final String TO = "test@test.com";
	private final String TO2 = "test2@test.com";
	private final String INTERCEPT_ADRESSE = "stop@test.com";
	private final int SMTP_PORT = 2525;

	private InternetAddress from, to, to2;
	private InternetAddress interceptAddress;
	private SimpleSmtpServer server;

	@Before
	public void before() throws UnsupportedEncodingException {
		server = SimpleSmtpServer.start(SMTP_PORT);
		from = new InternetAddress(FROM, FROM);
		to = new InternetAddress(TO, TO);
		to2 = new InternetAddress(TO2, TO2);
		interceptAddress = new InternetAddress(INTERCEPT_ADRESSE,
				INTERCEPT_ADRESSE);

		smtpService = SimpleSmtpServiceImpl.createInstance(from,
				interceptAddress, null, true, "").withServer(
				SmtpServer.createInstance(SmtpServer.DEFAULT_HOST, SMTP_PORT));
	}

	@After
	public void after() {
		server.stop();
	}

	@Test
	public void testMailwithOneRecipient() throws InterruptedException,
			ExecutionException {
		smtpService.setInterceptAll(false);
		smtpService.send(createInstance(TITLE, HTML_BODY, TEXT_BODY, to))
				.notAlreadySent().get();
		assertEquals(1, server.getReceivedEmailSize());
		final SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		assertTrue(email.getHeaderValue("To").contains(TO));
	}

	@Test
	public void testInterceptedMailwithOneRecipient()
			throws InterruptedException, ExecutionException {
		smtpService.setInterceptAll(true);
		smtpService.send(createInstance(TITLE, HTML_BODY, TEXT_BODY, to))
				.notAlreadySent().get();
		assertEquals(1, server.getReceivedEmailSize());
		final SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		assertTrue(email.getHeaderValue("To").contains(INTERCEPT_ADRESSE));
	}

	@Test
	public void testMailwithRecipients() throws InterruptedException,
			ExecutionException {
		InternetAddress[] tos = { to };
		InternetAddress[] ccs = { to2 };
		smtpService.setInterceptAll(false);
		smtpService.send(
				createInstance(TITLE, HTML_BODY, TEXT_BODY, tos)
				.withCcs(ccs)).notAlreadySent().get();
		assertEquals(1, server.getReceivedEmailSize());
		final SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		assertTrue(email.getHeaderValue("To").contains(TO));
		assertNotNull(email.getHeaderValue("Cc"));
		assertTrue(email.getHeaderValue("Cc").contains(TO2));
	}

	@Test
	public void testInterceptedMailwithRecipients()
			throws InterruptedException, ExecutionException {
		InternetAddress[] tos = { to };
		InternetAddress[] ccs = { to2 };
		smtpService.setInterceptAll(true);
		smtpService.send(
				createInstance(TITLE, HTML_BODY, TEXT_BODY, tos)
				.withCcs(ccs)).notAlreadySent().get();
		assertEquals(1, server.getReceivedEmailSize());
		final SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		assertTrue(email.getHeaderValue("To").contains(INTERCEPT_ADRESSE));
		assertNull("Cc must be null", email.getHeaderValue("Cc"));
	}
}
