/**
 * ESUP-Portail Example Application - Copyright (c) 2011 ESUP-Portail consortium.
 */
package org.esupportail.example.web.jsf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2011
 * 
 */
public class ContentCaptureServletResponse extends HttpServletResponseWrapper {

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * The content buffer.
	 */
	private ByteArrayOutputStream contentBuffer;

	/**
	 * The writer.
	 */
	private PrintWriter writer;

	/**
	 * @param response
	 */
	public ContentCaptureServletResponse(HttpServletResponse response) {
		super(response);
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer == null) {
			contentBuffer = new ByteArrayOutputStream();
			writer = new PrintWriter(contentBuffer);
		}
		return writer;
	}

	/**
	 * @return the content.
	 */
	public String getContent() {
		writer.flush();
		String xhtmlContent = new String(contentBuffer.toByteArray());
		return xhtmlContent;
	}
	
}
