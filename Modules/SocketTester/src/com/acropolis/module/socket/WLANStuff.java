/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * WLANStuff
 * com.acropolis.module.socket
 * WLANStuff.java
 * Created - 2013-11-06 4:17:26 PM	
 * Modified - 2013-11-06 4:17:26 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.socket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * @author CPH-iMac
 *
 */
public class WLANStuff 
{

	public static WifiManager wifiManager = null;
	public static WifiInfo wifiInfo = null;

	public static final String CPH_SSID = "\"CPH Inc.\"";
	public static String SSID = "";

	public static boolean onCPHWLAN()
	{

		boolean onCPH = false;
		if(isConnected())
		{
			wifiManager = (WifiManager) 
					MainActivity.
					getContext().
					getSystemService(Context.WIFI_SERVICE);
			wifiInfo = wifiManager.getConnectionInfo();
			SSID = wifiInfo.getSSID();
			Logger.Debugger("SSID::"+SSID);
			if(SSID.equals((String)CPH_SSID))
			{
				onCPH = true;
				Logger.Debugger("connected to CPH");
				MainActivity.postToast("cannot connect to server...on CPHInc wifi");
			}
			else
			{
				onCPH = false;
			}
		}
		return onCPH;
	}

	public static boolean isConnected()
	{
		boolean connected = false;
		ConnectivityManager connMngr = (ConnectivityManager)
				MainActivity.getContext().
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMngr.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI);

		if(networkInfo.isConnected())
			connected = true;
		else
			connected = false;

		return connected;
	}

}
