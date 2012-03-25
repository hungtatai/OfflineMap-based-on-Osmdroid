package idv.hondadai.offlinemap.tileprovider.modules;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * 
 * @author Honda Dai
 * 
 */
public class OfflineDatabaseFileArchive implements
		org.osmdroid.tileprovider.modules.IArchiveFile {

	private static final Logger logger = LoggerFactory
			.getLogger(OfflineDatabaseFileArchive.class);

	private final SQLiteDatabase mDatabase;
	private final int mMaxZoom;
	private final int mMinZoom;
	private String debug_z;

	private OfflineDatabaseFileArchive(final SQLiteDatabase pDatabase,
			int pMaxZoom, int pMinZoom) {
		mDatabase = pDatabase;
		mMaxZoom = pMaxZoom;
		mMinZoom = pMinZoom;
	}

	public static OfflineDatabaseFileArchive getOfflineDatabaseFileArchive(
			final File pFile, int pMaxZoom, int pMinZoom)
			throws SQLiteException {
		return new OfflineDatabaseFileArchive(
				SQLiteDatabase.openOrCreateDatabase(pFile, null), pMaxZoom, pMinZoom);
	}

	@Override
	protected void finalize() throws Throwable {
		if (mDatabase != null || !mDatabase.isOpen())
			mDatabase.close();
		super.finalize();
	}

	public InputStream getInputStream(final ITileSource pTileSource,
			final MapTile pTile) {
//		 ITileSource is unimportant, just for unify interface
		try {
			InputStream ret = null;
			int zoom = mMaxZoom - pTile.getZoomLevel() - 2; // because z is
															// between -2 to 17
//			 if(zoom > mMaxZoom) zoom = mMaxZoom;
//			 else if( mMinZoom > zoom) zoom = mMinZoom;

			final String x = Integer.toString(pTile.getX());
			final String y = Integer.toString(pTile.getY());
			final String z = Integer.toString(zoom);
			String sql = String.format("select image from tiles where x=%s and y=%s and z=%s", x, y, z);
			final Cursor cur = mDatabase.rawQuery(sql, null);

//			if (this.debug_z != z) {
//				logger.debug("change z= " + z);
//				this.debug_z = z;
//			}

			if (cur.getCount() != 0) {
				cur.moveToFirst();
				ret = new ByteArrayInputStream(cur.getBlob(0));
			}
			cur.close();
			if (ret != null) {
				return ret;
			}
		} catch (final Throwable e) {
			logger.warn("Error getting db stream: " + pTile, e);
		}

		return null;
	}

	@Override
	public String toString() {
		return "DatabaseFileArchive [mDatabase=" + mDatabase.getPath() + "]";
	}

}
