/**
 *
 */
package org.esupportail.commons.jsf;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.exceptions.NoRequestBoundException;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;
import org.esupportail.commons.utils.ContextUtils;

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

	@Override
	public void afterPhase(final PhaseEvent arg0) {
		//do nothing

	}

	@Override
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
				I18nService i18nService = (I18nService) BeanUtils.getBean("i18nService");
				context.getExternalContext().getRequestMap().put(
						DEFAULT_STRINGVAR,
						i18nService.getStrings(locale));
			}
		} catch (NoRequestBoundException e) {
			//do nothing
			logger.warn("in ResourceBundlePhaseListener::beforePhase NoRequestBoundException");
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
