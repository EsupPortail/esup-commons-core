/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.authentication;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.esupportail.commons.services.authentication.utils.ContextUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * A CAS authenticator.
 */
public class ShibbolethApacheModuleAuthenticationService extends AbstractRealAuthenticationService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 943489018651202646L;

	/**
	 * The default expected end of the request URI.
	 */
	private static final String DEFAULT_ALLOWED_URI_END = "/stylesheets/shibboleth.faces";

	/**
	 * A logger.
	 */
	private final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * The header that holds the id.
	 */
	private String idHeader;

	/**
	 * The Shibboleth attribute headers.
	 */
	private List<String> attributeHeaders;

	/**
	 * The expected end of the request URI.
	 */
	private String allowedUriEnd = DEFAULT_ALLOWED_URI_END;

	/**
	 * Bean constructor.
	 */
	private ShibbolethApacheModuleAuthenticationService() {
	}

	/**
	 * Bean constructor .
	 */
	private ShibbolethApacheModuleAuthenticationService(String idHeader, String attributeHeaders) {
		Assert.hasText(idHeader, "property idHeader of class " + this.getClass().getName()
				+ " can not be null or empty");
		Assert.hasText(attributeHeaders, "property attributeHeaders of class " 
				+ getClass() + " should not be null");
		
		this.idHeader = idHeader;
		this.attributeHeaders = new ArrayList<String>();
		for (String attributeHeader : attributeHeaders.split(",")) {
			if (!this.attributeHeaders.contains(attributeHeader)) {
				this.attributeHeaders.add(attributeHeader);
			}
		}
		Collections.sort(this.attributeHeaders);
	}

	/**
	 * a static factory method
	 */
	public static ShibbolethApacheModuleAuthenticationService createInstance() {
		return new ShibbolethApacheModuleAuthenticationService();
	}

	@Override
	protected String getAuthId() {
		Map<String, List<String>> headers = getRequestHeaders();
		if (headers == null) {
			return null;
		}
		
		List<String> idValues = headers.get(getIdHeader());
		if (idValues == null || idValues.isEmpty()) {
			return null;
		}
		HttpServletRequest request = ((ServletRequestAttributes) ContextUtils.getContextAttributes()).getRequest();
		
		if (request == null) {
			throw new RuntimeException("Possible Shibooleth HTTP headers hacking (null request)");
		}
		
		String uri = request.getRequestURI();
		if (uri == null) {
			throw new RuntimeException("Possible Shibooleth HTTP headers hacking (null URI)");
		}
		
		if (!uri.endsWith(allowedUriEnd)) {
			throw new RuntimeException("Possible Shibooleth HTTP headers hacking (requestURI: " + uri + ")");
		}
		return isoToUTF8(idValues.get(0));
	}

	@Override
	protected Map<String, List<String>> getAuthAttributes() {
		Map<String, List<String>> headers = getRequestHeaders();
		if (headers == null) {
			return null;
		}
		Map<String, List<String>> attributes = new HashMap<String, List<String>>();
		for (String headerName : attributeHeaders) {
			List<String> values = headers.get(headerName);
			if (values != null && !values.isEmpty()) {
				for (int i = 0; i < values.size(); i++) {
					values.set(i, isoToUTF8(values.get(i)));
				}
				attributes.put(headerName, values);
			}
		}
		return attributes;
	}

	@Override
	protected String getAuthType() {
		return AuthConstante.SHIBBOLETH.value();
	}

	/**
	 * @return the idHeader
	 */
	protected String getIdHeader() {
		return idHeader;
	}

	/**
	 * Recodes string from ISO-8859-1 to UTF-8.
	 * @param isoString String in ISO-8859-1
	 * @return String in UTF-8
	 */
	protected String isoToUTF8(final String isoString) {
		String utf8String = null;
		if (null != isoString) {
			try {
				utf8String = new String(isoString.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("Exception while coding HTTP headers from ISO to UTF-8", e);
				utf8String = isoString;
			}
		}
		return utf8String;
	}

	/**
	 * @return the allowedUriEnd
	 */
	protected String getAllowedUriEnd() {
		return allowedUriEnd;
	}

	/**
	 * @param allowedUriEnd the allowedUriEnd to set
	 */
	public void setAllowedUriEnd(final String allowedUriEnd) {
		if (!StringUtils.hasText(allowedUriEnd)) {
			this.allowedUriEnd = null;
		} else {
			this.allowedUriEnd = allowedUriEnd;
		}
	}
	
	/**
	 * @return The request headers.
	 */
	private static Map<String, List<String>> getRequestHeaders() {
		HttpServletRequest request = ((ServletRequestAttributes) ContextUtils.getContextAttributes()).getRequest();
		if (request == null) {
			return null;
		}
		
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames == null) {
			return null;
		}
		
		Map<String, List<String>>  result = new HashMap<String, List<String>>();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			List<String> values = new ArrayList<String>();
			result.put(headerName, values);
			Enumeration<String> headers = request.getHeaders(headerName);
			while (headers.hasMoreElements()) {
				String header = headers.nextElement();
				values.add(header);
			}
		}
		return result;
	}
}
