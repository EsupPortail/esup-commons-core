package org.esupportail.blank.touch.ui;

import com.vaadin.addon.touchkit.ui.NavigationManager;

public class ViewsGroupManager extends NavigationManager {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -5934608894249425305L;
	
	@Override
	public void attach() {
		super.attach();
	}

	public ViewsGroupManager(WelcomeViewController welcomeViewController) {
		navigateTo(welcomeViewController);
	}

}
