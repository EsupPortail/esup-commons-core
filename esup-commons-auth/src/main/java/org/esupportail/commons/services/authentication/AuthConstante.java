package org.esupportail.commons.services.authentication;

/**
 * Authentication Type Enum.
 */
public enum AuthConstante {

	    NONE(""), APPLICATION("application"), CAS("cas"), SHIBBOLETH("shibboleth"), SPECIFIC("specific");
	    
	    private final String value;

	    AuthConstante(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public static AuthConstante fromValue(String v) {
	        for (AuthConstante c : AuthConstante.values()) {
	            if (c.value.equals(v)) {
	                return c;
	            }
	        }
	        throw new IllegalArgumentException(v);
	    }

	}
