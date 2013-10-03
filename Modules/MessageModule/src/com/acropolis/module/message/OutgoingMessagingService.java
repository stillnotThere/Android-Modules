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
import android.content.ContentProviderClient;
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
						new String[]{"*"}, null, null,null);
//						new String[]{"date","address","body,type"}, null, null,null);

				//				outgoingCounter++;	

				String[] body = new String[smsCursor.getCount()];
				String[] type = new String[smsCursor.getCount()];
				String[] address = new String[smsCursor.getCount()];
				String[] date = new String[smsCursor.getCount()];

				
				if(smsCursor.moveToFirst())
				{
					for(int j=0;j<smsCursor.getColumnCount();j++)
					{
						Logger.Debug(smsCursor.getColumnName(j));
					}
					for(int i=0;i<smsCursor.getColumnCount();i++)
					{
						body[i] = smsCursor.getString(smsCursor.getColumnIndex("body"));
						address[i] = smsCursor.getString(smsCursor.getColumnIndex("address"));
						type[i] = smsCursor.getString(smsCursor.getColumnIndex("type")); 
						date[i] = smsCursor.getString(smsCursor.getColumnIndex("date"));

						if(type[i].equalsIgnoreCase("1"))
						{
							//received
							++incomingCounter;
							Logger.Debug("msg received:count:"+incomingCounter+
									"\nbody::"+ body[i]+
									"\naddress::"+address[i]);
						}
						else
						{
							//sent
							++outgoingCounter;
							Logger.Debug("msg sent count:"+outgoingCounter +
									"\nbody::"+ body[i]+
									"\naddress::"+address[i]);
						}

					}
				}

				Logger.Debug("outgoingcounter::"+outgoingCounter);
			}

		});

		return Service.START_STICKY;
	}
}
