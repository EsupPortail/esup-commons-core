package org.esupportail.blank.touch.ui;

import java.util.Locale;

import org.esupportail.blank.touch.Application;
import org.esupportail.blank.touch.ProtectedApplication;
import org.esupportail.commons.services.i18n.I18nUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class MapViewController extends NavigationView implements
		PositionCallback {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -8372367905854283801L;

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * Constructor.
	 */
	public MapViewController() {
		super();
	}

	@Override
	public void attach() {
		super.attach();
		buildView();
	};

	private void buildView() {
		// Tool Bar
		Locale applicationLocale = TouchKitApplication.get().getLocale();
		
		// get Position
		TouchKitApplication.get().getMainWindow().detectCurrentPosition(this);

		CssLayout content = new CssLayout();
		content.setWidth("100%");
		setCaption(I18nUtils.createI18nService().getString("MAPS.TEXT",
				applicationLocale));

		// Introduction
		VerticalComponentGroup componentGroup = new VerticalComponentGroup();
		Label mapsIntro = new Label(I18nUtils.createI18nService().getString(
				"MAPS.INTRO", applicationLocale), Label.CONTENT_XHTML);
		componentGroup.addComponent(mapsIntro);
		content.addComponent(componentGroup);

		VerticalComponentGroup menuGroup = new VerticalComponentGroup();
		menuGroup.setCaption(I18nUtils.createI18nService().getString(
				"MAPS.MENU", applicationLocale));

		final MapTargetViewController mapTargetView;
		if (TouchKitApplication.get().getClass().getSimpleName()
				.equals("Application")) {
			mapTargetView = ((Application) getApplication())
					.getMapTargetViewController();
		} else {
			mapTargetView = ((ProtectedApplication) getApplication())
					.getMapTargetViewController();
		}

		final Switch switchEnseignement = new Switch("Enseignement");
		switchEnseignement.setValue(true);
		switchEnseignement.addListener(new Property.ValueChangeListener() {
			/**
			 * For Serialize.
			 */
			private static final long serialVersionUID = 7702174854445433875L;

			public void valueChange(ValueChangeEvent event) {
				mapTargetView
						.setSwitchEnseignement((Boolean) switchEnseignement
								.getValue());
				mapTargetView.detach();
				mapTargetView.attach();
			}
		});
		menuGroup.addComponent(switchEnseignement);

		final Switch switchBibliotheque = new Switch("Bibliothèque");
		switchBibliotheque.addListener(new Property.ValueChangeListener() {
			/**
			 * For Serialize.
			 */
			private static final long serialVersionUID = 7702174987445433875L;

			public void valueChange(ValueChangeEvent event) {
				mapTargetView
						.setSwitchBibliotheque((Boolean) switchBibliotheque
								.getValue());
				mapTargetView.detach();
				mapTargetView.attach();
			}
		});
		menuGroup.addComponent(switchBibliotheque);

		final Switch switchAdministration = new Switch("Administration");
		switchAdministration.addListener(new Property.ValueChangeListener() {
			/**
			 * For Serialize.
			 */
			private static final long serialVersionUID = 7702198357445433875L;

			public void valueChange(ValueChangeEvent event) {
				mapTargetView
						.setSwitchAdministration((Boolean) switchAdministration
								.getValue());
				mapTargetView.detach();
				mapTargetView.attach();
			}
		});
		menuGroup.addComponent(switchAdministration);

		final Switch switchMyLocation = new Switch(I18nUtils
				.createI18nService().getString("MAP.LOCATION",
						applicationLocale));
		switchMyLocation.addListener(new Property.ValueChangeListener() {
			/**
			 * For Serialize.
			 */
			private static final long serialVersionUID = 1968123574562215288L;

			public void valueChange(ValueChangeEvent event) {
				mapTargetView.setSwitchMyLocation((Boolean) switchMyLocation
						.getValue());
				mapTargetView.detach();
				mapTargetView.attach();
			}
		});
		menuGroup.addComponent(switchMyLocation);

		// Map target view
		NavigationButton mapTargetViewButton = new NavigationButton(I18nUtils
				.createI18nService().getString("MAP.TEXT", applicationLocale));
		mapTargetViewButton.setTargetView(mapTargetView);
		menuGroup.addComponent(mapTargetViewButton);

		content.addComponent(menuGroup);

		this.setContent(content);
	}

	@Override
	public void onSuccess(Position position) {
				if (TouchKitApplication.get().getClass().getSimpleName()
					.equals("Application")) {
				((Application) getApplication()).getMapTargetViewController().setCurrentLongitude(position
						.getLongitude());
				((Application) getApplication()).getMapTargetViewController().setCurrentLatitude(position
						.getLatitude());
			} else {
				((ProtectedApplication) getApplication())
						.setCurrentLongitude(position.getLongitude());
				((ProtectedApplication) getApplication())
						.setCurrentLatitude(position.getLatitude());
		}
	}

	@Override
	public void onFailure(int errorCode) {
		// TODO Auto-generated method stub
		System.out.println(errorCode);
	}

}
