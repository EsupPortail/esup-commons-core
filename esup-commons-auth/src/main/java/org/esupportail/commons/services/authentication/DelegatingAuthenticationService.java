/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.authentication;

import java.util.ArrayList;
import java.util.List;

import org.esupportail.commons.services.authentication.info.AuthInfo;

/**
 * An abstract typed authenticator.
 */
public class DelegatingAuthenticationService extends AbstractAuthenticationService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -163359171428098265L;

	/**
	 * The authenticators to delegate.
	 */
	private List<AuthenticationService> authenticationServices;

	/**
	 * Bean constructor.
	 */
	private DelegatingAuthenticationService() {
		this.authenticationServices = new ArrayList<AuthenticationService>();
	}
	
	/**
	 * Bean constructor .
	 */
	private DelegatingAuthenticationService(List<AuthenticationService> authenticationServices) {
		this.authenticationServices = new ArrayList<AuthenticationService>();
		if(authenticationServices != null) {
			for (AuthenticationService authenticationService : authenticationServices) {
				if (authenticationService.isEnabled()) {
					this.authenticationServices.add(authenticationService);
				}
			}			
		}

	}
	
	/**
	 * a static factory method
	 */
	public static DelegatingAuthenticationService createInstance(List<AuthenticationService> authenticationServices) {
		return new DelegatingAuthenticationService(authenticationServices);
	}

//	@Override
//	public void afterPropertiesSet() {
//		super.afterPropertiesSet();
//		if (authenticationServices == null || authenticationServices.isEmpty()) {
//			logger.warn("no authenticator set or enabled!");
//		}
//	}

	@Override
	public AuthInfo getAuthInfo() {
		for (AuthenticationService authenticationService : authenticationServices) {
			AuthInfo authInfo = authenticationService.getAuthInfo();
			if (authInfo != null) {
				return authInfo;
			}
		}
		return null;
	}

//	/**
//	 * @return the authenticators
//	 */
//	public List<AuthenticationService> getAuthenticationServices() {
//		return authenticationServices;
//	}
//
//	/**
//	 * @param authenticationServices the authenticationServices to set
//	 */
//	public void setAuthenticationServices(final List<AuthenticationService> authenticationServices) {
//		this.authenticationServices = new ArrayList<AuthenticationService>();
//		for (AuthenticationService authenticationService : authenticationServices) {
//			if (authenticationService.isEnabled()) {
//				this.authenticationServices.add(authenticationService);
//			}
//		}
//	}

}