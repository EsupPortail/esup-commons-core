package org.esupportail.example.services.authentication;

import org.esupportail.example.domain.beans.User;


/**
 * The interface of authenticators.
 */
public interface Authenticator {

	/**
	 * @return the authenticated user.
	 */
	User getUser();

}