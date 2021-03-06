/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * MinutesListenerModule
 * com.acropolis.radio.module.monitoring.controller
 * Interceptor.java
 * Created - 2013-07-26	
 * Modified - 2013-07-31
 * TODO
 */
package com.acropolis.radio.module.monitor.controller;

import android.os.AsyncTask;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;

import com.acropolis.radio.module.logger.Logger;

public class CallInterceptor 
{

	public static class CallStateListener extends PhoneStateListener
	{
		private CallLogAT callLog = new CallLogAT();

		public void onCallStateChanged(int state,String incomingNumber)
		{

			switch (state)
			{
			case TelephonyManager.CALL_STATE_IDLE:
			{
				RadioEngine.IDLE = true;
				if(RadioEngine.__INCOMING)
				{
					RadioEngine.__INCOMING = false;
					RadioEngine.__OUTGOING = false;
				}
			};

			case TelephonyManager.CALL_STATE_RINGING:
			{
				RadioEngine.RINGING = true;
			};

			case TelephonyManager.CALL_STATE_OFFHOOK:
			{
				RadioEngine.OFFHOOK = true;
				if(RadioEngine.IDLE && RadioEngine.RINGING && RadioEngine.OFFHOOK)
				{
					RadioEngine.__INCOMING = true;

					RadioEngine.__OUTGOING = false;
					
					callLog.execute();
				}

				if(RadioEngine.IDLE && RadioEngine.OFFHOOK)
				{
					RadioEngine.__OUTGOING = true;

					RadioEngine.__INCOMING = false;
					
					callLog.execute();
				}

			};
			}
		}

		//look over Handler(non UI thread)
		private class CallLogAT extends AsyncTask<Void,Void,Boolean>
		{

			@Override
			protected Boolean doInBackground(Void... params) {
				String callType = CallLog.Calls.TYPE;
				Logger.Information("Call Type ::"+RadioEngine.callType);

				if(callType.equalsIgnoreCase(RadioEngine.INCOMING))
				{
					GetDetails.setCallType(RadioEngine._INCOMING);
					Logger.Information("Incoming");
					RadioEngine.callPhoneNumber = CallLog.Calls.NUMBER;
					RadioEngine.callDuration = Integer.decode(CallLog.Calls.DURATION);
					RadioEngine.callTimeStamp = Integer.decode(CallLog.Calls.DATE);

					//storing
					PersistValues();
				}
				if(callType.equalsIgnoreCase(RadioEngine.OUTGOING))
				{
					GetDetails.setCallType(RadioEngine._OUTGOING);
					Logger.Information("Outgoing");
					RadioEngine.callPhoneNumber = CallLog.Calls.NUMBER;
					RadioEngine.callDuration = Integer.decode(CallLog.Calls.DURATION);
					RadioEngine.callTimeStamp = Integer.decode(CallLog.Calls.DATE);

					//storing
					PersistValues();
				}
				if(callType.equalsIgnoreCase(RadioEngine.MISSED))
				{Logger.Information("Missed");}
				return true;
			}
		}
		
		public void PersistValues()
		{
			switch(GetDetails.getCallType())
			{
			case RadioEngine._INCOMING:
			{
//				int dbIn = Integer.parseInt(DBAdapter.retrieveAValue(DBConstants.INCOMINGCALL));
				int sessionIn = GetDetails.getCallDuration().intValue();
				Logger.Debug("incomingSeconds::"+sessionIn);
//				int totalIn = dbIn + sessionIn;
//				DBAdapter.updateValues(DBConstants.INCOMINGCALL, String.valueOf(totalIn));
				GetDetails.setCallDuration(Integer.valueOf(0));
			};
			case RadioEngine._OUTGOING:
			{
//				int dbOut = Integer.parseInt(DBAdapter.retrieveAValue(DBConstants.OUTGOINGCALL));
				int sessionOut = GetDetails.getCallDuration().intValue();
				Logger.Debug("outgoingSeconds::"+sessionOut);
//				int totalOut = dbOut + sessionOut;
//				DBAdapter.updateValues(DBConstants.OUTGOINGCALL, String.valueOf(totalOut));
				GetDetails.setCallDuration(Integer.valueOf(0));
			};
			}
			
			
			
		}
	}

	/**
	 *	Listens for Radio Service changes 
	 */
	public static class ServiceStateListener extends PhoneStateListener
	{
		/**
		 * {@link android.telephony.PhoneStateListener#onServiceStateChanged(ServiceState)}
		 * radio-registration active monitoring incld. radio off(explicitly)
		 * states::
		 * 	ACITVE_SERVICE
		 * 	INACTIVE_SERVICE
		 * 	INACTIVE_POWER_OFF
		 */
		public void onServiceStateChanged(ServiceState serviceState)
		{
			Logger.Information("ServiceState changed");
			switch(serviceState.getState())
			{

			case ServiceState.STATE_IN_SERVICE:
			{RadioEngine.CURRENT_SERVICE_STATE = RadioEngine.ACTIVE_SERVICE;};

			case ServiceState.STATE_POWER_OFF:
			{RadioEngine.CURRENT_SERVICE_STATE = RadioEngine.INACTIVE_POWER_OFF;};

			case ServiceState.STATE_OUT_OF_SERVICE:
			{RadioEngine.CURRENT_SERVICE_STATE = RadioEngine.INACTIVE_SERVICE;};

			case ServiceState.STATE_EMERGENCY_ONLY:
			{RadioEngine.CURRENT_SERVICE_STATE = RadioEngine.INACTIVE_SERVICE;};

			}
		}
	}

}