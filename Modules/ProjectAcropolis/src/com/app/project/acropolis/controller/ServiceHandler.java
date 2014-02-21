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

import com.app.project.acropolis.Logger;
import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.comm.SocketClientFormatter;
import com.app.project.acropolis.comm.SocketServerConnector;
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
	final String hangoutsmsOutUri = "content://sms/inbox";
	Uri outUri = Uri.parse(msgOutUri);
	Context _context = ProjectAcropolisActivity.getContext();

	final IBinder ibinder = new ServiceBinder();
	
	public class ServiceBinder extends Binder
	{
		public ServiceHandler getService()
		{
			return ServiceHandler.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return ibinder;
	}

	public void onCreate()
	{
		Logger.Debug(this.getClass().getSimpleName());

		setupSocketHandler();
		setupMessageMonitoring();
		setupCallMonitoring();
		setupDataMonitoring();
		setupRoamingChanges();
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
		intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		context.registerReceiver(new BillingCycleListener(),intentFilter,null,null);
	}

	public void setupSocketHandler()
	{
		Logger.Debug("socket handler");
		Handler socketClientHandler = new Handler();
		socketClientHandler.
		post(new SocketClientFormatter(ProjectAcropolisActivity.getContext()));
		
		//start looping when done
		socketClientHandler.postDelayed(new SocketClientFormatter(ProjectAcropolisActivity.getContext()),4*60*60*1000);
		
	}

	public void setupServerSocketHandler()
	{
		Handler socketServerHandler = new Handler();
		socketServerHandler.post(new SocketServerConnector());
	}

	public void setupCallMonitoring()
	{
		Handler callhandler = new Handler();
		
		ProjectAcropolisActivity.getContext().
		getContentResolver().
		registerContentObserver(
				CallLog.Calls.CONTENT_URI, true,
				new CallMonitoring_2(callhandler));
	}

	public void setupMessageMonitoring()
	{
		Logger.Debug("messagemonitoring");
		ContentResolver contentResolver = ProjectAcropolisActivity.getContext().getContentResolver();
		contentResolver.registerContentObserver(outUri, true, 
				new MessageMonitoring());//_context));
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
		intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		context.registerReceiver(new RoamingListener(),
				intentFilter,"android.permission.ACCESS_NETWORK_STATE",null);
	}

	public int onStartCommand(Intent intent,int arg0,int arg1)
	{
		return Service.START_NOT_STICKY;
	}

}
