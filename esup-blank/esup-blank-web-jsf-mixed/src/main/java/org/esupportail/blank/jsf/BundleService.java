/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.jsf;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public class BundleService {

	/**
	 * @param key
	 * @return a String in current locale.
	 */
	public static String getString(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context == null) {
			Locale theLocale = Locale.getDefault();
			ResourceBundle bundle = ResourceBundle.getBundle(
					"properties.i18n.bundles.Messages", theLocale);
			String text = getMessageResourceString(bundle, key, null, theLocale);
			return text;
		}
		String text = getMessageResourceString(context.getApplication()
				.getResourceBundle(context, "msgs"), key, null, context
				.getViewRoot().getLocale());
		return text;
	}

	/**
	 * @param key
	 * @param params
	 * @return a String in current locale with parameter(s).
	 */
	public static String getString(String key, Object[] params) {
		FacesContext context = FacesContext.getCurrentInstance();
		String text = getMessageResourceString(context.getApplication()
				.getResourceBundle(context, "msgs"), key, params, context
				.getViewRoot().getLocale());
		return text;
	}

	/**
	 * @param resourceBundle
	 * @param key
	 * @param params
	 * @param locale
	 * @return a String for a key and for a locale.
	 */
	private static String getMessageResourceString(
			ResourceBundle resourceBundle, String key, Object params[],
			Locale locale) {
		String text = null;
		try {
			text = resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			text = "?? key " + key + " not found ??";
		}
		if (params != null) {
			MessageFormat mf = new MessageFormat(text, locale);
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}

}
