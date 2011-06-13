/**
 * ESUP-Portail Example Application - Copyright (c) 2011 ESUP-Portail consortium.
 */
package org.esupportail.example.web.jsf;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;
import javax.portlet.faces.Bridge;

import org.esupportail.commons.services.exceptionHandling.ExceptionService;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.ContextUtils;

import com.sun.facelets.FaceletViewHandler;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2011
 * 
 */
public class FaceletServletOrPortletViewHandler extends FaceletViewHandler {

	/**
	 * A logger.
	 */
	private Logger logger = new LoggerImpl(getClass());

	/**
	 * @param parent
	 */
	public FaceletServletOrPortletViewHandler(ViewHandler parent) {
		super(parent);
	}

	@Override
	protected ResponseWriter createResponseWriter(FacesContext context)
			throws IOException, FacesException {
		// Only override if in a portlet request
		if (context.getExternalContext().getRequestMap().get(Bridge.PORTLET_LIFECYCLE_PHASE) == null) {
			return super.createResponseWriter(context);
		}

		ExternalContext extContext = context.getExternalContext();
		RenderKit renderKit = context.getRenderKit();
		// Avoid a cryptic NullPointerException when the renderkit ID
		// is incorrectly set
		if (renderKit == null) {
			String id = context.getViewRoot().getRenderKitId();
			throw new IllegalStateException(
					"No render kit was available for id \"" + id + "\"");
		}

		RenderResponse response = (RenderResponse) extContext.getResponse();

		// get our content type
		String contentType = (String) extContext.getRequestMap().get(
				"facelets.ContentType");

		// get the encoding
		String encoding = (String) extContext.getRequestMap().get(
				"facelets.Encoding");

		ResponseWriter writer;
		// append */* to the contentType so createResponseWriter will succeed no
		// matter
		// the requested contentType.
		if (contentType != null && !contentType.equals("*/*")) {
			contentType += ",*/*";
		}
		// Create a dummy ResponseWriter with a bogus writer,
		// so we can figure out what content type the ReponseWriter
		// is really going to ask for
		try {
			writer = renderKit.createResponseWriter(NullWriter.Instance,
					contentType, encoding);
		} catch (IllegalArgumentException e) {
			// Added because of an RI bug prior to 1.2_05-b3. Might as well
			// leave it in case other
			// impls have the same problem.
			// https://javaserverfaces.dev.java.net/issues/show_bug.cgi?id=613
			log.fine("The impl didn't correctly handled '*/*' in the content type list.  Trying '*/*' directly.");
			writer = renderKit.createResponseWriter(NullWriter.Instance, "*/*",
					encoding);
		}

		// Override the JSF provided content type if necessary
		contentType = getResponseContentType(context, writer.getContentType());
		encoding = getResponseEncoding(context, writer.getCharacterEncoding());

		// apply them to the response
		response.setContentType(contentType + "; charset=" + encoding);

		// removed 2005.8.23 to comply with J2EE 1.3
		// response.setCharacterEncoding(encoding);

		// Now, clone with the real writer
		writer = writer.cloneWithWriter(response.getWriter());

		return writer;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected String getResponseEncoding(FacesContext context, String orig) {
		String encoding = orig;

		// see if we need to override the encoding
		Map m = context.getExternalContext().getRequestMap();
		Map sm = context.getExternalContext().getSessionMap();

		// 1. check the request attribute
		if (m.containsKey("facelets.Encoding")) {
			encoding = (String) m.get("facelets.Encoding");
			if (logger.isDebugEnabled()) {
				logger.debug("Facelet specified alternate encoding '"
						+ encoding + "'");
			}
			sm.put(CHARACTER_ENCODING_KEY, encoding);
		}

		// 2. get it from request
		if (encoding == null) {
			encoding = context.getExternalContext()
					.getResponseCharacterEncoding();
		}

		// 3. get it from the session
		if (encoding == null) {
			encoding = (String) sm.get(CHARACTER_ENCODING_KEY);
			if (logger.isDebugEnabled()) {
				logger.debug("Session specified alternate encoding '"
						+ encoding + "'");
			}
		}

		// 4. default it
		if (encoding == null) {
			encoding = "UTF-8";
			if (logger.isDebugEnabled()) {
				logger.debug("ResponseWriter created had a null CharacterEncoding, defaulting to UTF-8");
			}
		}

		return encoding;
	}

	/**
	 * @author Yves Deschamps (Universite de Lille 1) - 2011
	 *
	 */
	protected static class NullWriter extends Writer {

		/**
		 * A NullWriter Instance.
		 */
		static final NullWriter Instance = new NullWriter();

		@Override
		public void write(char[] buffer) {
		}

		@Override
		public void write(char[] buffer, int off, int len) {
		}

		@Override
		public void write(String str) {
		}

		@Override
		public void write(int c) {
		}

		@Override
		public void write(String str, int off, int len) {
		}

		@Override
		public void close() {
		}

		@Override
		public void flush() {
		}
	}

	@Override
	protected void handleRenderException(FacesContext context, Exception ex)
			throws IOException, ELException, FacesException {
		if (logger.isDebugEnabled()) {
			logger.debug("------------------- Handle Exception from facelets -------------------");
		}
		if (context.getViewRoot().getViewId().indexOf("exception.xhtml") > -1) {
			/*
			 * This is to protect from infinite redirects if the error page
			 * itself is updated in the future and has an error
			 */
			if (logger.isDebugEnabled()) {
				logger.debug("------------------- boucle -------------------");
			}
			super.handleRenderException(context, ex);
			return;
		}

		context.getExternalContext().getSessionMap()
				.put("javax.servlet.error.exception", ex);

		// Only if in a portlet request
		if (context.getExternalContext().getRequestMap().get(Bridge.PORTLET_LIFECYCLE_PHASE) != null) {
			ExternalContext externalContext = context.getExternalContext();
			PortletRequest request = (PortletRequest) externalContext
					.getRequest();
			ContextUtils.bindRequestAndContext(request,
					(PortletContext) externalContext.getContext());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("------------------- markExceptionCaught -------------------");
		}
		ExceptionUtils.markExceptionCaught();

		ExceptionService exceptionService = null;
		if (logger.isDebugEnabled()) {
			logger.debug("------------------- Appel Exception Service -------------------");
		}
		exceptionService = ExceptionUtils.catchException(ex);
		ExceptionUtils.markExceptionCaught(exceptionService);

		if (logger.isDebugEnabled()) {
			logger.debug("------------------- handleNavigation -------------------");
		}
		NavigationHandler navigation = context.getApplication()
				.getNavigationHandler();		
		navigation.handleNavigation(context, "", exceptionService.getExceptionView());
	}
}
