/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.utils.strings;

import java.util.Hashtable;
import java.util.Map;

/**
 * A class to decode HTML entities to UTF-8.
 */
public class NcrDecoder {

	/** Magic number. */
	private static final int TEN = 10;
	/** Magic number. */
	private static final int SIXTEEN = 16;

	/**
	 * The entities to decode.
	 */
	private static Map<String, String> entities;

	/**
	 * Constructor.
	 */
	private NcrDecoder() {
		super();
	}

	/**
	 * @return the entities to decode.
	 */
	private static synchronized Map<String, String> getEntities() {
		if (entities == null) {
			entities = new Hashtable<String, String>();
			entities.put("quot", "\"");
			entities.put("amp", "&amp;");
			entities.put("lt", "&lt;");
			entities.put("gt", "&gt;");
			entities.put("nbsp", " ");
			entities.put("iexcl", "�");
			entities.put("cent", "�");
			entities.put("pound", "�");
			entities.put("curren", "�");
			entities.put("yen", "�");
			entities.put("brvbar", "�");
			entities.put("sect", "�");
			entities.put("uml", "�");
			entities.put("copy", "�");
			entities.put("ordf", "�");
			entities.put("laquo", "�");
			entities.put("not", "�");
			entities.put("shy", "�");
			entities.put("reg", "�");
			entities.put("macr", "�");
			entities.put("deg", "�");
			entities.put("plusmn", "�");
			entities.put("sup2", "�");
			entities.put("sup3", "�");
			entities.put("acute", "�");
			entities.put("micro", "�");
			entities.put("para", "�");
			entities.put("middot", "�");
			entities.put("cedil", "�");
			entities.put("sup1", "�");
			entities.put("ordm", "�");
			entities.put("raquo", "�");
			entities.put("frac14", "�");
			entities.put("frac12", "�");
			entities.put("frac34", "�");
			entities.put("iquest", "�");
			entities.put("Agrave", "�");
			entities.put("Aacute", "�");
			entities.put("Acirc", "�");
			entities.put("Atilde", "�");
			entities.put("Auml", "�");
			entities.put("Aring", "�");
			entities.put("AElig", "�");
			entities.put("Ccedil", "�");
			entities.put("Egrave", "�");
			entities.put("Eacute", "�");
			entities.put("Ecirc", "�");
			entities.put("Euml", "�");
			entities.put("Igrave", "�");
			entities.put("Iacute", "�");
			entities.put("Icirc", "�");
			entities.put("Iuml", "�");
			entities.put("ETH", "�");
			entities.put("Ntilde", "�");
			entities.put("Ograve", "�");
			entities.put("Oacute", "�");
			entities.put("Ocirc", "�");
			entities.put("Otilde", "�");
			entities.put("Ouml", "�");
			entities.put("times", "�");
			entities.put("Oslash", "�");
			entities.put("Ugrave", "�");
			entities.put("Uacute", "�");
			entities.put("Ucirc", "�");
			entities.put("Uuml", "�");
			entities.put("Yacute", "�");
			entities.put("THORN", "�");
			entities.put("szlig", "�");
			entities.put("agrave", "�");
			entities.put("aacute", "�");
			entities.put("acirc", "�");
			entities.put("atilde", "�");
			entities.put("auml", "�");
			entities.put("aring", "�");
			entities.put("aelig", "�");
			entities.put("ccedil", "�");
			entities.put("egrave", "�");
			entities.put("eacute", "�");
			entities.put("ecirc", "�");
			entities.put("euml", "�");
			entities.put("igrave", "�");
			entities.put("iacute", "�");
			entities.put("icirc", "�");
			entities.put("iuml", "�");
			entities.put("eth", "�");
			entities.put("ntilde", "�");
			entities.put("ograve", "�");
			entities.put("oacute", "�");
			entities.put("ocirc", "�");
			entities.put("otilde", "�");
			entities.put("ouml", "�");
			entities.put("divide", "�");
			entities.put("oslash", "�");
			entities.put("ugrave", "�");
			entities.put("uacute", "�");
			entities.put("ucirc", "�");
			entities.put("uuml", "�");
			entities.put("yacute", "�");
			entities.put("thorn", "�");
			entities.put("yuml", "�");
			entities.put("apos", "'");
		}
		return entities;
	}

//	/**
//	 * @return the entities to decode.
//	 */
//	private static synchronized Map<String, String> getEntities() {
//		if (entities == null) {
//			entities = new Hashtable<String, String>();
//			//Quotation mark
//			entities.put("quot", "\"");
//			//Ampersand
//			entities.put("amp", "\u0026");
//			//Less than
//			entities.put("lt", "\u003C");
//			//Greater than
//			entities.put("gt", "\u003E");
//			//Nonbreaking space
//			entities.put("nbsp", "\u00A0");
//			//Inverted exclamation point
//			entities.put("iexcl", "\u00A1");
//			//Cent sign
//			entities.put("cent", "\u00A2");
//			//Pound sign
//			entities.put("pound", "\u00A3");
//			//General currency sign
//			entities.put("curren", "\u00A4");
//			//Yen sign
//			entities.put("yen", "\u00A5");
//			//Broken vertical bar
//			entities.put("brvbar", "\u00A6");
//			//Section sign
//			entities.put("sect", "\u00A7");
//			//Umlaut
//			entities.put("uml", "\u00A8");
//			//Copyright
//			entities.put("copy", "\u00A9");
//			//Feminine ordinal
//			entities.put("ordf", "\u00AA");
//			//Left angle quote
//			entities.put("laquo", "\u00AB");
//			//Not sign
//			entities.put("not", "\u00AC");
//			//Soft hyphen
//			entities.put("shy", "\u00AD");
//			//Registered trademark
//			entities.put("reg", "\u00AE");
//			//Macron accent
//			entities.put("macr", "\u00AF");
//			//Degree sign
//			entities.put("deg", "\u00B0");
//			//Plus or minus
//			entities.put("plusmn", "\u00B1");
//			//Superscript 2
//			entities.put("sup2", "\u00B2");
//			//Superscript 3
//			entities.put("sup3", "\u00B3");
//			//Acute accent
//			entities.put("acute", "\u00B4");
//			//Micro sign (Greek mu)
//			entities.put("micro", "\u00B5");
//			//Paragraph sign
//			entities.put("para", "\u00B6");
//			//Middle dot
//			entities.put("middot", "\u00B7");
//			//Cedilla
//			entities.put("cedil", "\u00B8");
//			//Superscript 1
//			entities.put("sup1", "\u00B9");
//			//Masculine ordinal
//			entities.put("ordm", "\u00BA");
//			//Right angle quote
//			entities.put("raquo", "\u00BB");
//			//Fraction one-fourth
//			entities.put("frac14", "\u00BC");
//			//Fraction one-half
//			entities.put("frac12", "\u00BD");
//			//Fraction three-fourths
//			entities.put("frac34", "\u00BE");
//			//Inverted question mark
//			entities.put("iquest", "\u00BF");
//			//Capital A, grave accent
//			entities.put("Agrave", "\u00C0");
//			//Capital A, acute accent
//			entities.put("Aacute", "\u00C1");
//			//Capital A, circumflex accent
//			entities.put("Acirc", "\u00C2");
//			//Capital A, tilde
//			entities.put("Atilde", "\u00C3");
//			//Capital A, umlaut
//			entities.put("Auml", "\u00C4");
//			//Capital A, ring
//			entities.put("Aring", "\u00C5");
//			//Capital AE ligature
//			entities.put("AElig", "\u00C6");
//			//Capital C, cedilla
//			entities.put("Ccedil", "\u00C7");
//			//Capital E, grave accent
//			entities.put("Egrave", "\u00C8");
//			//Capital E, acute accent
//			entities.put("Eacute", "\u00C9");
//			//Capital E, circumflex accent
//			entities.put("Ecirc", "\u00CA");
//			//Capital E, umlaut
//			entities.put("Euml", "\u00CB");
//			//Capital I, grave accent
//			entities.put("Igrave", "\u00CC");
//			//Capital I, acute accent
//			entities.put("Iacute", "\u00CD");
//			//Capital I, circumflex accent
//			entities.put("Icirc", "\u00CE");
//			//Capital I, umlaut
//			entities.put("Iuml", "\u00CF");
//			//Capital eth, Icelandic
//			entities.put("ETH", "\u00D0");
//			//Capital N, tilde
//			entities.put("Ntilde", "\u00D1");
//			//Capital O, grave accent
//			entities.put("Ograve", "\u00D2");
//			//Capital O, acute accent
//			entities.put("Oacute", "\u00D3");
//			//Capital O, circumflex accent
//			entities.put("Ocirc", "\u00D4");
//			//Capital O, tilde
//			entities.put("Otilde", "\u00D5");
//			//Capital O, umlaut
//			entities.put("Ouml", "\u00D6");
//			//Multiply sign
//			entities.put("times", "\u00D7");
//			//Capital O, slash
//			entities.put("Oslash", "\u00D8");
//			//Capital U, grave accent
//			entities.put("Ugrave", "\u00D9");
//			//Capital U, acute accent
//			entities.put("Uacute", "\u00DA");
//			//Capital U, circumflex accent
//			entities.put("Ucirc", "\u00DB");
//			//Capital U, umlaut
//			entities.put("Uuml", "\u00DC");
//			//Capital Y, acute accent
//			entities.put("Yacute", "\u00DD");
//			//Capital thorn, Icelandic
//			entities.put("THORN", "\u00DE");
//			//Small sz ligature, German
//			entities.put("szlig", "\u00DF");
//			//Small a, grave accent
//			entities.put("agrave", "\u00E0");
//			//Small a, acute accent
//			entities.put("aacute", "\u00E1");
//			//Small a, circumflex accent
//			entities.put("acirc", "\u00E2");
//			//Small a, tilde
//			entities.put("atilde", "\u00E3");
//			//Small a, umlaut
//			entities.put("auml", "\u00E4");
//			//Small a, ring
//			entities.put("aring", "\u00E5");
//			//Small ae ligature
//			entities.put("aelig", "\u00E6");
//			//Small c, cedilla
//			entities.put("ccedil", "\u00E7");
//			//Small e, grave accent
//			entities.put("egrave", "\u00E8");
//			//Small e, acute accent
//			entities.put("eacute", "\u00E9");
//			//Small e, circumflex accent
//			entities.put("ecirc", "\u00EA");
//			//Small e, umlaut
//			entities.put("euml", "\u00EB");
//			//Small i, grave accent
//			entities.put("igrave", "\u00EC");
//			//Small i, acute accent
//			entities.put("iacute", "\u00ED");
//			//Small i, circumflex accent
//			entities.put("icirc", "\u00EE");
//			//Small i, umlaut
//			entities.put("iuml", "\u00EF");
//			//Small eth, Icelandic
//			entities.put("eth", "\u00F0");
//			//Small n, tilde
//			entities.put("ntilde", "\u00F1");
//			//Small o, grave accent
//			entities.put("ograve", "\u00F2");
//			//Small o, acute accent
//			entities.put("oacute", "\u00F3");
//			//Small o, circumflex accent
//			entities.put("ocirc", "\u00F4");
//			//Small o, tilde
//			entities.put("otilde", "\u00F5");
//			//Small o, umlaut
//			entities.put("ouml", "\u00F6");
//			//Division sign
//			entities.put("divide", "\u00F7");
//			//Small o, slash
//			entities.put("oslash", "\u00F8");
//			//Small u, grave accent
//			entities.put("ugrave", "\u00F9");
//			//Small u, acute accent
//			entities.put("uacute", "\u00FA");
//			//Small u, circumflex accent
//			entities.put("ucirc", "\u00FB");
//			//Small u, umlaut
//			entities.put("uuml", "\u00FC");
//			//Small y, acute accent
//			entities.put("yacute", "\u00FD");
//			//Small thorn, Icelandic
//			entities.put("thorn", "\u00FE");
//			//Small y, umlaut
//			entities.put("yuml", "\u00FF");
//			//apos
//			entities.put("apos", "'");
//		}
//		return entities;
//	}
//
	/**
	 * Decode a String from HTML to UTF-8.
	 * @param str
	 * @return the decoded string.
	 */
	public static String decode(final String str) {
		if (str == null) {
			return null;
		}
		StringBuffer ostr = new StringBuffer();
		int i1 = 0;
		int i2 = 0;
		while (i2 < str.length()) {
			i1 = str.indexOf("&", i2);
			if (i1 == -1 ) {
				ostr.append(str.substring(i2, str.length()));
				break;
			}
			ostr.append(str.substring(i2, i1));
			i2 = str.indexOf(";", i1);
			if (i2 == -1 ) {
				ostr.append(str.substring(i1, str.length()));
				break;
			}
			String tok = str.substring(i1 + 1, i2);
			if (tok.charAt(0) == '#') {
				tok = tok.substring(1);
				try {
					int radix = TEN;
					if (tok.trim().charAt(0) == 'x') {
						radix = SIXTEEN;
						tok = tok.substring(1, tok.length());
					}
					ostr.append((char) Integer.parseInt(tok, radix));
				} catch (NumberFormatException exp) {
					ostr.append('?');
				}
			} else {
				String decodedEntity = getEntities().get(tok);
				if (decodedEntity != null) {
					ostr.append(decodedEntity);
				} else {
					ostr.append("???&" + tok + ";???");
				}
			}
			i2++;
		}
		return ostr.toString();
	}

	/**
	 * @param args
	 */
	public static void main(final String [] args) {
		System.err.println(NcrDecoder.decode("Le webmail de l&apos;universit&#233; ..."));
	}
	
}

