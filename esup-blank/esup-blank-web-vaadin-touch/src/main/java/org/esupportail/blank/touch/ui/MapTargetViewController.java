package org.esupportail.blank.touch.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.esupportail.blank.touch.util.PointOfInterest;
import org.esupportail.commons.services.i18n.I18nUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.vaadin.vol.GoogleHybridMapLayer;
import org.vaadin.vol.GoogleSatelliteMapLayer;
import org.vaadin.vol.GoogleStreetMapLayer;
import org.vaadin.vol.Marker;
import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Window;

public class MapTargetViewController extends NavigationView {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -2296393246311022174L;

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final Logger logger = new LoggerImpl(getClass());

	private List<PointOfInterest> pois = new ArrayList<PointOfInterest>();

	private Map<String, String> longitudesLatitudesMap;

	private Map<String, String> typesMap;

	private Boolean switchEnseignement = true;

	private Boolean switchAdministration = false;

	private Boolean switchBibliotheque = false;

	private Boolean switchMyLocation = false;

	private double currentLongitude;

	private double currentLatitude;

	// Patch nécessaire pour ne plus voir le Copyright Google (distribué par
	// openLayer)
	private String openLayerPatch = "OpenLayers.Layer.Google.v3.repositionMapElements = function() {	google.maps.event.trigger(this.mapObject, \"resize\");	var div = this.mapObject.getDiv().firstChild;	if (!div || div.childNodes.length < 3) {		this.repositionTimer = window.setTimeout(OpenLayers.Function.bind(				this.repositionMapElements, this), 250);		return false;	}	var cache = OpenLayers.Layer.Google.cache[this.map.id];	var container = this.map.viewPortDiv;	for ( var i = div.children.length - 1; i >= 0; --i) {		if (div.children[i].style.zIndex == 1000001) {			var termsOfUse = div.children[i];			container.appendChild(termsOfUse);			termsOfUse.style.zIndex = \"1100\";			termsOfUse.style.bottom = \"\";			termsOfUse.className = \"olLayerGoogleCopyright olLayerGoogleV3\";			termsOfUse.style.display = \"\";			cache.termsOfUse = termsOfUse;		}		if (div.children[i].style.zIndex == 1000000) {			var poweredBy = div.children[i];			container.appendChild(poweredBy);			poweredBy.style.zIndex = \"1100\";			poweredBy.style.bottom = \"\";			poweredBy.className = \"olLayerGooglePoweredBy olLayerGoogleV3 gmnoprint\";			poweredBy.style.display = \"\";			cache.poweredBy = poweredBy;		}	if (div.children[i].style.zIndex == 10000002) {			container.appendChild(div.children[i]);		}	}	this.setGMapVisibility(this.visibility);};";

	private boolean jsLoaded;

	/**
	 * Constructor.
	 */
	public MapTargetViewController() {
		super();
	}

	@Override
	public void attach() {
		super.attach();
		buildView();
	};

	private void buildView() {

		// load js patch for openLayer
		if (!jsLoaded) {
			Window window = getWindow();
			if (window != null) {
				getWindow().executeJavaScript(openLayerPatch);
				jsLoaded = true;
			}
		}
		
		// Get Locale
		Locale applicationLocale = TouchKitApplication.get().getLocale();

		// POI Init.
		Iterator<Entry<String, String>> it = longitudesLatitudesMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, String> pairs = it.next();
			PointOfInterest poi = new PointOfInterest();
			poi.setName(pairs.getKey());
			String locationInMap = pairs.getValue();
			poi.setLongitude(new Double(locationInMap.substring(6,
					locationInMap.indexOf(" "))));
			poi.setLatitude(new Double(locationInMap.substring(
					locationInMap.indexOf(" ") + 1, locationInMap.indexOf(")"))));
			poi.setType(typesMap.get(poi.getName()));
			pois.add(poi);
		}

		CssLayout content = new CssLayout();
		content.setWidth("100%");
		setCaption(I18nUtils.createI18nService().getString("MAP.TEXT",
				applicationLocale));

		OpenLayersMap map = new OpenLayersMap();

		OpenStreetMapLayer osm = new OpenStreetMapLayer();
		GoogleStreetMapLayer gsm = new GoogleStreetMapLayer();
		GoogleSatelliteMapLayer gss = new GoogleSatelliteMapLayer();
		GoogleHybridMapLayer ghm = new GoogleHybridMapLayer();

		MarkerLayer markerLayer = new MarkerLayer();

