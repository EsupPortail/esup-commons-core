package org.esupportail.blank.touch.ui;

import java.util.Locale;

import org.esupportail.commons.services.i18n.I18nUtils;

import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.TabSheet.Tab;

public class MainTabsheet extends TabBarView {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 538003394680295304L;

	boolean applicationProtected;
	
	Tab[] addTabs = new Tab[4];

	@Override
	public void attach() {
		super.attach();
	}

	public MainTabsheet(WelcomeViewController welcomeViewController) {
		Locale applicationLocale = TouchKitApplication.get().getLocale();
		ViewsGroupManager navigationManager = new ViewsGroupManager(
				welcomeViewController);
		navigationManager.setCaption(I18nUtils.createI18nService().getString(
				"MENU.WELCOME", applicationLocale));
		addTabs[0] = addTab(navigationManager);
		addTabs[0].setCaption(I18nUtils.createI18nService().getString(
				"MENU.WELCOME", applicationLocale));
		addTabs[0].setIcon(new ThemeResource("mobile/house.png"));
		
		PreferencesViewController preferencesViewController = new PreferencesViewController();
		preferencesViewController.setCaption(I18nUtils.createI18nService()
				.getString("MENU.PREFERENCES", applicationLocale));		
		addTabs[1] = addTab(preferencesViewController);
		addTabs[1].setIcon(new ThemeResource("mobile/asterisk_orange.png"));
		
		AboutViewController aboutView = new AboutViewController(I18nUtils
				.createI18nService().getString("MENU.ABOUT", applicationLocale));
		addTabs[2] = addTab(aboutView);
		addTabs[2].setIcon(new ThemeResource("mobile/information.png"));
		
		HelpViewController helpView = new HelpViewController(I18nUtils
				.createI18nService().getString("MENU.HELP", applicationLocale));
		addTabs[3] = addTab(helpView);
		addTabs[3].setIcon(new ThemeResource("mobile/help.png"));
		
		setSelectedTab(navigationManager);
	}

	/**
	 * @return the applicationProtected
	 */
	public boolean isApplicationProtected() {
		return applicationProtected;
	}

	/**
	 * @param applicationProtected the applicationProtected to set
	 */
	public void setApplicationProtected(boolean applicationProtected) {
		this.applicationProtected = applicationProtected;
	}

	/**
	 * @return the addTabs
	 */
	public Tab[] getAddTabs() {
		return addTabs;
	}

}
