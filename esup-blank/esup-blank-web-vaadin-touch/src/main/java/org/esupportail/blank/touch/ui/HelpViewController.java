package org.esupportail.blank.touch.ui;

import java.util.Locale;

import org.esupportail.commons.services.i18n.I18nUtils;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class HelpViewController extends NavigationView {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -2207428199668856923L;

	public HelpViewController(String caption) {
		Locale applicationLocale = TouchKitApplication.get().getLocale();
		CssLayout content = new CssLayout();
		content.setWidth("100%");
        setCaption(I18nUtils.createI18nService().getString("MENU.HELP", applicationLocale));
		VerticalComponentGroup componentGroup = new VerticalComponentGroup();
		Label helpIntro = new Label(I18nUtils.createI18nService().getString("HELP.INTRO", applicationLocale), Label.CONTENT_XHTML);
        componentGroup.addComponent(helpIntro);
		content.addComponent(componentGroup);
		this.setContent(content);
    }	

}
