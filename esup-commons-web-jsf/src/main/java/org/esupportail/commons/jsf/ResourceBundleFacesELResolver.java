package org.esupportail.commons.jsf;

import java.util.Locale;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotFoundException;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;

public class ResourceBundleFacesELResolver extends SpringBeanFacesELResolver {
	
	/**
	 * Bean constructor.
	 */
	private ResourceBundleFacesELResolver() {
	}
	
	/**
	 * a static factory method
	 */
	public static ResourceBundleFacesELResolver createInstance() {
		return new ResourceBundleFacesELResolver();
	}
	
	/**
	 * A logger.
	 */
	private static final Logger logger = Logger
			.getLogger(ResourceBundleFacesELResolver.class);

	
	@Override
	public Object getValue(ELContext elContext, Object base, Object property)
			throws ELException {
		if (base instanceof MessageSource && property instanceof String) {
			String result = ((MessageSource) base).getMessage(
					(String) property, null, getLocale());
			logger.debug(" Result for " + property + ": " + result);
			if (null != result) {
				elContext.setPropertyResolved(true);
			}
			return result;
		}
		return super.getValue(elContext, base, property);
	}

	private Locale getLocale() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}
}
