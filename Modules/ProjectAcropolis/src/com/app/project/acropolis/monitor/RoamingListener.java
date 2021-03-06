/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * RoamingListener
 * com.app.project.acropolis
 * RoamingListener.java
 * Created - 2013-10-11 3:33:19 PM	
 * Modified - 2013-10-11 3:33:19 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.app.project.acropolis.GlobalConstants;

/**
 * @author CPH-iMac
 *
 */
public class RoamingListener extends BroadcastReceiver
{
	public final String[] CAN_OPERATORS = {"Rogers","TELUS","Bell"};
	public boolean roaming = false;

	public ConnectivityManager connectivityManager = null;
	public NetworkInfo networkInfo = null;

	public Context _context = null;
	public Intent _intent = null;
	public boolean isRoaming = false;
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		_context = context;
		_intent = intent;
		if(new GlobalConstants().isMobileNetworkType(context))
		{
			isRoaming = new GlobalConstants().checkRoaming(context);
		}
	}

}
