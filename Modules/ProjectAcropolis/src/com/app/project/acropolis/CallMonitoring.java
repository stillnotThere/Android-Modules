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
package com.app.project.acropolis;

import java.util.concurrent.TimeUnit;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog.Calls;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

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

	DBAdapter dbAdapter = null;

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
			//			Logger.Debug("Incoming call");
			// get start of cursor
			Logger.Debug("Getting Log activity...");
			String[] projection = new String[]{Calls.NUMBER,Calls.DURATION};
			Cursor cur = MainActivity.getContext().getContentResolver().query(Calls.CONTENT_URI, projection, null, null, Calls.DATE +" desc");
			cur.moveToFirst();
			String lastCallnumber = cur.getString(0);
			String lastCallduration = cur.getString(1);
			Logger.Debug("last callNumber::"+lastCallnumber + 
					"\nduration::"+lastCallduration );

			dbAdapter = new DBAdapter(MainActivity.getContext());

			//			if(!dbAdapter.isEmpty())
			//			{
			long previous = Long.parseLong(dbAdapter.getValue(DBOpenHelper.INCOMING));
			long newV = convert(Long.parseLong(lastCallduration));
			long total = previous + newV;

			ContentValues cv = new ContentValues();
			cv.put(DBOpenHelper.INCOMING, String.valueOf(total));
			dbAdapter.update(cv);

			cur.close();
			//			}
			//			else
			//			{
			//				ContentValues cv = new ContentValues();
			//				cv.put(DBOpenHelper.INCOMING, String.valueOf(
			//						convert(Long.parseLong(lastCallduration))));
			//				dbAdapter.update(cv);
			//			}
		}
	}

	private class OutgoingRunnable implements Runnable
	{
		@Override
		public void run()
		{
			//			Logger.Debug("Incoming call");
			// get start of cursor
			Logger.Debug("Getting Log activity...");
			String[] projection = new String[]{Calls.NUMBER,Calls.DURATION};
			Cursor cur = MainActivity.getContext().getContentResolver().query(Calls.CONTENT_URI, projection, null, null, Calls.DATE +" desc");
			cur.moveToFirst();
			String lastCallnumber = cur.getString(0);
			String lastCallduration = cur.getString(1);
			Logger.Debug("last callNumber::"+lastCallnumber + 
					"\nduration::"+lastCallduration );

			long previous = Long.parseLong(dbAdapter.getValue(DBOpenHelper.OUTGOING));
			long newV = convert(Long.parseLong(lastCallduration));
			long total = previous + newV;

			ContentValues cv = new ContentValues();
			cv.put(DBOpenHelper.OUTGOING, String.valueOf(total));
			dbAdapter.update(cv);

			cur.close();
			//			}
			//			else
			//			{
			//				ContentValues cv = new ContentValues();
			//				cv.put(DBOpenHelper.OUTGOING, String.valueOf(
			//						convert(Long.parseLong(lastCallduration))));
			//				dbAdapter.update(cv);
			//			}


		}
	}

	private long convert(long sec)
	{
		long minute = TimeUnit.MINUTES.convert(sec, 
				TimeUnit.SECONDS);
		Logger.Debug("minute::"+minute);
		return minute;
	}

}