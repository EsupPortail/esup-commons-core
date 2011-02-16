/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.web.portlet;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.myfaces.context.servlet.FacesContextImpl;
import org.apache.myfaces.context.servlet.ServletFacesContextImpl;
import org.apache.myfaces.portlet.MyFacesGenericPortlet;
import org.esupportail.commons.services.application.VersionningUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.ContextUtils;
import org.springframework.web.portlet.context.PortletRequestAttributes;

/**
 * A JSF-based portlet that catches exception and gives them to an exception service.
 */
public class FacesPortlet extends MyFacesGenericPortlet implements Serializable {
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4232039696236207722L;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * Bean constructor.
	 */
	public FacesPortlet() {
		super();
	}

	/**
	 * @see javax.portlet.Portlet#init(javax.portlet.PortletConfig)
	 */
	@Override
	public void init(final PortletConfig portletConfig) throws PortletException {
		try {
			super.init(portletConfig);
		} catch (Throwable t) {
			//TODO CL V2 : use exception in jsf module
			//ExceptionUtils.catchException(t);
			if (t instanceof PortletException) {
				throw (PortletException) t;
			}
			throw new PortletException(t);
		}
	}

	/**
	 * @see org.apache.myfaces.portlet.MyFacesGenericPortlet#initMyFaces()
	 */
	@Override
	protected void initMyFaces() {
		// do nothing to prevent double loading of pahse listeners
		// NB: the job has already been done by StartupServletContextListener
		// cf https://issues.apache.org/jira/browse/MYFACES-1671
	}

	/**
	 * Catch an exception.
	 * @param throwable
	 * @return an exception service
	 */
	//TODO CL V2 : use exception in jsf module
//	protected ExceptionService catchException(
//			final Throwable throwable) {
//		ExceptionUtils.markExceptionCaught(); 
//		ExceptionService exceptionService = ExceptionUtils.catchException(throwable);
//		ExceptionUtils.markExceptionCaught(exceptionService); 
//		return exceptionService;
//	}

    /**
     * This method follows JSF Spec section 2.1.1.  It renders the default view from a non-faces
     * request.
     *
     * @param request The portlet render request.
     * @param response The portlet render response.
     */
    @Override
	protected void nonFacesRequest(
			final RenderRequest request, 
			final RenderResponse response) throws PortletException {
        nonFacesRequest(request, response, null);
    }

