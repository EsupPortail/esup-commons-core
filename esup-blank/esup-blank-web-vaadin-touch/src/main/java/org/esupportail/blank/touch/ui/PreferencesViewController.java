package org.esupportail.blank.touch.ui;

import java.util.Locale;

import org.esupportail.blank.touch.util.Translations;
import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.services.i18n.I18nUtils;
import org.springframework.context.ApplicationContext;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;

public class PreferencesViewController extends NavigationView {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 9037367124804682759L;

	/**
	 * Constructor.
	 */
	PreferencesViewController() {
		super();
	}

	@Override
	public void attach() {
		super.attach();
		buildView();
	}

	private void buildView() {
		Locale applicationLocale = TouchKitApplication.get().getLocale();
		CssLayout content = new CssLayout();
		content.setWidth("100%");
		setCaption(I18nUtils.createI18nService().getString("MENU.PREFERENCES",
				applicationLocale));
		Label preferencesIntro = new Label(I18nUtils.createI18nService()
				.getString("PREFERENCES.INTRO", applicationLocale),
				Label.CONTENT_XHTML);
		VerticalComponentGroup componentGroup = new VerticalComponentGroup();
		componentGroup.addComponent(preferencesIntro);
		content.addComponent(componentGroup);
		Locale[] availableLocales = Translations.getAvailableLocales();
		final OptionGroup languageSelect = new OptionGroup();
		for (Locale locale : availableLocales) {
			languageSelect.addItem(locale);
			languageSelect.setItemCaption(locale,
					locale.getDisplayLanguage(locale));
			if (locale.getLanguage().equals(applicationLocale.getLanguage())) {
				languageSelect.setValue(locale);
			}
		}
		languageSelect.addListener(new Property.ValueChangeListener() {
			/**
			 * For Serialize.
			 */
			private static final long serialVersionUID = 7702174854445433875L;

			public void valueChange(ValueChangeEvent event) {
				/*
				 * Language has changed. Set it to the application and then
				 * rebuild the whole UI by assigning new MainTabsheet to window.
				 */
				TouchKitApplication.get().setLocale(
						(Locale) event.getProperty().getValue());
				ApplicationContext springContext = ApplicationContextHolder
						.getContext();
					WelcomeViewController welcomeViewController = (WelcomeViewController) springContext
							.getBean(("welcomeViewController"));
					TouchKitApplication
							.get()
							.getMainWindow()
							.setContent(new MainTabsheet(welcomeViewController));
			}
		});
		languageSelect.setImmediate(true);
		componentGroup.addComponent(languageSelect);
		content.addComponent(componentGroup);
		this.setContent(content);
	}

}
