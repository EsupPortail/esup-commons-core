/**
 * 
 */
package org.esupportail.example.web.beans;

import java.io.Serializable;


/**
 * @author cleprous
 * Treatment : describe the navigation in page.
 */
public class Treatment  implements Serializable {

	
	/**
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -7867226722831677556L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The Treatment code.
	 */
	private String code;
	
	/**
	 * The rang.
	 */
	private Integer rang;
	
	/**
	 * The action.
	 */
	private String action;
	
	/**
	 * The label.
	 */
	private String label;
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public Treatment() {
		super();
	}


	/**
	 * @param code
	 * @param rang
	 * @param action
	 * @param label
	 */
	public Treatment(final String code, final Integer rang, 
				final String action, final String label) {
		super();
		this.code = code;
		this.rang = rang;
		this.action = action;
		this.label = label;
	}


	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!(obj instanceof Treatment)) { return false; }
		Treatment other = (Treatment) obj;
		if (code == null) {
			if (other.getCode() != null) { return false; }
		} else if (!code.equals(other.getCode())) { return false; }
		return true;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Treatment#" + hashCode() + "[code=[" + code 
				+ "],label=[" + getLabel() + "],[rang=[" 
				+ rang + "],[action=[" + action + "]]";
	}
	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}


	/**
	 * @return the rang
	 */
	public Integer getRang() {
		return rang;
	}


	/**
	 * @param rang the rang to set
	 */
	public void setRang(final Integer rang) {
		this.rang = rang;
	}


	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * @param label the label to set
	 */
	public void setlabel(final String label) {
		this.label = label;
	}


	/**
	 * @return the page
	 */
	public String getPage() {
		return action;
	}


	/**
	 * @param page the page to set
	 */
	public void setPage(final String page) {
		this.action = page;
	}


	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}


	/**
	 * @param action the action to set
	 */
	public void setAction(final String action) {
		this.action = action;
	}


	

}
