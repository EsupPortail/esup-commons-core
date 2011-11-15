/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.web.controllers;

import java.io.IOException;
import java.util.Locale;

import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.i18n.I18nUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
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
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * The exception controller.
	 */
	private ExceptionController exceptionController;

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
	 * The CAS logout URL.
	 */
	private String casLogoutUrl;

	/**
	 * The show exception details state.
	 */
	private boolean showExceptionDetails;

	/**
	 * The current User.
	 */
	private User currentUser;

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
		Assert.notNull(this.casLogoutUrl, "property casLogoutUrl of class "
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
			if (currentUser != null && currentUser.getLogin().equals(uid)) {
				return currentUser;
			}
			try {
				currentUser = getDomainService().getUser(uid);
			} catch (UserNotFoundException e) {
				currentUser = new User();
				currentUser.setLogin(uid);
				currentUser.setDisplayName(I18nUtils.createI18nService()
						.getString(e.getMessage()));
				currentUser.setAdmin(false);
			}
			return currentUser;
		}
		User authUser;
		try {
			authUser = authenticator.getUser();
			if (authUser != null) {
				if (currentUser != null
						&& currentUser.getLogin().equals(authUser.getLogin())) {
					return currentUser;
				}
				// for updating
				String uid = authUser.getLogin();
				try {
					currentUser = getDomainService().getUser(uid);
				} catch (UserNotFoundException e) {
					currentUser = new User();
					currentUser.setLogin(uid);
					currentUser.setDisplayName(I18nUtils.createI18nService()
							.getString(e.getMessage()));
					currentUser.setAdmin(false);
				}
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
		currentUser = null;
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
		// Il convient de desactiver les elements d'identification et de
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
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.getViewRoot().setLocale(locale);
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
		return accessibilityMode;
	}

	/**
	 * @param accessibilityMode
	 *            The accessibilityMode to set
	 */
	public void setAccessibilityMode(String accessibilityMode) {
		this.accessibilityMode = accessibilityMode;
	}

	/**
	 * @param exceptionController
	 */
	public void setExceptionController(ExceptionController exceptionController) {
		this.exceptionController = exceptionController;
	}

	/**
	 * @return the show exception details state.
	 */
	public boolean isShowExceptionDetails() {
		return showExceptionDetails;
	}

	/**
	 * @return navigation.
	 */
	public String showExceptionDetailsAction() {
		this.showExceptionDetails = true;
		return null;
	}

	/**
	 * @return navigation.
	 */
	public String hideExceptionDetailsAction() {
		this.showExceptionDetails = false;
		return null;
	}

	/**
	 * @return an Url (with the good host, port and context...).
	 */
	public String getServletUrl() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getRequestContextPath()
				+ "/stylesheets/home.xhtml";
	}

	/**
	 * @return display accessibility mode.
	 */
	public String getDisplayAccessibilityMode() {
		if (accessibilityMode.equals("default"))
			return I18nUtils.createI18nService().getString(
					"PREFERENCES.ACCESSIBILITY.DEFAULT");
		if (accessibilityMode.equals("inaccessible"))
			return I18nUtils.createI18nService().getString(
					"PREFERENCES.ACCESSIBILITY.INACCESSIBLE");
		return I18nUtils.createI18nService().getString(
				"PREFERENCES.ACCESSIBILITY.SCREENREADER");

	}

	/**
	 * @return language.
	 */
	public String getDisplayLanguage() {
		Locale locale = getLocale();
		StringBuffer buf = new StringBuffer(locale.getDisplayLanguage(locale));
		return buf.toString();
	}

	/**
	 * @param event
	 * @return null;
	 */
	public String setLocaleAction(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("language");
		String languageString = component.getValue().toString();
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.getViewRoot().setLocale(new Locale(languageString));
		}
		return null;
	}
	
	/**
	 * @param event
	 * @return null;
	 */
	public String login(ActionEvent event) {
		this.action = null;
		return null;
	}

}
