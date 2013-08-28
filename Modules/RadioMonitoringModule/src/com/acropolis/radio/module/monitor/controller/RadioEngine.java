/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * MinutesListenerModule
 * com.acropolis.radio.module.monitoring.controller
 * RadioEngine.java
 * Created - 2013-07-26	
 * Modified - 2013-08-2
 * TODO
 */
package com.acropolis.radio.module.monitor.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.acropolis.radio.module.RadioModuleActivity;
import com.acropolis.radio.module.logger.Logger;

public class RadioEngine 
{
	/*singleton initialization*/
	protected static Intent _intent = null;
	protected static Context _context = null;
	protected static TelephonyManager _teleManager = null;
	protected static ConnectivityManager _connectivityManager = null;
	protected static SmsManager _smsManager = null;
	protected static IntentFilter _callLogIF = null;
	{
		_intent = (Intent) com.acropolis.radio.module.
				RadioModuleActivity.getAppIntent();
		_context = (Context) RadioModuleActivity.getAppContext();
		_teleManager = (TelephonyManager)
				_context.getSystemService(Context.TELEPHONY_SERVICE);
		_connectivityManager = (ConnectivityManager) 
				_context.getSystemService(Context.CONNECTIVITY_SERVICE);
		_smsManager = (SmsManager) SmsManager.getDefault();
	}
	
	/**************************************************************************/
	/**Radio Service Constants*/
	/*Service State*/
	protected static int CURRENT_SERVICE_STATE = 0;
	protected final static int ACTIVE_SERVICE = 21;
	protected final static int INACTIVE_SERVICE = 22;
	protected final static int INACTIVE_POWER_OFF = 25;
	/**************************************************************************/
	
	/**************************************************************************/
	/**Call Monitoring Variables**/
	/*Phone State*/
	protected static boolean IDLE = false;
	protected static boolean OFFHOOK = false;
	protected static boolean RINGING = false;
	/*Call Type*/
	protected static int callType = 0;
	protected final static int _INCOMING = 31;
	protected final static int _OUTGOING = 32;
	protected static boolean __INCOMING = false ;
	protected static boolean __OUTGOING = false;
	protected final static String INCOMING = "incoming";
	protected final static String OUTGOING = "outgoing";
	protected final static String MISSED = "missed";
	/*Call log details*/
	protected static String callPhoneNumber = "";
	protected static Integer callTimeStamp = Integer.valueOf(0);	//milliseconds
	protected static Integer callDuration = Integer.valueOf(0);	//milliseconds
	protected static int callDurationFormatted = 0;//seconds
	protected static String callTimeStampFormatted = "";//SimpleDateFormat
	protected static CallLog callLog=null;
	protected static String INCOMING_NUMBER;
	/**************************************************************************/
	
	/**************************************************************************/
	/**Data Monitoring Variables**/
	/*Data check variables*/
	protected static int CURRENT_DATA_NETWORK = 0;
	protected static int CURRENT_DATA_STATE = 0;
	protected static int CURRENT_NETWORK_TYPE = 0;	//WiFi or Mobile or Bluetooth
	/*Data connection state*/
	protected static final int NETWORK_BLUETOOTH = 411;
	protected static final int NETWORK_WIFI = 412;
	protected static final int NETWORK_WIMAX = 413;
	protected static final int NETWORK_MOBILE = 414; //includes DUN/HIPRI/SUPL
	protected static final int NETWORK_MMS = 415;
	protected static final int NETWORK_ETHERNET = 416;
	protected static final int DATA_ACTIVE = 41;
	protected static final int DATA_INACTIVE = 42;
	protected static boolean START_MONITORING = false;
	/*Mobile Data activity state*/
	protected final static int DATA_DUPLEX = 402;
	protected final static int DATA_DOWNLOADING = 405;
	protected final static int DATA_UPLOADING = 406;
	protected final static int DATA_DORMANT = 407;
	/*Mobile Data counter containers (bytes)*/
	protected static long DOWNLOADED = 0;
	protected static long UPLOADED = 0;
	protected static int DATA_ACTIVITY = 0;
	/**************************************************************************/
	
	/**************************************************************************/
	/**Message Monitoring Variables**/
	/*Message state(identification)*/
	protected static boolean MESSAGE_STATE_RECEIVED = false;
	protected static boolean MESSAGE_STATE_SENT = false;
	/*Message counter containers*/
	protected static int MESSAGE_SENT = 0;
	protected static int MESSAGE_RECEIVED = 0;
	/**************************************************************************/
	
	/**
	 * START YOUR ENGINES!!!!!
	 * @return true
	 */
	public boolean igniteEngine()
	{
//		IntentFilter intentFilter = new IntentFilter(android.intet.);
		_teleManager.listen(
				new com.acropolis.radio.module.monitor.controller.
				CallInterceptor.ServiceStateListener(),
				PhoneStateListener.LISTEN_SERVICE_STATE);
		Logger.Debug("Service State mode successful");
		_teleManager.listen(
				new com.acropolis.radio.module.monitor.controller.
				DataInterecptor.DataConnectionState(),
				PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
		_teleManager.listen(
				new com.acropolis.radio.module.monitor.controller.
				DataInterecptor.DataActivityMonitor(),
				PhoneStateListener.LISTEN_DATA_ACTIVITY);
		Logger.Debug("Data connection activity mode successful");
		Logger.Debug("Data connection state mode successful");
		_teleManager.listen(
				new com.acropolis.radio.module.monitor.controller.
				CallInterceptor.CallStateListener(),
				PhoneStateListener.LISTEN_CALL_STATE);
		Logger.Debug("Call State mode successful");
		Logger.Debug("Engine started");
		return true;
	}

	/**
	 * KILL KILLL!!!!!
	 * @return true
	 */
	public boolean killEngine()
	{
		_teleManager.listen(
				new com.acropolis.radio.module.monitor.controller.
				CallInterceptor.ServiceStateListener(), 
				PhoneStateListener.LISTEN_NONE);
		_teleManager.listen(
				new com.acropolis.radio.module.monitor.controller.
				DataInterecptor.DataConnectionState(),
				PhoneStateListener.LISTEN_NONE);
		_teleManager.listen(
				new com.acropolis.radio.module.monitor.controller.
				DataInterecptor.DataActivityMonitor(),
				PhoneStateListener.LISTEN_NONE);
		_teleManager.listen(
				new com.acropolis.radio.module.monitor.controller.
				CallInterceptor.CallStateListener(),
				PhoneStateListener.LISTEN_NONE);
		Logger.Debug("Engine stopped");
		return true;
	}

}