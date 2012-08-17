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

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#setParameters(
	 * java.lang.Throwable)
	 */
	public void setParameters(
			final Throwable t) {
		throwable = t;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#handleException()
	 */
	public void handleException() throws ExceptionHandlingException {
		logger.error(throwable);
		throw new ExceptionHandlingException(throwable);
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getApplicationName()
	 */
	public String getApplicationName() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getApplicationVersion()
	 */
	public Version getApplicationVersion() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getClient()
	 */
	public String getClient() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getCookies()
	 */
	public Set<String> getCookies() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getDate()
	 */
	public Long getDate() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getThrowable()
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getPortal()
	 */
	public String getPortal() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getQueryString()
	 */
	public String getQueryString() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getQuickStart()
	 */
	public Boolean getQuickStart() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getRecipientEmail()
	 */
	public String getRecipientEmail() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getRequestHeaders()
	 */
	public Set<String> getRequestHeaders() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getRequestParameters()
	 */
	public Set<String> getRequestParameters() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getServer()
	 */
	public String getServer() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getSessionAttributes()
	 */
	public Set<String> getSessionAttributes() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getGlobalSessionAttributes()
	 */
	public Set<String> getGlobalSessionAttributes() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getRequestAttributes()
	 */
	public Set<String> getRequestAttributes() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getSystemProperties()
	 */
	public Set<String> getSystemProperties() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getUserAgent()
	 */
	public String getUserAgent() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getUserId()
	 */
	public String getUserId() {
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.exceptionHandling.ExceptionService#getExceptionView()
	 */
	public String getExceptionView() {
		// never called since handleException() always throws an throwable
		return null;
	}

}
