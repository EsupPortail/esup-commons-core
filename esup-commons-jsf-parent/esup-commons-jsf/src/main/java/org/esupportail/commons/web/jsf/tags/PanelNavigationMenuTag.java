/**
 * ESUP-Portail Commons - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-commons
 */
package org.esupportail.commons.web.jsf.tags;

import javax.faces.component.UIComponent;

import org.esupportail.commons.web.jsf.tags.config.TagsConfigurator;

import org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlPanelNavigationMenuTag;

/**
 * The ESUP-Portail implementation of the panelNavigationMenu tag.
 */
public class PanelNavigationMenuTag extends HtmlPanelNavigationMenuTag {

	/**
	 * Constructor.
	 */
	public PanelNavigationMenuTag() {
		super();
	}

	@Override
	protected void setProperties(final UIComponent component) {
		TagsConfigurator tagsConfigurator = TagsConfigurator.getInstance();
		setLayout(TagUtils.getStringValueExpression(
				getFacesContext().getApplication(),tagsConfigurator.getMenuLayout()));
		setStyleClass(TagUtils.getStringValueExpression(
				getFacesContext().getApplication(),tagsConfigurator.getMenuStyleClass()));
		setActiveItemClass(TagUtils.getStringValueExpression(
				getFacesContext().getApplication(),tagsConfigurator.getMenuActiveItemStyleClass()));
		setItemClass(TagUtils.getStringValueExpression(
				getFacesContext().getApplication(),tagsConfigurator.getMenuItemStyleClass()));
		super.setProperties(component);
	}
}
