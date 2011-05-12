/**
 * ESUP-Portail Directory Application - Copyright (c) 2011 ESUP-Portail consortium.
 */
package org.esupportail.annuaire2.web.jsf;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.PortletRequest;

import org.apache.myfaces.trinidad.util.ExternalContextUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.ContextUtils;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2011
 * 
 */
public class ServletOrPortletPhaseListener implements PhaseListener {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * For logging.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	@Override
	public void afterPhase(PhaseEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("In afterPhase");
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		boolean portletMode = ExternalContextUtils.isPortlet(fc
				.getExternalContext());
		if (logger.isDebugEnabled()) {
			logger.debug("Portlet mode: " + portletMode);
		}
		if ((event.getPhaseId().equals(PhaseId.RENDER_RESPONSE)
				|| fc.getResponseComplete()
				|| (!event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) && fc
						.getRenderResponse()) && portletMode) {
			ExternalContext externalContext = fc.getExternalContext();
			PortletRequest request = (PortletRequest) externalContext
					.getRequest();
			ContextUtils.unexposeRequest(request);
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("In beforePhase");
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		boolean portletMode = ExternalContextUtils.isPortlet(fc
				.getExternalContext());
		if (logger.isDebugEnabled()) {
			logger.debug("Portlet mode: " + portletMode);
		}
		if ((event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) && portletMode) {
			ExternalContext externalContext = fc.getExternalContext();
			PortletRequest request = (PortletRequest) externalContext
					.getRequest();
			ContextUtils.exposeRequest(request);
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
