/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail;

import java.io.Serializable;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.event.TransportEvent;

/** 
 * The interface of SMTP services, used to send emails.
 */
public interface SmtpService extends Serializable {

	/**
	 * Send an email. The email may be intercepted depending on the configuration.
	 * @param template
	 * @return
	 * 
	 * May throw a {@link MessagingException}
	 */
	Future<TransportEvent> send(MessageTemplate template);
	
	
	/**
	 * Send an email. Email will never be intercepted, even if configured so.
	 * @param template
	 * @return 
	 * 
	 * May throw a {@link MessagingException}
	 */
	Future<TransportEvent> sendDoNotIntercept(MessageTemplate template);
	
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
	Future<TransportEvent> test();
	
}
