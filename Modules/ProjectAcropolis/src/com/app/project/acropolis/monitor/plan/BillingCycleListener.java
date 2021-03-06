/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * BillingCycleListener
 * com.app.project.acropolis.monitor.plan
 * BillingCycleListener.java
 * Created - 2013-11-15 12:01:20 PM	
 * Modified - 2013-11-15 12:01:20 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.monitor.plan;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.app.project.acropolis.GlobalConstants;

/**
 * BroadcastReceiver listening date change against bill date 
 */
public class BillingCycleListener extends BroadcastReceiver 
{
	
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		long adjustTZ = differenceOnTimeZone();

		Handler resetHandler = new Handler();
		if(adjustTZ == 0)
		{
			resetHandler.post(resetThen);
		}
		else 
		{
			resetHandler.postDelayed(resetThen, adjustTZ);
		}
	}

	/**
	 * Reset all values
	 */
	public Runnable resetThen = new Runnable()
	{
		public void run()
		{
//			DBAdapter.resetValues();
		}
	};
	
	/**
	 * Checks TimeZone difference if there 
	 * @return difference of time zone in GMT hrs. 0 if locale is same as billing
	 */
	private long differenceOnTimeZone()
	{
		long difference = 0;
		TimeZone currentTimeZone = TimeZone.getDefault();
		Calendar currentTimeZoneCalendar = new GregorianCalendar(currentTimeZone);
		Date currentTimeZoneDate = currentTimeZoneCalendar.getTime();
		long currentTimeZonemilli = currentTimeZoneDate.getTime();
		
		TimeZone billingTimeZone = TimeZone.getTimeZone(GlobalConstants.CPH_TIMEZONE);
		Calendar billingTimeZoneCalendar = new GregorianCalendar(billingTimeZone);
		Date billingTimeZoneDate = billingTimeZoneCalendar.getTime();
		long billingTimeZonemilli = billingTimeZoneDate.getTime();
		
		if(billingTimeZone.hasSameRules(currentTimeZone))
		{
			difference=0;
		}
		else
		{
			if(currentTimeZonemilli > billingTimeZonemilli)
			{
				difference = currentTimeZonemilli - billingTimeZonemilli;
			}
			else if(currentTimeZonemilli < billingTimeZonemilli)
			{
				difference = billingTimeZonemilli - currentTimeZonemilli;
			}
		}
		return difference;
	}
	
}