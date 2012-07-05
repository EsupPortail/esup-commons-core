/**
 * 
 */
package org.esupportail.commons.jsf;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.PortletRequest;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.ContextUtils;

/**
 * @author bourges
 * 
 * PortletPhaseListener est indispensable en mode portlet pour remplacer org.springframework.web.context.request.RequestContextListener
 *
 */
public class PortletPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 6056033610786238561L;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());	

	/**
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent event) {
		logger.debug("In beforePhase");
		if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
			FacesContext context = event.getFacesContext();
			PortletRequest request = (PortletRequest) context.getExternalContext().getRequest();
			ContextUtils.exposeRequest(request);
		}
	}

	/**
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent event) {
		logger.debug("In afterPhase");
		FacesContext context = event.getFacesContext();
		if (event.getPhaseId().equals(PhaseId.RENDER_RESPONSE) || context.getResponseComplete()
				|| (!event.getPhaseId().equals(PhaseId.RESTORE_VIEW) && context.getRenderResponse())) {
			PortletRequest request = (PortletRequest) context.getExternalContext().getRequest();
			ContextUtils.unexposeRequest(request);
		}
	}

	/**
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
