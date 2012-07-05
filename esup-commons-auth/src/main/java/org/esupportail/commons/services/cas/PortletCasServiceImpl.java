/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.cas;

import java.util.Map;

import org.esupportail.commons.utils.ContextUtils;

/** 
 * The implementation of CasService for portlets. The PGT is supposed 
 * to be passed to the portlet as a preferences attribute.
 */
public class PortletCasServiceImpl implements CasService {
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 7540349970957963666L;

	/**
	 * @see org.esupportail.commons.services.cas.AbstractCasService#getProxyTicket(java.lang.String)
	 */
	public String getProxyTicket(String targetService) throws CasException {
		Map<String,String> userInfo = (Map<String,String>) ContextUtils.getGlobalSessionAttribute("javax.portlet.userinfo");
		String proxyTicket = userInfo.get("casProxyTicket");
		return proxyTicket;
	}

}
