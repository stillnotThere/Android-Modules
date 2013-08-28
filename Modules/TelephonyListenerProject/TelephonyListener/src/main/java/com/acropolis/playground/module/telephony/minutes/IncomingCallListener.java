package com.acropolis.playground.module.telephony.minutes;

/*******************************************************************************
 * Cell Phone Hospital Inc. and Project Acropolis Copyright (c).
 * 2013.
 * @author - Rohan K.M {rohan.mahendroo@gmail.com}
 * Are copyright protected to author and is delegated to the software developed/development and distribution under CPH jurisdiction.
 ******************************************************************************/

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.acropolis.playground.module.telephony.Activity.TelephonyActivity;
import com.acropolis.playground.module.telephony.Logger.Logging;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CellPhoneHospitalInc on 2013-07-08.
 */
public class IncomingCallListener extends PhoneStateListener
{



	IncomingRecorderThread inThread = new IncomingRecorderThread();

	public boolean RINGING = false;
	public boolean INCOMING = false;

	private SimpleDateFormat sdf_time = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
	private Date currentDate = new Date();

	public String incomingNumber = "";
	public String timeStamp = "";
	public int incomingDuration = 0;

	public Intent intent = null;
	public Bundle bundle = null;
	public String extraState = "";
	public String extraNumber = "";

	/**
	 * * #doc android.telephony.PhoneStateListener#onCallStateChanged
	 * @param state
	 * @param incomingNumber
	 */
	@Override
	public void onCallStateChanged(int state, String incomingNumber)
	{
		super.onCallStateChanged(state, incomingNumber);

		switch (state)
		{

			case TelephonyManager.CALL_STATE_RINGING:
			{
				RINGING = true;
			};

			case TelephonyManager.CALL_STATE_OFFHOOK:
			{
				if(RINGING)
				{
					if(incomingNumber==null)
					{
						extraNumber = getExtraString(TelephonyManager.EXTRA_INCOMING_NUMBER);
						if(extraNumber==null)
							Logging.Information("number unknown");
						else
							Logging.Information("extra number");
					}

					Logging.Information("Call answered #"+incomingNumber);
					INCOMING = true;
					inThread.start();
				}
				else
				{
					//todo check with OutgoingCallReceiver broadcast-receiver
				}
			};

			case TelephonyManager.CALL_STATE_IDLE:
			{
				if(RINGING && INCOMING)
				{
					inThread.kill();
				}
			};

			default:
			{
				RINGING = false;
				INCOMING = false;
			};
		}
	}

	public String getExtraString(String extra)
	{
		intent = TelephonyActivity.getAppIntent();
		return intent.getStringExtra(extra);
	}

	public class IncomingRecorderThread extends Thread
	{
		public IncomingRecorderThread()
		{

		}

		@Override
		public void run()
		{

		}

		public void kill()
		{

		}

	}



}