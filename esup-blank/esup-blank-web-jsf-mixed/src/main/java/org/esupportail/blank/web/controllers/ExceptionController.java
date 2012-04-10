/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.web.controllers;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpSession;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.apache.myfaces.trinidad.util.ExternalContextUtils;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.utils.BeanUtils;
import org.esupportail.commons.utils.ContextUtils;
import org.esupportail.commons.web.controllers.Resettable;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public class ExceptionController extends
		org.esupportail.commons.web.controllers.ExceptionController {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -8358372071814470805L;
	/**
	 * For Logging.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * True if we are in portlet mode.
	 */
	private boolean portletMode;

	/**
	 * Constructors.
	 */
	public ExceptionController() {
		super();
	}

	/**
	 * @return true if portlet mode.
	 */
	private boolean isPortletMode() {
		if (logger.isDebugEnabled()) {
			logger.debug("Mode detected in Application");
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		portletMode = ExternalContextUtils.isPortlet(fc.getExternalContext());
		if (logger.isDebugEnabled()) {
			if (portletMode) {
				logger.debug("Portlet mode detected");
			} else {
				logger.debug("Servlet mode detected");
			}
		}
		return portletMode;
	}

	@Override
	public String restart() {
		Map<String, Object> resettables = BeanUtils
				.getBeansOfClass(Resettable.class);
		for (String name : resettables.keySet()) {
			if (logger.isDebugEnabled()) {
				logger.debug("trying to reset bean [" + name + "]...");
			}
			Object bean = resettables.get(name);
			if (bean == null) {
				throw new ConfigException("bean [" + name + "] is null, "
						+ "application can not be restarted.");
			}
			if (!(bean instanceof Resettable)) {
				throw new ConfigException("bean [" + name
						+ "] does not implement Resettable, "
						+ "application can not be restarted.");
			}
			((Resettable) bean).reset();
			if (logger.isDebugEnabled()) {
				logger.debug("bean [" + name + "] was reset.");
			}
		}
		if (!isPortletMode()) {
			// it is always this case !
			ExceptionUtils.unmarkExceptionCaught();
			((HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false)).invalidate();
		}
		return "applicationRestarted";
	}

	/**
	 * @return true if an exception have been detected.
	 */
	public boolean isException() {
		if (isPortletMode()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			PortletRequest request = (PortletRequest) externalContext
					.getRequest();
			ContextUtils.bindRequestAndContext(request,
					(PortletContext) externalContext.getContext());
		}
		return ExceptionUtils.getMarkedExceptionService() != null;
	}

}
