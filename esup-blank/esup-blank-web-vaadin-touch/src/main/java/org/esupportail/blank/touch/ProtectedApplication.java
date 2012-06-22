package org.esupportail.blank.touch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.esupportail.blank.domain.DomainService;
import org.esupportail.blank.domain.beans.User;
import org.esupportail.blank.services.auth.Authenticator;
import org.esupportail.blank.touch.ui.MainTabsheet;
import org.esupportail.blank.touch.ui.MapTargetViewController;
import org.esupportail.blank.touch.ui.MapViewController;
import org.esupportail.blank.touch.ui.WebViewController;
import org.esupportail.blank.touch.ui.WelcomeViewController;
import org.esupportail.blank.touch.util.SpringContextHelper;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;

/**
 * The Protected Application's "main" class
 */
@Configurable
public class ProtectedApplication extends TouchKitApplication implements
		HttpServletRequestListener {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -2900547265157356445L;

	/**
	 * For Logging.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	static CustomizedSystemMessages customizedSystemMessages = new CustomizedSystemMessages();
	static {
		customizedSystemMessages.setSessionExpiredNotificationEnabled(false);
	}

	/**
	 * The Authenticator Bean.
	 */
	private Authenticator authenticator;

	/**
	 * The Domain Bean.
	 */
	DomainService domainService;

	/**
	 * The Welcome View Controller.
	 */
	WelcomeViewController welcomeViewController;

	/**
	 * The Web View Controller.
	 */
	WebViewController webViewController;

	/**
	 * The Map View Controller.
	 */
	MapViewController mapViewController;

	/**
	 * The Map Target View Controller.
	 */
	private MapTargetViewController mapTargetViewController;
	
 	/**
	 * The Current User.
	 */
	private User currentUser;

	/**
	 * for invalidate session.
	 */
	boolean invalidate;

	/*
	 * Default the location
	 */
	private double currentLongitude;
	private double currentLatitude;

	/**
	 * Make application reload itself when the session has expired. Our demo app
	 * gains nothing for showing session expired message.
	 * 
	 * @see TouchKitApplication#getSystemMessages()
	 */
	public static SystemMessages getSystemMessages() {
		return customizedSystemMessages;
	}

	@Override
	public void init() {
		super.init();
		linkDomainAndViews();
		setMainWindow(new MainWindow());
	}

	private void linkDomainAndViews() {
		// Link with Model and Views
		SpringContextHelper helper = new SpringContextHelper(this);
		authenticator = (Authenticator) helper.getBean("authenticator");
		domainService = (DomainService) helper.getBean("domainService");
		welcomeViewController = (WelcomeViewController) helper
				.getBean("welcomeViewController");
		webViewController = (WebViewController) helper
				.getBean("webViewController");
		mapViewController = (MapViewController) helper
				.getBean("mapViewController");
	}

	@Override
	public void onBrowserDetailsReady() {
		getMainWindow().setContent(new MainTabsheet(welcomeViewController));
	}

	/**
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		try {
			User authUser = authenticator.getUser();
			if (authUser != null) {
				if (currentUser != null
						&& currentUser.getLogin().equals(authUser.getLogin())) {
					return currentUser;
				}
				// for updating
				String uid = authUser.getLogin();
				try {
					currentUser = domainService.getUser(uid);
				} catch (UserNotFoundException e) {
					currentUser = new User();
					currentUser.setLogin(uid);
					currentUser.setDisplayName("Not Found");
					currentUser.setAdmin(false);
				}
				return currentUser;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param invalidate
	 *            the invalidate to set
	 */
	public void setInvalidate(boolean invalidate) {
		this.invalidate = invalidate;
	}

	@Override
	public void onRequestStart(HttpServletRequest request,
			HttpServletResponse response) {
		super.onRequestStart(request, response);
	}

	@Override
	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {
		if (invalidate) {
			HttpSession session = request.getSession(false);
			if (session != null && !session.isNew()) {
				if (logger.isDebugEnabled()) {
					logger.debug("Invalidate session");
				}
				session.invalidate();
			}
		}
	}

	/**
	 * @return the welcomeViewController
	 */
	public WelcomeViewController getWelcomeViewController() {
		return welcomeViewController;
	}

	/**
	 * @param welcomeViewController
	 *            the welcomeViewController to set
	 */
	public void setWelcomeViewController(
			WelcomeViewController welcomeViewController) {
		this.welcomeViewController = welcomeViewController;
	}

	/**
	 * @return the webViewController
	 */
	public WebViewController getWebViewController() {
		return webViewController;
	}

	/**
	 * @param webViewController the webViewController to set
	 */
	public void setWebViewController(WebViewController webViewController) {
		this.webViewController = webViewController;
	}

	/**
	 * @return the mapViewController
	 */
	public MapViewController getMapViewController() {
		return mapViewController;
	}

	/**
	 * @param mapViewController the mapViewController to set
	 */
	public void setMapViewController(MapViewController mapViewController) {
		this.mapViewController = mapViewController;
	}

	/**
	 * @return the mapTargetViewController
	 */
	public MapTargetViewController getMapTargetViewController() {
		return mapTargetViewController;
	}

	/**
	 * @param mapTargetViewController the mapTargetViewController to set
	 */
	public void setMapTargetViewController(
			MapTargetViewController mapTargetViewController) {
		this.mapTargetViewController = mapTargetViewController;
	}

	/**
	 * @return the currentLongitude
	 */
	public double getCurrentLongitude() {
		return currentLongitude;
	}

	/**
	 * @param currentLongitude the currentLongitude to set
	 */
	public void setCurrentLongitude(double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	/**
	 * @return the currentLatitude
	 */
	public double getCurrentLatitude() {
		return currentLatitude;
	}

	/**
	 * @param currentLatitude the currentLatitude to set
	 */
	public void setCurrentLatitude(double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}

}
