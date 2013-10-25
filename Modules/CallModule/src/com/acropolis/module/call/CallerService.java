/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * CallerService
 * com.acropolis.module.call
 * CallerService.java
 * Created - 2013-10-10 6:21:15 PM	
 * Modified - 2013-10-10 6:21:15 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.call;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * @author CPH-iMac
 *
 */
public class CallerService extends Service
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
		setupCallListener();
//		setupOutgoingListener();
	}

	public int onStartCommand(Intent intent,int flags,int startId)
	{
		return Service.START_STICKY;
	}

	public void setupCallListener()
	{
		TelephonyManager telephonyManager = (TelephonyManager)
				getApplicationContext().
				getSystemService(Context.TELEPHONY_SERVICE);

		telephonyManager.listen(new CallStateListener(),
				PhoneStateListener.LISTEN_CALL_STATE);
	}

	public void setupOutgoingListener()
	{
		IntentFilter intentFilter =
				new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		registerReceiver(new OutgoingReceiver(),intentFilter,
				"android.permission.READ_PHONE_STATE",null);
	}

}
