/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * ServiceReceiver
 * com.app.project.acropolis
 * ServiceReceiver.java
 * Created - 2013-10-11 6:40:12 PM	
 * Modified - 2013-10-11 6:40:12 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author CPH-iMac
 *
 */
public class ServiceReceiver extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Intent serviceIntent = new Intent(context,ServiceHandler.class);
		context.startService(serviceIntent);
	}

}
