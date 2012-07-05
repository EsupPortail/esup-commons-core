/**
 * 
 */
package org.esupportail.commons.services.urlGeneration;

/**
 * @author cleprous
 * This values is the same to AuthUtils in module auth.
 */
public enum AuthEnum {

	/**
	 * Type for null authentication.
	 */
	none,
	
	/**
	 * Type for application authentication.
	 */
	application,
	
	/**
	 * Type for CAS authentication.
	 */
	cas,
	
	
	/**
	 * Type for Shibboleth authentication.
	 */
	shibboleth,
	
	/**
	 * Type for specific authentication.
	 */
	specific;
	
	
	
}
