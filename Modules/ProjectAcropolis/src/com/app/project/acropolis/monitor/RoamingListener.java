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

import com.app.project.acropolis.Logger;
import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class RoamingListener extends BroadcastReceiver
{
	public final String[] CAN_OPERATORS = {"Rogers","TELUS"};
	public boolean roaming = false;

	public ConnectivityManager connectivityManager = null;
	public NetworkInfo networkInfo = null;

	public Context _context = null;
	public Intent _intent = null;
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		_context = context;
		_intent = intent;
		checkRoaming();
		checkNetworkInfo();
	}

	private void checkNetworkInfo()
	{
		ConnectivityManager _cm = (ConnectivityManager) 
				_context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if((networkInfo = _cm.getActiveNetworkInfo())!=null)
			{
				Logger.Debug(networkInfo.getTypeName());

			}
	}

	/**
	 * Checks roaming operator if applicable
	 * @return true if Roaming, false if Local
	 */
	public boolean checkRoaming()
	{
		TelephonyManager _tm = (TelephonyManager)
				_context.getSystemService(Context.TELEPHONY_SERVICE);
		boolean ret = false;
		if(String.valueOf(_tm.isNetworkRoaming())!=null)
			ret = changeRoaming();
		return ret;
	}

	public boolean changeRoaming()
	{
		TelephonyManager _tm = (TelephonyManager)
				ProjectAcropolisActivity.
				getContext().
				getSystemService(Context.TELEPHONY_SERVICE);
		if(_tm.getNetworkOperatorName().length()>0 ||
				_tm.getNetworkOperatorName()!=null)
		{
			for(int i=0;i<CAN_OPERATORS.length;i++)
			{
				if(!_tm.
						getNetworkOperatorName().
						equalsIgnoreCase(CAN_OPERATORS[i]))
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
		ContentValues cv = new ContentValues();
		cv.put(DBOpenHelper.ROAMING, String.valueOf(roaming));
		DBAdapter.update(cv);
		return roaming;
	}

}
