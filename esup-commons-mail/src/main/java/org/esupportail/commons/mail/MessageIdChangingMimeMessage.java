/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * @author lusl0338
 * MimeMessage that can has Message-ID set.
 */
public class MessageIdChangingMimeMessage extends MimeMessage {

	/**
	 * A Logger.
	 */
	private static final Logger logger = Logger.getLogger(MessageIdChangingMimeMessage.class);

	/**
	 * Message-ID to set.
	 */
	private String messageId;

	/**
	 * @param session Session
	 * @param messageId Message-ID to set
	 */
	public MessageIdChangingMimeMessage(final Session session, final String messageId) {
		this(session);
		this.setMessageId(messageId);
	}

	/**
	 * @see javax.mail.internet.MimeMessage#MimeMessage(javax.mail.Session)
	 */
	public MessageIdChangingMimeMessage(final Session session) {
		super(session);
	}

	/**
	 * @param messageId - Message-ID to set
	 */
	public void setMessageId(final String messageId) {
		this.messageId = messageId;
		logger.debug("Setting messageID: " + messageId);
	}

	@Override
	protected synchronized void updateHeaders() throws MessagingException {
		super.updateHeaders();
		if ((messageId != null) && (messageId.length() > 0)) {
			setHeader("Message-ID", messageId);
		}
	}
}
