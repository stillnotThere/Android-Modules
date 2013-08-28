/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * MinutesListenerModule
 * com.acropolis.module.listener.minutes.controller
 * GetDetails.java
 * Created - 2013-07-31	
 * Modified - 2013-07-31
 * TODO
 */
package com.acropolis.radio.module.monitor.controller;

/**
 * 
 * Retrieves/sends call state, service state,
 * call phone#, call time-stamp, call duration, call type 
 *
 */
public class GetDetails
{
	/**
	 * fetch device's active service state or radio off
	 * (SIM active/absent deduction)
	 * @return CURRENT_SERVICE_STATE
	 */
	public static int getServiceState()
	{
		return RadioEngine.CURRENT_SERVICE_STATE;
	}

	/**
	 * @return Last call placed or answered phone number
	 */
	public static String getCallPhoneNumber() 
	{
		return RadioEngine.callPhoneNumber;
	}

	/**
	 * @return Last call place or answered time(milliseconds)
	 */
	public static Integer getCallTimeStamp() 
	{
		return RadioEngine.callTimeStamp;
	}

	public static void setCallDuration(Integer newvalue)
	{
		RadioEngine.callDuration = newvalue;
	}
	
	/**
	 * @return Last call place or answered duration(milliseconds)
	 */
	public static Integer getCallDuration() 
	{
		return RadioEngine.callDuration;
	}

	public static void setCallType(int type)
	{
		RadioEngine.callType = type;
	}
	/**
	 * @return Last call type ("incoming","outgoing","missed")
	 */
	public static int getCallType() 
	{
		return RadioEngine.callType;
	}
	
	/**
	 * @return Downloaded bytes
	 */
	public static long getSessionDownloadedBytes()
	{
		return RadioEngine.DOWNLOADED;
	}
	
	/**
	 * @return Uploaded bytes
	 */
	public static long getSessionUploadedBytes()
	{
		return RadioEngine.UPLOADED;
	}
	
}