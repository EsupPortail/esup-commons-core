/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.logging; 

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.esupportail.commons.exceptions.ExceptionHandlingException;

/**
 * A class that provides static utilities for exception handling.
 */
public final class LogExceptionUtils {
	
	/**
	 * The name of the session attribute set to prevent from infite redirections.
	 */
	private static final String EXCEPTION_MARKER_NAME = "exception.marker";

	/**
	 * The text separator for the stack trace.
	 */
	private static final String STACK_TRACE_SEPARATOR = 
		"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ";

	/**
	 * The "caused by" element.
	 */
	private static final String STACK_TRACE_CAUSED_BY = "caused by: "; 
	
	/**
	 * A logger.
	 */
	private static final Logger LOGGER = new LoggerImpl(LogExceptionUtils.class);

	/**
	 * Private constructor.
	 */
	private LogExceptionUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return A list of strings that correspond to the stack trace of a throwable.
	 * @param t
	 */
	private static List<String> internalGetStackTraceStrings(final Throwable t) {
		List<String> result = new ArrayList<String>();
		result.add(t.toString());
		for (StackTraceElement element : t.getStackTrace()) {
			result.add(element.toString());
		}
		Throwable cause;
		//TODO CL V2: l'objet ServletException ne devrait pas etre dans le module core (non web)
//		if (t instanceof ServletException) {
//			cause = ((ServletException) t).getRootCause();
//		} else {
			cause = t.getCause();
//		}
		if (cause != null) {
			result.add(STACK_TRACE_SEPARATOR + STACK_TRACE_CAUSED_BY);
			result.addAll(internalGetStackTraceStrings(cause));
		}
		return result;
	}

	/**
	 * @return A printable form of the stack trace of an exception.
	 * @param t
	 */
	public static String getPrintableStackTrace(final Throwable t) {
		StringBuffer sb = new StringBuffer();
		List<String> strings = internalGetStackTraceStrings(t);
		String separator = "";
		for (String string : strings) {
			sb.append(separator).append(string);
			separator = "\n";
		}
		return sb.toString();
	}

	/**
	 * @return A list of strings that correspond to the short stack trace of a throwable.
	 * @param t
	 * @param addPrefix true to add the "caused by" prefix
	 */
	private static List<String> internalGetShortStackTraceStrings(final Throwable t, final boolean addPrefix) {
		List<String> result = new ArrayList<String>();
		if (addPrefix) {
			result.add(STACK_TRACE_CAUSED_BY + t.toString());
		} else {
			result.add(t.toString());
		}
		Throwable cause;
		//TODO CL V2: l'objet ServletException ne devrait pas etre dans le module core (non web)
//		if (t instanceof ServletException) {
//			cause = ((ServletException) t).getRootCause();
//		} else {
			cause = t.getCause();
//		}
		if (cause != null) {
			result.addAll(internalGetShortStackTraceStrings(cause, true));
		}
		return result;
	}

	/**
	 * @return A list of strings that correspond to the short stack trace of an exception.
	 * @param t
	 */
	public static List<String> getShortStackTraceStrings(final Throwable t) {
		return internalGetShortStackTraceStrings(t, false);
	}

	/**
	 * @return A short printable form of the stack trace of an exception.
	 * @param t
	 */
	public static String getShortPrintableStackTrace(final Throwable t) {
		StringBuffer sb = new StringBuffer();
		List<String> strings = getShortStackTraceStrings(t);
		String separator = "";
		for (String string : strings) {
			sb.append(separator).append(string);
			separator = "\n";
		}
		return sb.toString();
	}

}

