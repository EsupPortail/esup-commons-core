package org.esupportail.blank.web.controllers;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.utils.BeanUtils;

public class ExceptionController extends
		org.esupportail.commons.web.controllers.ExceptionController {

	/**
	 * Constructors.
	 */
	public ExceptionController() {
		super();
	}

	@Override
	public String restart() {
		SessionController sessionController = (SessionController) BeanUtils.getBean("sessionController");
		sessionController.reset();
		PreferencesController preferencesController = (PreferencesController) BeanUtils.getBean("preferencesController");
		preferencesController.reset();
		((HttpSession) FacesContext.getCurrentInstance()
				   .getExternalContext().getSession(false)).invalidate();	
		ExceptionUtils.unmarkExceptionCaught();
		return "applicationRestarted";
	}

}
