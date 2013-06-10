package org.esupportail.commons.services.authentication.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.portlet.context.PortletRequestAttributes;

public class ContextUtils {	

	/**
	 * Private constructor.
	 */
	private ContextUtils() {
		throw new UnsupportedOperationException();
	}
		
	/**
	 * @return The request attributes.
	 * @throws NoRequestBoundException 
	 */
	public static RequestAttributes getContextAttributes() throws RuntimeException {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			throw new RuntimeException("no request bound to thread!");
		}
		if (!(requestAttributes instanceof ServletRequestAttributes) 
				&& !(requestAttributes instanceof PortletRequestAttributes)) {
			throw new IllegalArgumentException(
					"requestAttributes of unknown class [" + requestAttributes.getClass() + "]");
		}
		return requestAttributes;
	}
	
}
