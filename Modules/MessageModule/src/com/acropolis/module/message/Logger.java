/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * Logger
 * com.acropolis.module.message
 * Logger.java
 * Created - 2013-10-02 3:33:50 PM	
 * Modified - 2013-10-02 3:33:50 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.message;

import android.util.Log;

/**
 * @author CPH-iMac
 *
 */
public class Logger 
{

	public static final String TAG = "##MESSAGEMODULE";
	
	public static void Debug(String msg)
	{
		Log.d(TAG, msg);
	}
	
}
