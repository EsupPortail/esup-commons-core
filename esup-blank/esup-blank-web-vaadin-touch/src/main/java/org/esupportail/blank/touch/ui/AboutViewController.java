package org.esupportail.blank.touch.ui;

import java.util.Locale;

import org.esupportail.commons.services.i18n.I18nUtils;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class AboutViewController extends NavigationView {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 2039578663810396735L;

	public AboutViewController(String caption) {
		Locale applicationLocale = TouchKitApplication.get().getLocale();
		CssLayout content = new CssLayout();
		content.setWidth("100%");
        setCaption(I18nUtils.createI18nService().getString("MENU.ABOUT", applicationLocale));
		VerticalComponentGroup componentGroup = new VerticalComponentGroup();
		Label aboutIntro = new Label(I18nUtils.createI18nService().getString("ABOUT.INTRO", applicationLocale),
				Label.CONTENT_XHTML);
		componentGroup.addComponent(aboutIntro);
		content.addComponent(componentGroup);
		this.setContent(content);
	}

}
