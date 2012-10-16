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
	private Map<String, Object> applicationMap;

	/**
	 * The session map.
	 */
	private Map<String, Object> sessionMap;

	/**
	 * The request parameter map.
	 */
	private Map<String, String> requestParameterMap;

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
	public Map<String, Object> getApplicationMap() {
		if (applicationMap == null) {
			return new HashMap<String, Object>();
		}
		return applicationMap;
	}

	/**
	 * @param applicationMap
	 */
	public void setApplicationMap(final Map<String, Object> applicationMap) {
		this.applicationMap = applicationMap;
	}

	@Override
	public Map<String, Object> getSessionMap() {
		if (sessionMap == null) {
			sessionMap = new HashMap<String, Object>();
		}
		return sessionMap;

	}

	@Override
	public Map<String, Object> getRequestMap() {
		/*
		 * if (requestMap == null) { requestMap = new MockRequestMap(request); }
		 * return (requestMap);
		 */
		return null;
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		if (requestParameterMap != null) {
			return requestParameterMap;
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * @param requestParameterMap
	 */
	public void setRequestParameterMap(final Map<String, String> requestParameterMap) {
		this.requestParameterMap = requestParameterMap;
	}

	@Override
    public void setRequestCharacterEncoding(final String encoding) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String[]> getRequestParameterValuesMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<String> getRequestParameterNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String> getRequestHeaderMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String[]> getRequestHeaderValuesMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getRequestCookieMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Locale getRequestLocale() {
		return request.getLocale();
	}

	@SuppressWarnings("unchecked")
    @Override
	public Iterator<Locale> getRequestLocales() {
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

	@SuppressWarnings("rawtypes")
    @Override
	public Map getInitParameterMap() {
		throw new UnsupportedOperationException();
	}

    @Override
	public Set<String> getResourcePaths(final String path) {
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
	private class LocalesIterator implements Iterator<Locale> {

		/**
		 * The locales.
		 */
		private Enumeration<Locale> locales;

		/**
		 * Constructor.
		 * @param locales
		 */
		public LocalesIterator(final Enumeration<Locale> locales) {
			this.locales = locales;
		}

		@Override
        public boolean hasNext() {
			return locales.hasMoreElements();
		}

		@Override
        public Locale next() {
			return locales.nextElement();
		}

		@Override
        public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}