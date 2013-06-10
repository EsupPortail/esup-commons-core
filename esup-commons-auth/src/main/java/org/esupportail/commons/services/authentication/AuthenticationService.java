/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.authentication;

import java.io.Serializable;

import org.esupportail.commons.services.authentication.info.AuthInfo;

/** 
 * The interface of the service used to get authentication.
 */
public interface AuthenticationService extends Serializable {
	
	/**
	 * @return true if enabled.
	 */
	boolean isEnabled();
	
	/**
	 * @return the current auth.
	 */
	AuthInfo getAuthInfo();
	
}

