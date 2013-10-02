/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * MessageAT
 * com.acropolis.module.messagemodule
 * MessageAT.java
 * Created - 2013-10-02 2:56:36 PM	
 * Modified - 2013-10-02 2:56:36 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.message;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

/**
 * @author CPH-iMac
 *
 */
public class OutgoingMessagingService extends Service
{
	Context appContext = MainActivity.getAppContext();
	Cursor smsSentCursor = null;
	final Uri outgoingMessageUri = Uri.parse("content://sms/out");
	final static String[] projections = {"date","address","body"};
	int sentcounter = 0;
	final static String selection = null;	//row (WHERE clause)
	final static String[] selectionArgs = null;	//conditions
	final static String sortOrder = null;	//sorting (default last msg first)
	
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		ContentResolver outgoingMessageCR = 
				appContext.getContentResolver();
		outgoingMessageCR.registerContentObserver(
				outgoingMessageUri, true,
				new OutgoingMessageContentObserver(null));
		return null;
	}
	
	public int onStartCommand(Intent intent,int flags,int startId)
	{
		Logger.Debug("restarting service");
		return Service.START_STICKY;
	}	

	public class OutgoingMessageContentObserver extends ContentObserver
	{
		public OutgoingMessageContentObserver(Handler handler) {super(handler);}

		public void onChange(boolean selfChange)
		{
			super.onChange(selfChange);

			smsSentCursor = appContext.getContentResolver().query(
					outgoingMessageUri, projections, selection, 
					selectionArgs, sortOrder);
			smsSentCursor.moveToFirst();	//last message received
			sentcounter++;
			Logger.Debug("sentcounter::::"+sentcounter);
		}

	}
}
