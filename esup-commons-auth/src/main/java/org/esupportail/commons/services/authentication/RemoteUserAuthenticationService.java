/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.authentication;

import javax.servlet.http.HttpServletRequest;

import org.esupportail.commons.services.authentication.utils.ContextUtils;

import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * A RemoteUser authenticator.
 */
public class RemoteUserAuthenticationService extends AbstractRealAuthenticationService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3616080509231674818L;

	/**
	 * The authentication type.
	 */
	private String authType;
	
	/**
	 * Bean constructor.
	 */
	private RemoteUserAuthenticationService() {
	}

	/**
	 * Bean constructor.
	 */
	private RemoteUserAuthenticationService(String authType) {
		Assert.hasText(authType, "property authType of class " + this.getClass().getName()
				+ " can not be null or empty");
		this.authType = authType;
	}
	
	/**
	 * a static factory method
	 */
	public static RemoteUserAuthenticationService createInstance(String authType) {
		return new RemoteUserAuthenticationService(authType);
	}
	
	@Override
	public String getAuthId() {
		HttpServletRequest request = ((ServletRequestAttributes) ContextUtils.getContextAttributes()).getRequest();
		return request.getRemoteUser();
	}

	@Override
	protected String getAuthType() {
		return authType;
	}
}
