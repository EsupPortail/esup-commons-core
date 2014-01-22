package org.esupportail.commons.mail.model;

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

import static java.util.Arrays.asList;

public final class MessageTemplate implements Serializable {

	private static final long serialVersionUID = -7979409386413697720L;

	private static final String
            STACK_TRACE_SEPARATOR =	"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ",
            STACK_TRACE_CAUSED_BY = "caused by: ";

	private final InternetAddress[] tos, ccs, bccs;
	private final String messageId, subject, htmlBody, textBody;
	private final List<File> files;

    private MessageTemplate(InternetAddress[] tos,
                            InternetAddress[] ccs,
                            InternetAddress[] bccs,
                            String messageId,
                            String subject,
                            String htmlBody,
                            String textBody,
                            List<File> files) {
        this.tos = tos;
        this.ccs = ccs;
        this.bccs = bccs;
        this.messageId = messageId;
        this.subject = subject;
        this.htmlBody = htmlBody;
        this.textBody = textBody;
        this.files = files;
    }

    public static MessageTemplate create(final String subject,
                                         final String htmlBody,
                                         final String textBody,
                                         final InternetAddress... tos) {
		assert tos != null : "recipients of the message cannot be null";
		assert !(htmlBody == null && textBody == null) : "htmlBody and textBody cannot be both null";
        final InternetAddress[] ccs = {}, bccs = {};
        return new MessageTemplate(tos, ccs, bccs, "", subject, htmlBody, textBody, new ArrayList<File>());
	}

	public static MessageTemplate create(final Throwable throwable, final InternetAddress... tos) {
		final Throwable cause = throwable.getCause();
		final StringBuilder titre = new StringBuilder(getVersion());
		if (cause != null)
            titre.append(" ").append(cause.getMessage());
        else
            titre.append(" ").append("Exception");

		titre.append(" sur ").append(getServer());
		return create(titre.toString(), "", getPrintableStackTrace(throwable), tos);
	}

	/**
	 * @return A list of strings that correspond to the stack trace of a
	 *         throwable.
	 */
	private static List<String> getStackTraceStrings(final Throwable t) {
		final List<String> result = new ArrayList<>();
		result.add(t.toString());
		for (StackTraceElement element : t.getStackTrace())
            result.add(element.toString());
		final Throwable cause = t.getCause();
		if (cause != null) {
			result.add(STACK_TRACE_SEPARATOR + STACK_TRACE_CAUSED_BY);
			result.addAll(getStackTraceStrings(cause));
		}
		return result;
	}

	/**
	 * @return A printable form of the stack trace of an exception.
	 */
	private static String getPrintableStackTrace(final Throwable t) {
		final StringBuilder sb = new StringBuilder();
		final List<String> strings = getStackTraceStrings(t);
		String separator = "";
		for (String string : strings) {
			sb.append(separator).append(string);
			separator = "\n";
		}
		return sb.toString();
	}
	
	private static String getVersion() {
		final String version = System.getProperty("${project.version}");
		return version != null ? version : "";
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
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageTemplate that = (MessageTemplate) o;

        if (!Arrays.equals(bccs, that.bccs)) return false;
        if (!Arrays.equals(ccs, that.ccs)) return false;
        if (files != null ? !files.equals(that.files) : that.files != null) return false;
        if (htmlBody != null ? !htmlBody.equals(that.htmlBody) : that.htmlBody != null) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        if (textBody != null ? !textBody.equals(that.textBody) : that.textBody != null) return false;
        if (!Arrays.equals(tos, that.tos)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tos != null ? Arrays.hashCode(tos) : 0;
        result = 31 * result + (ccs != null ? Arrays.hashCode(ccs) : 0);
        result = 31 * result + (bccs != null ? Arrays.hashCode(bccs) : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (htmlBody != null ? htmlBody.hashCode() : 0);
        result = 31 * result + (textBody != null ? textBody.hashCode() : 0);
        result = 31 * result + (files != null ? files.hashCode() : 0);
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        return result;
    }

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("MessageTemplate(");
		if (tos != null && !asList(tos).isEmpty())
			builder.append("tos=").append(Arrays.toString(tos)).append(", ");
		if (ccs != null && !asList(ccs).isEmpty())
			builder.append("ccs=").append(Arrays.toString(ccs)).append(", ");
		if (bccs != null && !asList(bccs).isEmpty())
			builder.append("bccs=").append(Arrays.toString(bccs)).append(", ");
		if (subject != null)
			builder.append("subject=").append(subject).append(", ");
		if (htmlBody != null)
			builder.append("htmlBody=").append(htmlBody).append(", ");
		if (textBody != null)
			builder.append("textBody=").append(textBody).append(", ");
		if (files != null && !files.isEmpty())
			builder.append("files=").append(files).append(")");
		return builder.toString();
	}

	public InternetAddress[] getTos() {
		return tos;
	}

	public MessageTemplate withTos(final InternetAddress... tos) {
		return new MessageTemplate(tos, ccs, bccs, messageId, subject, htmlBody, textBody, files);
	}

	public InternetAddress[] getCcs() {
		return ccs;
	}

	public MessageTemplate withCcs(final InternetAddress... ccs) {
        return new MessageTemplate(tos, ccs, bccs, messageId, subject, htmlBody, textBody, files);
	}

	public InternetAddress[] getBccs() {
		return bccs;
	}

	public MessageTemplate withBccs(final InternetAddress... bccs) {
        return new MessageTemplate(tos, ccs, bccs, messageId, subject, htmlBody, textBody, files);
	}

    public String getMessageId() {
        return messageId;
    }

    public MessageTemplate withMessageId(final String messageId) {
        return new MessageTemplate(tos, ccs, bccs, messageId, subject, htmlBody, textBody, files);
    }

	public String getSubject() {
		return subject;
	}

	public MessageTemplate withSubject(final String subject) {
        return new MessageTemplate(tos, ccs, bccs, messageId, subject, htmlBody, textBody, files);
	}

	public String getHtmlBody() {
		return htmlBody;
	}

	public MessageTemplate withHtmlBody(final String htmlBody) {
        return new MessageTemplate(tos, ccs, bccs, messageId, subject, htmlBody, textBody, files);
	}

	public String getTextBody() {
		return textBody;
	}

	public MessageTemplate withTextBody(final String textBody) {
        return new MessageTemplate(tos, ccs, bccs, messageId, subject, htmlBody, textBody, files);
	}

	public List<File> getFiles() {
		return files;
	}

	public MessageTemplate withFiles(final List<File> files) {
        return new MessageTemplate(tos, ccs, bccs, messageId, subject, htmlBody, textBody, files);
	}

	public MessageTemplate withFiles(final File... files) {
        return withFiles(asList(files));
	}
}

