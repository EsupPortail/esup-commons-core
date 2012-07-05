/**
 * 
 */
package org.esupportail.commons.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author cleprous
 *
 */
public class ApplicationContextHolder implements ApplicationContextAware {

	
	/*
	 *************************** PROPERTIES ******************************** */
	
	/** Contexte Spring qui sera injecte par Spring directement */
	private static ApplicationContext context;;

	
	/*
	 *************************** INIT ************************************** */
	
	/**
	 * Constructor.
	 */
	public ApplicationContextHolder() {
		super();
	}

	
	
	/*
	 *************************** METHODS *********************************** */
	
	/**
	 * @see org.springframework.context.ApplicationContextAware
	 * #setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(final ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
	

	/**
	 * Methode statique pour récupérer le contexte
	 */
	public static ApplicationContext getContext() {
		return context;
	}

	

	/*
	 *************************** ACCESSORS ********************************* */

}
