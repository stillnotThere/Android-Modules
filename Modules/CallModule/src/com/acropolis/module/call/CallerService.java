/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * CallerService
 * com.acropolis.module.call
 * CallerService.java
 * Created - 2013-10-10 6:21:15 PM	
 * Modified - 2013-10-10 6:21:15 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.call;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * @author CPH-iMac
 *
 */
public class CallerService extends Service
{

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	public void onCreate()
	{
		setupCallListener();
//		setupOutgoingListener();
	}

	public int onStartCommand(Intent intent,int flags,int startId)
	{
		return Service.START_STICKY;
	}

	public void setupCallListener()
	{
		TelephonyManager telephonyManager = (TelephonyManager)
				getApplicationContext().
				getSystemService(Context.TELEPHONY_SERVICE);

		telephonyManager.listen(new CallStateListener(),
				PhoneStateListener.LISTEN_CALL_STATE);
	}

	public void setupOutgoingListener()
	{
		IntentFilter intentFilter =
				new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		registerReceiver(new OutgoingCallReceiver(),intentFilter);
	}

	protected int outgoingcallCounter = 0;

	public class OutgoingCallReceiver extends BroadcastReceiver
	{
		public void onReceive(Context context,Intent intent)
		{
			getContentResolver().registerContentObserver(
					CallLog.Calls.CONTENT_URI, true, new ContentObserver(null)
					{
						public void onChange(boolean selfChange)
						{
							String[] projection = 
								{
									Calls.NUMBER,
									Calls.DURATION,
									Calls.TYPE,
									Calls.DATE
								};
							String selection = null;
							String[] selectionArgs = null;
							String sortOrder = CallLog.Calls.DATE+" DESC";

							Cursor outCursor = 
									getContentResolver().query(
											android.provider.CallLog.Calls.CONTENT_URI, 
											projection, 
											selection, 
											selectionArgs,
											sortOrder);

							if(outCursor.moveToFirst())
							{
								int type = outCursor.getInt(outCursor.getColumnIndex(Calls.TYPE));

								if(type == Calls.OUTGOING_TYPE)
								{
									outgoingcallCounter++;
									if(outgoingcallCounter<1)
									{
										Logger.Debug("Outgoing Call " +
												"\nNumber:::"+ 
												outCursor.getString(outCursor.getColumnIndex(Calls.NUMBER))
												+
												"\nDuration::"+outCursor.getString(outCursor.getColumnIndex(Calls.DURATION)));
//										outgoingcallCounter = 0;
									}
									else if(outgoingcallCounter>=1)
									{
										outgoingcallCounter=0;
									}
									
								}
							}
							outCursor.close();
						}
					});
		}
	}

}