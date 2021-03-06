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
import android.os.IBinder;

/**
 * @author CPH-iMac
 *
 */
public class OutgoingMessagingService extends Service
{

	/**
	 * Android src
	 * 
	 * android.provider.Telephony
	 * reference URL == http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.3_r2.1/android/provider/Telephony.java#Telephony.TextBasedSmsColumns.0MESSAGE_TYPE_ALL
	 *
	 * Content URI content://sms
	 * Content Columns and Data type 
	 * _id 	int
	 * _thread_id	int
	 * address	String
	 * person	object
	 * date	int
	 * date)sent	int
	 * protocol	string/obj
	 * read	int
	 * status	int
	 * type	int
	 * reply_path_present	boolean
	 * subject	string
	 * body	string
	 * service_center	int
	 * locked	int(bool)
	 * error_code	int
	 * seen/meta-data	int(bool)
	 * 
	 * MESSAGE_TYPE_ALL		0
	 * MESSAGE_TYPE_INBOX	1
	 * MESSAGE_TYPE_SENT	2
	 * MESSAGE_TYPE_DRAFT	3
	 * MESSAGE_TYPE_OUTBOX	4
	 * MESSAGE_TYPE_FAILED	5
	 * MESSAGE_TYPE_QUEUED	6
	 *  
	 * */
		
	final String msgOutUri = "content://sms";
	Uri outUri = Uri.parse(msgOutUri);
	Context context = null;
	Intent intent = null;
	int outgoingCounter = 0;
	int incomingCounter = 0;
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		Logger.Debug("onBind");
		return null;
	}

	public int onStartCommand(Intent intent,int flags,int startId)
	{
		Logger.Debug("onStartCommand");
		context = MainActivity.getAppContext();
		this.intent = intent;

		ContentResolver contentResolver = context.getContentResolver();
		//check device contentprovider packages
		//		ContentProviderClient contentProviderClient = contentResolver.acquireContentProviderClient(outUri);

		contentResolver.registerContentObserver(outUri, true, new ContentObserver(null)
		{
			//TODO
			public void onChange(boolean selfChange)
			{
				super.onChange(selfChange);
				Cursor smsCursor = context.getContentResolver().query(outUri,
						new String[]{"type"}, null, null,null);


				if(smsCursor.moveToFirst())
				{
					if(smsCursor.getString(smsCursor.getColumnIndex("type")).equalsIgnoreCase("1"))
					{
						//received
						++incomingCounter;
						Logger.Debug("msg received count:"+incomingCounter);
					}
					else if(smsCursor.getString(smsCursor.getColumnIndex("type")).equalsIgnoreCase("2"))
					{
						//sent
						++outgoingCounter;
						Logger.Debug("msg sent count::"+outgoingCounter);
					}
				}
				smsCursor.close();
			}

		});

		return Service.START_STICKY;
	}
}
