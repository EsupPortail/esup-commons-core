package org.esupportail.commons.services;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;

import org.esupportail.commons.services.smtp.SimpleSmtpServiceImpl;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

@ContextConfiguration(locations="/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SmtpServiceTest {

	@Resource(name="smtpService")
	SimpleSmtpServiceImpl smtpService;

	String TITLE = "Title";
	String HTML_BODY = "<html><body>Hello</body></html>";
	String TEXT_BODY = "Hello";
	String TO = "test@test.com";
	String TO2 = "test2@test.com";
	String INTERCEPT_ADRESSE = "stop@test.com";
	int SMTP_PORT=2525;
	InternetAddress to, to2;
	InternetAddress interceptAddress;
	SimpleSmtpServer server;

	@Before
	public void before() {
		server = SimpleSmtpServer.start(SMTP_PORT);
		try {
			to = new InternetAddress(TO, TO);
			to2 = new InternetAddress(TO2, TO2);
			interceptAddress = new InternetAddress(INTERCEPT_ADRESSE, INTERCEPT_ADRESSE);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@After
	public void after() {
		server.stop();
	}

	@Test
	public void testMailwithOneRecipient(){
		smtpService.send(to, TITLE, HTML_BODY, TEXT_BODY);
		Assert.assertEquals(1, server.getReceivedEmailSize());
		SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		Assert.assertThat(email.getHeaderValue("To"), Matchers.containsString(TO));
	}

	@Test
	public void testInterceptedMailwithOneRecipient(){
		smtpService.setInterceptAddress(interceptAddress);
		smtpService.setInterceptAll(true);
		smtpService.send(to, TITLE, HTML_BODY, TEXT_BODY);
		Assert.assertEquals(1, server.getReceivedEmailSize());
		SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		Assert.assertThat(email.getHeaderValue("To"), Matchers.containsString(INTERCEPT_ADRESSE));
	}

	@Test
	public void testMailwithRecipients(){
		InternetAddress[] tos = {to};
		InternetAddress[] ccs = {to2};
		smtpService.sendtocc(tos, ccs, null, TITLE, HTML_BODY, TEXT_BODY, null);
		Assert.assertEquals(1, server.getReceivedEmailSize());
		SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		Assert.assertThat(email.getHeaderValue("To"), Matchers.containsString(TO));
		Assert.assertThat(email.getHeaderValue("Cc"), Matchers.containsString(TO2));
	}

	@Test
	public void testInterceptedMailwithRecipients(){
		InternetAddress[] tos = {to};
		InternetAddress[] ccs = {to2};
		smtpService.setInterceptAddress(interceptAddress);
		smtpService.setInterceptAll(true);
		smtpService.sendtocc(tos, ccs, null, TITLE, HTML_BODY, TEXT_BODY, null);
		Assert.assertEquals(1, server.getReceivedEmailSize());
		SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();
		Assert.assertThat(email.getHeaderValue("To"), Matchers.containsString(INTERCEPT_ADRESSE));
		Assert.assertNull("Cc must be null", email.getHeaderValue("Cc"));
	}

}
