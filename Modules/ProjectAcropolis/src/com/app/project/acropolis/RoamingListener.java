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
package com.app.project.acropolis;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

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
	
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		DBAdapter dbAdapter = new DBAdapter(context);
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		if(telephonyManager.getNetworkOperatorName().length()>0)
		{
			for(int i=0;i<CAN_OPERATORS.length;i++)
			{
				if(telephonyManager.
						getNetworkOperatorName().equalsIgnoreCase(CAN_OPERATORS[i]))
					roaming = false;
				else
					roaming = true;
			}
			ContentValues cv = new ContentValues();
			cv.put(DBOpenHelper.ROAMING, String.valueOf(roaming));
			dbAdapter.update(cv);
		}
		
		 
	}

	
	
}
