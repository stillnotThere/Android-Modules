/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * Logger
 * com.acropolis.module.call
 * Logger.java
 * Created - 2013-10-10 3:57:08 PM	
 * Modified - 2013-10-10 3:57:08 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.call;

import android.util.Log;

/**
 * @author CPH-iMac
 *
 */
public class Logger
{
	final static String TAG = "##CallModule";
	
	public static void Debug(String msg)
	{
		Log.d(TAG, msg);
	}
	
}
