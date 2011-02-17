/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.domain;

import java.util.ArrayList;
import java.util.List;

import org.esupportail.blank.domain.beans.User;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Yves Deschamps (Universit� de Lille 1) - 2010
 * 
 */
public class DomainServiceImpl implements DomainService, InitializingBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 5562208937407153456L;

	/**
	 * 
	 */
	private final Logger logger = new LoggerImpl(this.getClass());

	// En l'absence de Dao et de Ldap, on constitue ici une liste... limit�e de
	// fait � l'utilisateur courant.
	private List<User> users;

	/**
	 * Constructor.
	 */
	public DomainServiceImpl() {
		super();
		users = new ArrayList<User>();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		// nothing to do yet.
	}

	/**
	 * @see org.esupportail.blank.domain.DomainService#getUser(java.lang.String)
	 */
	public User getUser(String uid) {
		User user = null;
		for (User userInList : users) {
			if (userInList.getId().equals(uid)) {
				user = userInList;
				break;
			}
		}
		if (user == null) {
			user = new User();
			user.setId(uid);
			// On cr�e l'utilisateur, son nom complet prend la valeur de l'Uid.
			user.setDisplayName(uid);
			user.setLanguage("fr");
			user.setAccessibilityMode("default");
			users.add(user);
		}
		return user;
	}

}
