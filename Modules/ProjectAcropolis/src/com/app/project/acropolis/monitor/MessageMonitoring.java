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
package com.app.project.acropolis.monitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.Logger;
import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class MessageMonitoring extends ContentObserver 
{
	private Context _context = null;
	final String msgOutUri = "content://sms";
	Uri outUri = Uri.parse(msgOutUri);

	public MessageMonitoring(Context __context) 
	{
		super(null);
		_context = __context;
	}

	//TODO
	public void onChange(boolean selfChange)
	{
		super.onChange(selfChange);
		int outgoingCounter = 0;
		int incomingCounter = 0;
		int outgoingRCounter = 0;
		int incomingRCounter = 0;

		Context context = ProjectAcropolisActivity.getContext();

		Cursor smsCursor = context.getContentResolver().query(outUri,
				new String[]{"type"}, null, null,null);


		if(smsCursor.moveToFirst())
		{
			if(smsCursor.getString(smsCursor.getColumnIndex("type")).equalsIgnoreCase("1"))
			{
				if(GlobalConstants.checkRoaming(_context))
				{
					//received
					incomingRCounter = incomingRCounter + 1;
					Logger.Debug("msg received count:"+incomingRCounter);

					long previous = Long.parseLong(DBAdapter.getValue(_context,DBOpenHelper.ROAM_RECEIVED));
					long newV = incomingRCounter;
					long total = previous + newV;

					ContentValues cv = new ContentValues();
					cv.put(DBOpenHelper.ROAM_RECEIVED, String.valueOf(total));
					DBAdapter.update(_context,cv);
				}
				else
				{
					//received
					incomingCounter = incomingCounter + 1;
					Logger.Debug("msg received count:"+incomingCounter);

					long previous = Long.parseLong(DBAdapter.getValue(_context,DBOpenHelper.LOCAL_RECEIVED));
					long newV = incomingCounter;
					long total = previous + newV;

					ContentValues cv = new ContentValues();
					cv.put(DBOpenHelper.LOCAL_RECEIVED, String.valueOf(total));
					DBAdapter.update(_context,cv);
				}
			}
			else if(smsCursor.getString(smsCursor.getColumnIndex("type")).equalsIgnoreCase("2"))
			{
				if(GlobalConstants.checkRoaming(_context))
				{
					//sent
					outgoingRCounter = outgoingRCounter + 1;
					Logger.Debug("msg sent count::"+outgoingRCounter);

					long previous = Long.parseLong(DBAdapter.getValue(_context,DBOpenHelper.ROAM_SENT));
					long newV = outgoingRCounter;
					long total = previous + newV;

					ContentValues cv = new ContentValues();
					cv.put(DBOpenHelper.ROAM_SENT, String.valueOf(total));
					DBAdapter.update(_context,cv);
				}
				else
				{
					//sent
					outgoingCounter = outgoingCounter + 1;
					Logger.Debug("msg sent count::"+outgoingCounter);

					long previous = Long.parseLong(DBAdapter.getValue(_context,DBOpenHelper.LOCAL_SENT));
					long newV = outgoingCounter;
					long total = previous + newV;

					ContentValues cv = new ContentValues();
					cv.put(DBOpenHelper.LOCAL_SENT, String.valueOf(total));
					DBAdapter.update(_context,cv);
				}
			}
		}
		smsCursor.close();
	}

}