package org.esupportail.blank.touch;

import com.vaadin.addon.touchkit.ui.TouchKitWindow;

public class MainWindow extends TouchKitWindow {

    /**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 1066475470053108000L;
	
	public MainWindow() {
        setCaption("Esup-Blank");
    }
	
	@Override
    public void attach() {
        super.attach();
        configureWebAppSettings();
   }

	private void configureWebAppSettings() {
        /*
         * These configurations modify how the app behaves as "ios webapp".
         * Configuration is done in attach to be sure getUrl() returns a correct
         * address.
         */
        addApplicationIcon(getApplication().getURL()
                + "VAADIN/themes/base/mobile/icon.png");
        setStartupImage(getApplication().getURL()
                + "VAADIN/themes/base/mobile/startup.png");
        setWebAppCapable(true);
        setPersistentSessionCookie(true);
    }
    
}
