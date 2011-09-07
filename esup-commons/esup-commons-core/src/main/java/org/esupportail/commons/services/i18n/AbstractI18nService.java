/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * An abstract class that should be inherited by all the implementations 
 * of I18nService.
 */
@SuppressWarnings("serial")
public abstract class AbstractI18nService implements I18nService {
	
	/**
	 * Bean constructor.
	 */
	protected AbstractI18nService() {
		super();
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getDefaultLocale()
	 */
	public Locale getDefaultLocale() {
		return I18nUtils.getDefaultLocale();
	}

	/**
	 * @param bundleBasename 
	 * @param locale
	 * @return The resource bundle corresponding to a Locale.
	 */
	protected synchronized ResourceBundle getResourceBundle(
			final String bundleBasename,
			final Locale locale) {
		return I18nUtils.getResourceBundle(bundleBasename, locale);
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getStrings()
	 */
	public Map<String, String> getStrings() {
		return getStrings(getDefaultLocale());
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#printableRelativeDate(long, java.util.Locale)
	 */
	public String printableRelativeDate(final long date, final Locale locale) {
		return I18nUtils.printableRelativeDate(date, locale);
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#printableRelativeDate(long)
	 */
	public String printableRelativeDate(final long date) {
		return printableRelativeDate(date, getDefaultLocale());
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#printableDate(long, java.util.Locale)
	 */
	public String printableDate(final long date, final Locale locale) {
		return I18nUtils.printableDate(date, locale);		
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#printableDate(long)
	 */
	public String printableDate(final long date) {
		return printableDate(date, getDefaultLocale());		
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getString(java.lang.String, java.util.Locale)
	 */
	public String getString(final String key, final Locale locale) {
		return getStrings(locale).get(key);
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getString(java.lang.String)
	 */
	public String getString(
			final String key) {
		return getString(key, getDefaultLocale());
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getString(
	 * java.lang.String, java.util.Locale, java.lang.Object[])
	 */
	public String getString(
			final String key, 
			final Locale locale, 
			final Object... args) {
		String string = getString(key, locale);
		MessageFormat mf = new MessageFormat(string, locale);
		return mf.format(args);
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getString(java.lang.String, java.lang.Object[])
	 */
	public String getString(
			final String key, 
			final Object... args) {
		return getString(key, getDefaultLocale(), args);
	}

}

