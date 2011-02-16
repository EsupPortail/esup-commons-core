/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-blank
 */
package org.esupportail.example.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.web.jsf.tags.TagUtils;
import org.esupportail.example.web.beans.Treatment;


/**
 * A visual bean for the welcome page.
 */
public class TreatmentController  extends AbstractContextAwareController {

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
	 * Treatment.
	 */
	private Treatment treatmentCurrent;

	
	/*
	 ******************* INIT ******************** */
	
	/**
	 * Bean constructor.
	 */
	public TreatmentController() {
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
	 * @see org.esupportail.example.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
	 */
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
	}
	
	/**
	 * @see org.esupportail.example.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		treatmentCurrent = new Treatment("CAS_DEMO", 1,
				"welcomeController.goCasDemo",getString("TREATMENTS.CAS_DEMO"));
	}


	/*
	 ******************* CALLBACK ******************** */
	
	
	/*
	 ******************* METHODS ******************** */
	
	/**
	 * THis list can be define by the spring beans.
	 * @return 
	 */
	public List<Treatment> getTreatments() {
		List<Treatment> list = new ArrayList<Treatment>();
		Treatment n = new Treatment("CAS_DEMO", 1,
				"welcomeController.goCasDemo",getString("TREATMENTS.CAS_DEMO"));
		list.add(n);
		n = new Treatment("AJAX_DEMO", 2,
				"simpleController.goAjaxDemo",getString("TREATMENTS.AJAX_DEMO"));
		list.add(n);
		n = new Treatment("EXCEPTION_DEMO", 3,
				"welcomeController.goExceptionDemo",getString("TREATMENTS.EXCEPTION_DEMO"));
		list.add(n);
		n = new Treatment("JPA_DEMO", 4,
				"welcomeController.goJpaDemo",getString("TREATMENTS.JPA_DEMO"));
		list.add(n);
		n = new Treatment("DEEPLINKING_DEMO", 5,
				"welcomeController.goLinkDemo",getString("TREATMENTS.DEEPLINKING_DEMO"));
		list.add(n);
		return list;
		
	}
 	
	
	/**
	 * @return String
	 */
	public String action() {
		System.out.println("entering action current = " + treatmentCurrent);
		String elAction = TagUtils.makeELExpression(treatmentCurrent.getAction());
		FacesContext context = FacesContext.getCurrentInstance();
		MethodExpression method = context.getApplication()
		.getExpressionFactory().createMethodExpression(
				context.getELContext(), elAction, String.class, new Class[]{});
		//execute the method define to action attributes.
		Object navRules = method.invoke(context.getELContext(), null);
		return (String) navRules;
	}
	

	/*
	 ******************* ACCESSORS ******************** */
	


	/**
	 * @return the treatmentCurrent
	 */
	public Treatment getTreatmentCurrent() {
		return treatmentCurrent;
	}

	/**
	 * @param treatmentCurrent the treatmentCurrent to set
	 */
	public void setTreatmentCurrent(final Treatment treatmentCurrent) {
		this.treatmentCurrent = treatmentCurrent;
	}


}
