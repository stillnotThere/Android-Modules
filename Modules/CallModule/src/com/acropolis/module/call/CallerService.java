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
		getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI,true,outgoingCallObserver);
//		setupOutgoingListener();
	}

//	public int onStartCommand(Intent intent,int flags,int startId)
//	{
//		return Service.START_STICKY;
//	}

	public void setupCallListener()
	{
		TelephonyManager telephonyManager = (TelephonyManager)
				getApplicationContext().
				getSystemService(Context.TELEPHONY_SERVICE);

		telephonyManager.listen(new IncomingListener(),
				PhoneStateListener.LISTEN_CALL_STATE);
	}

	//Required start once from Activity (since Android Os 4.1.2)
	public void setupOutgoingListener()
	{
		IntentFilter intentFilter =
				new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		registerReceiver(new OutgoingListener(),intentFilter,
				"android.permission.READ_PHONE_STATE",null);
	}

	public void onDestroy()
	{
		getContentResolver().unregisterContentObserver(outgoingCallObserver);
		super.onDestroy();
	}

	final ContentObserver outgoingCallObserver = new ContentObserver(null) 
	{
		final long threshold = 1000; //10,000 milliseconds
		long lastCall = 0;
		long nextUpdate = 0;

		public void onChange(boolean selfChange)
		{
			Logger.Debug("onChanges");
			String[] projection = 
				{
//					Calls.DURATION,
					Calls.TYPE
				};
			String selection = null;
			String[] selectionArgs = null;
			String sortOrder = null;//CallLog.Calls.DATE+" DESC";

			Cursor outCursor = 
					getContentResolver().query(
							android.provider.CallLog.Calls.CONTENT_URI, 
							projection, 
							selection, 
							selectionArgs,
							sortOrder);
			if(outCursor!=null)
			{
				if(outCursor.moveToFirst())
				{
					int type = outCursor.getInt(outCursor.
							getColumnIndex(Calls.TYPE));
					if(type == Calls.OUTGOING_TYPE)
					{
						Logger.Debug("Outgoing Call " +
								"\nNumber:::"+ 
								outCursor.
								getString(outCursor.
										getColumnIndex(Calls.NUMBER))
										+
										"\nDuration::"+
										outCursor.
										getString(outCursor.
												getColumnIndex(Calls.DURATION)));
					}
				}
			}
			outCursor.close();
		}
	};

}
