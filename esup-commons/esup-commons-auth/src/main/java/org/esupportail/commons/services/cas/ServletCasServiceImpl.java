/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.cas;

import org.esupportail.commons.utils.ContextUtils;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.InitializingBean;

/** 
 * The implementation of CasService for setvlet.
 */
public class ServletCasServiceImpl implements InitializingBean, CasService {
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3376709081615445608L;

	/**
	 * Bean constructor.
	 */
	public ServletCasServiceImpl() {
		//nothing to do
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		// nothing to check
	}

	/**
	 * @see org.esupportail.commons.services.cas.CasService#getProxyTicket(java.lang.String)
	 */
	public String getProxyTicket(final String targetService) throws CasException {
		Assertion assertion = (Assertion) ContextUtils.getGlobalSessionAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		String ret = assertion.getPrincipal().getProxyTicketFor(targetService);
		return ret;
	}

	/**
	 * @see org.esupportail.commons.services.cas.CasService#validate()
	 */
	public void validate() throws CasException {
		// nothing to validate
	}
	
}