		double centerLon = 0;
		double centerLat = 0;
		for (PointOfInterest poi : pois) {
			if (poi.getName().equals("Campus")) {
				centerLon = poi.getLongitude();
				centerLat = poi.getLatitude();
			}
			if (switchEnseignement && poi.getType().equals("Enseignement")) {
				ThemeResource icon = new ThemeResource(
						"mobile/marker-green.png");
				Marker marker = new Marker(poi.getLongitude(),
						poi.getLatitude());
				marker.setDescription(poi.getName());
				marker.setIcon(icon, 20, 20);
				markerLayer.addComponent(marker);
			}
			if (switchBibliotheque && poi.getType().equals("Bibliothèque")) {
				ThemeResource icon = new ThemeResource("mobile/marker-blue.png");
				Marker marker = new Marker(poi.getLongitude(),
						poi.getLatitude());
				marker.setDescription(poi.getName());
				marker.setIcon(icon, 20, 20);
				markerLayer.addComponent(marker);
			}
			if (switchAdministration && poi.getType().equals("Administration")) {
				ThemeResource icon = new ThemeResource("mobile/marker-gold.png");
				Marker marker = new Marker(poi.getLongitude(),
						poi.getLatitude());
				marker.setDescription(poi.getName());
				marker.setIcon(icon, 20, 20);
				markerLayer.addComponent(marker);
			}
		}
		map.setZoom(15);

		if (switchMyLocation) {
			map.setCenter(currentLongitude, currentLatitude);
			ThemeResource icon = new ThemeResource("mobile/marker-red.png");
			Marker marker = new Marker(currentLongitude, currentLatitude);
			marker.setDescription(I18nUtils.createI18nService().getString(
					"MAP.LOCATION", applicationLocale));
			marker.setIcon(icon, 20, 20);
			markerLayer.addComponent(marker);
		} else {
			// default campus position
			map.setCenter(centerLon, centerLat);
		}

		// base layers
		map.addLayer(osm);
		map.addLayer(gsm);

		// other layers
		map.addLayer(gss);
		map.addLayer(ghm);

		map.addLayer(markerLayer);

		map.setSizeFull();
		content.addComponent(map);

		this.setContent(content);

	}

	/**
	 * @return the longitudesLatitudesMap
	 */
	public Map<String, String> getLongitudesLatitudesMap() {
		return longitudesLatitudesMap;
	}

	/**
	 * @param longitudesLatitudesMap
	 *            the longitudesLatitudesMap to set
	 */
	public void setLongitudesLatitudesMap(
			Map<String, String> longitudesLatitudesMap) {
		this.longitudesLatitudesMap = longitudesLatitudesMap;
	}

	/**
	 * @return the typesMap
	 */
	public Map<String, String> getTypesMap() {
		return typesMap;
	}

	/**
	 * @param typesMap
	 *            the typesMap to set
	 */
	public void setTypesMap(Map<String, String> typesMap) {
		this.typesMap = typesMap;
	}

	/**
	 * @return the switchEnseignement
	 */
	public Boolean getSwitchEnseignement() {
		return switchEnseignement;
	}

	/**
	 * @param switchEnseignement
	 *            the switchEnseignement to set
	 */
	public void setSwitchEnseignement(Boolean switchEnseignement) {
		this.switchEnseignement = switchEnseignement;
	}

	/**
	 * @return the switchAdministration
	 */
	public Boolean getSwitchAdministration() {
		return switchAdministration;
	}

	/**
	 * @param switchAdministration
	 *            the switchAdministration to set
	 */
	public void setSwitchAdministration(Boolean switchAdministration) {
		this.switchAdministration = switchAdministration;
	}

	/**
	 * @return the switchBibliotheque
	 */
	public Boolean getSwitchBibliotheque() {
		return switchBibliotheque;
	}

	/**
	 * @param switchBibliotheque
	 *            the switchBibliotheque to set
	 */
	public void setSwitchBibliotheque(Boolean switchBibliotheque) {
		this.switchBibliotheque = switchBibliotheque;
	}

	/**
	 * @return the switchMyLocation
	 */
	public Boolean getSwitchMyLocation() {
		return switchMyLocation;
	}

	/**
	 * @param switchMyLocation
	 *            the switchMyLocation to set
	 */
	public void setSwitchMyLocation(Boolean switchMyLocation) {
		this.switchMyLocation = switchMyLocation;
	}

	/**
	 * @return the currentLongitude
	 */
	public double getCurrentLongitude() {
		return currentLongitude;
	}

	/**
	 * @param currentLongitude
	 *            the currentLongitude to set
	 */
	public void setCurrentLongitude(double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	/**
	 * @return the currentLatitude
	 */
	public double getCurrentLatitude() {
		return currentLatitude;
	}

	/**
	 * @param currentLatitude
	 *            the currentLatitude to set
	 */
	public void setCurrentLatitude(double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}

}
