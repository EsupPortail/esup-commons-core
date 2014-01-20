/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail;

import org.esupportail.commons.mail.model.MailStatus;
import org.esupportail.commons.mail.model.MessageTemplate;

import java.util.concurrent.Future;

import javax.mail.MessagingException;

/** 
 * The interface of SMTP services, used to send emails.
 */
public interface SmtpService {

	/**
	 * Send an email. The email may be intercepted depending on the configuration.
	 *
     * @param messageTemplate the message to send
     * @return
	 * 
	 * May throw a {@link MessagingException}
	 */
	Future<MailStatus> send(MessageTemplate messageTemplate) throws MessagingException;
	
	
	/**
	 * Send an email. Email will never be intercepted, even if configured so.
	 *
     *
     * @param template
     * @return
	 * 
	 * May throw a {@link MessagingException}
	 */
	Future<MailStatus> sendDoNotIntercept(final MessageTemplate template) throws MessagingException;
	
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
