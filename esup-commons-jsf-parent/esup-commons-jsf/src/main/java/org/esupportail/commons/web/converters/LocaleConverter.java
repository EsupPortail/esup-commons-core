/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.web.converters;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.util.StringUtils;

/**
 * A JSF converter to pass Locale instances.
 */
public class LocaleConverter implements Converter, Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4035732742368776367L;

	/**
	 * Bean constructor.
	 */
	public LocaleConverter() {
		super();
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (!StringUtils.hasText(value)) {
			return null;
		}
		return new Locale(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null || !StringUtils.hasText(value.toString())) {
			return "";
		}
		if (!(value instanceof Locale)) {
			throw new UnsupportedOperationException(
					"object " + value + " is not a Locale.");
		}
		Locale locale = (Locale) value;
		return locale.toString();
	}

}
