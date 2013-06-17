package org.esupportail.commons.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.internet.InternetAddress;

public class MessageTemplate {
	
	private InternetAddress[] tos;
	private InternetAddress[] ccs; 
	private InternetAddress[] bccs;
	private String subject; 
	private String htmlBody; 
	private String textBody;
	private List<File> files = new ArrayList<>();
	private String messageId;
	private boolean intercept = true;
	
	private MessageTemplate() {
	}
	
	public static final MessageTemplate createInstance(final String subject,
			final String htmlBody, final String textBody,
			final InternetAddress... tos) {
		final MessageTemplate template = new MessageTemplate()
				.withTos(tos)
				.withSubject(subject)
				.withHtmlBody(htmlBody)
				.withTextBody(textBody);
		assert tos != null : "recipients of the message cannot be null";
		assert !(htmlBody == null && textBody == null) : "htmlBody and textBody cannot be both null";
		return template;
	}
	
	/**
	 * @return the tos
	 */
	public InternetAddress[] getTos() {
		return tos;
	}
	
	/**
	 * @param tos the tos to set
	 * @return 
	 */
	public MessageTemplate withTos(final InternetAddress... tos) {
		this.tos = tos;
		return this;
	}
	
	/**
	 * @return the ccs
	 */
	public InternetAddress[] getCcs() {
		return ccs;
	}
	
	/**
	 * @param ccs the ccs to set
	 * @return 
	 */
	public MessageTemplate withCcs(final InternetAddress... ccs) {
		this.ccs = ccs;
		return this;
	}
	
	/**
	 * @return the bccs
	 */
	public InternetAddress[] getBccs() {
		return bccs;
	}
	
	/**
	 * @param bccs the bccs to set
	 * @return 
	 */
	public MessageTemplate withBccs(final InternetAddress... bccs) {
		this.bccs = bccs;
		return this;
	}
	
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param subject the subject to set
	 * @return 
	 */
	public MessageTemplate withSubject(final String subject) {
		this.subject = subject;
		return this;
	}
	
	/**
	 * @return the htmlBody
	 */
	public String getHtmlBody() {
		return htmlBody;
	}
	
	/**
	 * @param htmlBody the htmlBody to set
	 * @return 
	 */
	public MessageTemplate withHtmlBody(final String htmlBody) {
		this.htmlBody = htmlBody;
		return this;
	}
	/**
	 * @return the textBody
	 */
	public String getTextBody() {
		return textBody;
	}
	/**
	 * @param textBody the textBody to set
	 * @return 
	 */
	public MessageTemplate withTextBody(final String textBody) {
		this.textBody = textBody;
		return this;
	}
	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}
	
	/**
	 * @param files the files to set
	 * @return 
	 */
	public MessageTemplate withFiles(final List<File> files) {
		if (files != null) {
			this.files = files;
		}
		return this;
	}
	
	/**
	 * @param files the files to set
	 * @return 
	 */
	public MessageTemplate withFiles(final File... files) {
		if (files != null) {
			return withFiles(Arrays.asList(files));
		}
		return this;
	}
	
	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}
	
	/**
	 * @param messageId the messageId to set
	 * @return 
	 */
	public MessageTemplate withMessageId(final String messageId) {
		this.messageId = messageId;
		return this;
	}
	
	/**
	 * @return the intercept
	 */
	public boolean isIntercept() {
		return intercept;
	}
	
	/**
	 * @param intercept the intercept to set
	 * @return 
	 */
	public MessageTemplate withIntercept(final boolean intercept) {
		this.intercept = intercept;
		return this;
	}
	
	
}
