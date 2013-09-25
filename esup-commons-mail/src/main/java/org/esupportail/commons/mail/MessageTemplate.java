package org.esupportail.commons.mail;

import java.io.File;
import java.io.Serializable;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.mail.internet.InternetAddress;

public class MessageTemplate implements Serializable {

	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = -7979409386413697720L;

	/**
	 * The text separator for the stack trace.
	 */
	private static final String STACK_TRACE_SEPARATOR = 
			"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ";

	/**
	 * The "caused by" element.
	 */
	private static final String STACK_TRACE_CAUSED_BY = "caused by: ";

	private InternetAddress[] tos;
	private InternetAddress[] ccs;
	private InternetAddress[] bccs;
	private String subject;
	private String htmlBody;
	private String textBody;
	private List<File> files = new ArrayList<>();
	private String messageId;
	private boolean intercept = true;

	protected MessageTemplate() {
	}

	public static final MessageTemplate createInstance(final String subject,
			final String htmlBody, final String textBody,
			final InternetAddress... tos) {
		assert tos != null : "recipients of the message cannot be null";
		assert !(htmlBody == null && textBody == null) : 
			"htmlBody and textBody cannot be both null";
		return new MessageTemplate().withTos(tos).withSubject(subject)
				.withHtmlBody(htmlBody).withTextBody(textBody);
	}

	public static final MessageTemplate createInstance(
			final Throwable throwable, final InternetAddress... tos) {
		final Throwable cause = throwable.getCause();
		final StringBuffer titre = new StringBuffer(getVersion());
		if (cause != null) {
			titre.append(" ").append(cause.getMessage());
		} else {
			titre.append(" ").append("Exception");
		}
		titre.append(" sur ").append(getServer());
		
		return createInstance(titre.toString(), null,
				getPrintableStackTrace(throwable), tos);
	}

	/**
	 * @return A list of strings that correspond to the stack trace of a
	 *         throwable.
	 * @param t
	 */
	private static List<String> getStackTraceStrings(final Throwable t) {
		List<String> result = new ArrayList<String>();
		result.add(t.toString());
		for (StackTraceElement element : t.getStackTrace()) {
			result.add(element.toString());
		}
		Throwable cause = t.getCause();
		if (cause != null) {
			result.add(STACK_TRACE_SEPARATOR + STACK_TRACE_CAUSED_BY);
			result.addAll(getStackTraceStrings(cause));
		}
		return result;
	}

	/**
	 * @return A printable form of the stack trace of an exception.
	 * @param t
	 */
	private static String getPrintableStackTrace(final Throwable t) {
		StringBuffer sb = new StringBuffer();
		List<String> strings = getStackTraceStrings(t);
		String separator = "";
		for (String string : strings) {
			sb.append(separator).append(string);
			separator = "\n";
		}
		return sb.toString();
	}
	
	private static String getVersion() {
		final String version = System.getProperty("${project.version}");
		if (version != null) {
			return version;
		}
		return "";
	}
	
	private static String getServer() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
			    NetworkInterface current = interfaces.nextElement();
			    if (!current.isUp() || current.isLoopback() || current.isVirtual()) continue;
			    Enumeration<InetAddress> addresses = current.getInetAddresses();
			    while (addresses.hasMoreElements()) {
			        InetAddress currentAddr = addresses.nextElement();
			        if (currentAddr.isLoopbackAddress() || 
			        		currentAddr instanceof Inet6Address) continue;
			        return currentAddr.getHostAddress();
			    }
			}
			return InetAddress.getLocalHost().getHostAddress();
		} catch (SocketException | UnknownHostException e) {
			return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bccs);
		result = prime * result + Arrays.hashCode(ccs);
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		result = prime * result
				+ ((htmlBody == null) ? 0 : htmlBody.hashCode());
		result = prime * result + (intercept ? 1231 : 1237);
		result = prime * result
				+ ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result
				+ ((textBody == null) ? 0 : textBody.hashCode());
		result = prime * result + Arrays.hashCode(tos);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MessageTemplate other = (MessageTemplate) obj;
		if (!Arrays.equals(bccs, other.bccs)) {
			return false;
		}
		if (!Arrays.equals(ccs, other.ccs)) {
			return false;
		}
		if (files == null) {
			if (other.files != null) {
				return false;
			}
		} else if (!files.equals(other.files)) {
			return false;
		}
		if (htmlBody == null) {
			if (other.htmlBody != null) {
				return false;
			}
		} else if (!htmlBody.equals(other.htmlBody)) {
			return false;
		}
		if (intercept != other.intercept) {
			return false;
		}
		if (messageId == null) {
			if (other.messageId != null) {
				return false;
			}
		} else if (!messageId.equals(other.messageId)) {
			return false;
		}
		if (subject == null) {
			if (other.subject != null) {
				return false;
			}
		} else if (!subject.equals(other.subject)) {
			return false;
		}
		if (textBody == null) {
			if (other.textBody != null) {
				return false;
			}
		} else if (!textBody.equals(other.textBody)) {
			return false;
		}
		if (!Arrays.equals(tos, other.tos)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageTemplate [");
		if (tos != null && !Arrays.asList(tos).isEmpty())
			builder.append("tos=").append(Arrays.toString(tos)).append(", ");
		if (ccs != null && !Arrays.asList(ccs).isEmpty())
			builder.append("ccs=").append(Arrays.toString(ccs)).append(", ");
		if (bccs != null && !Arrays.asList(bccs).isEmpty())
			builder.append("bccs=").append(Arrays.toString(bccs)).append(", ");
		if (subject != null)
			builder.append("subject=").append(subject).append(", ");
		if (htmlBody != null)
			builder.append("htmlBody=").append(htmlBody).append(", ");
		if (textBody != null)
			builder.append("textBody=").append(textBody).append(", ");
		if (files != null && !files.isEmpty())
			builder.append("files=").append(files).append(", ");
		builder.append("intercept=").append(intercept).append("]");
		return builder.toString();
	}

	/**
	 * @return the tos
	 */
	public InternetAddress[] getTos() {
		return tos;
	}

	/**
	 * @param tos
	 *            the tos to set
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
	 * @param ccs
	 *            the ccs to set
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
	 * @param bccs
	 *            the bccs to set
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
	 * @param subject
	 *            the subject to set
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
	 * @param htmlBody
	 *            the htmlBody to set
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
	 * @param textBody
	 *            the textBody to set
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
	 * @param files
	 *            the files to set
	 * @return
	 */
	public MessageTemplate withFiles(final List<File> files) {
		if (files != null) {
			this.files = files;
		}
		return this;
	}

	/**
	 * @param files
	 *            the files to set
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
	 * @param messageId
	 *            the messageId to set
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
	 * @param intercept
	 *            the intercept to set
	 * @return
	 */
	public MessageTemplate withIntercept(final boolean intercept) {
		this.intercept = intercept;
		return this;
	}

}
