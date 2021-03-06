/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * WLANStuff
 * com.app.project.acropolis.comm
 * WLANStuff.java
 * Created - 2013-11-07 12:15:27 PM	
 * Modified - 2013-11-07 12:15:27 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.comm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.Logger;
import com.app.project.acropolis.ProjectAcropolisActivity;

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

	public static String getSSID()
	{
		Logger.Debug("WLAN connected on CPH Inc.");
		SSID = wifiInfo.getSSID();
		return SSID;
	}

	public static boolean onCPHWLAN()
	{
		boolean onCPH = false;
		wifiManager = (WifiManager) 
				ProjectAcropolisActivity.
				getContext().
				getSystemService(Context.WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();
		if(isConnected())
		{
			String ssid = getSSID();
			if(ssid.equalsIgnoreCase(CPH_SSID))
			{
				onCPH = true;
				ProjectAcropolisActivity.postToast("Connected to CPH WLAN...cannot communicate to server");
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
		try {
			ConnectivityManager connMngr = (ConnectivityManager)
					ProjectAcropolisActivity.getContext().
					getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMngr.getNetworkInfo(
					ConnectivityManager.TYPE_WIFI);

			if(networkInfo.isConnected())
				connected = true;
			else
				connected = false;
		} catch(Exception e){
			e.printStackTrace();
			DataTumblr.setErrorMsg(e.getMessage());
			new Handler().post(
					new GlobalConstants.TriggerEvent(
							GlobalConstants.EXCEPTION_HANDLER));
		}
		return connected;
	}

}