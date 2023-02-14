	var content = document.getElementById('popup-content');//

	var center = ol.proj.transform([21.422891, 41.9998876], 'EPSG:4326', 'EPSG:3857'); //initial position of map
	var view = new ol.View({
		center: center,
		zoom: 16.5
	});

	//raster layer on map
	var OSMBaseLayer = new ol.layer.Tile({
		source: new ol.source.OSM()
	});

	straitSource = new ol.source.Vector({ wrapX: true });
	var straitsLayer = new ol.layer.Vector({
		source: straitSource
	});

	map = new ol.Map({
		layers: [OSMBaseLayer, straitsLayer],
		target: 'map',
		view: view,
		controls: [new ol.control.FullScreen(), new ol.control.Zoom()]
	});

	// Popup showing the position the user clicked
	var container = document.getElementById('popup');
	var popup = new ol.Overlay({
		element: container,
		autoPan: true,
		autoPanAnimation: {
			duration: 250
		}
	});
	map.addOverlay(popup);

	/* Add a pointermove handler to the map to render the popup.*/
	map.on('click', function (evt) {
	var feature = map.forEachFeatureAtPixel(evt.pixel, function (feat, layer) {
		return feat;
	}
	);

	if (feature && feature.get('type') == 'Point') {
		var coordinate = evt.coordinate;    //default projection is EPSG:3857 you may want to use ol.proj.transform

		content.innerHTML = feature.get('desc');
		popup.setPosition(coordinate);
	}
	else {
		popup.setPosition(undefined);
		
	}
	});
	
	//add all locations from locations.json file (Exported in homework 1)
	
	function addPointGeom() {
		$.getJSON("locations.json", function (data) {
			data.forEach(function(item) { //iterate through array...
				console.log(item);
				var longitude = item.Long,
					latitude = item.Lat,
					ime = item.Ime,
					iconFeature = new ol.Feature({
						geometry: new ol.geom.Point(ol.proj.transform([longitude, latitude], 'EPSG:4326',
							'EPSG:3857')),
					  type: 'Point',
						desc: "<pre><b style = 'font-size: 18px'>" + ime + '</b>' + '<br><br>Working hours: 08:00 - 00:00<br><br>' + "<a style = 'font-size: 16px; text-decoration: none; ' href = 'https://www.google.com/maps?q=" + latitude + ',' + longitude + "'>Пронајди рута</a></pre>"}),
					iconStyle = new ol.style.Style({
						image: new ol.style.Circle({
							radius: 10,
							stroke: new ol.style.Stroke({
								color: 'blue'
							}),
							fill: new ol.style.Fill({
								color: [57, 228, 193, 0.84]
							}),
						})
					});

				iconFeature.setStyle(iconStyle);

				straitSource.addFeature(iconFeature);
			});
		})
	}

	addPointGeom();