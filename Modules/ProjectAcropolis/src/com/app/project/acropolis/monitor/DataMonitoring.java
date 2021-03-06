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

import android.content.Context;
import android.net.TrafficStats;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.database.PersistedData;

/**
 * @author CPH-iMac
 *
 */
public class DataMonitoring extends PhoneStateListener 
{
	long downloaded = 0;
	long uploaded = 0;
	final Context _context = ProjectAcropolisActivity.getContext();
	long DB_D = Long.parseLong(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_DOWNLOADED));
	long DB_U = Long.parseLong(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_UPLOADED));
	long DB_R_D = Long.parseLong(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_DOWNLOADED));
	long DB_R_U = Long.parseLong(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_UPLOADED));
	
	long incD = 0;
	long incU = 0;
	long lastD = 0;
	long lastU = 0;

	long incRD = 0;
	long incRU = 0;
	long lastRD = 0;
	long lastRU = 0;
	
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
		if(new GlobalConstants().checkRoaming(_context))
		{
			incRD = downloaded - lastRD;
			DB_R_D = DB_R_D + incRD;
			lastRD = downloaded;
			incRD=0;
			new PersistedData().putData(GlobalConstants.PersistenceConstants.ROAM_DOWNLOADED, String.valueOf(DB_R_D));
		}
		else
		{
			incD = downloaded - lastD;
			DB_D = DB_D + incD;
			lastD = downloaded;
			incD=0;
			new PersistedData().putData(GlobalConstants.PersistenceConstants.LOCAL_DOWNLOADED, String.valueOf(DB_D));
		}
	}

	public void checkUpload()
	{
		if(new GlobalConstants().checkRoaming(_context))
		{
			incRU = uploaded - lastRU;
			DB_R_U = DB_R_U + incRU;
			lastRU = uploaded;
			incRU=0;
			new PersistedData().putData(GlobalConstants.PersistenceConstants.LOCAL_UPLOADED, String.valueOf(DB_R_U));
		}
		else
		{
			incU = uploaded - lastU;
			DB_U = DB_U + incU;
			lastU = uploaded;
			incU=0;
			new PersistedData().putData(GlobalConstants.PersistenceConstants.LOCAL_UPLOADED, String.valueOf(DB_U));
		}
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format(Locale.CANADA,"%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

}
