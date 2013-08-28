/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * ContolSwitches
 * com.acropolis.location.module.controller
 * ContolSwitches.java
 * Created - 2013-08-21 2:01:51 PM	
 * Modified - 2013-08-21 2:01:51 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.location.module.controller;

import logger.Logger;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.acropolis.location.module.view.LocationModuleActivity;

public class ControlSwitches 
{
	static Context appContext=null;
	static Intent appIntent=null;
	static TelephonyManager teleManager=null;
	
	public static void setupMainBoard()
	{
		appContext = LocationModuleActivity.getAppContext();
		appIntent = LocationModuleActivity.getAppIntent();
		teleManager = (TelephonyManager) 
				appContext.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public static boolean switchON()
	{
		Logger.Debug("listening for SERVICE STATE");
		teleManager.listen(new RoamingListener(), 
				PhoneStateListener.LISTEN_SERVICE_STATE);
		return true;
	}
	
	
	public static boolean switchOFF()
	{	
		teleManager.listen(new RoamingListener(),
				PhoneStateListener.LISTEN_NONE);
		return false;
	}
	
}