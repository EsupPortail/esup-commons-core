/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.smtp;

import java.io.File;
import java.util.List;

import javax.mail.internet.InternetAddress;

/**
 * An implementation of SmtpService that sends no email at all.
 */
public class VoidSmtpServiceImpl extends AbstractSmtpService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 1738559445155844511L;

	/**
	 * Constructor.
	 */
	public VoidSmtpServiceImpl() {
		super();
	}

	@Override
	public void send(
			final InternetAddress to,
			final String subject,
			final String htmlBody,
			final String textBody,
			final String messageId) {
		// do nothing
	}

	@Override
	public void send(
			final InternetAddress to,
			final String subject,
			final String htmlBody,
			final String textBody,
			final List<File> files,
			final String messageId) {
		// do nothing
	}

	@Override
	public void sendtocc(
			final InternetAddress[] tos,
			final InternetAddress[] ccs,
			final InternetAddress[] bccs,
			final String subject,
			final String htmlBody,
			final String textBody,
			final List<File> files,
			final String messageId) {
		// do nothing

	}

	@Override
	public void sendDoNotIntercept(
			final InternetAddress to,
			final String subject,
			final String htmlBody,
			final String textBody,
			final String messageId) {
		// do nothing
	}

	@Override
	public void sendDoNotIntercept(
			final InternetAddress to,
			final String subject,
			final String htmlBody,
			final String textBody,
			final List<File> files,
			final String messageId) {
		// do nothing
	}

}
