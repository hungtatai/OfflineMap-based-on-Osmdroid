#ReadMe
##Introduction
*OfflineMap-based-on-Osmdroid* is an library using for   establish off-line map on your android project. You can easily build off-line google map, osm map, etc.. (whatever you can get) on your project. It's based on osmdroid-android-3.0.5 ( see <http://code.google.com/p/osmdroid>) , and use slf4j for debug.

		// init Layout
		setContentView(R.layout.main);
		this.mapLayout = (RelativeLayout) findViewById(R.id.mapLayout);

		// init Offline Map
		this.mapView = new OfflineMapView(this, "GoogleMapCH.sqlitedb");
		this.mapController = mapView.getController();

		// set Zoom Countrol
		this.mapView.setBuiltInZoomControls(true);
		// set Touch Control
		this.mapView.setMultiTouchControls(true);
		// zoom to 15
		this.mapController.setZoom(15);
		//add mapview
		this.mapLayout.addView(this.mapView, new RelativeLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT));

		// scroll to 24082456, 120558472
		GeoPoint geoPoint = new GeoPoint(24082456, 120558472);
		this.mapController.setCenter(geoPoint);
