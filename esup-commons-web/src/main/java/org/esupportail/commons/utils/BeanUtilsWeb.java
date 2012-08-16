/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.utils; 

import java.util.Map;

import javax.portlet.PortletContext;
import javax.servlet.ServletContext;

import org.esupportail.commons.exceptions.ConfigException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.portlet.context.PortletApplicationContextUtils;

/**
 * A class to bind a beanFocatry to the current thread and retrieve beans.
 */
@Deprecated
public final class BeanUtilsWeb extends BeanUtils {   
	

	/**
	 * Private constructor.
	 */
	private BeanUtilsWeb() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Set the bean factory from a servlet context.
	 * @param servletContext
	 */
	public static void initBeanFactory(final ServletContext servletContext) {
		CONTEXT_HOLDER.set(
				WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext));
	}

	/**
	 * Set the bean factory from a portlet context.
	 * @param portletContext
	 */
	public static void initBeanFactory(final PortletContext portletContext) {
		CONTEXT_HOLDER.set(
				PortletApplicationContextUtils.getRequiredWebApplicationContext(portletContext));
	}
	
	

}
