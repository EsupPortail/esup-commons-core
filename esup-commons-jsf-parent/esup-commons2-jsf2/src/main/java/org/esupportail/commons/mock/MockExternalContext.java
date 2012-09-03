/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mock;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * An external context mock.
 *
 */
public class MockExternalContext extends ExternalContext {

	/**
	 * The servlet context.
	 */
	private ServletContext context;

	/**
	 * The servlet request.
	 */
	private ServletRequest request;

	/**
	 * The servlet response.
	 */
	private ServletResponse response;

	/**
	 * The application map.
	 */
	private Map applicationMap;

	/**
	 * The session map.
	 */
	private Map sessionMap;

	/**
	 * The request parameter map.
	 */
	private Map requestParameterMap;

	/**
	 * Constructor.
	 * @param context
	 * @param request
	 * @param response
	 */
	public MockExternalContext(
			final ServletContext context,
			final ServletRequest request,
			final ServletResponse response) {
		this.context = context;
		this.request = request;
		this.response = response;
	}

	@Override
	public Object getSession(final boolean create) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getContext() {
		return context;
	}

	@Override
	public Object getRequest() {
		return request;
	}

	@Override
	public void setRequest(final Object request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getResponse() {
		return response;
	}

	@Override
	public void setResponse(final Object response) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setResponseCharacterEncoding(final String encoding) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map getApplicationMap() {
		if (applicationMap == null) {
			return new HashMap();
		}
		return applicationMap;
	}

	/**
	 * @param applicationMap
	 */
	public void setApplicationMap(final Map applicationMap) {
		this.applicationMap = applicationMap;
	}

	@Override
	public Map getSessionMap() {
		if (sessionMap == null) {
			sessionMap = new HashMap();
		}
		return sessionMap;

	}

	@Override
	public Map getRequestMap() {
		/*
		 * if (requestMap == null) { requestMap = new MockRequestMap(request); }
		 * return (requestMap);
		 */
		return null;
	}

	@Override
	public Map getRequestParameterMap() {
		if (requestParameterMap != null) {
			return requestParameterMap;
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * @param requestParameterMap
	 */
	public void setRequestParameterMap(final Map requestParameterMap) {
		this.requestParameterMap = requestParameterMap;
	}

	@Override
	public void setRequestCharacterEncoding(final String encoding) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map getRequestParameterValuesMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator getRequestParameterNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map getRequestHeaderMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map getRequestHeaderValuesMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map getRequestCookieMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Locale getRequestLocale() {
		return request.getLocale();
	}

	@Override
	public Iterator getRequestLocales() {
		return new LocalesIterator(request.getLocales());
	}

	@Override
	public String getRequestPathInfo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestContextPath() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestServletPath() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestCharacterEncoding() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestContentType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getResponseCharacterEncoding() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getResponseContentType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getInitParameter(final String name) {
		if (name.equals(javax.faces.application.StateManager.STATE_SAVING_METHOD_PARAM_NAME)) {
			return null;
		}
		if (name.equals(javax.faces.webapp.FacesServlet.LIFECYCLE_ID_ATTR)) {
			return null;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Map getInitParameterMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set getResourcePaths(final String path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getResource(final String path) throws MalformedURLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getResourceAsStream(final String path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeActionURL(final String sb) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeResourceURL(final String sb) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeNamespace(final String aValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void dispatch(final String requestURI) throws FacesException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void redirect(final String requestURI) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void log(final String message) {
		context.log(message);
	}

	@Override
	public void log(final String message, final Throwable throwable) {
		context.log(message, throwable);
	}

	@Override
	public String getAuthType() {
		return ((HttpServletRequest) request).getAuthType();
	}

	@Override
	public String getRemoteUser() {
		return ((HttpServletRequest) request).getRemoteUser();
	}

	@Override
	public java.security.Principal getUserPrincipal() {
		return ((HttpServletRequest) request).getUserPrincipal();
	}

	@Override
	public boolean isUserInRole(final String role) {
		return ((HttpServletRequest) request).isUserInRole(role);
	}

	/**
	 * An iterator for Locale.
	 */
	private class LocalesIterator implements Iterator {

		/**
		 * The locales.
		 */
		private Enumeration locales;

		/**
		 * Constructor.
		 * @param locales
		 */
		public LocalesIterator(final Enumeration locales) {
			this.locales = locales;
		}

		@Override
		public boolean hasNext() {
			return locales.hasMoreElements();
		}

		@Override
		public Object next() {
			return locales.nextElement();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}