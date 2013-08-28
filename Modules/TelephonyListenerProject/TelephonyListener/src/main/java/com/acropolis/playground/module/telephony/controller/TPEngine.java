package com.acropolis.playground.module.telephony.controller;

/*******************************************************************************
 * Cell Phone Hospital Inc. and Project Acropolis Copyright (c).
 * 2013.
 * @author - Rohan K.M {rohan.mahendroo@gmail.com}
 * Are copyright protected to author and is delegated to the software
 * developed/development and distribution under CPH jurisdiction.
 ******************************************************************************/

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.acropolis.playground.module.telephony.Logger.Logging;
import com.acropolis.playground.module.telephony.Messaging.MessageListener;
import com.acropolis.playground.module.telephony.data.DataListener;
import com.acropolis.playground.module.telephony.location.RoamingListener;
import com.acropolis.playground.module.telephony.minutes.CallMonitorHub;
import com.acropolis.playground.module.telephony.minutes.IncomingCallListener;
import com.acropolis.playground.module.telephony.Activity.TelephonyActivity;

/**
 * Created by CellPhoneHospitalInc on 2013-07-08.
 */
public class TPEngine
{
	final static int LOCATION_ENGINE = 11;
	final static int ROAMING_ENGINE = 12;
	final static int MINUTES_ENGINE = 13;
	final static int MESSAGES_ENGINE = 14;
	final static int DATA_ENGINE = 15;
	final static int SERVER_ENGINE = 16;

	protected static TelephonyManager _telephonyManager = null;
	protected static Context appContext = null;

	public TPEngine()
	{
		Logging.Information(this.getClass().toString());
	}

	public static void Ignition()
	{
		//device TelephonyManager instance
		appContext = TelephonyActivity.getAppContext();
		_telephonyManager = (TelephonyManager)
				appContext.getSystemService(Context.TELEPHONY_SERVICE);
	}


	public String getDevicePhoneNumber()
	{
		String phoneNumber = _telephonyManager.getLine1Number();
		Logging.Information("Phone Number::"+phoneNumber);
		return phoneNumber;
	}

	public String getOperatorName()
	{
		String operatorName = _telephonyManager.getNetworkOperatorName();
		Logging.Information("Operator Name::"+operatorName);
		return operatorName;
	}

	public int getPhoneType()
	{
		int phoneType = _telephonyManager.getPhoneType();
		Logging.Information("Phone-Type::"+phoneType);
		return phoneType;
	}

	public boolean isCellular()
	{
		boolean isCell = false;
		switch(getPhoneType())
		{
			case TelephonyManager.PHONE_TYPE_GSM:{
				Logging.Information("GSM");
				isCell = true;
			}
			case TelephonyManager.PHONE_TYPE_CDMA:{
				Logging.Information("CDMA");
				isCell = true;
			}
			case TelephonyManager.PHONE_TYPE_NONE:{
				Logging.Information("No Radio TABLET WLAN Only");
				CharSequence sequence = "Device not supported";
				isCell = false;
				Toast.makeText(appContext,sequence,Toast.LENGTH_LONG).show();
			}
		}
		return  isCell;
	}

	public void startEngines()
	{
		CallMonitorHub.startMonitoring(_telephonyManager);
		_telephonyManager.listen(new DataListener(),
				PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
						& PhoneStateListener.LISTEN_DATA_ACTIVITY);
		_telephonyManager.listen(new RoamingListener(),
				PhoneStateListener.LISTEN_SERVICE_STATE
						& PhoneStateListener.LISTEN_CELL_LOCATION);
		//TODO
		new MessageListener();
	}

	public boolean stopAllEngines()
	{
		CallMonitorHub.stopMonitoring();
//		_telephonyManager.listen(new IncomingCallListener(),
//				PhoneStateListener.LISTEN_NONE);
		_telephonyManager.listen(new DataListener(),
				PhoneStateListener.LISTEN_NONE);
		_telephonyManager.listen(new RoamingListener(),
				PhoneStateListener.LISTEN_NONE);
	//TODO
		//stop message listener
		return true;
	}

	public boolean stopEngine(Class name,int engine)
	{
		switch(engine)
		{
			case LOCATION_ENGINE:
			{
				//TODO
				Logging.Information("LOCATION ENGINE stopped");
			};
			case ROAMING_ENGINE:
			{
				_telephonyManager.listen(new RoamingListener(),
						PhoneStateListener.LISTEN_NONE);
				Logging.Information("ROAMING LISTENER stopped");
			};
			case MINUTES_ENGINE:
			{
				CallMonitorHub.stopMonitoring();
				_telephonyManager.listen(new IncomingCallListener(),
						PhoneStateListener.LISTEN_NONE);
			};
			case MESSAGES_ENGINE:
			{
				//TODO
				Logging.Information("MESSAGES ENGINE stopped");
			};
			case DATA_ENGINE:
			{
				_telephonyManager.listen(new DataListener(),
						PhoneStateListener.LISTEN_NONE);
				Logging.Information("DATA ENGINE stopped");
			};
			case SERVER_ENGINE:
			{
				//TODO
				Logging.Information("SERVER ENGINE stopped");
			};
			default:
			{
				Logging.Verbose("Unknown Engine!!!"
						+ "\nClass:"+name.getClass().toString());
			}
		}

		return true;
	}

}