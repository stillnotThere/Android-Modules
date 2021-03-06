/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * IncomingListener
 * com.acropolis.module.call
 * CallStateListener.java
 * Created - 2013-10-10 3:48:50 PM	
 * Modified - 2013-10-10 3:48:50 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.call;

import android.database.Cursor;
import android.provider.CallLog.Calls;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * @author CPH-iMac
 *
 */
public class IncomingListener extends PhoneStateListener
{
	String str = "";
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
			Logger.Debug("\n\n\n"+incomingNumber.length());
			Logger.Debug("idle\t"+incomingNumber);
//			ringing = false;
//			INoffhook = false;
			if(incomingNumber.length()==0)
			{
				if(INoffhook)
				{
					if(incoming)
					{
						str = "incoming";
//						Handler INhandler = new Handler();
//						INhandler.postDelayed(new LogRunnable(),500);
					}
				}
				INoffhook = false;
				incoming = false;
				ringing = false;
				
//				if(OUTringing)
//				{
//					outgoing = true;
//					if(outgoing)
//					{
//						str = "outgoing";
//						Handler OUThandler = new Handler();
//						OUThandler.postDelayed(new LogRunnable(), 500);
//					}
//				}
//				OUTringing = false;
//				outgoing = false;
//				OUToffhook = false;
			}

		case TelephonyManager.CALL_STATE_OFFHOOK:
			Logger.Debug("\n\n\n"+incomingNumber.length());
			Logger.Debug("off hook\t"+incomingNumber);
			if(incomingNumber.length()==0)
			{
				if(ringing)
				{
					INoffhook = true;
					incoming = true;
				}
//				else	//possible outgoing
//				{
//					OUToffhook = true;
//				}
			}


		case TelephonyManager.CALL_STATE_RINGING:
			Logger.Debug("\n\n\n"+incomingNumber.length());
			Logger.Debug("ringing\t"+incomingNumber);
			
			Logger.Debug(String.valueOf(incomingNumber.length()));
			
			if(incomingNumber.length()>0)
			{
				ringing = true;
			}
			else if(incomingNumber.length()==0)
			{
//				if(OUToffhook)
//				{
//					OUTringing = true;
//				}
				Logger.Debug("null ##");
			}
			
		}

		//		incoming = false;

	}

	private class LogRunnable implements Runnable
	{
		@Override
		public void run()
		{
//			Logger.Debug("Incoming call");
			// get start of cursor
		
			Logger.Debug("Getting Log activity..."+str);
			String[] projection = new String[]{Calls.NUMBER,Calls.DURATION,Calls.TYPE};
			Cursor cur = MainActivity.getContext().getContentResolver().query(Calls.CONTENT_URI, projection, null, null, Calls.DATE +" desc");
			cur.moveToFirst();
			String lastCallnumber = cur.getString(0);
			String lastCallduration = cur.getString(1);
			String lastCallType = cur.getString(2);
			Logger.Debug("last callNumber::"+lastCallnumber + 
					"\nduration::"+lastCallduration );
		}
	}
	
}
