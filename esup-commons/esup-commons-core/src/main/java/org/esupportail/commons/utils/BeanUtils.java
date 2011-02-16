/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.utils; 

import java.util.Map;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.exceptions.ConfigException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * A class to bind a beanFocatry to the current thread and retrieve beans.
 */
public class BeanUtils {   
	
	/**
	 * The configuration file where Spring beans are defined.
	 */
	protected static final String SPRING_CONFIG_FILE = "/properties/applicationContext.xml"; 

	/**
	 * The bean factory used for this thread.
	 */
	protected static final ThreadLocal<ApplicationContext> CONTEXT_HOLDER = new ThreadLocal<ApplicationContext>();

	/**
	 * Private constructor.
	 */
	protected BeanUtils() {
		throw new UnsupportedOperationException();
	}

	
	
	/**
	 * Initialize the bean factory using a given config file.
	 * @param configFile
	 */
	public static void initBeanFactory(final String configFile) {
		CONTEXT_HOLDER.set(new FileSystemXmlApplicationContext("classpath:" + configFile));
	}

	/**
	 * @return the bean factory for the thread.
	 */
	protected static BeanFactory getBeanFactory() {
		if (ApplicationContextHolder.getContext() == null) {
			throw new ConfigException("vous devez ajouter le bean ApplicationContextHolder dans votre fichier application.xml");
			//initBeanFactory(SPRING_CONFIG_FILE);
		}
		return ApplicationContextHolder.getContext();
	}

	/**
	 * Get a bean.
	 * @param name the name of the bean
	 * @return a bean.
	 * @throws ConfigException
	 */
	public static Object getBean(
			final String name) throws ConfigException {
		try {
			return getBeanFactory().getBean(name);
		} catch (BeansException e) {
			throw new ConfigException(e);
		}
	}

	/**
	 * @param type 
	 * @return the beans of a given type.
	 * @throws ConfigException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getBeansOfClass(
			final Class type) throws ConfigException {
		BeanFactory beanFactory = getBeanFactory();
		if (!(beanFactory instanceof ListableBeanFactory)) {
			throw new ConfigException(
					"bean factory is not an instance of ListableBeanFactory (" 
					+ beanFactory.getClass() + ")");
		}
		return BeanFactoryUtils.beansOfTypeIncludingAncestors(
				(ListableBeanFactory) beanFactory, type);
	}

}
