/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.authentication;



import org.esupportail.commons.utils.ContextUtils;
import org.esupportail.commons.utils.HttpUtils;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

/** 
 * A CAS authenticator.
 */
public class CasFilterAuthenticationService extends AbstractTypedAuthenticationService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 943489018651202646L;

	/**
	 * Bean constructor.
	 */
	public CasFilterAuthenticationService() {
		super();
	}

	/**
	 * @see org.esupportail.commons.services.authentication.AbstractTypedAuthenticationService#getAuthId()
	 */
	@Override
	protected String getAuthId() {
		Assertion assertion = (Assertion) ContextUtils.getGlobalSessionAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if (assertion != null) {
			return assertion.getPrincipal().getName();					
		}
		return null;
	}

	/**
	 * @see org.esupportail.commons.services.authentication.AbstractTypedAuthenticationService#getAuthType()
	 */
	@Override
	protected String getAuthType() {
		return AuthUtils.CAS;
	}

}
