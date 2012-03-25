ReadMe
==============

##Introduction
**OfflineMap-based-on-Osmdroid** is an library using for   establish off-line map on your android project. 
You can easily build off-line google map, osm map, etc.. (whatever you can get) on your project. 

It's based on osmdroid-android-3.0.5 ( see <http://code.google.com/p/osmdroid>) , and use slf4j for debug.


##Install

###Step 1
Download:

* [osmdroid](http://code.google.com/p/osmdroid/)
* [SLF4J](http://www.slf4j.org/)



Test Environment

* osmdroid-android 3.0.5
* slf4j-android 1.5.8




###Step 2

Using [**Mobile Atlas Creator 1.8**]() download map data. (You can use lastest *Mobile Atlas Creator* certainly. but after 1.9.x version it *Doesn't support* google map.) 


Mobile Atlas Creator's Options:

* Map source: Google map, OpenStreetMap, etc..
* Zoom Levels: 19 to 0. 19 is the largest level.
* **※Atlas settings: you must choose "Big Planet Tracks SQLite". ** 



After setting, click *Create atlas* button to start downloading. Map data will be put into *"./atlases"*.

Finally, move .sqlitedb to SD card.


###Step 3

Import:

	import idv.hondadai.offlinemap.views.OfflineMapView;

	import org.osmdroid.util.GeoPoint;
	import org.osmdroid.views.MapController;
	import org.osmdroid.views.MapView;




Using OfflineMapView to build your off-line map

	MapView mapView = new OfflineMapView(this, "Your Database File's path");


Demo Code:

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


Layout:

	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout 	xmlns:android="http://schemas.android.com/apk/res/android"
  	  android:layout_width="fill_parent"
  	  android:layout_height="fill_parent"
  	  android:orientation="vertical" >

   	 <RelativeLayout
   	     android:id="@+id/mapLayout"
   	     android:layout_width="wrap_content"
    	    android:layout_height="wrap_content">
  	  </RelativeLayout>

	</LinearLayout>
