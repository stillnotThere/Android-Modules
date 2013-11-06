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
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * @author CPH-iMac
 *
 */
public class WLANStuff 
{

	public WifiManager wifiManager = null;
	public WifiInfo wifiInfo = null;

	public WLANStuff()
	{
		if(isConnected())
		{
			wifiManager = (WifiManager) 
					MainActivity.
					getContext().
					getSystemService(Context.WIFI_SERVICE);
			wifiInfo = wifiManager.getConnectionInfo();
			Logger.Debugger("wifi connected\nSSID::"+wifiInfo.getSSID());
			MainActivity.setMsg("" +
					"SSID::"+wifiInfo.getSSID()+
					"\nBSSID::"+wifiInfo.getBSSID() +
					"\nNetworkID::"+wifiInfo.getNetworkId()+
					"\nRSSI::"+wifiInfo.getRssi()+
					"\nIP::"+wifiInfo.getIpAddress());
		}
	}

	public boolean isConnected()
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
