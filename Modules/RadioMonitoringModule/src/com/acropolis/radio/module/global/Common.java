/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * Common
 * com.acropolis.radio.module.global
 * Common.java
 * Created - 2013-08-26 4:39:33 PM	
 * Modified - 2013-08-26 4:39:33 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.radio.module.global;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * @author CPH-iMac
 *
 */
public class Common 
{
	public final static String datePattern = "yyyyMMddHHmm";
	public final static SimpleDateFormat sdf = new SimpleDateFormat(datePattern,
			Locale.CANADA);

	public static String getConvertedDate(int milli)
	{
		return (String)sdf.format(new Date(milli));
	}
	
	/*DUMMY VALUES*/
	public final static String DUMMY_ID = "0";
	public final static String DUMMY_TIMESTAMP = datePattern;
	public final static String getDevicePhoneNumber(Context context)
	{
		return ((TelephonyManager)context.getApplicationContext().
				getSystemService(Context.TELEPHONY_SERVICE)).
				getLine1Number();
	}
	public final static String DUMMY_ROAMING = "false";
	public final static String DUMMY_INCOMING = "0";
	public final static String DUMMY_OUTGOING = "0";
	public final static String DUMMY_RECEIVED = "0";
	public final static String DUMMY_SENT = "0";
	public final static String DUMMY_DOWNLOADED = "0";
	public final static String DUMMY_UPLOADED = "0";
	
}
