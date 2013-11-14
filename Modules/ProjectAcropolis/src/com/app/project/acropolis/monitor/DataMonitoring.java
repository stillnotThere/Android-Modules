/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DataStateListener
 * com.acropolis.module.data
 * DataStateListener.java
 * Created - 2013-10-11 1:55:59 PM	
 * Modified - 2013-10-11 1:55:59 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.monitor;

import java.util.Locale;

import android.content.ContentValues;
import android.net.TrafficStats;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class DataMonitoring extends PhoneStateListener 
{

	long downloaded = 0;
	long uploaded = 0;

	long DB_D = Long.parseLong(DBAdapter.getValue(DBOpenHelper.LOCAL_DOWNLOADED));
	long DB_U = Long.parseLong(DBAdapter.getValue(DBOpenHelper.LOCAL_UPLOADED));

	long tmpD = 0;
	long tmpU = 0;

	long incD = 0;
	long incU = 0;
	
	long lastD = 0;
	long lastU = 0;

	public void onDataActivity(int direction)
	{

		switch(direction)
		{
		case TelephonyManager.DATA_ACTIVITY_IN:
			downloaded = TrafficStats.getMobileRxBytes();
//			Logger.Debug("DATA IN\nbytes::"+downloaded);

		case TelephonyManager.DATA_ACTIVITY_OUT:
			uploaded = TrafficStats.getMobileTxBytes();
//			Logger.Debug("DATA OUT\nbytes::"+uploaded);

		case TelephonyManager.DATA_ACTIVITY_INOUT:
			downloaded = TrafficStats.getMobileRxBytes();
			uploaded = TrafficStats.getMobileTxBytes();
//			Logger.Debug("DATA INOUT\nbytes::"+(downloaded + uploaded));
		}

		checkDownload();
		checkUpload();
		
//		Logger.Debug(
//				"Downloaded:::"+ humanReadableByteCount(downloaded,true) +
//				"\nUploaded:::"+ humanReadableByteCount(uploaded,true));
	}

	public void checkDownload()
	{
		incD = downloaded - lastD;
		DB_D = DB_D + incD;
		lastD = downloaded;
		incD=0;
		
		ContentValues cvD = new ContentValues();
		cvD.put(DBOpenHelper.LOCAL_DOWNLOADED, String.valueOf(DB_D));
		DBAdapter.update(cvD);
	}

	public void checkUpload()
	{
		
		incU = uploaded - lastU;
		DB_U = DB_U + incU;
		lastU = uploaded;
		incU=0;
		
		ContentValues cvU = new ContentValues();
		cvU.put(DBOpenHelper.LOCAL_UPLOADED, String.valueOf(DB_U));
		DBAdapter.update(cvU);
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format(Locale.CANADA,"%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

}