    /**
     * This method follows JSF Spec section 2.1.1.  It renders a view from a non-faces
     * request.  This is useful for a default view as well as for views that need to
     * be rendered from the portlet's edit and help buttons.
     *
     * @param request The portlet render request.
     * @param response The portlet render response.
     * @param view The name of the view that needs to be rendered.
     */
	@Override
	protected void nonFacesRequest(
    		final RenderRequest request, 
    		final RenderResponse response, 
    		final String view)
            throws PortletException {
    	 // do this in case nonFacesRequest is called by a subclass
		setContentType(request, response);
        ApplicationFactory appFactory =
            (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        Application application = appFactory.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        ServletFacesContextImpl facesContext = (ServletFacesContextImpl) facesContext(request, response);
   		facesContext.setExternalContext(makeExternalContext(request, response));
        String viewToRender = view;
        if (viewToRender == null) {
        	// the call to selectDefaultView was moved here to be sure 
        	// that the faces context has been initialized before
        	//TODO CL V2 : use dao in jsf module
        	//DatabaseUtils.begin();
        	viewToRender = selectDefaultView(request, response);
        	//DatabaseUtils.commit();
        }
        UIViewRoot viewRoot = viewHandler.createView(facesContext, viewToRender);
        viewRoot.setViewId(viewToRender);
        facesContext.setViewRoot(viewRoot);
        setPortletRequestFlag(request);
        try {
			lifecycle.render(facesContext);
		} catch (Throwable t) {
			if (t instanceof PortletException) {
				throw (PortletException) t;
			}
			throw new PortletException(t);
		}
    }
	
	/**
	 * @see org.apache.myfaces.portlet.MyFacesGenericPortlet#facesRender(
	 * javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	@Override
	public void facesRender(
			final RenderRequest request, 
			final RenderResponse response) 
	throws PortletException, IOException {
    	long startTime = System.currentTimeMillis();
		PortletRequestAttributes previousRequestAttributes = 
			ContextUtils.bindRequestAndContext(request, getPortletContext());
		//TODO CL V2 : use exception in jsf module
		//if (!ExceptionUtils.exceptionAlreadyCaught()) {
			boolean error = false;
			try {
				//TODO CL V2 : use dao in jsf module
				//DatabaseUtils.open();
	    		VersionningUtils.checkVersion(true, false);
	            setContentType(request, response);
	            String viewId = request.getParameter(VIEW_ID);
	            if ((viewId == null)
	            		//|| sessionInvalidated(request) //TODO CL V2
	            		) {
	                setPortletRequestFlag(request);
	              //TODO CL V2 : use to module annotations
	                //MonitorUtils.clear();
	                nonFacesRequest(request,  response);
	            } else {
		            setPortletRequestFlag(request);
		            ServletFacesContextImpl facesContext = null;
	                facesContext = (ServletFacesContextImpl) request.
	                                                        getPortletSession().
	                                                        getAttribute(CURRENT_FACES_CONTEXT);
	                if (facesContext == null) { 
	                	// processAction was not called
	                   facesContext = (ServletFacesContextImpl) facesContext(request, response);
	                   ////////////////////////////
	                   ApplicationFactory appFactory =
	                       (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
	                   Application application = appFactory.getApplication();
	                   ViewHandler viewHandler = application.getViewHandler();
	                   UIViewRoot viewRoot = viewHandler.createView(facesContext, viewId);
	                   viewRoot.setViewId(viewId);
	                   facesContext.setViewRoot(viewRoot);
	                   ////////////////////////////
	                }
	                if (!facesContext.getResponseComplete()) {
		                facesContext.setExternalContext(makeExternalContext(request, response));
		              //TODO CL V2
		                //restoreRequestAttributes(request);
		              //TODO CL V2 : use to module annotations
//		        		CacheUtils.clearRequest();
//		        		MonitorUtils.clear();
		                lifecycle.render(facesContext);
	                }
	            }
				return;
			} catch (Throwable t) {
				error = true;
				//TODO CL V2 : use exception in jsf module
				//catchException(t);
			} finally {
				//TODO CL V2 : use dao in jsf module
				//DatabaseUtils.close();
				if (!error) {
					// Call ContextUtils.unbindRequest if no error only because 
					// used by ExceptionUtils.getMarkedExceptionService()
					//TODO CL V2 : use to module annotations
					//MonitorUtils.log(startTime, "RENDER STAGE");
					ContextUtils.unbindRequest(previousRequestAttributes);
				}
			}
		//}
		try {
			//TODO CL V2 : use exception in jsf module
//			ExceptionService exceptionService = ExceptionUtils.getMarkedExceptionService();
//			if (exceptionService == null) {
//				logger.error("An exception was thrown but no exception service was found!");
//			} else {
//				nonFacesRequest(request, response, exceptionService.getExceptionView());
//			}
		} catch (Throwable t) {
			logger.error("An exception was caught while rendering an exception, giving up", t);
			handleExceptionFromLifecycle(t);
		} finally {
			ContextUtils.unbindRequest(previousRequestAttributes);
		}
	}

	/**
	 * @see javax.portlet.Portlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     * A patched version of the original processAction method to catch exceptions and
     * get the facesContext in exception reports.
     * @param request 
     * @param response 
     */
 	@Override
    public void processAction(
    		final ActionRequest request, 
    		final ActionResponse response) {
        if (sessionTimedOut(request)) {
        	return;
        }
    	long startTime = System.currentTimeMillis();
		PortletRequestAttributes previousRequestAttributes = null;
        //FIXME CL V2:  j'ai changé ServeltFacesContextImpl to FacesContextImpl
		FacesContextImpl facesContext = null;
        try {
    		previousRequestAttributes = ContextUtils.bindRequestAndContext(request, getPortletContext());
    		//TODO CL V2 : use dao in jsf module
//    		DatabaseUtils.open();
//    		DatabaseUtils.begin();
    		VersionningUtils.checkVersion(true, false);
            facesContext = new SerializableServletFacesContextImpl(portletContext, request, response);
            request.getPortletSession().setAttribute(CURRENT_FACES_CONTEXT, facesContext);
    		facesContext.setExternalContext(makeExternalContext(request, response));
            setPortletRequestFlag(request);
            lifecycle.execute(facesContext);
            if (!facesContext.getResponseComplete()) {
                response.setRenderParameter(VIEW_ID, facesContext.getViewRoot().getViewId());
            }
          //TODO CL V2 : use dao in jsf module
            //DatabaseUtils.commit();
        } catch (Throwable t) {
        	//TODO CL V2 : use exception in jsf module
//			ExceptionService exceptionService = catchException(t);
//			response.setRenderParameter(VIEW_ID, exceptionService.getExceptionView());
        } finally {
        	//TODO CL V2 : use exception in jsf module
//            try {
//				DatabaseUtils.close();
//			} catch (DatabaseException e) {
//				ExceptionUtils.catchException(e);
//			}
        	//TODO CL V2 : use to module annotations
        	//MonitorUtils.log(startTime, "PROCESS STAGE");
        	//TODO CL V2      
        	//saveRequestAttributes(request);
        	ContextUtils.unbindRequest(previousRequestAttributes);
        }
    }

	/**
	 * @see org.apache.myfaces.portlet.MyFacesGenericPortlet#logException(java.lang.Throwable, java.lang.String)
	 */
	@Override
	protected void logException(
			@SuppressWarnings("unused")
			final Throwable t, 
			@SuppressWarnings("unused")
			final String msgPrefix) {
		// logged by the exception manager
	}

}
