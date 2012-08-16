package org.esupportail.commons.services.mobile;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface of view selection service
 * 
 * @author bourges
 *
 */
public interface ViewSelectorService {

	/**
	 * @param viewName - the name of the view to complete with device specific prefix 
	 * @param request - the http request
	 * @return the calified view name
	 */
	public String getCalifiedViewName(String viewName, HttpServletRequest request); 
	
	/**
	 * @param viewName - the name of the view to complete with device specific prefix 
	 * @param request - the http request
	 * @param isWAI - is in WAI mode
	 * @return the calified view name
	 */
	public String getCalifiedViewName(String viewName, HttpServletRequest request, boolean isWAI); 
	
	/**
	 * @param request - the http request
	 * @return true is current agent is a mobile
	 */
	public boolean isMobile(HttpServletRequest request);
	
	/**
	 * @param request - the http request
	 * @return true is current agent have a geolocation functionality 
	 */
	public boolean isGPSAware(HttpServletRequest request);
	
	
}
