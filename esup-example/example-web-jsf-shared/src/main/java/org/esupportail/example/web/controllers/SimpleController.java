/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-blank
 */
package org.esupportail.example.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.example.domain.beans.User;
import org.esupportail.example.web.utils.NavigationRulesConst;


/**
 * A visual bean for the welcome page.
 */
public class SimpleController  extends AbstractContextAwareController {

	/*
	 ******************* PROPERTIES ******************** */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -239570715531002003L;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(this.getClass());

	/**
	 * Name.
	 */
	private String name;
	
	/**
	 * idUser.
	 */
	private String idUser;
	


	/*
	 ******************* INIT ******************** */

	/**
	 * Bean constructor.
	 */
	public SimpleController() {
		super();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + hashCode();
	}

	/**
	 * @see org.esupportail.example.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		name = null;
		idUser = null;
		
	}

	/**
	 * @see org.esupportail.example.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
	}

	/*
	 ******************* CALLBACK ******************** */

	/**
	 * @return String
	 */
	public String goAjaxDemo() {
		if (logger.isDebugEnabled()) {
			logger.debug("entering goAjaxDemo return " + NavigationRulesConst.AJAX_DEMO);
		}
		return NavigationRulesConst.AJAX_DEMO;
	}


	/*
	 ******************* METHODS ******************** */


	/* **********************************
	 * BEGIN TO DEMO AJAX
	 ************************************ */
	
	/**
	 * @param query
	 * @return List of id User
	 */
	public List<String> complete(final String query) {  
		List<String> results = new ArrayList<String>();  

		for (User u : getAllUsers()) { 
			if (u.getId().startsWith(query)) {
				results.add(u.getId());
			}
		}  

		return results;  
	} 

	
	
	/* **********************************
	 * END TO DEMO AJAX
	 ************************************ */
	
	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return User
	 */
	public List<User> getAllUsers() {
		return getDomainService().getUsers();
	}
	


	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the idUser
	 */
	public String getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(final String idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
