/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.utils; 

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.portlet.PortletRequest;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * A class that provides facilities with the context.
 */
public class JpaUtils {
	
	public static final String DEFAULT_PERSISTENCE_MANAGER_FACTORY_BEAN_NAME = "entityManagerFactory";

	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(JpaUtils.class);

	/**
	 * Un marqueur pour stocker le témoin de participation à une transaction JPA dans la requête
	 */
	private static final String REQUEST_JPA_PARTICIPATE_ATTRIBUTE =
		JpaUtils.class.getName() + ".REQUEST_JPA_PARTICIPATE";	

	/**
	 * Un marqueur pour stocker le manager d'entités JPA dans la requête
	 */
	private static final String REQUEST_JPA_ENTITY_MANAGER_ATTRIBUTE =
		JpaUtils.class.getName() + ".REQUEST_JPA_ENTITY_MANAGER";	

	/**
	 * Private constructor.
	 */
	private JpaUtils() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * expose the JPA Entity Manager to the current thread
	 * @param request
	 */
	public static void exposeEntityManager(PortletRequest request) {
		//use code form doFilterInternal of Spring OpenEntityManagerInViewFilter	
		EntityManagerFactory emf = getEntityManagerFactory();
		request.setAttribute(REQUEST_JPA_ENTITY_MANAGER_ATTRIBUTE, emf);
		Boolean participate = false;

		if (TransactionSynchronizationManager.hasResource(emf)) {
			participate = true;
		}
		else {
			logger.debug("Opening JPA EntityManager in exposeEntityManager");
			try {
				EntityManager em = emf.createEntityManager();
				TransactionSynchronizationManager.bindResource(emf, new EntityManagerHolder(em));
			}
			catch (PersistenceException ex) {
				throw new DataAccessResourceFailureException("Could not create JPA EntityManager", ex);
			}
		}
		request.setAttribute(REQUEST_JPA_PARTICIPATE_ATTRIBUTE, participate);
	}

	/**
	 * @return EntityManagerFactory registered in Spring configuration
	 */
	private static EntityManagerFactory getEntityManagerFactory() {
		return ApplicationContextHolder.getContext().getBean(DEFAULT_PERSISTENCE_MANAGER_FACTORY_BEAN_NAME, EntityManagerFactory.class);
	}
	
	/**
	 * unexpose the JPA Entity Manager from the current thread
	 * @param request
	 */
	public static void unexposeEntityManager(PortletRequest request) {
		//use code form doFilterInternal of Spring OpenEntityManagerInViewFilter
		Boolean participate =
			(Boolean) request.getAttribute(REQUEST_JPA_PARTICIPATE_ATTRIBUTE);
		if (!participate) {
			EntityManagerFactory emf = (EntityManagerFactory) request.getAttribute(REQUEST_JPA_ENTITY_MANAGER_ATTRIBUTE);
			EntityManagerHolder emHolder = (EntityManagerHolder)
					TransactionSynchronizationManager.unbindResource(emf);
			logger.debug("Closing JPA EntityManager in unexposeEntityManager");
			EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
		}
	}
	
}
