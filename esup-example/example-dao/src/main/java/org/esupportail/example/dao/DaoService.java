/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-blank
 */
package org.esupportail.example.dao;

import java.io.Serializable;
import java.util.List;

import org.esupportail.commons.services.application.Version;
import org.esupportail.example.domain.beans.User;
import org.esupportail.example.domain.beans.VersionManager;

/**
 * The DAO service interface.
 */
public interface DaoService extends Serializable {

	//////////////////////////////////////////////////////////////
	// User
	//////////////////////////////////////////////////////////////
	
	/**
	 * @param id
	 * @return the User instance that corresponds to an id.
	 */
	User getUser(String id);

	/**
	 * @return the list of all the users.
	 */
	List<User> getUsers();

	/**
	 * Add a user.
	 * @param user
	 */
	void addUser(User user);

	/**
	 * Delete a user.
	 * @param user
	 */
	void deleteUser(User user);

	/**
	 * Update a user.
	 * @param user
	 */
	void updateUser(User user);

	//////////////////////////////////////////////////////////////
	// Version
	//////////////////////////////////////////////////////////////

	/**
	 * Get application version
	 * @return Version
	 */
	VersionManager getVersionManager();

	/**
	 * Set application version
	 * @param version
	 */
	void addVersionManager(VersionManager versionManager);

	/**
	 * remove application version
	 * @param version
	 */
	void deleteVersionManager();

}
