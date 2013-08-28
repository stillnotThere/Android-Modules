package com.acropolis.playground.module.telephony.minutes;

/*******************************************************************************
 * Cell Phone Hospital Inc. and Project Acropolis Copyright (c).
 * 2013.
 * @author - Rohan K.M {rohan.mahendroo@gmail.com}
 * Are copyright protected to author and delegated to the
 * software development and
 * distribution under CPH jurisdiction.
 ******************************************************************************/

import android.content.Context;
import android.provider.CallLog;

import com.acropolis.playground.module.telephony.Activity.TelephonyActivity;
import com.acropolis.playground.module.telephony.Logger.Logging;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by CellPhoneHospitalInc on 2013-07-12.
 */
public class LogChecker implements  Runnable
{

	private static Context appContext = null;

	final static int IncomingClassCaller = 11;
	final static int OutgoingClassCaller = 22;
	static String LastOutgoingCall = "";
	static String LastIncomingCall = "";

	//HashMap keys
	final static String INCOMING_TYPE = "INCOMING";
	final static String OUTGOING_TYPE = "OUTGOING";
	final static String PHONE_NUMBER = "PHONE_NUMBER";
	final static String TIME_OF_CALL = "TIME_OF_CALL";	//yyyy-mm-dd hh:mm:ss (24hrs)

	final static int callDetailsAttributeLength = 3;

	protected static void checkLog()
	{

	}

	protected static HashMap fetchCallDetails(int caller,Date timeOfCall)
	{
		appContext = TelephonyActivity.getAppContext();
		HashMap _callDetails = new HashMap(callDetailsAttributeLength);
		_callDetails.clear();
		synchronized (_callDetails)
		{
			switch (caller)
			{

				case IncomingClassCaller:
				{
					//check for incoming call strict


				};

				case OutgoingClassCaller:
				{
					//check for outgoing call strict
					LastOutgoingCall = CallLog.Calls.getLastOutgoingCall(appContext);

				};

			}
		}

		return _callDetails;
	}

	@Override
	public void run()
	{

	}
}