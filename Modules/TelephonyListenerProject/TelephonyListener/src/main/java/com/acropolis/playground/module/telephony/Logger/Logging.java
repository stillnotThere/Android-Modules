package com.acropolis.playground.module.telephony.Logger;

/*******************************************************************************
 * Cell Phone Hospital Inc. and Project Acropolis Copyright (c).
 * 2013.
 * @author - Rohan K.M {rohan.mahendroo@gmail.com}
 * Are copyright protected to author and is delegated to the software developed/development and distribution under CPH jurisdiction.
 ******************************************************************************/

import android.util.Log;

/**
 * Created by CellPhoneHospitalInc on 2013-07-08.
 */
public class Logging
{

	private final static String appLogTag = "*TelephonyListenerProject";

	public static int Information(String msg)
	{
		return Log.i(appLogTag,msg);
	}

	public static int Verbose(String msg)
	{
		return Log.v(appLogTag,msg);
	}

	public static int Debug(String msg)
	{
		return Log.d(appLogTag,msg);
	}

	public static int Error(String msg)
	{
		return Log.e(appLogTag,msg);
	}
}