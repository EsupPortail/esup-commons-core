package org.esupportail.example.domain.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author bourges
 *
 */
@Entity
public class Information implements Serializable {
	
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5254209997874898229L;
	

	/**
	 * 
	 */
	@Id @GeneratedValue
	private long id;
	
	/**
	 * 
	 */
	private String informationKey;
	
	/**
	 * 
	 */
	private String informationValue;

	/*
	 ******************* INIT ******************* */

	/**
	 * Constructors.
	 */
	public Information() {
		super();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((informationKey == null) ? 0 : informationKey.hashCode());
		result = prime
				* result
				+ ((informationValue == null) ? 0 : informationValue.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Information other = (Information) obj;
		if (informationKey == null) {
			if (other.informationKey != null)
				return false;
		} else if (!informationKey.equals(other.informationKey))
			return false;
		if (informationValue == null) {
			if (other.informationValue != null)
				return false;
		} else if (!informationValue.equals(other.informationValue))
			return false;
		return true;
	
	}
	
	/*
	 ******************* METHODS ******************* */
	
	/*
	 ******************* ACCESSORS ******************* */
	
	/**
	 * @return the informationKey
	 */
	public String getInformationKey() {
		return informationKey;
	}

	/**
	 * @param informationKey the informationKey to set
	 */
	public void setInformationKey(final String informationKey) {
		this.informationKey = informationKey;
	}

	/**
	 * @return the informationValue
	 */
	public String getInformationValue() {
		return informationValue;
	}

	/**
	 * @param informationValue the informationValue to set
	 */
	public void setInformationValue(final String informationValue) {
		this.informationValue = informationValue;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final long id) {
		this.id = id;
	}
	

}
