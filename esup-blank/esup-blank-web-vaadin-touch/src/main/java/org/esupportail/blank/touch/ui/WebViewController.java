package org.esupportail.blank.touch.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.esupportail.commons.services.i18n.I18nUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Panel;

public class WebViewController extends NavigationView {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 7552813179872391990L;

	/**
	 * For Logging.
	 */
	private final Logger logger = new LoggerImpl(getClass());
	
	/**
	 * For Demo.
	 */
	private String url;

	/**
	 * Constructor.
	 */
	public WebViewController() {
		super();
	}

	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();
	}

	@Override
	public void attach() {
		super.attach();
		buildView();
	}

	private void buildView() {
		Locale applicationLocale = TouchKitApplication.get().getLocale();
		CssLayout content = new CssLayout();
		content.setWidth("100%");
		setCaption(I18nUtils.createI18nService().getString("WEBVIEW.TEXT", applicationLocale));
		URL url;
		Embedded browser = null;
		try {
			logger.info("Demo url: " + this.url);
			url = new URL(this.url);
			browser = new Embedded(null, new ExternalResource(url));
			browser.setType(Embedded.TYPE_BROWSER);	
			content.addComponent(browser);
			browser.setSizeUndefined();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setContent(content);	
		if (browser != null) {
			browser.requestRepaint();
		}
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
