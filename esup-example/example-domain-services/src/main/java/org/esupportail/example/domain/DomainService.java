/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-blank
 */
package org.esupportail.example.domain;

import java.io.Serializable;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.application.Version;
import org.esupportail.example.domain.beans.User;

/**
 * The domain service interface.
 */
@WebService
@Path("/domainService/")
@Produces("application/json")
public interface DomainService extends Serializable {

	//////////////////////////////////////////////////////////////
	// User
	//////////////////////////////////////////////////////////////

	/**
	 * @param id
	 * @return the User instance that corresponds to an id.
	 * @throws UserNotFoundException
	 */
	@GET
	@Path("/users/{id}")
	User getUser(@PathParam("id") String id) throws UserNotFoundException;

	/**
	 * @return the list of all the users.
	 */
	@GET
	@Path("/users")
	List<User> getUsers();

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

	/**
	 * Get the application version stored in database
	 * @return the current version stored in database
	 */
	Version getDatabaseVersion();

	/**
	 * Persit the application version in database
	 * @param version
	 */
	void updateDatabaseVersion(String version);

}
