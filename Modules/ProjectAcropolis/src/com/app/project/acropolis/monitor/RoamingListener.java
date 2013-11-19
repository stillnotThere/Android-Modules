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
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class RoamingListener extends BroadcastReceiver
{
	public static final String[] CAN_OPERATORS = {"Rogers","TELUS"};
	public static boolean roaming = false;

	public ConnectivityManager connectivityManager = null;
	public static TelephonyManager telephonyManager = null;
	public NetworkInfo.State state = null;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		checkRoaming();
	}

	public static boolean checkRoaming()
	{

		if(telephonyManager.isNetworkRoaming())
			changeRoaming();
		else
			crossCheck();	
		
		return roaming;
	}

	public static boolean changeRoaming()
	{
		try {
			if(telephonyManager.getNetworkOperatorName().length()>0)
			{
				for(int i=0;i<CAN_OPERATORS.length;i++)
				{
					if(!telephonyManager.
							getNetworkOperatorName().equalsIgnoreCase(CAN_OPERATORS[i]))
					{
						roaming = true;
						break;
					}
					else
					{
						roaming = false;
					}
				}
				ContentValues cv = new ContentValues();
				cv.put(DBOpenHelper.ROAMING, String.valueOf(roaming));
				DBAdapter.update(cv);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return roaming;
	}

	public static void crossCheck()
	{
		try {
			if(telephonyManager.getNetworkOperatorName().length()>0)
			{
				for(int i=0;i<CAN_OPERATORS.length;i++)
				{
					if(!telephonyManager.
							getNetworkOperatorName().equalsIgnoreCase(CAN_OPERATORS[i]))
					{
						roaming = true;
						break;
					}
					else
					{
						roaming = false;
					}
				}
			}
		} catch(NullPointerException e1) {

		}
	}
}
