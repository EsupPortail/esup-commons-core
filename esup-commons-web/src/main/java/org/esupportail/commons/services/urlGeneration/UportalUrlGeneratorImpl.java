/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.urlGeneration; 

import java.util.Map;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.utils.strings.StringUtilsWeb;

/**
 * A bean to generate URLs to the application.
 */
public class UportalUrlGeneratorImpl extends AbstractCasUrlGenerator {

	/**
	 * The name of the parameter that uPortal uses to pass parameters to channels.
	 */
	public static final String ARGS_PARAM = "uP_args";
	
	/**
	 * The uP_fname uPortal parameter.
	 */
	protected static final String UPORTAL_FNAME_PARAM = "uP_fname";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 5175024312097175630L;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());
	
	/**
	 * The uPortal URL.
	 */
	private String uportalUrl;
	
	/**
	 * The uPortal Login URL.
	 */
	private String uportalLoginUrl;
	
	/**
	 * The uPortal Guest URL.
	 */
	private String uportalGuestUrl;
	
	/**
	 * The uPortal funtionnal name (the name used to publish the uPortal channel of the portlet).
	 */
	private String uportalFunctionnalName;
	
	/**
	 * Bean constructor.
	 */
	public UportalUrlGeneratorImpl() {
		super();
	}
	
	/**
	 * @see org.esupportail.commons.services.urlGeneration.AbstractCasUrlGenerator#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Assert.notNull(uportalFunctionnalName, "property uportalFunctionnalName of class " 
				+ this.getClass().getName() + " can not be null");
		if (uportalLoginUrl == null) {
			if (uportalUrl == null) {
				logger.warn(
						"uportalLoginUrl and uportalUrl are null, "
						+ "uPortal URLs for CAS users and Shibboleth will not be available");
			} else {
				uportalLoginUrl = uportalUrl + "/Login";
			}
		}
		if (uportalGuestUrl == null) {
			if (uportalUrl == null) {
				logger.warn(
						"uportalGuestUrl and uportalUrl are null, "
						+ "uPortal URLs for application users will not be available");
			} else {
				uportalGuestUrl = uportalUrl + "/Guest";
			}
		}
	}

	/**
	 * @see org.esupportail.commons.services.urlGeneration.AbstractUrlGenerator
	 * #url(org.esupportail.commons.services.urlGeneration.AuthEnum, java.util.Map)
	 */
	@Override
	protected String url(
			final AuthEnum authType,
			final Map<String, String> params) {
		if (logger.isDebugEnabled()) {
			logger.debug("url(auth=" + authType + ", params=" + params + ")");
		}
		String arg = encodeParamsToArg(params);
		if (logger.isDebugEnabled()) {
			logger.debug("url(): arg=[" + arg + "]");
			logger.debug("url(): StringUtils.utf8UrlEncode(arg)=[" + StringUtilsWeb.utf8UrlEncode(arg) + "]");
		}
		String url;
		if (AuthEnum.cas.equals(authType)) {
			if (getCasLoginUrl() == null) {
				throw new UnsupportedOperationException("casLoginUrl is null");
			}
			if (uportalLoginUrl == null) {
				throw new UnsupportedOperationException("uportalLoginUrl is null");
			}
			url = getUportalLoginUrl();
		} else if (AuthEnum.cas.equals(authType)) {
			if (uportalLoginUrl == null) {
				throw new UnsupportedOperationException("uportalLoginUrl is null");
			}
			url = getUportalLoginUrl();
		} else {
			if (uportalGuestUrl == null) {
				throw new UnsupportedOperationException("uportalGuestUrl is null");
			}
			url = getUportalGuestUrl();
		}
		url += "?" + UPORTAL_FNAME_PARAM + "=" + StringUtilsWeb.utf8UrlEncode(getUportalFunctionnalName());
		if (logger.isDebugEnabled()) {
			logger.debug("url(): url1=[" + url + "]");
		}
		if (arg != null) {
			url = url + "&" + ARGS_PARAM 
			+ "=" + StringUtilsWeb.utf8UrlEncode(arg);
			if (logger.isDebugEnabled()) {
				logger.debug("url(): url2=[" + url + "]");
			}
		}
		if (AuthEnum.cas.equals(authType)) {
			String encodedUrl = StringUtilsWeb.utf8UrlEncode(url);
			url = String.format(getCasLoginUrl(), 
					encodedUrl);
			if (logger.isDebugEnabled()) {
				logger.debug("url(): encodedUrl=[" + encodedUrl + "]");
				logger.debug("url(): url3=[" + url + "]");
			}
		}
		return url;
	}

	/**
	 * @return the uportalFunctionnalName
	 */
	protected String getUportalFunctionnalName() {
		return uportalFunctionnalName;
	}

	/**
	 * @param uportalFunctionnalName the uportalFunctionnalName to set
	 */
	public void setUportalFunctionnalName(final String uportalFunctionnalName) {
		this.uportalFunctionnalName = StringUtilsWeb.nullIfEmpty(uportalFunctionnalName);
	}

	/**
	 * @return the uportalUrl
	 */
	protected String getUportalUrl() {
		return uportalUrl;
	}

	/**
	 * @param uportalUrl the uportalUrl to set
	 */
	public void setUportalUrl(final String uportalUrl) {
		this.uportalUrl = StringUtilsWeb.nullIfEmpty(uportalUrl);
		if (this.uportalUrl != null) {
			while (uportalUrl.endsWith("/")) {
				this.uportalUrl = this.uportalUrl.substring(0, this.uportalUrl.length() - 1);
			}
		}
	}

	/**
	 * @return the uportalLoginUrl
	 */
	protected String getUportalLoginUrl() {
		return uportalLoginUrl;
	}

	/**
	 * @param uportalLoginUrl the uportalLoginUrl to set
	 */
	public void setUportalLoginUrl(final String uportalLoginUrl) {
		this.uportalLoginUrl = StringUtilsWeb.nullIfEmpty(uportalLoginUrl);
	}

	/**
	 * @return the uportalGuestUrl
	 */
	protected String getUportalGuestUrl() {
		return uportalGuestUrl;
	}

	/**
	 * @param uportalGuestUrl the uportalGuestUrl to set
	 */
	public void setUportalGuestUrl(final String uportalGuestUrl) {
		this.uportalGuestUrl = StringUtilsWeb.nullIfEmpty(uportalGuestUrl);
	}

}
