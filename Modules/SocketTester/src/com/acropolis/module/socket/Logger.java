/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * Logger
 * com.acropolis.module.socket
 * Logger.java
 * Created - 2013-11-06 4:32:01 PM	
 * Modified - 2013-11-06 4:32:01 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.socket;

import android.util.Log;

/**
 * @author CPH-iMac
 *
 */
public class Logger 
{
	public static final String TAG = "%% Socket";
	public static void Debugger(String msg)
	{
		Log.d(TAG, msg);
	}
}
