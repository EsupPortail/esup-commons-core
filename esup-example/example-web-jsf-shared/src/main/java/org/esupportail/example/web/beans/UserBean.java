/**
* Esup-portail - esup-anciens - 2009
* http://subversion.univ-rennes1.fr/repos/57si-anciens
* 
*/

/**
 * 
 */
package org.esupportail.example.web.beans;

import javax.persistence.Id;

import org.esupportail.commons.web.controllers.Resettable;

/**
 * @author cleprous
 *
 */
public class UserBean implements Resettable {

	
	/*
	 *************************** PROPERTIES ******************************** */

	/**
	 * Id of the user.
	 */
	private String id;
	
    /**
	 * Display Name of the user.
	 */
    private String displayName;
	
	/*
	 *************************** INIT ************************************** */

	/**
	 * Constructors.
	 */
	public UserBean() {
		super();
	}

	/**
	 * @see org.esupportail.commons.web.controllers.Resettable#reset()
	 */
	@Override
	public void reset() {
		id = null;
		displayName = null;
	}
	
	/*
	 *************************** METHODS *********************************** */

	/*
	 *************************** ACCESSORS ********************************* */

	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}



	
}
