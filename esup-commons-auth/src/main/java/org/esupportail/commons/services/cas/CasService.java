/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.cas;

import java.io.Serializable;


/** 
 * The interface of the CAS service used to retrieve PTs from the CAs server.
 */
public interface CasService extends Serializable {
	
	/**
	 * @param targetService The service the PT should be sent to.
	 * @return a PT.
	 * @throws CasException 
	 */
	String getProxyTicket(String targetService) throws CasException;
	
}
