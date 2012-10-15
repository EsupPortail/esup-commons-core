/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.urlGeneration;

import java.util.HashMap;
import java.util.Map;

import org.esupportail.commons.utils.strings.StringUtilsWeb;

/**
 * An abstract class that implements UrlGenerator.
 */
@SuppressWarnings("serial")
public abstract class AbstractUrlGenerator implements UrlGenerator {

	/**
	 * The separator between parameters.
	 */
	public static final String PARAMS_SEPARATOR = "&";

	/**
	 * The separator between name and value.
	 */
	public static final String NAME_VALUE_SEPARATOR = "=";

	/**
	 * Bean constructor.
	 */
	protected AbstractUrlGenerator() {
		super();
	}

	/**
	 * Encode parameters into an argument.
	 * @param params
	 * @return the argument
	 */
	public static String encodeParamsToArg(
			final Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			return null;
		}
		String arg = "";
		String separator = "";
		for (String name : params.keySet()) {
			arg = arg + separator + name + NAME_VALUE_SEPARATOR
			+ StringUtilsWeb.utf8UrlEncode(params.get(name));
			separator = PARAMS_SEPARATOR;
		}
		String encodedArg;
		encodedArg = arg;
		return encodedArg;
	}

	/**
	 * Decode arguments into parameters.
	 * @param arg
	 * @return the parameters
	 */
	public static Map<String, String> decodeArgToParams(
			final String arg) {
		if (arg == null) {
			return null;
		}
		String decodedArg;
		decodedArg = arg;
		Map<String, String> params = new HashMap<String, String>();
		for (String param : decodedArg.split(PARAMS_SEPARATOR)) {
			String[] nameValueArray = param.split(NAME_VALUE_SEPARATOR, 2);
			if (nameValueArray.length == 2) {
				params.put(nameValueArray[0], StringUtilsWeb.utf8UrlDecode(nameValueArray[1]));
			} else {
				params.put(nameValueArray[0], "");
			}
		}
		return params;
	}

	/**
	 * @param authType
	 * @param params
	 * @return a link to the application with parameters.
	 */
	protected abstract String url(
			final AuthEnum authType,
			final Map<String, String> params);

	@Override
	public String guestUrl(
			final Map<String, String> params) {
		return url(AuthEnum.application, params);
	}

	@Override
	public String guestUrl() {
		return guestUrl(null);
	}

	@Override
	public String casUrl(
			final Map<String, String> params) {
		return url(AuthEnum.cas, params);
	}

	@Override
	public String casUrl() {
		return casUrl(null);
	}

	@Override
	public String shibbolethUrl(
			final Map<String, String> params) {
		return url(AuthEnum.shibboleth, params);
	}

	@Override
	public String shibbolethUrl() {
		return shibbolethUrl(null);
	}

}
