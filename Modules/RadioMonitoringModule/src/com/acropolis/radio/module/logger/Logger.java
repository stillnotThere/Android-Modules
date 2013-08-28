package com.acropolis.radio.module.logger;

import android.util.Log;

public class Logger 
{
	static String tag = "RadioMonitoring_Module";
	public static void Information(String msg)
	{
		Log.i(tag, msg);
	}
	public static void Error(String msg,Throwable throwable)
	{
		if(throwable==null)
			Log.e(tag, msg);
		else
			Log.e(tag, msg, throwable);
	}
	public static void Debug(String msg)
	{
		Log.d(tag, msg);
	}
	
	public static void Verbose(String msg)
	{
		Log.v(tag, msg);
	}
	
	public static void whatTHEhell(String msg,
			Throwable throwable)
	{
		Log.wtf(tag, msg, throwable);
	}
}