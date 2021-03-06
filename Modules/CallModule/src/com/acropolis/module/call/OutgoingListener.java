/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * OutgoingListener
 * com.acropolis.module.call
 * OutgoingReceiver.java
 * Created - 2013-10-25 3:55:22 PM	
 * Modified - 2013-10-25 3:55:22 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.CallLog.Calls;

/**
 * @author CPH-iMac
 *
 */
public class OutgoingListener extends BroadcastReceiver
{
	Context context = null;
	final ContentObserver outgoingCallObserver = new ContentObserver(null) 
	{
		final long threshold = 1000; //10,000 milliseconds
		long lastCall = 0;
		long nextUpdate = 0;
		
		public void onChange(boolean selfChange)
		{
			String[] projection = 
				{
					Calls.NUMBER,
					Calls.DURATION,
					CallLog.Calls.TYPE ,
					CallLog.Calls.DATE
				};
			String selection = null;
			String[] selectionArgs = null;
			String sortOrder = CallLog.Calls.DATE+" DESC";

			Cursor outCursor = 
					context.getContentResolver().query(
							android.provider.CallLog.Calls.CONTENT_URI, 
							projection, 
							selection, 
							selectionArgs,
							sortOrder);

			
			if(outCursor.moveToFirst())
			{
				int type = outCursor.getInt(outCursor.
						getColumnIndex(Calls.TYPE));

				if(type == Calls.OUTGOING_TYPE)
				{
//					lastCall = System.currentTimeMillis();

//					if(nextUpdate < threshold)
//					{
						Logger.Debug("Outgoing Call " +
								"\nNumber:::"+
								outCursor.
								getString(outCursor.
										getColumnIndex(Calls.NUMBER)) +
								"\nDuration::"+
								outCursor.
								getString(outCursor.
										getColumnIndex(Calls.DURATION)));
						
								
										
//						nextUpdate = lastCall + threshold;
//						
//					}
				}
			}
			outCursor.close();
		}
	};
	
	public void onReceive(Context _context,Intent _intent)
	{
		context = _context;
		context.getContentResolver().
		registerContentObserver(CallLog.Calls.CONTENT_URI, true,outgoingCallObserver);
	}

	
}