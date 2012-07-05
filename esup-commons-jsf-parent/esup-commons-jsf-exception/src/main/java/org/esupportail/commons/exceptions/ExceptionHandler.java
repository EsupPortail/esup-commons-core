/**
 * CRI - Université de Rennes 1 - <nom du projet> - <année>
 * <url de gestion du projet>
 * Version de la norme de développement : <version de ce document> 
 */
/**
 * 
 */
package org.esupportail.commons.exceptions;

import java.io.IOException;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.exceptionHandling.ExceptionService;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;

/**
 * @author cleprous
 * ExceptionHandler : defined in web.xml : use by jsf to catch and redirect to exception.
 */
public class ExceptionHandler {


	/*
	 *************************** PROPERTIES ******************************** */

		/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/*
	 *************************** INIT ************************************** */

	/**
	 * Constructors.
	 */
	public ExceptionHandler() {
		super();
	}


	/*
	 *************************** METHODS *********************************** */



	
	/**
	 * @param context
	 * @param ex
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void handleException(final FacesContext context, final Exception ex)  {
		ExceptionUtils.markExceptionCaught();
		ExceptionService e = null;
//		try {
			e = ExceptionUtils.catchException(ex);
//		} catch (Throwable t) {
//			handleExceptionHandlingException(t);
//			// never reached, prevent from warnings
//			return;
//		}
		ExceptionUtils.markExceptionCaught(e);
		NavigationHandler navigation = context.getApplication().getNavigationHandler();
		// Redirection vers la page des erreurs
		navigation.handleNavigation(context, "", e.getExceptionView());
	}

//TODO avoir pour l'utilisation 28/04/2010 : cleprous
//
//	/**
//	 * Wrap exceptions thrown when already catching an exception.
//	 * @param t
//	 */
//	private void handleExceptionHandlingException(
//			final Throwable t) {
//		logger.error(
//				"An exception was thrown while already catching a previous exception:",
//				t);
//		if (t instanceof IOException) {
//			throw (IOException) t;
//		} else if (t instanceof ServletException) {
//			throw (ServletException) t;
//		} else if (t.getMessage() != null) {
//			throw new ServletException(t.getMessage(), t);
//		} else {
//			throw new ServletException(t);
//		}
//	}

	/*
	 *************************** ACCESSORS ********************************* */


}

