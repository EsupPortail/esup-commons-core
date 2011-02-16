/**
 * 
 */
package org.esupportail.commons.services.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author bourges
 *
 */
public class JasigLikeViewSelectorService implements ViewSelectorService, InitializingBean {

	private static final String DEFAULT_BROWSER_PREFIX = "BROWSER_";

	private static final String DEFAULT_MOBILE_PREFIX = "MOBILE_";

	private static final String DEFAULT_WAI_PREFIX = "WAI_";

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * Prefix used when a classic browser is detected
	 */
	private String browserPrefix;

	/**
	 * Prefix used when a mobile browser is detected
	 */
	private String mobilePrefix;

	/**
	 * Prefix used when a WAI view is requested
	 */
	private String waiPrefix;

	/**
	 * Regexes of mobile device user agents 
	 */
	private List<Pattern> mobileDeviceRegexes = null;

	/**
	 * Set a list of regex patterns for user agents which should be considered
	 * to be mobile devices.
	 * 
	 * @param patterns
	 */
	@Resource(name="mobileDeviceRegexes")
	public void setMobileDeviceRegexes(List<String> patterns) {
		this.mobileDeviceRegexes = new ArrayList<Pattern>();
		for (String pattern : patterns) {
			this.mobileDeviceRegexes.add(Pattern.compile(pattern));
		}
	}

	public String getCalifiedViewName(String viewName,
			HttpServletRequest request, boolean isWAI) {
		String prefix = null;
		// is a mobile ?
		if (isMobile(request)) {
			prefix = mobilePrefix;
		}
		// not checked as an mobile device
		if (prefix == null) {
			prefix = browserPrefix;
			// is WAI view is requested
			if (isWAI) {
				prefix = prefix + waiPrefix;
			}
		}		
		return prefix + viewName;
	}	

	public String getCalifiedViewName(String viewName,
			HttpServletRequest request) {
		return getCalifiedViewName(viewName, request, false);
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		if (browserPrefix == null) {
			logger.info("property BrowserPrefix is not set default value " + DEFAULT_BROWSER_PREFIX + " is used");
			browserPrefix = DEFAULT_BROWSER_PREFIX;
		}
		if (mobilePrefix == null) {
			logger.info("property mobilePrefix is not set default value " + DEFAULT_MOBILE_PREFIX + " is used");
			mobilePrefix = DEFAULT_MOBILE_PREFIX;
		}
		if (waiPrefix == null) {
			logger.info("property mobilePrefix is not set default value " + DEFAULT_WAI_PREFIX + " is used");
			waiPrefix = DEFAULT_WAI_PREFIX;
		}
		Assert.notNull(mobileDeviceRegexes, "property mobileDeviceRegexes is not null\b" +
				"Please declare a <util:list id=\"mobileDeviceRegexes\"> in your spring configuration !");
	}

	/**
	 * @return the browserPrefix
	 */
	public String getBrowserPrefix() {
		return browserPrefix;
	}

	/**
	 * @param browserPrefix the browserPrefix to set
	 */
	public void setBrowserPrefix(String browserPrefix) {
		this.browserPrefix = browserPrefix;
	}

	/**
	 * @return the mobilePrefix
	 */
	public String getMobilePrefix() {
		return mobilePrefix;
	}

	/**
	 * @param mobilePrefix the mobilePrefix to set
	 */
	public void setMobilePrefix(String mobilePrefix) {
		this.mobilePrefix = mobilePrefix;
	}

	public boolean isMobile(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		// check to see if this is a mobile device
		if (this.mobileDeviceRegexes != null && userAgent != null) {
			for (Pattern regex : this.mobileDeviceRegexes) {
				if (regex.matcher(userAgent).matches()) {
					return true;
				}
			}
		}	
		return false;
	}

	public boolean isGPSAware(HttpServletRequest request) {
		return false;
	}

}
