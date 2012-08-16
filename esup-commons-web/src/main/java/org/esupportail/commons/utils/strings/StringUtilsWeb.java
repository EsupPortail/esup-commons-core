/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.utils.strings; 

import java.io.IOException;

import org.springframework.web.util.HtmlUtils;



/**
 * A class to convert HTML to text using Swing.
 */
public final class StringUtilsWeb extends StringUtils {   
	
	

	/**
	 * Private constructor.
	 */
	private StringUtilsWeb() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Parse HTML and return text.
	 * @param htmlString an HTML string.
	 * @return a String.
	 * @throws IOException
	 */
	public static String htmlToText(final String htmlString) throws IOException {
		return HtmlToTextParserCallBack.convert(htmlString);
	}

	/**
	 * @param input
	 * @return The input string where special HTML characters have been replaced.
	 */
	public static String escapeHtml(final String input) {
		if (input == null) {
			return null;
		}
		return HtmlUtils.htmlEscape(input);
	}

	
}
