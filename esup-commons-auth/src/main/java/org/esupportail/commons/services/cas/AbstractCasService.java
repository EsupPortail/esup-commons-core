/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.cas;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;

/** 
 * An abstract implementation of CasService; inheriting classes 
 * should simply implement getProxyGrantingTicket() (each implementation
 * has its own way to know what the PGT is).
 */
@SuppressWarnings("serial")
public abstract class AbstractCasService implements CasService {

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());
	
	/**
	 * The CAS validate URL.
	 */
	private String casValidateUrl;

	/**
	 * The URL of the service itself.
	 */
	private String service;

	/**
	 * The proxy callback URL.
	 */
	private String proxyCallbackUrl;
	
	/**
	 * Bean constructor.
	 */
	public AbstractCasService() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.hasText(casValidateUrl, "property [casValidateUrl] of class [" 
				+ getClass() + "] can not be null");
		Assert.hasText(service, "property [service] of class [" 
				+ getClass() + "] can not be null");
		Assert.hasText(proxyCallbackUrl, "property [proxyCallbackUrl] of class [" 
				+ getClass() + "] can not be null");
	}
	
	/**
	 * @return the service ticket that was passed to the application.
	 */
	protected abstract String getServiceTicket();

	/**
	 * @see org.esupportail.commons.services.cas.CasService#getProxyTicket(java.lang.String)
	 */
	abstract public String getProxyTicket(final String targetService) throws CasException;

	/**
	 * @param casValidateUrl the casValidateUrl to set
	 */
	public void setCasValidateUrl(final String casValidateUrl) {
		this.casValidateUrl = casValidateUrl;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(final String service) {
		this.service = service;
	}

	/**
	 * @param proxyCallbackUrl the proxyCallbackUrl to set
	 */
	public void setProxyCallbackUrl(final String proxyCallbackUrl) {
		this.proxyCallbackUrl = proxyCallbackUrl;
	}
	
}
