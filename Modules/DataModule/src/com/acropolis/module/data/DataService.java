/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DataService
 * com.acropolis.module.data
 * DataService.java
 * Created - 2013-10-11 1:41:36 PM	
 * Modified - 2013-10-11 1:41:36 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.data;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * @author CPH-iMac
 *
 */
public class DataService extends Service 
{

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

	public void onCreate()
	{
		TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		
		Logger.Debugger(telephonyManager.getNetworkOperatorName());
		
		telephonyManager.listen(new DataStateListener(), PhoneStateListener.LISTEN_DATA_ACTIVITY);
	}

	public int onStartCommand(Intent intent,int flagId,int arg1)
	{
		return Service.START_STICKY;
	}

}
