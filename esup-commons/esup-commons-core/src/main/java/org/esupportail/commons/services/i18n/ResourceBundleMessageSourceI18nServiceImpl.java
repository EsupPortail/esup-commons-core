/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.i18n;

import java.util.Locale;
import java.util.Map;

import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;

/**
 * An implementation of I18nService that loads several bundles.
 * See /properties/i18n/i18n.xml for details.
 * 
 * See /properties/i18n/i18n-example.xml.
 */
public class ResourceBundleMessageSourceI18nServiceImpl extends AbstractI18nService implements InitializingBean {
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 4294880275369021655L;

	/**
	 * The basename of the properties files that holds the bundles.
	 */
	private MessageSource messageSource;
	
	/**
	 * Bean constructor.
	 */
	public ResourceBundleMessageSourceI18nServiceImpl() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(messageSource, 
				"property messageSource of class " + getClass().getName() + " can not be null");
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getStrings(java.util.Locale)
	 */
	public Map<String, String> getStrings(final Locale locale) {
		throw new RuntimeException("getStrings not supported by " + getClass().getName());
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getString(java.lang.String, java.util.Locale)
	 */
	@Override
	public String getString(String key, Locale locale) {
		return messageSource.getMessage(key, null, locale);
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getString(java.lang.String)
	 */
	@Override
	public String getString(String key) {
		return messageSource.getMessage(key, null, getDefaultLocale());
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getString(java.lang.String, java.util.Locale, java.lang.Object[])
	 */
	@Override
	public String getString(String key, Locale locale, Object... args) {
		return messageSource.getMessage(key, args, locale);
	}

	/**
	 * @see org.esupportail.commons.services.i18n.I18nService#getString(java.lang.String, java.lang.Object[])
	 */
	@Override
	public String getString(String key, Object... args) {
		return messageSource.getMessage(key, args, getDefaultLocale());
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


}

