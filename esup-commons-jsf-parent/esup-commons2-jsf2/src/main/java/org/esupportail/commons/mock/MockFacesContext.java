/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mock;

import java.util.Iterator;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

/**
 * A faces context mock.
 */
public class MockFacesContext extends FacesContext {

	/**
	 * The external context.
	 */
	private ExternalContext externalContext;

	/**
	 * The application.
	 */
	private Application application;

	/**
	 * A view root.
	 */
	private UIViewRoot viewRoot;

	/**
	 * Constructor.
	 */
	public MockFacesContext() {
		super();
	}

	@Override
	public Application getApplication() {
		return application;
	}

	/**
	 * @param application
	 */
	public void setApplication(final Application application) {
		this.application = application;
	}

	@Override
	public Iterator getClientIdsWithMessages() {
		return null;
	}

	@Override
	public ExternalContext getExternalContext() {
		return externalContext;
	}

	/**
	 * @param externalContext
	 */
	public void setExternalContext(final ExternalContext externalContext) {
		this.externalContext = externalContext;
	}

	@Override
	public Severity getMaximumSeverity() {
		return null;
	}

	@Override
	public Iterator getMessages() {
		return null;
	}

	@Override
	public Iterator getMessages(final String arg0) {
		return null;
	}

	@Override
	public RenderKit getRenderKit() {
		return null;
	}

	@Override
	public boolean getRenderResponse() {
		return false;
	}

	@Override
	public boolean getResponseComplete() {
		return false;
	}

	@Override
	public ResponseStream getResponseStream() {
		return null;
	}

	@Override
	public void setResponseStream(final ResponseStream arg0) {
		// do nothing
	}

	@Override
	public ResponseWriter getResponseWriter() {
		return null;
	}

	@Override
	public void setResponseWriter(final ResponseWriter arg0) {
		// do nothing
	}

	@Override
	public UIViewRoot getViewRoot() {
		return viewRoot;
	}

	@Override
	public void setViewRoot(final UIViewRoot viewRoot) {
		this.viewRoot = viewRoot;
	}

	@Override
	public void addMessage(final String arg0, final FacesMessage arg1) {
		// do nothing
	}

	@Override
	public void release() {
		// do nothing
	}

	@Override
	public void renderResponse() {
		// do nothing
	}

	@Override
	public void responseComplete() {
		// do nothing
	}

}
