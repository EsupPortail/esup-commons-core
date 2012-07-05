/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.web.portlet;

import java.io.Serializable;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.apache.myfaces.context.servlet.FacesContextImpl;
import org.apache.myfaces.context.servlet.ServletFacesContextImpl;

/**
 * A serializable servlet faces context implementation.
 */
//FIXME CL V2 : j'ai changé ServeltFacesContextImpl to FacesContextImpl
public class SerializableServletFacesContextImpl extends FacesContextImpl implements Serializable {

	/**
	 * The id for serialization.
	 */
	private static final long serialVersionUID = 9118280247060798147L;

	/**
	 * Bean constructor.
	 * @param portletContext 
	 * @param request 
	 * @param response
	 */
	public SerializableServletFacesContextImpl(
			final PortletContext portletContext, 
			final PortletRequest request, 
			final PortletResponse response) {
		super(portletContext, request, response);
	}

}
