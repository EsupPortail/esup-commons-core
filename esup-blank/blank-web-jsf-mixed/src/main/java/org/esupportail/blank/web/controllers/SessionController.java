/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.web.controllers;

import java.io.IOException;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.myfaces.trinidad.util.ExternalContextUtils;
import org.esupportail.blank.domain.beans.User;
import org.esupportail.blank.services.auth.Authenticator;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.utils.strings.StringUtils;
import org.esupportail.commons.web.controllers.ExceptionController;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public class SessionController extends AbstractDomainAwareBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 6725001881639400299L;

	/**
	 * For Logging.
	 */
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * The exception controller (called when logging in/out).
	 */
	private ExceptionController exceptionController;

	/**
	 * @param exceptionController
	 */
	public void setExceptionController(ExceptionController exceptionController) {
		this.exceptionController = exceptionController;
	}

	/**
	 * The application version.
	 */
	private String version;

	/**
	 * The application site.
	 */
	private String site;

	/**
	 * The current action from menu.
	 */
	private String action;

	/**
	 * The from form current action.
	 */
	private String fromAction;

	/**
	 * The authenticator.
	 */
	private Authenticator authenticator;

	/**
	 * The detected mode (desktop or mobile).
	 */
	private boolean modeDetected;

	/**
	 * True if we are in portlet mode.
	 */
	private boolean portletMode;

	/**
	 * The accessibility mode.
	 */
	private String accessibilityMode = "default";

	/**
	 * The selected language.
	 */
	private String languageSelected;

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

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.exceptionController,
				"property exceptionController of class "
						+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.authenticator, "property authenticator of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.version, "property version of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.site, "property site of class "
				+ this.getClass().getName() + " can not be null");
	}

	/**
	 * 
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

	@Override
	public User getCurrentUser() {
		if (isPortletMode()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			String uid = fc.getExternalContext().getRemoteUser();
			return getDomainService().getUser(uid);
		}
		User authUser;
		try {
			authUser = authenticator.getUser();
			User currentUser = null;
			if (authUser != null) {
				// for updating
				String uid = authUser.getId();
				currentUser = getDomainService().getUser(uid);
				return currentUser;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void reset() {
		super.reset();
		action = null;
		fromAction = null;
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
	 */
	public String logoutAction() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext
				.getRequest();
		String preReturnUrl = request.getRequestURL().toString()
				.replaceFirst("/stylesheets/[^/]*$", "");
		int index = preReturnUrl.lastIndexOf("/");
		String returnUrl = preReturnUrl.substring(0, index + 1).concat(
				"welcome.xhtml");
		String forwardUrl;
		forwardUrl = String.format(casLogoutUrl,
				StringUtils.utf8UrlEncode(returnUrl));
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
	 * @return null.
	 */
	public String setLocaleAction() {
		setLocale(new Locale(languageSelected));
		return null;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		resetSessionLocale();
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.getViewRoot().setLocale(locale);
		}
		User currentUser = getCurrentUser();
		if (currentUser != null) {
			getCurrentUser().setLanguage(locale.getLanguage());
		}
	}

	@Override
	public Locale getLocale() {
		Locale locale = new Locale("fr");
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			locale = context.getViewRoot().getLocale();
		}
		return locale;
	}

	/**
	 * @return the accessibility mode of the current user or default if not
	 *         authenticated.
	 */
	public String getAccessibilityMode() {
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

	/**
	 * @return the selected language
	 */
	public String getLanguageSelected() {
		if (languageSelected == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			languageSelected = fc.getApplication().getDefaultLocale().toString();

		}
		return languageSelected;
	}

	/**
	 * @param languageSelected
	 *            the languageSelected to set
	 */
	public void setLanguageSelected(String languageSelected) {
		this.languageSelected = languageSelected;
	}

}
