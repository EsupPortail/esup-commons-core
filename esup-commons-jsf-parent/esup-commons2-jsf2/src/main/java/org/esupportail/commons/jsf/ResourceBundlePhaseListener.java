/**
 * 
 */
package org.esupportail.commons.jsf;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.exceptions.NoRequestBoundException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.ContextUtils;
import org.esupportail.commons.web.jsf.tags.config.TagsConfigurator;

/**
 * @author cleprous
 * Recupere tous les bundle.
 */
public class ResourceBundlePhaseListener implements PhaseListener {


	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -7429514681508723093L;

	/**
	 * The string var to bundle message
	 */
	public static final String DEFAULT_STRINGVAR = "msgs";

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());


	/**
	 * Constructor.
	 */
	public ResourceBundlePhaseListener() {
		super();
	}



	/**
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(final PhaseEvent arg0) {
		//do nothing

	}

	/**
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(final PhaseEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("enterig  RessourceBundlePhaseListener::beforePhase = " + event);
		}
		FacesContext context = event.getFacesContext();
		try {
			Locale locale = (Locale) ContextUtils.getSessionAttribute(AbstractI18nAwareBean.LOCALE_ATTRIBUTE);
			if (locale == null && context.getViewRoot() != null) {
				locale = context.getViewRoot().getLocale();
			}

			if (locale != null
					&& context.getExternalContext().getRequestMap().get(DEFAULT_STRINGVAR) == null) {
				TagsConfigurator tagsConfigurator = TagsConfigurator.getInstance();
				context.getExternalContext().getRequestMap().put(
						DEFAULT_STRINGVAR,
						tagsConfigurator.getStrings(locale));
			}
		} catch (NoRequestBoundException e) {
			//do nothing
			logger.warn("in ResourceBundlePhaseListener::beforePhase NoRequestBoundException");
		}


	}





	/**
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}




}
