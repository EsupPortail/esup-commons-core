/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.web.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.myfaces.trinidad.util.ExternalContextUtils;
import org.esupportail.blank.domain.DomainService;
import org.esupportail.blank.domain.beans.User;
import org.esupportail.blank.services.auth.Authenticator;
import org.esupportail.commons.services.i18n.I18nUtils;
import org.esupportail.commons.utils.strings.StringUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Yves Deschamps (Université de Lille 1) - 2010
 * 
 */
public class SessionController implements Serializable, InitializingBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 6725001881639400299L;

	private final Logger logger = Logger.getLogger(this.getClass());

	private String version;

	private String site;

	private String action;

	private String fromAction;

	private DomainService domainService;

	private Authenticator authenticator;

	private boolean modeDetected;

	private boolean portletMode;

	private String lastUserUid;

	private String defaultLocale;

	private String locale = defaultLocale;

	private String accessibilityMode = "default";

	/**
	 * The CAS logout URL.
	 */
	private String casLogoutUrl;

	/**
	 * Constructor.
	 */
	public SessionController() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		String[] args = {"", this.getClass().getName()};
		if (this.version == null) {
			args[0] = "version";
		}
		if (this.site == null) {
			args[0] = "site";
		}
		if (this.domainService == null) {
			args[0] = "domainService";
		}
		if (this.authenticator == null) {
			args[0] = "authenticator";
		}
		if (this.defaultLocale == null) {
			args[0] = "defaultLocale";
		}
		if (this.casLogoutUrl == null) {
			args[0] = "casLogoutUrl";
		}
		if (!args[0].equals("")) {
			throw new Exception(I18nUtils.createI18nService()
					.getString("CONFIG_EXCEPTION.TITLE", args));

		}
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the site
	 */
	public String getSite() {
		return site;
	}

	/**
	 * @param site
	 *            the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	private void resetControllers() {
		this.reset();
	}

	private void reset() {
		action = null;
		fromAction = null;
	}

	/**
	 * @return the current user, null if guest.
	 * @throws Exception 
	 * @throws Exception 
	 */
	public User getCurrentUser() {
		if (isPortletMode()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			String uid = fc.getExternalContext().getRemoteUser();
			if (lastUserUid == null) {
				lastUserUid = uid;
			} else {
				if (!lastUserUid.equals(uid)) {
					// uid change
					resetControllers();
					lastUserUid = uid;
				}
			}
			return domainService.getUser(uid);
		}
		User authUser;
		try {
			authUser = authenticator.getUser();
			User currentUser = null;
			if (authUser != null) {
				// for updating
				String uid = authUser.getId();
				currentUser = domainService.getUser(uid);
				if (currentUser != null) {
					if (lastUserUid == null) {
						lastUserUid = uid;
					} else {
						if (!lastUserUid.equals(uid)) {
							// uid change
							resetControllers();
							lastUserUid = uid;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param domainService
	 *            the domainService to set
	 */
	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	/**
	 * @return the fromAction
	 */
	public String getFromAction() {
		return fromAction;
	}

	/**
	 * @param fromAction
	 *            the fromAction to set
	 */
	public void setFromAction(String fromAction) {
		this.fromAction = fromAction;
	}

	/**
	 * @return true if portlet mode.
	 */
	public boolean isPortletMode() {
		if (!modeDetected) {
			modeDetected = true;
			if (logger.isDebugEnabled()) {
				logger.debug("Mode detected in Application");
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			portletMode = ExternalContextUtils.isPortlet(fc
					.getExternalContext());
			if (logger.isDebugEnabled()) {
				if (portletMode) {
					logger.debug("Portlet mode detected");
				} else {
					logger.debug("Servlet mode detected");
				}
			}
		}
		return portletMode;
	}

	/**
	 * @return true if login button is enable.
	 * @throws Exception 
	 */
	public boolean isLoginEnable() throws Exception {
		if (isPortletMode()) {
			return false;
		}
		return (getCurrentUser() == null);
	}

	/**
	 * @return true if login button is enable.
	 * @throws Exception 
	 */
	public boolean isLogoutEnable() throws Exception {
		if (isPortletMode()) {
			return false;
		}
		return (getCurrentUser() != null);
	}

	/**
	 * @return nothing and make logout.
	 * @throws Exception 
	 */
	public String logoutAction() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext
				.getRequest();
		String preReturnUrl = request.getRequestURL().toString().replaceFirst(
				"/stylesheets/[^/]*$", "");
		int index = preReturnUrl.lastIndexOf("/");
		String returnUrl = preReturnUrl.substring(0, index + 1).concat(
				"welcome.jsf");
		String forwardUrl;
		forwardUrl = String.format(casLogoutUrl, StringUtils
				.utf8UrlEncode(returnUrl));
		request.getSession().invalidate();
		request.getSession(true);
		try {
			externalContext.redirect(forwardUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		facesContext.responseComplete();
		// Il convient de désactiver les éléments d'identification et de
		// navigation
		action = "welcome";
		return null;
	}

	/**
	 * @param casLogoutUrl
	 *            the casLogoutUrl to set
	 */
	public void setCasLogoutUrl(String casLogoutUrl) {
		this.casLogoutUrl = casLogoutUrl;
	}

	/**
	 * @param authenticator
	 *            the authenticator to set
	 */
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	/**
	 * @param defaultLocale
	 *            the defaultLocale to set
	 */
	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	/**
	 * @return the locale of the current user or defaultLocale if not
	 *         authenticated.
	 * @throws Exception 
	 */
	public String getLocale() throws Exception {
		User currentUser = getCurrentUser();
		if (currentUser == null) {
			if (locale == null) {
				locale = defaultLocale;
			}
			return locale;
		}
		return currentUser.getLanguage();
	}

	/**
	 * @param locale
	 *            the locale to set
	 * @throws Exception 
	 */
	public void setLocale(String locale) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.getViewRoot().setLocale(new Locale(locale));
		}
		User currentUser = getCurrentUser();
		if (currentUser == null) {
			this.locale = locale;
		} else {
			getCurrentUser().setLanguage(locale);
		}
	}

	/**
	 * @return the accessibility mode of the current user or default if not
	 *         authenticated.
	 * @throws Exception 
	 * @throws Exception 
	 */
	public String getAccessibilityMode() throws Exception {
		User currentUser = getCurrentUser();
		if (currentUser == null) {
			return accessibilityMode;
		}
		return currentUser.getAccessibilityMode();
	}

	/**
	 * @param accessibilityMode
	 *            the accessibilityMode to set
	 * @throws Exception 
	 * @throws Exception 
	 */
	public void setAccessibilityMode(String accessibilityMode) throws Exception {
		User currentUser = getCurrentUser();
		if (currentUser == null) {
			this.accessibilityMode = accessibilityMode;
		} else {
			getCurrentUser().setAccessibilityMode(accessibilityMode);
		}
	}

}
