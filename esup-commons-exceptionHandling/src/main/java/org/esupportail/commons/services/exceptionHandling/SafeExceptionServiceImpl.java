/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.exceptionHandling;
 
import java.util.Set;

import org.esupportail.commons.exceptions.ExceptionHandlingException;
import org.esupportail.commons.services.application.Version;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * A safe implementation of ExceptionService, that just logs the throwable.
 */
public class SafeExceptionServiceImpl implements ExceptionService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 7162199100621969582L;

	/**
	 * The throwable caught.
	 */
	private Throwable throwable;
	
	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(SafeExceptionServiceImpl.class);
	
	/**
	 * Constructor.
	 */
	public SafeExceptionServiceImpl() {
		super();
	}

	@Override
	public void setParameters(
			final Throwable t) {
		throwable = t;
	}

	@Override
	public void handleException() throws ExceptionHandlingException {
		logger.error(throwable);
		throw new ExceptionHandlingException(throwable);
	}

	@Override
	public String getApplicationName() {
		return null;
	}

	@Override
	public Version getApplicationVersion() {
		return null;
	}

	@Override
	public String getClient() {
		return null;
	}

	@Override
	public Set<String> getCookies() {
		return null;
	}

	@Override
	public Long getDate() {
		return null;
	}

	@Override
	public Throwable getThrowable() {
		return throwable;
	}

	@Override
	public String getPortal() {
		return null;
	}

	@Override
	public String getQueryString() {
		return null;
	}

	@Override
	public Boolean getQuickStart() {
		return null;
	}

	@Override
	public String getRecipientEmail() {
		return null;
	}

	@Override
	public Set<String> getRequestHeaders() {
		return null;
	}

	@Override
	public Set<String> getRequestParameters() {
		return null;
	}

	@Override
	public String getServer() {
		return null;
	}

	@Override
	public Set<String> getSessionAttributes() {
		return null;
	}

	@Override
	public Set<String> getGlobalSessionAttributes() {
		return null;
	}

	@Override
	public Set<String> getRequestAttributes() {
		return null;
	}

	@Override
	public Set<String> getSystemProperties() {
		return null;
	}

	@Override
	public String getUserAgent() {
		return null;
	}

	@Override
	public String getUserId() {
		return null;
	}

	@Override
	public String getExceptionView() {
		// never called since handleException() always throws an throwable
		return null;
	}

}
