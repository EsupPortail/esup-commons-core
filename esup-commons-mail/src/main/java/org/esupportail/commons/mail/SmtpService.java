/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail;

import org.esupportail.commons.mail.model.MailStatus;
import org.esupportail.commons.mail.model.MessageTemplate;

import javax.mail.MessagingException;
import java.util.concurrent.Future;

/** 
 * The interface of SMTP services, used to send emails.
 */
public interface SmtpService {

    public enum Interception { AsConfigured, Forced, None }

	/**
	 * Send an email. The email may be intercepted depending on the configuration.
	 *
     * @param messageTemplate the message to send
     * @return a {@link Future} representing the sending in progress
	 * @throws MessagingException
	 */
	Future<MailStatus> send(MessageTemplate messageTemplate) throws MessagingException;
	
	
	/**
	 * Send an email. Interception can be forced, regardless of the configuration,
     * with <code>forceInterception = true</code>
     *
     * @return a {@link Future} representing the sending in progress
	 * @throws MessagingException
	 */
	Future<MailStatus> send(final MessageTemplate template, Interception interception) throws MessagingException;
	
	/**
	 * @return true if the class supports testing. If false, calls to method 
	 * test() should throw an exception.
	 */
	boolean supportsTest();

	
	/**
	 * Test the SMTP connection.
	 * @return
	 * 
	 * May throw a {@link MessagingException}
	 */
	Future<MailStatus> test() throws MessagingException;

    void shutdown();
}
