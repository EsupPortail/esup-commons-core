/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.i18n;

import java.io.Serializable;
import java.util.Locale; 
import java.util.Map;

/**
 * The interface of i18n services. An i18n service is a layer to abstract
 * resources bundles, that should never be accessed directly.
 */
public interface I18nService extends Serializable {

	/**
	 * @return The default locale.
	 */
	Locale getDefaultLocale();
	
	/**
	 * @return A map that contains the strings available for a locale.
	 * @param locale
	 */
	Map<String, String> getStrings(Locale locale);

	/**
	 * @return A map that contains the strings available for the default locale.
	 */
	Map<String, String> getStrings();

	/**
	 * @return The string that corresponds to a key and a locale.
	 * @param key
	 * @param locale
	 */
	String getString(String key, Locale locale);

	/**
	 * @return The string that corresponds to a key for the default locale.
	 * @param key
	 */
	String getString(String key);

	/**
	 * @return The string that corresponds to a key and a locale, where {0} ... {n} are replaced by args.
	 * @param key
	 * @param locale
	 * @param args
	 */
	String getString(String key, Locale locale, Object... args);

	/**
	 * @return The string that corresponds to a key for the default locale, where {0} ... {n} are replaced by args.
	 * @param key
	 * @param args
	 */
	String getString(String key, Object... args);

}
