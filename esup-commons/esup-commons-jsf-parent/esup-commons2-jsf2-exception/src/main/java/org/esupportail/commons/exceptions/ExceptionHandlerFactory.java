/**
 * 
 */
package org.esupportail.commons.exceptions;

import javax.faces.context.ExceptionHandler;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * @author cleprous
 *
 */
public class ExceptionHandlerFactory extends javax.faces.context.ExceptionHandlerFactory {


	/*
	 *************************** PROPERTIES ******************************** */
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());

	/**
	 * ExceptionHandlerFactory parent.
	 */
	private javax.faces.context.ExceptionHandlerFactory parent;
	/*
	 *************************** INIT ************************************** */

	/**
	 * Constructor.
	 */
	public ExceptionHandlerFactory() {
		super();
	}



	/**
	 * Constructor.
	 * @param parent
	 */
	public ExceptionHandlerFactory(final javax.faces.context.ExceptionHandlerFactory parent) {
		super();
		this.parent = parent;
	}



	/*
	 *************************** METHODS *********************************** */

	@Override
	public ExceptionHandler getExceptionHandler() {
		if (log.isDebugEnabled()) {
			log.debug("entering ExceptionHandlerFactory::getExceptionHandler");
		}
		if (parent != null) {
			ExceptionHandler result = parent.getExceptionHandler();
			result = new EsupExceptionHandler(result);
	
			return result;
		}
		return null;
	}



	/**
	 * @see javax.faces.context.ExceptionHandlerFactory#getWrapped()
	 */
	@Override
	public javax.faces.context.ExceptionHandlerFactory getWrapped() {
		return parent;
	}
	
	

	/*
	 *************************** ACCESSORS ********************************* */

}
