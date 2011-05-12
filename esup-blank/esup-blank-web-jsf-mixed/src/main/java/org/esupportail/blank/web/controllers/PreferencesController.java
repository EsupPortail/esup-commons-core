/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.blank.web.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.esupportail.commons.services.i18n.I18nUtils;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public class PreferencesController extends AbstractContextAwareController {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -4133220816313990369L;

	/**
	 * An items list for locals.
	 */
	private List<SelectItem> localeItems;

	/**
	 * An items list for accessibility.
	 */
	private List<SelectItem> accessibilityModeItems;

	/**
	 * Constructor.
	 */
	public PreferencesController() {
		super();
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
				localeItems.add(new SelectItem(locale, buf.toString()));
			}
		}
		return localeItems;
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
	
	@Override
	public void reset() {
		super.reset();
		accessibilityModeItems = null;
		localeItems = null;
	}
	
}
