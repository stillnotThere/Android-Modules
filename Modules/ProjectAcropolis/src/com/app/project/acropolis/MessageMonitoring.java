/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * MessageMonitoring
 * com.app.project.acropolis
 * MessageMonitoring.java
 * Created - 2013-10-11 3:20:16 PM	
 * Modified - 2013-10-11 3:20:16 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class MessageMonitoring extends ContentObserver 
{
	final String msgOutUri = "content://sms";
	Uri outUri = Uri.parse(msgOutUri);

	public MessageMonitoring() {
		super(null);
	}

	//TODO
	public void onChange(boolean selfChange)
	{
		int outgoingCounter = 0;
		int incomingCounter = 0;
		Context context = MainActivity.getContext();
		DBAdapter dbAdapter = new DBAdapter(context);

		super.onChange(selfChange);
		Cursor smsCursor = context.getContentResolver().query(outUri,
				new String[]{"type"}, null, null,null);


		if(smsCursor.moveToFirst())
		{
			if(smsCursor.getString(smsCursor.getColumnIndex("type")).equalsIgnoreCase("1"))
			{
				//received
				incomingCounter = incomingCounter + 1;
				Logger.Debug("msg received count:"+incomingCounter);

				//				if(dbAdapter.isEmpty())
				//				{
				long previous = Long.parseLong(dbAdapter.getValue(DBOpenHelper.RECEIVED));
				long newV = incomingCounter;
				long total = previous + newV;

				ContentValues cv = new ContentValues();
				cv.put(DBOpenHelper.RECEIVED, String.valueOf(total));
				dbAdapter.update(cv);
				//				}

			}
			else if(smsCursor.getString(smsCursor.getColumnIndex("type")).equalsIgnoreCase("2"))
			{
				//sent
				outgoingCounter = outgoingCounter + 1;
				Logger.Debug("msg sent count::"+outgoingCounter);

				long previous = Long.parseLong(dbAdapter.getValue(DBOpenHelper.SENT));
				long newV = outgoingCounter;
				long total = previous + newV;

				ContentValues cv = new ContentValues();
				cv.put(DBOpenHelper.SENT, String.valueOf(total));
				dbAdapter.update(cv);
			}
		}
		
		smsCursor.close();

	}

}