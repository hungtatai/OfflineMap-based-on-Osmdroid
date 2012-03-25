package idv.hondadai.offlinemapdemo;

import idv.hondadai.offlinemap.views.OfflineMapView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class OfflineMapDemoActivity extends Activity {
	// layout
	private RelativeLayout mapLayout;

	// MapView
	private MapView mapView;
	private MapController mapController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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

	}
}