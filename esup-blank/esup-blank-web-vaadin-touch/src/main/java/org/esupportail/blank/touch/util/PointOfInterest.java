package org.esupportail.blank.touch.util;

import java.io.Serializable;

public class PointOfInterest implements Serializable {
	
	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 1357364782058242520L;
	
	private double longitude;
	
	private double latitude;
	
	private String type;
	
	private String name;
	
	/**
	 * Constructor.
	 */
	public PointOfInterest() {
		super();
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PointOfInterest [longitude=" + longitude + ", latitude="
				+ latitude + ", type=" + type + ", name=" + name + "]";
	}

}
