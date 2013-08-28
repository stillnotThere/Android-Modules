package com.acropolis.playground.module.telephony.minutes;

/*******************************************************************************
 * Cell Phone Hospital Inc. and Project Acropolis Copyright (c).
 * 2013.
 * @author - Rohan K.M {rohan.mahendroo@gmail.com}
 * Are copyright protected to author and delegated to the
 * software development and
 * distribution under CPH jurisdiction.
 ******************************************************************************/

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.acropolis.playground.module.telephony.Activity.TelephonyActivity;

/**
 * Created by CellPhoneHospitalInc on 2013-07-10.
 */
public class CallMonitorHub
{

	private static TelephonyManager _telephonyManager = null;

	/**
	 * Initiate incoming listener and outgoing receiver
	 * @param telephonyManager
	 */
	public static void startMonitoring(TelephonyManager telephonyManager)
	{
		_telephonyManager = telephonyManager;
		//incoming
		_telephonyManager.listen(new IncomingCallListener(),
				PhoneStateListener.LISTEN_CALL_STATE);
		//outgoing
	}

	/**
	 *  Kills Incoming listener & Outgoing receiver
	 */
	public static void stopMonitoring()
	{
		//incoming
		_telephonyManager.listen(new IncomingCallListener(),
				PhoneStateListener.LISTEN_NONE);
		//outgoing
	}

}
