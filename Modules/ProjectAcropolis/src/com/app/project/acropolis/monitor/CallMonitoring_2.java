/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * CallMonitoring_2
 * com.app.project.acropolis.monitor
 * CallMonitoring_2.java
 * Created - 2013-11-12 4:44:33 PM	
 * Modified - 2013-11-12 4:44:33 PM
 * NOTES - 
 */
package com.app.project.acropolis.monitor;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog.Calls;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.Logger;
import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.database.PersistedData;

/**
 * @author CPH-iMac
 *
 */
public class CallMonitoring_2 extends ContentObserver 
{
	private Handler logHandler = new Handler();

	private final String[] projection = {Calls.TYPE,Calls.DURATION,Calls.DATE};
	private final String selection = null;
	private final String[] selectionArgs = null;
	private final String sortOrder = null;

	public CallMonitoring_2(Context __context) 
	{
		super(null);
	}

	public void onChange(boolean selfChange)
	{
		Logger.Debug(this.getClass().getSimpleName());
		GlobalConstants.counter++;
		Cursor cursor = ProjectAcropolisActivity.getContext().
				getContentResolver().
				query(
						Calls.CONTENT_URI,
						projection,
						selection,
						selectionArgs, 
						sortOrder);
		cursor.moveToFirst();
		Logger.Debug(cursor.getClass().toString());
		logHandler.postDelayed(checkLog, 10*1000);
		cursor.close();
	}

	public boolean deliverSelfNotifications()
	{
		return true;
	}

	//	private long oldCall = 0;
	//
	//	private boolean checkRepeatedCalls(long time)
	//	{
	//		boolean repeatCall = false;
	//
	//		oldCall = time;
	//		
	//		return repeatCall;
	//	}

	String lastCallnumber;
	int lastCallduration;
	int lastCallType;
	int lastCallTime;
	
	private Runnable checkLog = new Runnable() 
	{
		@Override
		public void run()
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

			lastCallnumber = cur.getString(0);
			lastCallduration = Integer.parseInt(cur.getString(1));
			lastCallType = Integer.parseInt(cur.getString(2));
			lastCallTime = Integer.parseInt(cur.getString(3));

			if(GlobalConstants.lastCallTime == 0)
			{
				GlobalConstants.lastCallTime = lastCallTime;
				proceed();
			}
			else	//may be a repeat or 
			{
				if(GlobalConstants.lastCallTime == lastCallTime)
				{
					//skip
					Logger.Debug("multiple call instances");
				}
				else 
				{
					GlobalConstants.lastCallTime = lastCallTime;
					proceed();
				}
			}

//			if(storedCallTime!=0)
//			{
//				if(storedCallTime != lastCallTime)
//				{
//					proceed();
//					storeLastCall(lastCallTime);
//				}
//				else	//redundant data
//				{
//					Logger.Debug("Repeated call");
//				}
//			}
//			else	//no previous call times
//			{
//				storeLastCall(lastCallTime);
//
//				if(lastCallType == Calls.INCOMING_TYPE)
//				{
//					Logger.Debug("incoming");
//					if(new GlobalConstants().isRoaming())
//					{
//						int db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_INCOMING));
//						db_temp = (int)convert(lastCallduration) + db_temp;
//						new PersistedData().putData(GlobalConstants.PersistenceConstants.ROAM_INCOMING, String.valueOf(db_temp));
//					}
//					else
//					{
//						int db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_INCOMING));
//						db_temp = (int)convert(lastCallduration) + db_temp;
//						new PersistedData().putData(GlobalConstants.PersistenceConstants.LOCAL_INCOMING, String.valueOf(db_temp));
//					}
//				}
//				if(lastCallType == Calls.OUTGOING_TYPE)
//				{
//					Logger.Debug("outgoing");
//					if(new GlobalConstants().isRoaming())
//					{
//						int db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_OUTGOING));
//						db_temp = (int)convert(lastCallduration) + db_temp;
//						new PersistedData().putData(GlobalConstants.PersistenceConstants.ROAM_OUTGOING, String.valueOf(db_temp));
//					}
//					else
//					{
//						int db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_OUTGOING));
//						db_temp = (int)convert(lastCallduration) + db_temp;
//						new PersistedData().putData(GlobalConstants.PersistenceConstants.LOCAL_OUTGOING, String.valueOf(db_temp));
//					}
//				}
//
//			}

			Logger.Debug("last callNumber::"+lastCallnumber + 
					"\nduration::"+lastCallduration +
					"\ntype::"+lastCallType +
					"\ntime::"+lastCallTime);
		}
	};

	private void proceed()
	{
		if(lastCallType == Calls.INCOMING_TYPE)
		{
			Logger.Debug("incoming");
			if(new GlobalConstants().isRoaming())
			{
				int db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_INCOMING));
				db_temp = (int)convert(lastCallduration) + db_temp;
				new PersistedData().putData(GlobalConstants.PersistenceConstants.ROAM_INCOMING, String.valueOf(db_temp));
			}
			else
			{
				int db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_INCOMING));
				db_temp = (int)convert(lastCallduration) + db_temp;
				new PersistedData().putData(GlobalConstants.PersistenceConstants.LOCAL_INCOMING, String.valueOf(db_temp));
			}
		}
		if(lastCallType == Calls.OUTGOING_TYPE)
		{
			Logger.Debug("outgoing");
			if(new GlobalConstants().isRoaming())
			{
				int db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_OUTGOING));
				db_temp = (int)convert(lastCallduration) + db_temp;
				new PersistedData().putData(GlobalConstants.PersistenceConstants.ROAM_OUTGOING, String.valueOf(db_temp));
			}
			else
			{
				int db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_OUTGOING));
				db_temp = (int)convert(lastCallduration) + db_temp;
				new PersistedData().putData(GlobalConstants.PersistenceConstants.LOCAL_OUTGOING, String.valueOf(db_temp));
			}
		}
	}
	
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
