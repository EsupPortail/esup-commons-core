/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.web.utils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public class AgentUtil implements Serializable, InitializingBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -1259495940256943174L;

	/**
	 * For logging.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * The phone family.
	 */
	private String phoneFamily;

	/**
	 * The skins.
	 */
	private Map<String, String> skins;

	/**
	 * True if mobile.
	 */
	private boolean mobile;

	/**
	 * Constructor.
	 */
	public AgentUtil() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(skins, "property skins of class "
				+ this.getClass().getName() + " can not be null");
	}

	/**
	 * @return the skin from user-agent detect.
	 */
	public String getPhoneFamily() {
		if (phoneFamily == null) {
			String agent = null;
			FacesContext fc = FacesContext.getCurrentInstance();
			agent = fc.getExternalContext().getRequestHeaderMap()
					.get("User-Agent");
			logger.info("User-Agent: " + agent);
			for (Iterator<String> i = skins.keySet().iterator(); i.hasNext();) {
				String key = i.next();
				if (agent != null && agent.indexOf(key) > -1) {
					phoneFamily = skins.get(key);
					mobile = true;
					return phoneFamily;
				}
			}
			phoneFamily = "minimalFamily";
		}
		return phoneFamily;
	}

	/**
	 * @return the skins
	 */
	public Map<String, String> getSkins() {
		return skins;
	}

	/**
	 * @param skins
	 *            the skins to set
	 */
	public void setSkins(Map<String, String> skins) {
		this.skins = skins;
	}

	/**
	 * @return the mobile
	 */
	public boolean isMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}

}
