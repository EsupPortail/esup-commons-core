/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail.exceptions;

/**
 * A class to represent the exceptions thrown when sending mails.
 */
public class SmtpException extends RuntimeException {

	public SmtpException(final String message) {
		super(message);
	}

	public SmtpException(final Exception cause) {
		super(cause);
	}

	public SmtpException(final String message, final Exception cause) {
		super(message, cause);
	}
}
