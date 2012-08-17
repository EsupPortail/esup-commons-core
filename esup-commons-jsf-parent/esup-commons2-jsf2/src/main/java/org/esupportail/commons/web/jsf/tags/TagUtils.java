/**
 * ESUP-Portail Commons - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-commons
 */
package org.esupportail.commons.web.jsf.tags;

import java.io.IOException;
import java.util.Locale;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * Utility class for tags.
 */
public class TagUtils {
	
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(TagUtils.class);

	/**
	 * Bean constructor.
	 */
	private TagUtils() {
		throw new UnsupportedOperationException();
	}
	

	
	/**
	 * Recursive method that manage the opening and closure components.
	 * Ex. : <li><ul><button/></ul></li>
	 * Stop condition: cNew hasn't more children --> close Tag
	 * @param cNew
	 * @param context
	 * @throws IOException
	 */
	public static void recursiveEncodechildren(
			final UIComponentBase cNew, 
			final FacesContext context) throws IOException {
		if (cNew.getChildren() != null && !cNew.getChildren().isEmpty()) {
			for (Object o : cNew.getChildren()) {
				UIComponentBase c2 = (UIComponentBase) o;
				c2.encodeBegin(context);
				recursiveEncodechildren(c2, context);
			}
			//we travelled all children must therefore close the parent
			cNew.encodeEnd(context);
		} else {
			cNew.encodeEnd(context);
		}
	}
	
	/**
	 * Make an EL Expression.
	 * EX. : #{param}
	 * @param param 
	 * @return String
	 */
	public static String makeELExpression(final String param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entering makeELExpression(" + param + " )");
		}
		return "#{" + param + "}";
	}
	
	
	/**
	 * Recursive method that returns the StringVar attribute of the upper PageTag.
	 * Stop condition: cNew is a PageTage or cNew.getParent == null.
	 * @param cNew
	 * @return String
	 */
	public static Locale getLocaleInUIViewRoot(
			final UIComponent cNew) {
		if (cNew instanceof UIViewRoot) {
			UIViewRoot ui = (UIViewRoot) cNew;
			return ui.getLocale();
		} else if (cNew.getParent() == null) {
			return null;
		}
		return getLocaleInUIViewRoot(cNew.getParent());
	}
	
	/**
	 * create the valueExpression to the string value.
	 * @param a
	 * @param s
	 * @return ValueExpression
	 */
	public static ValueExpression getStringValueExpression(Application a, String s) {
		return a.getExpressionFactory().createValueExpression(s, String.class);
	}

}
