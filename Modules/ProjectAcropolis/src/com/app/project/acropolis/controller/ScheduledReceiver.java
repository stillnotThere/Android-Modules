/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * ScheduledReceiver
 * com.app.project.acropolis
 * ScheduledReceiver.java
 * Created - 2013-10-11 6:40:04 PM	
 * Modified - 2013-10-11 6:40:04 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.controller;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author CPH-iMac
 *
 */
public class ScheduledReceiver extends BroadcastReceiver  
{
	// Restart service every 30 seconds
	private static final long REPEAT_TIME = 1000 * 10;
	
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {

		AlarmManager service = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, ServiceReceiver.class);
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
				PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar cal = Calendar.getInstance();
		// Start 30 seconds after boot completed
		cal.add(Calendar.SECOND, 30);
		//
		// Fetch every 30 seconds
		// InexactRepeating allows Android to optimize the energy consumption
		service.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				cal.getTimeInMillis(), REPEAT_TIME, pending);
//		service.
//		setInexactRepeating(
//				AlarmManager.ELAPSED_REALTIME_WAKEUP,
//				SystemClock.currentThreadTimeMillis(),
//				REPEAT_TIME,
//				pending);
	}

	
	
}
