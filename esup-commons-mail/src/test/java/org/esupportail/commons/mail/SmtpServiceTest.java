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
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.esupportail.commons.mail.SmtpService.Interception;
import static org.junit.Assert.*;

public class SmtpServiceTest {

	private static final String TITLE = "Title";
	private static final String HTML_BODY = "<html><body>Hello</body></html>";
	private static final String TEXT_BODY = "Hello";
	private static final String FROM = "me@test.com";
	private static final String TO = "test@test.com";
	private static final String TO2 = "test2@test.com";
	private static final String INTERCEPT_ADRESSE = "stop@test.com";
	private static final int SMTP_PORT = 2525;

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
                .build()
                .send(MessageTemplate.create(TITLE, HTML_BODY, TEXT_BODY, to))
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
                .build()
                .send(MessageTemplate.create(TITLE, HTML_BODY, TEXT_BODY, to))
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
                .build()
                .send(MessageTemplate
                        .create(TITLE, HTML_BODY, TEXT_BODY, tos)
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
        final SimpleSmtpService smtpService = SimpleSmtpService
                .builder(from, null, interceptAddress)
                .server(SmtpServerData.builder().port(SMTP_PORT).build())
                .interceptAll(true)
                .build();

        final MessageTemplate msg = MessageTemplate
                .create(TITLE, HTML_BODY, TEXT_BODY, to)
                .withCcs(to2);

        smtpService.send(msg).get(); // interceptAll = true
        smtpService.send(msg, Interception.None).get(); // interceptAll = true && Interception = None
        SimpleSmtpService.builder(from, null, interceptAddress) // interceptAll = false && Interception = Forced
                .server(smtpService.getServerData())
                .interceptAll(false)
                .build()
                .send(msg, Interception.Forced).get();

        final List<SmtpMessage> receivedEmail = new ArrayList<>(new AbstractCollection<SmtpMessage>() {
            public Iterator<SmtpMessage> iterator() {
                return server.getReceivedEmail();
            }
            public int size() {
                return server.getReceivedEmailSize();
            }
        });

        assertEquals(3, receivedEmail.size());

        final SmtpMessage email = receivedEmail.get(0);
        assertTrue(email.getHeaderValue("To").contains(INTERCEPT_ADRESSE));
        assertTrue(email.getHeaderValue("Cc").contains(INTERCEPT_ADRESSE));

        final SmtpMessage email2 = receivedEmail.get(1);
        assertFalse(email2.getHeaderValue("To").contains(INTERCEPT_ADRESSE));
        assertFalse(email2.getHeaderValue("Cc").contains(INTERCEPT_ADRESSE));

        final SmtpMessage email3 = receivedEmail.get(2);
        assertTrue(email3.getHeaderValue("To").contains(INTERCEPT_ADRESSE));
        assertTrue(email3.getHeaderValue("Cc").contains(INTERCEPT_ADRESSE));
	}

    @Test
    public void testCachingEmail() throws MessagingException, ExecutionException, InterruptedException {
        final SmtpService smtp = CachingEmailSmtpService.create(
                SimpleSmtpService
                        .builder(from, null, null)
                        .server(SmtpServerData.builder().port(SMTP_PORT).build())
                        .build(),
                new ConcurrentMapCache("testCachingEmail"));
        final MessageTemplate message =
                MessageTemplate.create(TITLE, HTML_BODY, TEXT_BODY, to);

        final MailStatus fstSending = smtp.send(message).get();
        final MailStatus sndSending = smtp.send(message).get();

        assertEquals(MailStatus.Delivered, fstSending);
        assertEquals(MailStatus.AlreadySent, sndSending);
    }

}
