package org.esupportail.blank.jsf.mixed.exceptions;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpSession;

import org.apache.myfaces.trinidad.util.ExternalContextUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionService;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.utils.ContextUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.portlet.context.PortletRequestAttributes;

public class ExceptionHandler {

	/**
	 * Un marqueur pour stocker les attributs initialement contenus dans la
	 * requête
	 */
	private static final String REQUEST_ATTRIBUTES_ATTRIBUTE = ContextUtils.class
			.getName() + ".REQUEST_ATTRIBUTES";

	/**
	 * Constructors.
	 */
	public ExceptionHandler() {
		super();
	}

	public void handleException(final FacesContext context, final Exception ex) {
		FacesContext fc = FacesContext.getCurrentInstance();
		boolean portletMode = ExternalContextUtils.isPortlet(fc
				.getExternalContext());
		if (portletMode) {
			PortletRequest request = (PortletRequest) context
					.getExternalContext().getRequest();

			PortletRequestAttributes requestAttributes = new PortletRequestAttributes(
					request);
			request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE,
					requestAttributes);
			// LocaleContextHolder.setLocale(request.getLocale());
			RequestContextHolder.setRequestAttributes(requestAttributes);

			PortletRequestAttributes retrieveAttributes = (PortletRequestAttributes) request
					.getAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE);
			PortletRequestAttributes threadAttributes = (PortletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			if (threadAttributes != null) {
				if (retrieveAttributes == null) {
					retrieveAttributes = threadAttributes;
				}
				// LocaleContextHolder.resetLocaleContext();
				RequestContextHolder.resetRequestAttributes();
			}
			if (retrieveAttributes != null) {
				retrieveAttributes.requestCompleted();
			}
		}
		ExceptionUtils.markExceptionCaught();
		ExceptionService exceptionService = null;
		exceptionService = ExceptionUtils.catchException(ex);
		ExceptionUtils.markExceptionCaught(exceptionService);
		NavigationHandler navigation = context.getApplication()
				.getNavigationHandler();
		navigation.handleNavigation(context, "", exceptionService.getExceptionView());
	}
	
}
