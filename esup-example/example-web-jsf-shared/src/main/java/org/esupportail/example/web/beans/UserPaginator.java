/**
 * CRI - Université de Rennes 1 - <nom du projet> - <année>
 * <url de gestion du projet>
 * Version de la norme de développement : <version de ce document> 
 */
package org.esupportail.example.web.beans;

import org.esupportail.commons.dao.HqlQueryPojo;
import org.esupportail.commons.domain.AbstractDomainQueryPaginator;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.web.controllers.Resettable;
import org.esupportail.example.domain.beans.User;

/**
 * @author pthomas
 * 
 */
public class UserPaginator 
	extends AbstractDomainQueryPaginator<User> 
	implements  Resettable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -2537410544373507818L;

	/*
	 * ************************** PROPERTIES ********************************
	 */

	/**
	 * A logger.
	 */
	private static final Logger log = new LoggerImpl(UserPaginator.class);


	/*
	 * ************************** INIT **************************************
	 */

	/**
	 * Constructor.
	 */
	public UserPaginator() {
		super();
	}

	/**
	 * @see org.esupportail.commons.web.beans.AbstractPaginator#reset()
	 */
	
	public void reset() {
		if (log.isDebugEnabled()) {
			log.debug("entering UserPaginator.reset()");
		}
		super.reset();
	}

	/*
	 * ************************** METHODS ***********************************
	 */

	/**
	 * @see org.esupportail.commons.domain.AbstractDomainQueryPaginator#getQueryString()
	 */
	protected String getQueryString() {
		if (log.isDebugEnabled()) {
			log.debug("entering UserPaginator.getQueryString()");
		}
		return "SELECT user FROM User user";
	}

	/**
	 * @see org.esupportail.commons.domain.AbstractDomainQueryPaginator#getHqlQueryPojo()
	 */
	@Override
	protected HqlQueryPojo getHqlQueryPojo() {
		//ESUP-Example n'utilise pas de HqlQueryPojo mais seulement une QueryString
		return null;
	}

	

	/*
	 * ************************** ACCESSORS *********************************
	 */

	

	
}