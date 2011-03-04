package org.esupportail.blank.web.jsf.mixed;

import java.io.IOException;
import java.util.Map;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.sun.facelets.FaceletViewHandler;

public class FaceletRedirectionViewHandler extends FaceletViewHandler {

	/**
	 * For logging.
	 */
	private final Logger logger = Logger.getLogger(this.getClass());

	public FaceletRedirectionViewHandler(ViewHandler parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
        // long monitorId = HibernatePerformanceMonitor.get().start();
        super.renderView(context, viewToRender);
        // HibernatePerformanceMonitor.get().stop(monitorId, "URL " + getURL(viewToRender));
    }

    private String getURL(UIViewRoot viewToRender) {
        StringBuilder results = new StringBuilder(viewToRender.getViewId());

        boolean first = true;
        for (Map.Entry<String, Object> urlParam : viewToRender.getAttributes().entrySet()) {
            if (first) {
                results.append('?');
                first = false;
            } else {
                results.append('&');
            }
            results.append(urlParam.getKey()).append("=").append(urlParam.getValue());
        }

        return results.toString();
    }

    @Override
	protected void handleRenderException(FacesContext context, Exception ex)
			throws IOException, ELException, FacesException {
		System.err
				.println("-------------------------- Exception Handling (facelets) ----------------------------");
		try {
			if (context.getViewRoot().getViewId()
					.equals("exception.xhtml")) {
				/*
				 * This is to protect from infinite redirects if the error page
				 * itself has an error; in this case, revert to the default
				 * error handling, which should provide extra context
				 * information to debug the issue
				 */
				logger.error(
						"Redirected back to ourselves, there must be a problem with the error.xhtml page",
						ex);
				super.handleRenderException(context, ex); // normal, ugly
				// error page used to diagnose the issue
				return; // return early, to prevent infinite redirects back to
						// ourselves
			}

			// squirrel the exception away in the session so it's maintained
			// across the redirect boundary
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.put("GLOBAL_RENDER_ERROR", ex);

			/*
			ExceptionUtils.markExceptionCaught();
			ExceptionService exceptionService = null;
			exceptionService = ExceptionUtils.catchException(ex);
			ExceptionUtils.markExceptionCaught(exceptionService);
			
			NavigationHandler navigation = context.getApplication()
					.getNavigationHandler();
			navigation.handleNavigation(context, "",
					exceptionService.getExceptionView());
			*/
			
			externalContext.redirect("exception.xhtml");

		} catch (IOException ioe) {
			logger.fatal(
					"Could not process redirect to handle application error",
					ioe);
		}
	}

}
