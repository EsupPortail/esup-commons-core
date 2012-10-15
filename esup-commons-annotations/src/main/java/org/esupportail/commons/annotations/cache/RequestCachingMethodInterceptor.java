/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.annotations.cache;

import org.esupportail.commons.utils.ContextUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * A caching interceptor that will check for results in request cache before calling methods.
 */
@Aspect
public class RequestCachingMethodInterceptor extends AbstractCachingMethodInterceptor {

	/**
	 * Constructor.
	 */
	public RequestCachingMethodInterceptor() {
		super();
	}

	@Override
	protected Object getContextAttribute(final String name) {
		return ContextUtils.getRequestAttribute(name);
	}

	@Override
	protected void setContextAttribute(
			final String name,
			final Object value) {
		ContextUtils.setRequestAttribute(name, value);
	}

	@Override
	@Around("@annotation(org.esupportail.commons.annotations.cache.RequestCache)")
	public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
		return super.around(joinPoint);
	}

}