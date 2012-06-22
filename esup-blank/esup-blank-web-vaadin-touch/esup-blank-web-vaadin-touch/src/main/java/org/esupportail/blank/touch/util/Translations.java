package org.esupportail.blank.touch.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translations {

	private static Locale[] locales = new Locale[] {
		Locale.ENGLISH, new Locale("fr"), new Locale("en")
	};

	public static Locale[] getAvailableLocales() {
		return locales;
	}
	
	public static ResourceBundle get(Locale locale) {
		if(locale == null) {
			locale = Locale.FRENCH;
		}
		return ResourceBundle.getBundle("Messages", locale);
	}

}
