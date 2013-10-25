/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * OutgoingReceiver
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
public class OutgoingReceiver extends BroadcastReceiver
{
	Context context = null;
	public int outgoingcallCounter = 0;
	
	public void onReceive(Context _context,Intent _intent)
	{
		context = _context;
		context.getContentResolver().registerContentObserver(
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
								context.getContentResolver().query(
										android.provider.CallLog.Calls.CONTENT_URI, 
										projection, 
										selection, 
										selectionArgs,
										sortOrder);

						if(outCursor.moveToFirst())
						{
							Logger.Debug(outCursor.getString(outCursor.getColumnIndex("TYPE")));
							int type = outCursor.getInt(outCursor.
									getColumnIndex(Calls.TYPE));

							if(type == Calls.OUTGOING_TYPE)
							{
								outgoingcallCounter++;
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
						outCursor.close();
					}
				});
	}
}