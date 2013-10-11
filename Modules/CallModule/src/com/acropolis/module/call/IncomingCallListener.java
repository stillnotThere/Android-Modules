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
package com.acropolis.module.call;

import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog.Calls;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * @author CPH-iMac
 *
 */
public class IncomingCallListener extends PhoneStateListener
{

	boolean numberPresent = false;
	boolean incoming = false;
	boolean missed = false;

	boolean ringing = false;
	boolean offhook = false;
	boolean idle = false;

	public void onCallStateChanged(int state,String incomingNumber)
	{

		switch(state)
		{
		case TelephonyManager.CALL_STATE_IDLE:
			ringing = false;
			offhook = false;
			if(incoming)
			{
				Handler handler = new Handler();

				//Put in delay because call log is not updated immediately when state changed
				// The dialler takes a little bit of time to write to it 500ms seems to be enough
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// get start of cursor
						Logger.Debug("Getting Log activity...");
						String[] projection = new String[]{Calls.NUMBER,Calls.DURATION};
						Cursor cur = MainActivity.getContext().getContentResolver().query(Calls.CONTENT_URI, projection, null, null, Calls.DATE +" desc");
						cur.moveToFirst();
						String lastCallnumber = cur.getString(0);
						String lastCallduration = cur.getString(1);
						Logger.Debug("last callNumber::"+lastCallnumber + 
								"\nduration::"+lastCallduration );
					}
				},500);
				Logger.Debug("call finished");
			}


		case TelephonyManager.CALL_STATE_OFFHOOK:

			if(incomingNumber!=null)
			{
				if(ringing)
				{
					offhook = true;
					if(offhook)
					{
						incoming = true;
						Logger.Debug("Incoming call");

						//flushing
						offhook = false;
					}
					//flushing
					ringing = false;
				}		
			}


		case TelephonyManager.CALL_STATE_RINGING:

			if(incomingNumber!=null)
			{
				ringing = true;
			}

		}

		//		incoming = false;

	}

}
