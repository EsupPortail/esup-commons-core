/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.web.converters;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.util.StringUtils;

/**
 * A JSF converter to pass Timestamp instances.
 */
public class TimestampConverter implements Converter, Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4604224911308971107L;

	/**
	 * Bean constructor.
	 */
	public TimestampConverter() {
		super();
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (!StringUtils.hasText(value)) {
			return null;
		}
		return new Timestamp(Long.valueOf(value));
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null || !StringUtils.hasText(value.toString())) {
			return "";
		}
		if (!(value instanceof Timestamp)) {
			throw new UnsupportedOperationException(
					"object " + value + " is not a Timestamp.");
		}
		Timestamp ts = (Timestamp) value;
		return String.valueOf(ts.getTime());
	}

}
