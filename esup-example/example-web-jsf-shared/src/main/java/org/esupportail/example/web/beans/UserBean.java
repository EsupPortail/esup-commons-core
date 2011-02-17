/**
* Esup-portail - esup-anciens - 2009
* http://subversion.univ-rennes1.fr/repos/57si-anciens
* 
*/

/**
 * 
 */
package org.esupportail.example.web.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotNull
	private String id;
	
    /**
	 * Display Name of the user.
	 */
	@Size(max = 10, min = 1)
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
