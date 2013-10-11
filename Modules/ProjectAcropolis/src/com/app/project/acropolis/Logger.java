/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * Logger
 * com.app.project.acropolis
 * Logger.java
 * Created - 2013-10-11 3:12:00 PM	
 * Modified - 2013-10-11 3:12:00 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis;

import android.util.Log;

/**
 * @author CPH-iMac
 *
 */
public class Logger 
{

	public final static String TAG = "Project Acropolis";
	
	public static void Debug(String msg)
	{
		Log.d(TAG, msg);
	}
	
}
