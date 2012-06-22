package org.esupportail.blank.touch.ui;

import java.util.Locale;

import org.esupportail.blank.domain.beans.User;
import org.esupportail.blank.touch.Application;
import org.esupportail.blank.touch.ProtectedApplication;
import org.esupportail.commons.services.i18n.I18nUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import com.vaadin.addon.touchkit.ui.NavigationBar;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;


public class WelcomeViewController extends NavigationView implements
		ClickListener {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 7552813173404391990L;

	/**
	 * For Logging.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * Version from configuration..
	 */
	private String version;

	/**
	 * Cas Logout Url.
	 */
	private String casLogoutUrl;

	/**
	 * Constructor.
	 */
	public WelcomeViewController() {
		super();
	}

	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();
	}

	@Override
	public void attach() {
		super.attach();
		buildView();
	}

	private void buildView() {
		// Tool Bar
		Locale applicationLocale = TouchKitApplication.get().getLocale();
		String uid = null;
		if (!TouchKitApplication.get().getClass().getSimpleName().equals("Application")) {
			User currentUser = ((ProtectedApplication) getApplication())
					.getCurrentUser();
			if (currentUser != null) {
				uid = (currentUser.getLogin());
			}
		}
		CssLayout content = new CssLayout();
		content.setWidth("100%");
		setCaption(I18nUtils.createI18nService().getString("MENU.WELCOME",
				applicationLocale));
		Button loginButton;
		if (uid == null) {
			loginButton = new Button(I18nUtils.createI18nService().getString(
					"MENU.LOGIN", applicationLocale), this);
		} else {
			loginButton = new Button(I18nUtils.createI18nService().getString(
					"MENU.LOGOUT", applicationLocale), this);
		}
		NavigationBar navigationBar = this.getNavigationBar();
		navigationBar.setRightComponent(loginButton);
		
		// Introduction
		VerticalComponentGroup componentGroup = new VerticalComponentGroup();
		Label welcomeIntro = null;
		if (uid == null) {
			welcomeIntro = new Label(I18nUtils.createI18nService().getString(
					"WELCOME.ANONYMOUS_INTRO", applicationLocale, version),
					Label.CONTENT_XHTML);
		} else {
			welcomeIntro = new Label(I18nUtils.createI18nService().getString(
					"WELCOME.AUTHENTIFIED_INTRO", applicationLocale, uid,
					version), Label.CONTENT_XHTML);
		}
		componentGroup.addComponent(welcomeIntro);
		content.addComponent(componentGroup);
		
        /*
         * Populate sub views
         */
		VerticalComponentGroup menuGroup = new VerticalComponentGroup();
		menuGroup.setCaption(I18nUtils.createI18nService().getString("WELCOME.MENU", applicationLocale));

		// Web view
		WebViewController webView;
		if (TouchKitApplication.get().getClass().getSimpleName().equals("Application")) {
			webView = ((Application) getApplication()).getWebViewController();
		} else {
			webView = ((ProtectedApplication) getApplication()).getWebViewController();
		}
		NavigationButton webViewButton = new NavigationButton(I18nUtils.createI18nService().getString("WEBVIEW.TEXT", applicationLocale));
		webViewButton.setTargetView(webView);
		menuGroup.addComponent(webViewButton);
		
		// Map view
		MapViewController mapView;
		if (TouchKitApplication.get().getClass().getSimpleName().equals("Application")) {
			mapView = ((Application) getApplication()).getMapViewController();
		} else {
			mapView = ((ProtectedApplication) getApplication()).getMapViewController();
		}
		NavigationButton mapViewButton = new NavigationButton(I18nUtils.createI18nService().getString("MAPS.TEXT", applicationLocale));
		mapViewButton.setTargetView(mapView);
		menuGroup.addComponent(mapViewButton);
		
		content.addComponent(menuGroup);	        
	        
		this.setContent(content);
		
	}

	public void buttonClick(ClickEvent event) {
		if (TouchKitApplication.get().getClass().getSimpleName().equals("Application")) {
			((Application) getApplication())
					.setLogoutURL("/protected/?RestartApplication=true");
			((Application) getApplication()).close();
		} else {
			((ProtectedApplication) getApplication())
					.setLogoutURL(casLogoutUrl);
			((ProtectedApplication) getApplication()).setInvalidate(true);
			if (logger.isDebugEnabled()) {
				logger.debug("On y passe Déconnexion");
			}
			((ProtectedApplication) getApplication()).close();
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
	 * @return the casLogoutUrl
	 */
	public String getCasLogoutUrl() {
		return casLogoutUrl;
	}

	/**
	 * @param casLogoutUrl
	 *            the casLogoutUrl to set
	 */
	public void setCasLogoutUrl(String casLogoutUrl) {
		this.casLogoutUrl = casLogoutUrl;
	}

}
