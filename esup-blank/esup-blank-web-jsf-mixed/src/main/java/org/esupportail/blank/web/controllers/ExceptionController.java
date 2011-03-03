/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.web.controllers;

import org.esupportail.commons.services.exceptionHandling.ExceptionService;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * The name of the session attribute set to prevent from infite redirections.
	 */
	private boolean exception;

	/**
	 * @return the exceptionService that was stored in session when the
	 *         exception was thrown.
	 */
	private ExceptionService getExceptionService() {
		return ExceptionUtils.getMarkedExceptionService();
	}

	/**
	 * Constructors.
	 */
	public ExceptionController() {
		super();
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
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false)).invalidate();
		ExceptionUtils.unmarkExceptionCaught();
		exception = false;
		return "applicationRestarted";
	}

	public boolean isException() {
		return exception;
	}

	public void setException(boolean exception) {
		this.exception = exception;		
	}

}
