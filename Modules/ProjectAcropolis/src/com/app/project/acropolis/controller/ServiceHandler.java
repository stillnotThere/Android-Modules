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
package com.app.project.acropolis.controller;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.Logger;
import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.comm.SocketClientFormatter;
import com.app.project.acropolis.monitor.CallMonitoring_2;
import com.app.project.acropolis.monitor.DataMonitoring;
import com.app.project.acropolis.monitor.MessageMonitoring;
import com.app.project.acropolis.monitor.RoamingListener;
import com.app.project.acropolis.monitor.plan.BillingCycleListener;

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
		public ServiceHandler getService()
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
		setupBillDateListener();
		setupSocketHandler();
		setupRoamingChanges();
		setupCallMonitoring();
		setupMessageMonitoring();
		setupDataMonitoring();
	}

	/**
	 * Register BroadcastReceiver 
	 * {@link BillingCycleListener#onReceive(Context, Intent)} 
	 * listening for ACTION_TIME_TICK
	 */
	public void setupBillDateListener()
	{
		Context context = ProjectAcropolisActivity.getContext();
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
		context.registerReceiver(new BillingCycleListener(), intentFilter,null,null);
	}
	
	public void setupSocketHandler()
	{
		Logger.Debug("234231234sdfdfdsg\n\n\n\n\n");
		GlobalConstants.socketClientHandler = new Handler();
		GlobalConstants.socketClientHandler.post(new SocketClientFormatter());
	}
	
	public void setupCallMonitoring()
	{
		ProjectAcropolisActivity.getContext().
		getContentResolver().
		registerContentObserver(
				CallLog.Calls.CONTENT_URI, true,
				new CallMonitoring_2());
//		TelephonyManager telephonyManager = (TelephonyManager) 
//				getSystemService(Context.TELEPHONY_SERVICE);
//
//		telephonyManager.listen(new CallMonitoring(),
//				PhoneStateListener.LISTEN_CALL_STATE);
	}

	public void setupMessageMonitoring()
	{
		ContentResolver contentResolver = ProjectAcropolisActivity.getContext().getContentResolver();
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
		Context context = ProjectAcropolisActivity.getContext();
		IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(new RoamingListener(),
				intentFilter,"android.permission.ACCESS_NETWORK_STATE",null);
	}
	
	public int onStartCommand(Intent intent,int arg0,int arg1)
	{
		return Service.START_NOT_STICKY;
	}

}
