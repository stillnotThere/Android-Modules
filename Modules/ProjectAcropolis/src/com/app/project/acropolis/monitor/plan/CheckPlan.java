/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * CheckPlan
 * com.app.project.acropolis.monitor.bill
 * CheckPlan.java
 * Created - 2013-11-13 3:16:43 PM	
 * Modified - 2013-11-13 3:16:43 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.monitor.plan;

import android.content.Context;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.comm.DataTumblr;
import com.app.project.acropolis.comm.SocketClientConnector;
import com.app.project.acropolis.database.PersistedData;

/**
 * @author CPH-iMac
 *
 */
public class CheckPlan 
{
	/**
	 * Retrieves mobile plan
	 * @param plan db key
	 * @return Set plan data for monitored argument
	 */
	private static long fetchPlan(Context __context,String _key)
	{
		long db_val = 0;
		db_val = Long.parseLong(new PersistedData().fetchData(_key));
		return db_val;
	}
	
	/**
	 * Check monitored data against mobile plan, if crossed threshold or exceeded plan
	 * user will be notified as well CPH server 
	 * @param _key DB key
	 * @param _recorded	DB key value
	 */
	public static void compare_set(Context __context,String _key,String _recorded)
	{
		long stored = fetchPlan(__context,_key);
		double recorded = Long.parseLong(_recorded);
		long threshold = (long) 
				(recorded * (GlobalConstants.PlanThreshold * 0.01)); 
		
		if(stored <= recorded)
		{
			if(stored == recorded) 
			{
				alertPlanEnding();
			}
			else if(recorded >= threshold)
			{
				alertPlanEnding();
			}
		}
	}
	
	private static void alertPlanEnding()
	{
		DataTumblr.setSendClientData("");
		new Thread(new SocketClientConnector.SokcetClientUrgentConnector()).start();
	}

	
	
}
