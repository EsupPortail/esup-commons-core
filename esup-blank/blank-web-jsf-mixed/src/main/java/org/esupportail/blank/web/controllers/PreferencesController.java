/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.web.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.esupportail.blank.domain.DomainService;
import org.esupportail.blank.domain.beans.User;
import org.esupportail.commons.services.i18n.I18nUtils;
import org.esupportail.commons.utils.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Yves Deschamps (Université de Lille 1) - 2010
 * 
 */
public class PreferencesController implements Serializable, InitializingBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -4133220816313990369L;

	private List<SelectItem> localeItems;

	private String languageSelected;

	private DomainService domainService;

	private List<SelectItem> accessibilityModeItems;

	/**
	 * Constructor.
	 */
	public PreferencesController() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if (this.domainService == null) {
			String[] args = { "domainService", this.getClass().getName() };
			throw new Exception(I18nUtils.createI18nService().getString(
					"CONFIG_EXCEPTION.TITLE", args));
		}
	}

	/**
	 * @return the localeItems
	 */
	public List<SelectItem> getLocaleItems() {
		if (localeItems == null) {
			localeItems = new ArrayList<SelectItem>();
			Iterator<Locale> iter = FacesContext.getCurrentInstance()
					.getApplication().getSupportedLocales();
			while (iter.hasNext()) {
				Locale locale = iter.next();
				StringBuffer buf = new StringBuffer(
						locale.getDisplayLanguage(locale));
				localeItems.add(new SelectItem(locale.getLanguage(), buf
						.toString()));
			}
		}
		return localeItems;
	}

	/**
	 * @return the languageSelected
	 */
	public String getLanguageSelected() {
		return languageSelected;
	}

	/**
	 * @param languageSelected
	 *            the languageSelected to set
	 */
	public void setLanguageSelected(String languageSelected) {
		this.languageSelected = languageSelected;
	}

	/**
	 * @return null.
	 */
	public String setLocaleAction() {
		setLocale(languageSelected);
		return null;
	}

	/**
	 * Change the locale for all views and in the domain service.
	 * 
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(String locale) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.getViewRoot().setLocale(new Locale(locale));
		}
		SessionController sessionController = (SessionController) BeanUtils
				.getBean("sessionController");
		sessionController.setLocale(locale);
		User currentUser = sessionController.getCurrentUser();
		if (currentUser != null) {
			currentUser.setLanguage(locale);
		}
	}

	/**
	 * @param domainService
	 *            the domainService to set
	 */
	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	/**
	 * @return the accessibilityModeItems
	 */
	public List<SelectItem> getAccessibilityModeItems() {
		accessibilityModeItems = new ArrayList<SelectItem>();
		accessibilityModeItems.add(new SelectItem("default", I18nUtils
				.createI18nService().getString(
						"PREFERENCES.ACCESSIBILITY.DEFAULT")));
		accessibilityModeItems.add(new SelectItem("inaccessible", I18nUtils
				.createI18nService().getString(
						"PREFERENCES.ACCESSIBILITY.INACCESSIBLE")));
		accessibilityModeItems.add(new SelectItem("screenReader", I18nUtils
				.createI18nService().getString(
						"PREFERENCES.ACCESSIBILITY.SCREENREADER")));
		return accessibilityModeItems;
	}

}
