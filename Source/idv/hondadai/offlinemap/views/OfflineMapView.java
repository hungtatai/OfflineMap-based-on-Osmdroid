package idv.hondadai.offlinemap.views;


import idv.hondadai.offlinemap.tileprovider.OfflineMapTileProviderBasic;

import org.osmdroid.DefaultResourceProxyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import android.content.Context;
import android.util.AttributeSet;

/**
 * 
 * @author Honda Dai
 * 
 */
public class OfflineMapView extends org.osmdroid.views.MapView{

	private Logger logger = LoggerFactory.getLogger(OfflineMapView.class);
	
	private int mMaxZoom;
	private int mMinZoom;
	
	/**
	 * Constructor used by XML layout resource (uses default tile source).
	 */
	public OfflineMapView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Standard Constructor.
	 */
	private OfflineMapView(final Context context, final String pDbPath, int pMaxZoom, int pMinZoom) {
		super(
			context, 
			256, 
			new DefaultResourceProxyImpl(context), 
			new OfflineMapTileProviderBasic(context, pDbPath, pMaxZoom, pMinZoom)
		);
		this.setUseDataConnection(false);
		
		if(pMinZoom>pMaxZoom)
		{
			int t = pMinZoom;
			pMinZoom = pMaxZoom;
			pMaxZoom = t;
			
			logger.warn("MinZoomLevel is LARGER than MaxZoomLever. (auto SWAP each other)");
		}
		
		mMaxZoom = pMaxZoom;
		mMinZoom = pMinZoom;
		
//		logger.error(getMaxZoomLevel()+"");
//		logger.error(getMinZoomLevel()+"");
	}

	public OfflineMapView(final Context context, final String pDbPath)
	{
		//org.osmdroid.views.MapView defaulted 18 to 0
		this(context, pDbPath, 19, 0); 
	}
	
	@Override
	public int getMaxZoomLevel() {
		return mMaxZoom;
	}
	
	@Override
	public int getMinZoomLevel() {
		return mMinZoom;
	}
	
}
