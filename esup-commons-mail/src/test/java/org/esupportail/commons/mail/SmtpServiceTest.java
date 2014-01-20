package org.esupportail.commons.mail;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import org.esupportail.commons.mail.model.MailStatus;
import org.esupportail.commons.mail.model.MessageTemplate;
import org.esupportail.commons.mail.model.SmtpServerData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class SmtpServiceTest {

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
		interceptAddress = new InternetAddress(INTERCEPT_ADRESSE, INTERCEPT_ADRESSE);
	}

	@After
	public void after() {
		server.stop();
	}

	@Test
	public void testMailwithOneRecipient() throws InterruptedException, ExecutionException, MessagingException {
        SimpleSmtpService
                .builder(from, null, interceptAddress)
                .server(SmtpServerData.builder().port(SMTP_PORT).build())
                .interceptAll(false)
                .charset("UTF-8")
                .build()
                .send(MessageTemplate.createInstance(TITLE, HTML_BODY, TEXT_BODY, to))
                .get();

		assertEquals(1, server.getReceivedEmailSize());
		final SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		assertTrue(email.getHeaderValue("To").contains(TO));
	}

	@Test
	public void testInterceptedMailwithOneRecipient() throws InterruptedException, ExecutionException, MessagingException {
        SimpleSmtpService
                .builder(from, null, interceptAddress)
                .server(SmtpServerData.builder().port(SMTP_PORT).build())
                .interceptAll(true)
                .charset("UTF-8")
                .build()
                .send(MessageTemplate.createInstance(TITLE, HTML_BODY, TEXT_BODY, to))
                .get();

		assertEquals(1, server.getReceivedEmailSize());
		final SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		assertTrue(email.getHeaderValue("To").contains(INTERCEPT_ADRESSE));
	}

	@Test
	public void testMailwithRecipients() throws InterruptedException, ExecutionException, MessagingException {
		InternetAddress[] tos = { to };
		InternetAddress[] ccs = { to2 };

        SimpleSmtpService
                .builder(from, null, interceptAddress)
                .server(SmtpServerData.builder().port(SMTP_PORT).build())
                .interceptAll(false)
                .charset("UTF-8")
                .build()
                .send(MessageTemplate
                        .createInstance(TITLE, HTML_BODY, TEXT_BODY, tos)
                        .withCcs(ccs))
                .get();

		assertEquals(1, server.getReceivedEmailSize());
		final SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		assertTrue(email.getHeaderValue("To").contains(TO));
		assertNotNull(email.getHeaderValue("Cc"));
		assertTrue(email.getHeaderValue("Cc").contains(TO2));
	}

	@Test
	public void testInterceptedMailwithRecipients() throws InterruptedException, ExecutionException, MessagingException {
		InternetAddress[] tos = { to };
		InternetAddress[] ccs = { to2 };

        SimpleSmtpService
                .builder(from, null, interceptAddress)
                .server(SmtpServerData.builder().port(SMTP_PORT).build())
                .interceptAll(true)
                .charset("UTF-8")
                .build()
                .send(MessageTemplate
                        .createInstance(TITLE, HTML_BODY, TEXT_BODY, tos)
                        .withCcs(ccs))
                .get();

		assertEquals(1, server.getReceivedEmailSize());
		final SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		assertTrue(email.getHeaderValue("To").contains(INTERCEPT_ADRESSE));
		assertNull("Cc must be null", email.getHeaderValue("Cc"));
	}

    @Test
    public void testCachingEmail() throws MessagingException, ExecutionException, InterruptedException {
        final SmtpService smtp = CachingEmailSmtpService.create(
                SimpleSmtpService
                        .builder(from, null, null)
                        .server(SmtpServerData.builder().port(SMTP_PORT).build())
                        .charset("UTF-8")
                        .build(),
                new ConcurrentMapCache("testCachingEmail"));
        final MessageTemplate message =
                MessageTemplate.createInstance(TITLE, HTML_BODY, TEXT_BODY, to);

        final MailStatus fstSending = smtp.send(message).get();
        final MailStatus sndSending = smtp.send(message).get();

        assertEquals(MailStatus.Delivered, fstSending);
        assertEquals(MailStatus.AlreadySent, sndSending);
    }

}
