/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * RoamingListener
 * com.acropolis.location.module.controller
 * RoamingListener.java
 * Created - 2013-08-21 2:05:40 PM	
 * Modified - 2013-08-21 2:05:40 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.location.module.controller;

import logger.Logger;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;

public class RoamingListener extends PhoneStateListener
{

	public static boolean roaming = false;
	private static int state = 0;
	private static String operatorName = "";
	
	public void onServiceStateChanges(ServiceState _serviceState)
	{
		Logger.Debug(this.getClass().toString());
		ServiceState serviceState = new ServiceState(_serviceState);
		state = serviceState.getState();
		
		switch(state)
		{
		
		case ServiceState.STATE_IN_SERVICE:
		{
			Logger.Debug(this.getClass()+"\nIN_SERVICE");
			operatorName = serviceState.getOperatorAlphaLong();
			roaming = serviceState.getRoaming();
			Logger.Debug("OpName::"+operatorName+"\nRoaming::"+roaming);
		};
		
		case ServiceState.STATE_OUT_OF_SERVICE:
		{Logger.Debug(this.getClass()+"\nOUT_OF_SERVICE");};
		
		case ServiceState.STATE_POWER_OFF:
		{Logger.Debug(this.getClass()+"\nPOWER_OFF");};
		
		case ServiceState.STATE_EMERGENCY_ONLY:
		{Logger.Debug(this.getClass()+"\nEMERGENCY_ONLY");};
		
		}
		
	}

	/**
	 * @return the roaming
	 */
	public static boolean isRoaming() 
	{
		return roaming;
	}

	/**
	 * @return the operatorName
	 */
	public static String getOperatorName()
	{
		return operatorName;
	}
			
}