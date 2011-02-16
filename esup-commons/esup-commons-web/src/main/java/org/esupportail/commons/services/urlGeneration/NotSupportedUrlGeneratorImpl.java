/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.urlGeneration; 

import java.util.Map;

/**
 * An class that implements UrlGenerator and throws exceptions when called.
 */
public class NotSupportedUrlGeneratorImpl extends AbstractCasUrlGenerator {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -1473068373498303560L;

	/**
	 * Bean constructor.
	 */
	protected NotSupportedUrlGeneratorImpl() {
		super();
	}
	
	/**
	 * @see org.esupportail.commons.services.urlGeneration.AbstractUrlGenerator
	 * #url(org.esupportail.commons.services.urlGeneration.AuthEnum, java.util.Map)
	 */
	@Override
	protected String url(
			final AuthEnum authType, 
			final Map<String, String> params) {
		throw new UnsupportedOperationException("class " + getClass().getName() + "should never be called.");
	}

}
