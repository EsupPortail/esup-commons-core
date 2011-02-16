/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.beans;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.InitializingBean;

/**
 * An abstract class inherited by all the beans for them to gain i18n features.
 */
@SuppressWarnings("serial")
public abstract class AbstractJsfMessagesAwareBean extends AbstractApplicationAwareBean implements InitializingBean, Serializable {

	/** 
	 * The name of the session attribute that stores the current locale.
	 */
	public static final String LOCALE_ATTRIBUTE = "locale";
	
	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(this.getClass());
	


	/**
	 * Constructor.
	 */
	protected AbstractJsfMessagesAwareBean() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
	}

	
	
	/**
	 * @see org.esupportail.commons.beans.AbstractI18nAwareBean#getDefaultLocale()
	 */
	public Locale getDefaultLocale() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context == null) {
			return Locale.getDefault();
		}
		UIViewRoot viewRoot = null;
		try {
			viewRoot = context.getViewRoot();
		} catch (IllegalStateException e) {
			// context has probably been released, happens on exception handling
		}
		if (viewRoot != null) {
			return viewRoot.getLocale();
		}
		Application application = null;
		try {
			application = context.getApplication();
		} catch (IllegalStateException e) {
			// context has probably been released, happens on exception handling
		}
		if (application == null) {
			return Locale.getDefault();
		}
		return application.getDefaultLocale();
	}
	

	/**
	 * @return a FacesMessage that corresponds to a message and a severity level.
	 * @param severity the severity
	 * @param message the message itself
	 */
	private FacesMessage getFacesFormattedMessage(
			final Severity severity, 
			final String message) {
		return new FacesMessage(severity, message, null);
	}

	/**
	 * @return a FacesMessage that corresponds to a message and a severity level.
	 * @param severity the severity
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 * @param i18nArg3 
	 */
	private FacesMessage getFacesMessage(
			final Severity severity, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2, 
			final Object i18nArg3) {
		return getFacesFormattedMessage(
				severity, getI18nService().getString(i18nMessage, i18nArg0, i18nArg1, i18nArg2, i18nArg3));
	}

	/**
	 * @return a FacesMessage that corresponds to a message and a severity level.
	 * @param severity the severity
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 */
	private FacesMessage getFacesMessage(
			final Severity severity, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2) {
		return getFacesFormattedMessage(
				severity, getI18nService().getString(i18nMessage, i18nArg0, i18nArg1, i18nArg2));
	}

	/**
	 * @return a FacesMessage that corresponds to a message and a severity level.
	 * @param severity the severity
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 */
	private FacesMessage getFacesMessage(
			final Severity severity, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1) {
		return getFacesFormattedMessage(severity, getI18nService().getString(i18nMessage, i18nArg0, i18nArg1));
	}

	/**
	 * @return a FacesMessage that corresponds to a message and a severity level.
	 * @param severity the severity
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 */
	private FacesMessage getFacesMessage(
			final Severity severity, 
			final String i18nMessage, 
			final Object i18nArg0) {
		return getFacesFormattedMessage(severity, getI18nService().getString(i18nMessage, i18nArg0));
	}

	/**
	 * @return a FacesMessage that corresponds to a message and a severity level.
	 * @param severity the severity
	 * @param i18nMessage the key of the message in the i18n bundles
	 */
	private FacesMessage getFacesMessage(
			final Severity severity, 
			final String i18nMessage) {
		return getFacesFormattedMessage(severity, getI18nService().getString(i18nMessage));
	}

	/**
	 * @return a warn FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 * @param i18nArg3 
	 */
	public FacesMessage getFacesWarnMessage(
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2, 
			final Object i18nArg3) {
		return getFacesMessage(FacesMessage.SEVERITY_WARN, i18nMessage, i18nArg0, i18nArg1, i18nArg2, i18nArg3);
	}

	/**
	 * @return a warn FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 */
	public FacesMessage getFacesWarnMessage(
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2) {
		return getFacesMessage(FacesMessage.SEVERITY_WARN, i18nMessage, i18nArg0, i18nArg1, i18nArg2);
	}

	/**
	 * @return a warn FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 */
	public FacesMessage getFacesWarnMessage(
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1) {
		return getFacesMessage(FacesMessage.SEVERITY_WARN, i18nMessage, i18nArg0, i18nArg1);
	}

	/**
	 * @return a warn FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 */
	public FacesMessage getFacesWarnMessage(
			final String i18nMessage, 
			final Object i18nArg0) {
		return getFacesMessage(FacesMessage.SEVERITY_WARN, i18nMessage, i18nArg0);
	}

	/**
	 * @return a warn FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 */
	public FacesMessage getFacesWarnMessage(
			final String i18nMessage) {
		return getFacesMessage(FacesMessage.SEVERITY_WARN, i18nMessage);
	}

	/**
	 * @return an error FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 * @param i18nArg3 
	 */
	public FacesMessage getFacesErrorMessage(
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2, 
			final Object i18nArg3) {
		return getFacesMessage(
				FacesMessage.SEVERITY_ERROR, i18nMessage, i18nArg0, i18nArg1, i18nArg2, i18nArg3);
	}

	/**
	 * @return an error FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 */
	public FacesMessage getFacesErrorMessage(
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2) {
		return getFacesMessage(FacesMessage.SEVERITY_ERROR, i18nMessage, i18nArg0, i18nArg1, i18nArg2);
	}

	/**
	 * @return an error FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 */
	public FacesMessage getFacesErrorMessage(
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1) {
		return getFacesMessage(FacesMessage.SEVERITY_ERROR, i18nMessage, i18nArg0, i18nArg1);
	}

	/**
	 * @return an error FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 */
	public FacesMessage getFacesErrorMessage(
			final String i18nMessage, 
			final Object i18nArg0) {
		return getFacesMessage(FacesMessage.SEVERITY_ERROR, i18nMessage, i18nArg0);
	}

	/**
	 * @return an error FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 */
	public FacesMessage getFacesErrorMessage(
			final String i18nMessage) {
		return getFacesMessage(FacesMessage.SEVERITY_ERROR, i18nMessage);
	}

	/**
	 * @return an info FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 * @param i18nArg3 
	 */
	public FacesMessage getFacesInfoMessage(
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2, 
			final Object i18nArg3) {
		return getFacesMessage(FacesMessage.SEVERITY_INFO, i18nMessage, i18nArg0, i18nArg1, i18nArg2, i18nArg3);
	}

	/**
	 * @return an info FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 */
	public FacesMessage getFacesInfoMessage(
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2) {
		return getFacesMessage(FacesMessage.SEVERITY_INFO, i18nMessage, i18nArg0, i18nArg1, i18nArg2);
	}

	/**
	 * @return an info FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 */
	public FacesMessage getFacesInfoMessage(
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1) {
		return getFacesMessage(FacesMessage.SEVERITY_INFO, i18nMessage, i18nArg0, i18nArg1);
	}

	/**
	 * @return an info FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 */
	public FacesMessage getFacesInfoMessage(
			final String i18nMessage, 
			final Object i18nArg0) {
		return getFacesMessage(FacesMessage.SEVERITY_INFO, i18nMessage, i18nArg0);
	}

	/**
	 * @return an info FacesMessage that corresponds to a message and a severity level.
	 * @param i18nMessage the key of the message in the i18n bundles
	 */
	public FacesMessage getFacesInfoMessage(
			final String i18nMessage) {
		return getFacesMessage(FacesMessage.SEVERITY_INFO, i18nMessage);
	}

	/**
	 * Add to the current context a message.
	 * @param severity the severity
	 * @param clientId the id of client that should receive the message
	 * @param message the message itself
	 */
	private void addFormattedMessage(
			final Severity severity, 
			final String clientId, 
			final String message) {
		FacesMessage errorMessage = getFacesFormattedMessage(severity, message);
		FacesContext.getCurrentInstance().addMessage(clientId, errorMessage);
	}

	/**
	 * Add to the current context a formatted error message.
	 * @param clientId the id of client that should receive the message
	 * @param message the message itself
	 */
	public void addFormattedError(
			final String clientId, 
			final String message) {
		addFormattedMessage(FacesMessage.SEVERITY_ERROR, clientId, message);
	}

	/**
	 * Add to the current context a formatted info message.
	 * @param clientId the id of client that should receive the message
	 * @param message the message itself
	 */
	public void addFormattedInfo(
			final String clientId, 
			final String message) {
		addFormattedMessage(FacesMessage.SEVERITY_INFO, clientId, message);
	}

	/**
	 * Add to the current context a formatted warn message.
	 * @param clientId the id of client that should receive the message
	 * @param message the message itself
	 */
	public void addFormattedWarn(
			final String clientId, 
			final String message) {
		addFormattedMessage(FacesMessage.SEVERITY_WARN, clientId, message);
	}

	/**
	 * Add to the current context a message.
	 * @param severity the severity
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 * @param i18nArg3 
	 */
	private void addMessage(
			final Severity severity, 
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2, 
			final Object i18nArg3) {
		String message = getI18nService().getString(
				i18nMessage, getLocale(), i18nArg0, i18nArg1, i18nArg2, i18nArg3);
		addFormattedMessage(severity, clientId, message);
	}

	/**
	 * Add to the current context a message.
	 * @param severity the severity
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 */
	private void addMessage(
			final Severity severity, 
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2) {
		String message = getI18nService().getString(i18nMessage, getLocale(), i18nArg0, i18nArg1, i18nArg2);
		addFormattedMessage(severity, clientId, message);
	}

	/**
	 * Add to the current context a message.
	 * @param severity the severity
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 */
	private void addMessage(
			final Severity severity, 
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1) {
		String message = getI18nService().getString(i18nMessage, getLocale(), i18nArg0, i18nArg1);
		addFormattedMessage(severity, clientId, message);
	}

	/**
	 * Add to the current context a message.
	 * @param severity the severity
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 */
	private void addMessage(
			final Severity severity, 
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0) {
		String message = getI18nService().getString(i18nMessage, getLocale(), i18nArg0);
		addFormattedMessage(severity, clientId, message);
	}

	/**
	 * Add to the current context a message.
	 * @param severity the severity
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 */
	private void addMessage(
			final Severity severity, 
			final String clientId, 
			final String i18nMessage) {
		String message = getI18nService().getString(i18nMessage, getLocale());
		addFormattedMessage(severity, clientId, message);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 * @param i18nArg3 
	 */
	protected void addWarnMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2, 
			final Object i18nArg3) {
		addMessage(FacesMessage.SEVERITY_WARN, clientId, i18nMessage, i18nArg0, i18nArg1, i18nArg2, i18nArg3);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 */
	protected void addWarnMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2) {
		addMessage(FacesMessage.SEVERITY_WARN, clientId, i18nMessage, i18nArg0, i18nArg1, i18nArg2);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 */
	protected void addWarnMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1) {
		addMessage(FacesMessage.SEVERITY_WARN, clientId, i18nMessage, i18nArg0, i18nArg1);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 */
	protected void addWarnMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0) {
		addMessage(FacesMessage.SEVERITY_WARN, clientId, i18nMessage, i18nArg0);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 */
	protected void addWarnMessage(
			final String clientId, 
			final String i18nMessage) {
		addMessage(FacesMessage.SEVERITY_WARN, clientId, i18nMessage);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 * @param i18nArg3 
	 */
	protected void addErrorMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2, 
			final Object i18nArg3) {
		addMessage(FacesMessage.SEVERITY_ERROR, clientId, i18nMessage, i18nArg0, i18nArg1, i18nArg2, i18nArg3);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 */
	protected void addErrorMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2) {
		addMessage(FacesMessage.SEVERITY_ERROR, clientId, i18nMessage, i18nArg0, i18nArg1, i18nArg2);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 */
	protected void addErrorMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1) {
		addMessage(FacesMessage.SEVERITY_ERROR, clientId, i18nMessage, i18nArg0, i18nArg1);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 */
	protected void addErrorMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0) {
		addMessage(FacesMessage.SEVERITY_ERROR, clientId, i18nMessage, i18nArg0);
	}

	/**
	 * Add to the current context a error message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 */
	protected void addErrorMessage(
			final String clientId, 
			final String i18nMessage) {
		addMessage(FacesMessage.SEVERITY_ERROR, clientId, i18nMessage);
	}

	/**
	 * Add to the current context an info message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 * @param i18nArg3 
	 */
	protected void addInfoMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2, 
			final Object i18nArg3) {
		addMessage(FacesMessage.SEVERITY_INFO, clientId, i18nMessage, i18nArg0, i18nArg1, i18nArg2, i18nArg3);
	}

	/**
	 * Add to the current context an info message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 * @param i18nArg2 
	 */
	protected void addInfoMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1, 
			final Object i18nArg2) {
		addMessage(FacesMessage.SEVERITY_INFO, clientId, i18nMessage, i18nArg0, i18nArg1, i18nArg2);
	}

	/**
	 * Add to the current context an info message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 * @param i18nArg1 
	 */
	protected void addInfoMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0, 
			final Object i18nArg1) {
		addMessage(FacesMessage.SEVERITY_INFO, clientId, i18nMessage, i18nArg0, i18nArg1);
	}

	/**
	 * Add to the current context an info message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 * @param i18nArg0 
	 */
	protected void addInfoMessage(
			final String clientId, 
			final String i18nMessage, 
			final Object i18nArg0) {
		addMessage(FacesMessage.SEVERITY_INFO, clientId, i18nMessage, i18nArg0);
	}

	/**
	 * Add to the current context an info message.
	 * @param clientId the id of client that should receive the message
	 * @param i18nMessage the key of the message in the i18n bundles
	 */
	protected void addInfoMessage(
			final String clientId, 
			final String i18nMessage) {
		addMessage(FacesMessage.SEVERITY_INFO, clientId, i18nMessage);
	}
	/**
	 * Add to the current context a message saying that the action is not authorized.
	 */
	protected void addUnauthorizedActionMessage() {
		addErrorMessage(null, "_.MESSAGE.UNAUTHORIZED_ACTION");
	}


}
