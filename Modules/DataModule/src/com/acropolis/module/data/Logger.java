/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * Logger
 * com.acropolis.module.data
 * Logger.java
 * Created - 2013-10-11 2:49:10 PM	
 * Modified - 2013-10-11 2:49:10 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.data;

import android.util.Log;

/**
 * @author CPH-iMac
 *
 */
public class Logger 
{

	public final static String TAG = "##DataMonitor";
	
	public static void Debugger(String msg)
	{
		Log.d(TAG, msg);
	}
	
}
