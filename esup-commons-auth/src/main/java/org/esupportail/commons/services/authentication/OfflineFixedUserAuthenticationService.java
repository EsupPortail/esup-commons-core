/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.authentication;

import org.springframework.util.Assert;

/**
 * This authenticator is to be used when working offline, it always returns the same user.
 */
public class OfflineFixedUserAuthenticationService extends AbstractRealAuthenticationService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -7500234316883442570L;

	/**
	 * The id of the fixed user.
	 */
	private String authId;

	/**
	 * The authentication type.
	 */
	private String authType;

	/**
	 * Bean constructor.
	 */
	private OfflineFixedUserAuthenticationService() {
	}

	/**
	 * Bean constructor .
	 */
	private OfflineFixedUserAuthenticationService(String authId, String authType) {
		Assert.hasText(authId, "property authId of class " + this.getClass().getName()
				+ " can not be null or empty");
		Assert.hasText(authType, "property authType of class " + this.getClass().getName()
				+ " can not be null or empty");		
		this.authId = authId;
		this.authType = authType;
	}
	
	/**
	 * a static factory method
	 */
	public static OfflineFixedUserAuthenticationService createInstance(String authId, String authType) {
		return new OfflineFixedUserAuthenticationService(authId, authType);
	}

	@Override
	protected String getAuthId() {
		return authId;
	}


	@Override
	protected String getAuthType() {
		return authType;
	}

}
