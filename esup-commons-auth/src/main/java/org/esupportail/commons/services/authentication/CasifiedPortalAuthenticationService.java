/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.authentication;

/**
 * A casified portal authenticator.
 */
public class CasifiedPortalAuthenticationService extends PortalAuthenticationService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3993849015420934582L;

	/**
	 * Bean constructor.
	 */
	public CasifiedPortalAuthenticationService() {
		super();
	}

	@Override
	protected String getAuthType() {
		return AuthUtils.CAS;
	}

	@Override
	public void setAuthType(final String authType) {
		throw new UnsupportedOperationException(
				"method " + getClass() + ".setAuthType() should never be called.");
	}

}
