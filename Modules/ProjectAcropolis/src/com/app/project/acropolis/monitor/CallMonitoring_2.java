/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * CallMonitoring_2
 * com.app.project.acropolis.monitor
 * CallMonitoring_2.java
 * Created - 2013-11-12 4:44:33 PM	
 * Modified - 2013-11-12 4:44:33 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.monitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog.Calls;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.Logger;
import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class CallMonitoring_2 extends ContentObserver 
{
	private Handler logHandler = new Handler();
	private int counter = 0;

	private final String[] projection = {Calls.TYPE,Calls.DURATION,Calls.DATE};
	private final String selection = null;
	private final String[] selectionArgs = null;
	private final String sortOrder = null;
	private Context _context = null;

	public CallMonitoring_2(Context __context) 
	{
		super(null);
		_context = __context;
	}

	public void onChange(boolean selfChange)
	{
		Cursor cursor = ProjectAcropolisActivity.getContext().
				getContentResolver().
				query(
						Calls.CONTENT_URI,
						projection,
						selection,
						selectionArgs, 
						sortOrder);
		cursor.moveToFirst();
		if(cursor!=null)
		{
			logHandler.postDelayed(checkLog, 30*1000);
		}
		cursor.close();
	}

	private Runnable checkLog = new Runnable() 
	{
		@Override
		public void run()
		{
			if(counter<1)
			{
				Logger.Debug("Getting Log activity...");
				String[] projection = new String[]{
						Calls.NUMBER,
						Calls.DURATION,
						Calls.TYPE,
						Calls.DATE};
				Cursor cur = ProjectAcropolisActivity.getContext().
						getContentResolver().
						query(
								Calls.CONTENT_URI,
								projection, 
								null, 
								null, 
								Calls.DATE +" desc");
				cur.moveToFirst();
				String lastCallnumber = cur.getString(0);
				int lastCallduration = Integer.parseInt(cur.getString(1));
				int lastCallType = Integer.parseInt(cur.getString(2));
				
				Logger.Debug("last callNumber::"+lastCallnumber + 
						"\nduration::"+lastCallduration +
						"\ntype::"+lastCallType);
				Logger.Debug("counter::\t\t"+counter);
				
				if(lastCallType == Calls.INCOMING_TYPE)
				{
					Logger.Debug("incoming");
					if(GlobalConstants.checkRoaming(_context))
					{
						int db_temp = Integer.parseInt(DBAdapter.getValue(_context,DBOpenHelper.ROAM_INCOMING));
						db_temp = (int)convert(lastCallduration) + db_temp;
						ContentValues cv = new ContentValues();
						cv.put(DBOpenHelper.ROAM_INCOMING, String.valueOf(db_temp));
						DBAdapter.update(_context,cv);
					}
					else
					{
						int db_temp = Integer.parseInt(DBAdapter.getValue(_context,DBOpenHelper.LOCAL_INCOMING));
						db_temp = (int)convert(lastCallduration) + db_temp;
						ContentValues cv = new ContentValues();
						cv.put(DBOpenHelper.LOCAL_INCOMING, String.valueOf(db_temp));
						DBAdapter.update(_context,cv);
					}
				}
				if(lastCallType == Calls.OUTGOING_TYPE)
				{
					Logger.Debug("outgoing");
					if(GlobalConstants.checkRoaming(_context))
					{
						int db_temp = Integer.parseInt(DBAdapter.getValue(_context,DBOpenHelper.ROAM_OUTGOING));
						db_temp = (int)convert(lastCallduration) + db_temp;
						ContentValues cv = new ContentValues();
						cv.put(DBOpenHelper.ROAM_OUTGOING, String.valueOf(db_temp));
						DBAdapter.update(_context,cv);
					}
					else
					{
						int db_temp = Integer.parseInt(DBAdapter.getValue(_context,DBOpenHelper.LOCAL_OUTGOING));
						db_temp = (int)convert(lastCallduration) + db_temp;
						ContentValues cv = new ContentValues();
						cv.put(DBOpenHelper.LOCAL_OUTGOING, String.valueOf(db_temp));
						DBAdapter.update(_context,cv);
					}
				}
			}
			else if(counter<=4)
			{
				counter=0;
			}
			counter++;
		}
	};

	public long convert(long sec)
	{
		long min=0;
		long secs=sec;
		if(secs%60==0)
		{
			min = secs/60;
		}
		else
		{
			min = (long) Math.abs(secs/60) + 1;
		}
		return min;
	}

	
}