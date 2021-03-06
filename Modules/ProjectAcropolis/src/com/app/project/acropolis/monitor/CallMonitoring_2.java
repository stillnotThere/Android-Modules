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

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

	public CallMonitoring_2(Handler handler) 
	{
		super(handler);
		logHandler = handler;
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
		logHandler.postDelayed(checkLog,10* 1000);
		cursor.close();
	}

	int previousCallTime=0;
	
	@SuppressLint("UseValueOf")
	private Runnable checkLog = new Runnable() 
	{
		int lastCallduration=0;
		int lastCallType=0;
		long lastCallTime=0;
		@Override
		public void run()
		{
			Logger.Debug("Checking Log...");
			String[] projection = new String[]{
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
		
			lastCallduration = Integer.parseInt(cur.getString(0));
			lastCallType = Integer.parseInt(cur.getString(1));
			lastCallTime = Long.parseLong(cur.getString(2).toString());
			
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
			cur.close();
			Logger.Debug("duration::"+lastCallduration +
					"\ntype::"+lastCallType +
					"\ntime::"+lastCallTime + 
					"\ncurrentTime::"+System.currentTimeMillis());
		}
		
		private void proceed()
		{
			if(lastCallType == Calls.INCOMING_TYPE)
			{
				Logger.Debug("incoming");
				if(isRoaming())
				{
					long db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_INCOMING));
					db_temp = convert(lastCallduration) + db_temp;
					new PersistedData().putData(GlobalConstants.PersistenceConstants.ROAM_INCOMING, String.valueOf(db_temp));
				}
				else
				{
					long db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_INCOMING));
					db_temp = convert(lastCallduration) + db_temp;
					new PersistedData().putData(GlobalConstants.PersistenceConstants.LOCAL_INCOMING, String.valueOf(db_temp));
				}
			}
			if(lastCallType == Calls.OUTGOING_TYPE)
			{
				Logger.Debug("outgoing");
				if(isRoaming())
				{
					long db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_OUTGOING));
					db_temp = convert(lastCallduration) + db_temp;
					new PersistedData().putData(GlobalConstants.PersistenceConstants.ROAM_OUTGOING, String.valueOf(db_temp));
				}
				else
				{
					long db_temp = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_OUTGOING));
					db_temp = convert(lastCallduration) + db_temp;
					new PersistedData().putData(GlobalConstants.PersistenceConstants.LOCAL_OUTGOING, String.valueOf(db_temp));
				}
			}
		}
		
//		private int convertToInt(String str)
//		{
//			  StringBuffer sBuffer = new StringBuffer();
//			  Pattern p = Pattern.compile("[0-9]+.[0-9]*|[0-9]*.[0-9]+|[0-9]+");
//			  Matcher m = p.matcher(str);
//			  while (m.find()) {
//			    sBuffer.append(m.group());
//			  }
//			  return sBuffer.toString();
//		}
		
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
	
	public boolean isRoaming()
	{
		boolean roam = false;
	
		try {
		
		ConnectivityManager cm = 
				(ConnectivityManager) 
				ProjectAcropolisActivity.
				getContext().
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		if(ni!=null)
		{
			roam = ni.isRoaming();
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			roam = true;
		}
		return roam;
	}
}
