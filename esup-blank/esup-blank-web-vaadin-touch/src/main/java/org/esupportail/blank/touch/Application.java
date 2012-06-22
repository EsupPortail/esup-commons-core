package org.esupportail.blank.touch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esupportail.blank.touch.ui.MainTabsheet;

import org.esupportail.blank.touch.ui.MapTargetViewController;
import org.esupportail.blank.touch.ui.MapViewController;
import org.esupportail.blank.touch.ui.WebViewController;
import org.esupportail.blank.touch.ui.WelcomeViewController;
import org.esupportail.blank.touch.util.SpringContextHelper;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;

/**
 * The Application's "main" class
 */
@Configurable
public class Application extends TouchKitApplication implements
		HttpServletRequestListener {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -2900547265157356445L;

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final Logger logger = new LoggerImpl(getClass());

	static CustomizedSystemMessages customizedSystemMessages = new CustomizedSystemMessages();
	static {
		customizedSystemMessages.setSessionExpiredNotificationEnabled(false);
	}

	/**
	 * The Welcome View Controller.
	 */
	private WelcomeViewController welcomeViewController;

	/**
	 * The Web View Controller.
	 */
	private WebViewController webViewController;

	/**
	 * The Map View Controller.
	 */
	private MapViewController mapViewController;

	/**
	 * The Map Target View Controller.
	 */
	private MapTargetViewController mapTargetViewController;

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
		linkViews();
		setMainWindow(new MainWindow());
	}

	private void linkViews() {
		// Link with Views
		SpringContextHelper helper = new SpringContextHelper(this);
		welcomeViewController = (WelcomeViewController) helper
				.getBean("welcomeViewController");
		webViewController = (WebViewController) helper
				.getBean("webViewController");

		mapViewController = (MapViewController) helper
				.getBean("mapViewController");

		mapTargetViewController = (MapTargetViewController) helper
				.getBean("mapTargetViewController");

		mapTargetViewController = (MapTargetViewController) helper
				.getBean("mapTargetViewController");

	}

	@Override
	public void onBrowserDetailsReady() {
		getMainWindow().setContent(new MainTabsheet(welcomeViewController));
	}

	@Override
	public void onRequestStart(HttpServletRequest request,
			HttpServletResponse response) {
		super.onRequestStart(request, response);
	}

	@Override
	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {
	}

	/**
	 * @return the webViewController
	 */
	public WebViewController getWebViewController() {
		return webViewController;
	}

	/**
	 * @param webViewController
	 *            the webViewController to set
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
	 * @param mapViewController
	 *            the mapViewController to set
	 */
	public void setMapViewController(MapViewController mapViewController) {
		this.mapViewController = mapViewController;
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
	 * @return the mapTargetViewController
	 */
	public MapTargetViewController getMapTargetViewController() {
		return mapTargetViewController;
	}

	/**
	 * @param mapTargetViewController
	 *            the mapTargetViewController to set
	 */
	public void setMapTargetViewController(
			MapTargetViewController mapTargetViewController) {
		this.mapTargetViewController = mapTargetViewController;
	}

}
