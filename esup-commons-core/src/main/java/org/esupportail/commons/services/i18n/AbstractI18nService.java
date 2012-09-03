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

	@Override
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

	@Override
	public Map<String, String> getStrings() {
		return getStrings(getDefaultLocale());
	}

	@Override
	public String printableRelativeDate(final long date, final Locale locale) {
		return I18nUtils.printableRelativeDate(date, locale);
	}

	@Override
	public String printableRelativeDate(final long date) {
		return printableRelativeDate(date, getDefaultLocale());
	}

	@Override
	public String printableDate(final long date, final Locale locale) {
		return I18nUtils.printableDate(date, locale);
	}

	@Override
	public String printableDate(final long date) {
		return printableDate(date, getDefaultLocale());
	}

	@Override
	public String getString(final String key, final Locale locale) {
		return getStrings(locale).get(key);
	}

	@Override
	public String getString(
			final String key) {
		return getString(key, getDefaultLocale());
	}

	@Override
	public String getString(
			final String key,
			final Locale locale,
			final Object... args) {
		String string = getString(key, locale);
		MessageFormat mf = new MessageFormat(string, locale);
		return mf.format(args);
	}

	@Override
	public String getString(
			final String key,
			final Object... args) {
		return getString(key, getDefaultLocale(), args);
	}

}

