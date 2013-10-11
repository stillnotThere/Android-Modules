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
package com.app.project.acropolis;

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
	
	public void onDataActivity(int direction)
	{
		DBAdapter dbAdapter = new DBAdapter(MainActivity.getContext());
		switch(direction)
		{
		case TelephonyManager.DATA_ACTIVITY_IN:
			downloaded = TrafficStats.getMobileRxBytes();
			Logger.Debug("DATA IN\nbytes::"+downloaded);
		
		case TelephonyManager.DATA_ACTIVITY_OUT:
			uploaded = TrafficStats.getMobileTxBytes();
			Logger.Debug("DATA OUT\nbytes::"+uploaded);
			
		case TelephonyManager.DATA_ACTIVITY_INOUT:
			downloaded = TrafficStats.getMobileRxBytes();
			uploaded = TrafficStats.getMobileTxBytes();
			Logger.Debug("DATA INOUT\nbytes::"+(downloaded + uploaded));
		}
		
//		if(!dbAdapter.isEmpty())
//		{
//			long previousD = Long.parseLong(dbAdapter.getValue(DBOpenHelper.DOWNLOADED));
//			long previousU = Long.parseLong(dbAdapter.getValue(DBOpenHelper.UPLOADED));
			long newD = downloaded;
			long newU = uploaded;
			
//			long totalD = previousD + newD;
//			long totalU = previousU + newU;
			
			long totalD = newD;
			long totalU = newU;
			
			ContentValues cvD = new ContentValues();
			cvD.put(DBOpenHelper.DOWNLOADED, String.valueOf(totalD));
			dbAdapter.update(cvD);
			
			ContentValues cvU = new ContentValues();
			cvU.put(DBOpenHelper.UPLOADED, String.valueOf(totalU));
			dbAdapter.update(cvU);
//		}
//		else
//		{
//			long newD = downloaded;
//			long newU = uploaded;
//			
//			ContentValues cvD = new ContentValues();
//			cvD.put(DBOpenHelper.DOWNLOADED, String.valueOf(newD));
//			dbAdapter.update(cvD);
//			
//			ContentValues cvU = new ContentValues();
//			cvU.put(DBOpenHelper.UPLOADED, String.valueOf(newU));
//			dbAdapter.update(cvU);
//		}
		
		Logger.Debug(
				"Downloaded:::"+ humanReadableByteCount(downloaded,true) +
				"\nUploaded:::"+ humanReadableByteCount(uploaded,true));
	}
	
	public static String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
}
