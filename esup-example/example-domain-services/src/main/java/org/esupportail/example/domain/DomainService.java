/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-blank
 */
package org.esupportail.example.domain;

import java.io.Serializable;
import java.util.List;

import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.example.domain.beans.User;

/**
 * The domain service interface.
 */
public interface DomainService extends Serializable {

	//////////////////////////////////////////////////////////////
	// User
	//////////////////////////////////////////////////////////////

	/**
	 * @param id
	 * @return the User instance that corresponds to an id.
	 * @throws UserNotFoundException
	 */
	User getUser(String id) throws UserNotFoundException;

	/**
	 * @return the list of all the users.
	 */
	List<User> getUsers();

	/**
	 * Update a user.
	 * @param user
	 */
	void updateUser(User user);

	/**
	 * Update a user's information (retrieved from the LDAP directory for instance).
	 * @param user
	 */
	void updateUserInfo(User user);
	
	/**
	 * Add an administrator.
	 * @param user
	 */
	void addAdmin(User user);

	/**
	 * Delete an administrator.
	 * @param user
	 */
	void deleteAdmin(User user);

	/**
	 * Delete an user.
	 * @param user
	 */
	void deleteUser(User user);

	/**
	 * Add an user.
	 * @param user
	 */
	void addUser(User user);

	

}
