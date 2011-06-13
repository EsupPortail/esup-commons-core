/**
 * ESUP-Portail Example Application - Copyright (c) 2011 ESUP-Portail consortium.
 */
package org.esupportail.example.web.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.esupportail.example.web.jsf.BundleService;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2011
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
	 * An items list for print format.
	 */
	private List<SelectItem> printFormatItems;

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
		accessibilityModeItems.add(new SelectItem("default", BundleService
				.getString("PREFERENCES.ACCESSIBILITY.DEFAULT")));
		accessibilityModeItems.add(new SelectItem("inaccessible", BundleService
				.getString("PREFERENCES.ACCESSIBILITY.INACCESSIBLE")));
		accessibilityModeItems.add(new SelectItem("screenReader", BundleService
				.getString("PREFERENCES.ACCESSIBILITY.SCREENREADER")));
		return accessibilityModeItems;
	}

	@Override
	public void reset() {
		super.reset();
		accessibilityModeItems = null;
		localeItems = null;
		printFormatItems = null;
	}

	/**
	 * @return printFormatItems.
	 */
	public List<SelectItem> getPrintFormatItems() {
		printFormatItems = new ArrayList<SelectItem>();
		printFormatItems.add(new SelectItem("A4P", BundleService
				.getString("PREFERENCES.PRINT.A4_PORTRAIT")));
		printFormatItems.add(new SelectItem("A4L", BundleService
				.getString("PREFERENCES.PRINT.A4_LANDSCAPE")));
		printFormatItems.add(new SelectItem("LUSP", BundleService
				.getString("PREFERENCES.PRINT.LUS_PORTRAIT")));
		printFormatItems.add(new SelectItem("LUSL", BundleService
				.getString("PREFERENCES.PRINT.LUS_LANDSCAPE")));
		return printFormatItems;
	}

}
