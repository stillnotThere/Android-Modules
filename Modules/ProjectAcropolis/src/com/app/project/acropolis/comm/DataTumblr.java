/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DataCollector
 * com.app.project.acropolis.comm
 * DataCollector.java
 * Created - 2013-11-01 12:35:33 PM	
 * Modified - 2013-11-01 12:35:33 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.comm;

import com.app.project.acropolis.Logger;

/**
 * @author CPH-iMac
 *
 */
public class DataTumblr 
{
	public static String rcv = "";
	public static String snd = "";
	
	public static  void setReceivedServerData(String msg)
	{
		rcv = msg;
	}
	
	public static String getReceivedServerData()
	{
		return rcv;
	}
	
	public static void setSendClientData(String msg)
	{
		Logger.Debug(msg);
		snd = msg;
	}
	
	public static String getSendClientData()
	{
		return snd;
	}
	
}
