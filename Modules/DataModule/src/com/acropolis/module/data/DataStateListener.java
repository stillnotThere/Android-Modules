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
package com.acropolis.module.data;

import android.net.TrafficStats;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * @author CPH-iMac
 *
 */
public class DataStateListener extends PhoneStateListener 
{

	long downloaded = 0;
	long uploaded = 0;
	
	public void onDataActivity(int direction)
	{
		switch(direction)
		{
		case TelephonyManager.DATA_ACTIVITY_IN:
			downloaded = TrafficStats.getMobileRxBytes();
			Logger.Debugger("DATA IN\nbytes::"+downloaded);
		
		case TelephonyManager.DATA_ACTIVITY_OUT:
			uploaded = TrafficStats.getMobileTxBytes();
			Logger.Debugger("DATA OUT\nbytes::"+uploaded);
			
		case TelephonyManager.DATA_ACTIVITY_INOUT:
			downloaded = TrafficStats.getMobileRxBytes();
			uploaded = TrafficStats.getMobileTxBytes();
			Logger.Debugger("DATA INOUT\nbytes::"+(downloaded + uploaded));
		}
		Logger.Debugger(
				"Downloaded:::"+downloaded +
				"\nUploaded:::"+uploaded);
	}
	
	public long getDownloaded()
	{
		return downloaded;
	}
	
	public long getUploaded()
	{
		return uploaded;
	}
	
}
