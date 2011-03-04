package org.esupportail.blank.web.jsf.mixed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class GenericErrorUIBean {

	/**
	 * For logging.
	 */
	private final Logger logger = Logger.getLogger(this.getClass());

    String summary;
    String details;
    List<Tuple<String, String>> trace;

    public GenericErrorUIBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        trace = new ArrayList<Tuple<String, String>>();
        Throwable ex = (Exception) sessionMap.remove("GLOBAL_RENDER_ERROR");

        // let's put this in the server log along with showing the user
        logger.error("Error processing user request", ex);

        String message = ex.getLocalizedMessage();
        String stack = StringUtil.getFirstStackTrace(ex);
        trace.add(new Tuple<String, String>(message, stack));

        while (ex.getCause() != null) {
            ex = ex.getCause();

            message = ex.getLocalizedMessage();
            stack = StringUtil.getFirstStackTrace(ex);
            trace.add(new Tuple<String, String>(message, stack));
        }

        summary = ex.getClass().getSimpleName();
        details = ex.getMessage();
    }

    public String getSummary() {
        return summary;
    }

    public String getDetails() {
        return details;
    }

    public List<Tuple<String, String>> getTrace() {
        return trace;
    }
    
}
