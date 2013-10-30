/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * ServiceHandler
 * com.app.project.acropolis
 * ServiceHandler.java
 * Created - 2013-10-11 3:09:55 PM	
 * Modified - 2013-10-11 3:09:55 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * @author CPH-iMac
 *
 */
public class ServiceHandler extends Service 
{
	final String msgOutUri = "content://sms";
	Uri outUri = Uri.parse(msgOutUri);

	public class ServiceBinder extends Binder
	{
		ServiceHandler getService()
		{
			return ServiceHandler.this;
		}
	}

	final IBinder ibinder = new ServiceBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		return ibinder;
	}

	public void onCreate()
	{
		setupRoamingChanges();
		setupCallMonitoring();
		setupMessageMonitoring();
		setupDataMonitoring();
	}

	public void setupCallMonitoring()
	{
		TelephonyManager telephonyManager = (TelephonyManager) 
				getSystemService(Context.TELEPHONY_SERVICE);

		telephonyManager.listen(new CallMonitoring(),
				PhoneStateListener.LISTEN_CALL_STATE);
	}

	public void setupMessageMonitoring()
	{
		ContentResolver contentResolver = MainActivity.getContext().getContentResolver();
		contentResolver.registerContentObserver(outUri, true, 
				new MessageMonitoring());
	}

	public void setupDataMonitoring()
	{
		TelephonyManager telephonyManager = (TelephonyManager)
				getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(new DataMonitoring(), 
				PhoneStateListener.LISTEN_DATA_ACTIVITY);
	}
	
	public void setupRoamingChanges()
	{
		Context context = MainActivity.getContext();
		IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(new RoamingListener(),
				intentFilter,"android.permission.ACCESS_NETWORK_STATE",null);
	}
	
	public int onStartCommand(Intent intent,int arg0,int arg1)
	{
		return Service.START_NOT_STICKY;
	}

}
