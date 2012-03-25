package idv.hondadai.offlinemap.tileprovider;


import idv.hondadai.offlinemap.tileprovider.modules.OfflineDatabaseFileArchive;

import java.io.File;

import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.tileprovider.modules.INetworkAvailablityCheck;
import org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider;
import org.osmdroid.tileprovider.modules.NetworkAvailabliltyCheck;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import android.content.Context;
import android.os.Environment;

/**
 * 
 * @author Honda Dai
 * 
 */
public class OfflineMapTileProviderBasic extends org.osmdroid.tileprovider.MapTileProviderArray implements org.osmdroid.tileprovider.IMapTileProviderCallback {

	private static final Logger logger = LoggerFactory.getLogger(OfflineMapTileProviderBasic.class);


	public OfflineMapTileProviderBasic(final Context pContext, final String pDbPath) {
		this(pContext, TileSourceFactory.DEFAULT_TILE_SOURCE, pDbPath, 17, 2);
	}

	public OfflineMapTileProviderBasic(final Context pContext, final String pDbPath, int pMaxZoom, int pMinZoom) {
		this(pContext, TileSourceFactory.DEFAULT_TILE_SOURCE, pDbPath, pMaxZoom, pMinZoom);
	}

	public OfflineMapTileProviderBasic(final Context pContext, final ITileSource pTileSource, final String pDbPath, int pMaxZoom, int pMinZoom) {
		this(new SimpleRegisterReceiver(pContext), new NetworkAvailabliltyCheck(pContext), pTileSource, pDbPath, pMaxZoom, pMinZoom);
	}

	public OfflineMapTileProviderBasic(final IRegisterReceiver pRegisterReceiver,
			final INetworkAvailablityCheck aNetworkAvailablityCheck, final ITileSource pTileSource, final String pDbPath, int pMaxZoom, int pMinZoom) {
		super(pTileSource, pRegisterReceiver);
		
		
		/* Offline Map */
		try {
			final File dbPath = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/"+pDbPath);
		
			if( !dbPath.exists() )
				logger.warn(pDbPath+" is not exists.");
			else if( !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) )
				logger.warn("SDcard is UNMOUNTED, can't load "+pDbPath);
			else {
				
				final OfflineDatabaseFileArchive tDatabaseFileArchive = OfflineDatabaseFileArchive.getOfflineDatabaseFileArchive(dbPath, pMaxZoom, pMinZoom);
				final IArchiveFile[] tArchiveFiles = new IArchiveFile[]{tDatabaseFileArchive};
				
				final MapTileFileArchiveProvider offlineArchiveProvider = new MapTileFileArchiveProvider( pRegisterReceiver, pTileSource, tArchiveFiles);
				mTileProviderList.add(offlineArchiveProvider);
			}
		}catch (Exception e){
			logger.error("unknown error: "+e);
		}
	}
}
