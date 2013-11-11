/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * CallStateListener
 * com.acropolis.module.call
 * CallStateListener.java
 * Created - 2013-10-10 3:48:50 PM	
 * Modified - 2013-10-10 3:48:50 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.monitor;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog.Calls;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.app.project.acropolis.Logger;
import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class CallMonitoring extends PhoneStateListener
{
	boolean numberPresent = false;
	boolean incoming = false;
	boolean missed = false;
	boolean outgoing = false;

	boolean ringing = false;
	boolean INoffhook = false;
	boolean idle = false;

	boolean OUToffhook = false;
	boolean OUTringing = false;

	public void onCallStateChanged(int state,String incomingNumber)
	{
		switch(state)
		{
		case TelephonyManager.CALL_STATE_IDLE:
			Logger.Debug("idle\t"+incomingNumber);
			//			ringing = false;
			//			INoffhook = false;
			if(incomingNumber.length()==0)
			{
				if(INoffhook)
				{
					if(incoming)
					{
						Handler INhandler = new Handler();
						INhandler.postDelayed(new IncomingRunnable(),500);
						Logger.Debug("call finished");
					}
				}
				INoffhook = false;
				incoming = false;
				ringing = false;

				if(OUTringing)
				{
					outgoing = true;
					Logger.Debug("Outgoing call");
					if(outgoing)
					{
						Handler OUThandler = new Handler();
						OUThandler.postDelayed(new OutgoingRunnable(), 500);
					}
				}
				OUTringing = false;
				outgoing = false;
				OUToffhook = false;
			}

		case TelephonyManager.CALL_STATE_OFFHOOK:
			Logger.Debug("off hook\t"+incomingNumber);
			if(incomingNumber.length()==0)
			{
				if(ringing)
				{
					INoffhook = true;
					incoming = true;
				}
				else	//possible outgoing
				{
					OUToffhook = true;
				}
			}


		case TelephonyManager.CALL_STATE_RINGING:
			Logger.Debug("ringing\t"+incomingNumber);

			if(incomingNumber.length()>0)
			{
				ringing = true;
			}
			else 
			{
				if(!ringing)
				{
					if(OUToffhook)
					{
						OUTringing = true;
					}
				}
				Logger.Debug("null");
			}

		}

		//		incoming = false;

	}

	private class IncomingRunnable implements Runnable
	{
		@Override
		public void run()
		{
			long callLong = 0;
			long previous=0;
			long newV=0;
			long total=0;
			Logger.Debug("Getting Log activity...");
			String[] projection = new String[]{Calls.NUMBER,Calls.DURATION};
			Cursor cur = ProjectAcropolisActivity.getContext().getContentResolver().query(Calls.CONTENT_URI, projection, null, null, Calls.DATE +" desc");
			cur.moveToFirst();
			String lastCallnumber = cur.getString(0);
			String lastCallduration = cur.getString(1);
			Logger.Debug("last callNumber::"+lastCallnumber + 
					"\nduration::"+lastCallduration );

			callLong = Long.parseLong(lastCallduration);
			if(callLong<=60)
			{
				newV = 1;
			}
			else
			{
				newV = convert(callLong);
			}
			previous = Long.parseLong(DBAdapter.getValue(DBOpenHelper.INCOMING));
			total = previous + newV;

			ContentValues cv = new ContentValues();
			cv.put(DBOpenHelper.INCOMING, String.valueOf(total));
			DBAdapter.update(cv);

			cur.close();
			//			}
		}
	}

	private class OutgoingRunnable implements Runnable
	{
		@Override
		public void run()
		{
			long callLong = 0;
			long previous=0;
			long newV=0;
			long total=0;
			Logger.Debug("Getting Log activity...");
			String[] projection = new String[]{Calls.NUMBER,Calls.DURATION};
			Cursor cur = ProjectAcropolisActivity.getContext().getContentResolver().query(Calls.CONTENT_URI, projection, null, null, Calls.DATE +" desc");
			cur.moveToFirst();
			String lastCallnumber = cur.getString(0);
			String lastCallduration = cur.getString(1);
			Logger.Debug("last callNumber::"+lastCallnumber + 
					"\nduration::"+lastCallduration );

			if(callLong<=60)
			{
				newV = 1; //1minute
			}
			else
			{
				newV = convert(callLong);
			}
			previous = Long.parseLong(DBAdapter.getValue(DBOpenHelper.OUTGOING));
			newV = convert(Long.parseLong(lastCallduration));
			total = previous + newV;

			ContentValues cv = new ContentValues();
			cv.put(DBOpenHelper.OUTGOING, String.valueOf(total));
			DBAdapter.update(cv);

			cur.close();


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
