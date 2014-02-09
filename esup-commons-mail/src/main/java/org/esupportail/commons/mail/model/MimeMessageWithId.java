/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail.model;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public final class MimeMessageWithId extends MimeMessage {

	private final String messageId;

	public MimeMessageWithId(final Session session, final String messageId) {
		super(session);
		this.messageId = messageId;
	}

	@Override
	protected synchronized void updateHeaders() throws MessagingException {
		super.updateHeaders();
		if ((messageId != null) && (messageId.length() > 0)) {
			setHeader("Message-ID", messageId);
		}
	}
}
