/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.authentication;

import org.esupportail.commons.services.authentication.utils.ContextUtils;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.context.request.RequestAttributes;

/**
 * A CAS authenticator.
 */
public class CasFilterAuthenticationService extends AbstractRealAuthenticationService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 943489018651202646L;

	/**
	 * Bean constructor.
	 */
	private CasFilterAuthenticationService() {
	}
	
	/**
	 * a static factory method
	 */
	public static CasFilterAuthenticationService createInstance() {
		return new CasFilterAuthenticationService();
	}

	@Override
	protected String getAuthType() {
		return AuthConstante.CAS.value();
	}
		
	/**
	 * @param name
	 * @return The attribute that corresponds to a name.
	 */
	@Override
	protected String getAuthId() {
		Assertion assertion = (Assertion) getContextAttribute(AbstractCasFilter.CONST_CAS_ASSERTION, RequestAttributes.SCOPE_GLOBAL_SESSION);
		if (assertion != null) {
			return assertion.getPrincipal().getName();
		}
		return null;
	}

	/**
	 * @param name 
	 * @param scope 
	 * @return The value of the attribute for a given scope.
	 */
	private static Object getContextAttribute(
			final String name,
			final int scope) {
		return ContextUtils.getContextAttributes().getAttribute(name, scope);
	}
}