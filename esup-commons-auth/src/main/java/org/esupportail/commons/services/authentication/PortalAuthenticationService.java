/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.authentication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.esupportail.commons.services.authentication.utils.ContextUtils;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;

/**
 * A portal authenticator.
 */
public class PortalAuthenticationService extends AbstractRealAuthenticationService {

	/**
	 * The default value for uidPortalAttribute.
	 */
	public static final String DEFAULT_UID_PORTAL_ATTRIBUTE = "uid";

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6775192478532264210L;

	/**
	 * The authentication type.
	 */
	private String authType;
	
	/**
	 * The portal attribute that contains the uid.
	 */
	private String uidPortalAttribute = DEFAULT_UID_PORTAL_ATTRIBUTE;

	/**
	 * The portal attributes.
	 */
	private List<String> portalAttributes;

	/**
	 * Bean constructor.
	 */
	private PortalAuthenticationService() {
	}
	
	/**
	 * Bean constructor .
	 */
	private PortalAuthenticationService(String portalAttributes, String uidPortalAttribute, String authType) {
		Assert.hasText(portalAttributes, "property portalAttributes of class " + this.getClass().getName()
				+ " can not be null or empty");
		Assert.hasText(authType, "property authType of class " + this.getClass().getName()
				+ " can not be null or empty");	
		
		this.portalAttributes = new ArrayList<String>();
		for (String portalAttribute : portalAttributes.split(",")) {
			if (!this.portalAttributes.contains(portalAttribute)) {
				this.portalAttributes.add(portalAttribute);
			}
		}
		Collections.sort(this.portalAttributes);
		
		this.uidPortalAttribute = uidPortalAttribute;
		
		this.authType = authType;
	}
	
	/**
	 * a static factory method
	 */
	public static PortalAuthenticationService createInstance(String portalAttributes, 
				String uidPortalAttribute, String authType) {
		return new PortalAuthenticationService(portalAttributes, uidPortalAttribute, authType);
	}

	@Override
	public String getAuthId() {
		return getPortalPref(uidPortalAttribute);
	}

	@Override
	protected Map<String, List<String>> getAuthAttributes() {
		if (portalAttributes == null) {
			return null;
		}
		Map<String, List<String>> attributes = new HashMap<String, List<String>>();
		for (String portalAttribute : portalAttributes) {
			String value = getPortalPref(portalAttribute);
			if (value != null) {
				List<String> values = new ArrayList<String>();
				values.add(value);
				attributes.put(portalAttribute, values);
			}
		}
		return attributes;
	}

	/**
	 * @return the uidPortalAttribute
	 */
	protected String getUidPortalAttribute() {
		return uidPortalAttribute;
	}

	@Override
	protected String getAuthType() {
		return authType;
	}
	
	/**
	 * @param prefName
	 * @return a JSR-168 preference (in portlet mode only).
	 */
	@SuppressWarnings("unchecked")
	protected static String getPortalPref(final String prefName) {
		Map<String, Object> attrs = (Map<String, Object>) ContextUtils.getContextAttributes().getAttribute(
								PortletRequest.USER_INFO, 
								RequestAttributes.SCOPE_REQUEST);
			if (attrs == null) {
			return null;
		}
		Object value = attrs.get(prefName);
		if (value == null) {
			return null;
		}
		return value.toString();
	}

}