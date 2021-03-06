/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * MessageInterceptor
 * com.acropolis.radio.module.monitoring.controller
 * MessageInterceptor.java
 * Created - 2013-08-02 12:44:02 PM	
 * Modified - 2013-08-02 12:44:02 PM
 * TODO - ammend BroadcastReceiver or Service
 * NOTES - 
 */

package com.acropolis.radio.module.monitor.controller;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;

import com.acropolis.radio.module.RadioModuleActivity;
import com.acropolis.radio.module.logger.Logger;

public class MessageInterceptor 
{

	static Context appContext = RadioModuleActivity.getAppContext();
	
	/**Received Messages**/
	final static String PDU = "pdu";
	static SmsMessage[] smsReceived = null;
	static String smsReceivedFrom = "";
	static String smsReceivedText = "";
	static int messagesReceived = 0;

	/**Sent Messages**/
	final static String ougoingContentURL = "content://sms/out";
	final static Uri outgoingMessageUri = Uri.parse(ougoingContentURL);
	static Cursor smsSentCursor = null;
	static String smsSentTo = "";
	static String smsSentText = "";
	final static String[] projections = {"date","address","body"};	
	//date(milli) phonenumber messagebody
	final static String selection = null;	//row (WHERE clause)
	final static String[] selectionArgs = null;	//conditions
	final static String sortOrder = null;	//sorting (default last msg first)
	
	public boolean PrecheckPackages()
	{
		return false;
	}

	public static class IncomingMessageReceiver extends BroadcastReceiver 
	{
		/* (non-Javadoc)
		 * @see android.content.BroadcastReceiver#onReceive
		 * (android.content.Context, android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			int sessionreceivedCounter = 0;
			if(intent.getAction().equalsIgnoreCase(
					"android.provider.Telephony.SMS_RECEIVED"))
			{
				Bundle bundle = intent.getExtras();
				smsReceived = null;
				if(bundle!=null)
				{
					Object[] pdus = (Object[]) bundle.get(PDU);
					messagesReceived = pdus.length;
					smsReceived = new SmsMessage[messagesReceived];
					for(int i=0;i<messagesReceived;i++)
					{
						smsReceived[i] = 
								SmsMessage.createFromPdu((byte[])pdus[i]);
						sessionreceivedCounter++;
//						if(i==messagesReceived)
//						{
//							smsReceivedFrom = smsReceived[i].
//									getOriginatingAddress();
//							smsReceivedText = smsReceived[i].
//									getMessageBody();
//							break;
//						}
					}
//					dbreceived = Integer.
//							parseInt(DBAdapter.retrieveAValue(
//									DBConstants.RECEIVEDMSG));
//					storetotal = sessionreceivedCounter + dbreceived;
//					DBAdapter.updateValues(DBConstants.RECEIVEDMSG, 
//							String.valueOf(storetotal));
				}
				Logger.Debug("msgReceived::"+sessionreceivedCounter);
			}
		}
	}
	
	public String getReceivedMessageNumber()
	{
		return smsReceivedFrom;
	}
	
	public String getReceivedMessageBody()
	{
		return smsReceivedText;
	}

	
	public static class OutgoingMessageService extends Service
	{
		/* (non-Javadoc)
		 * @see android.app.Service#onBind(android.content.Intent)
		 */
		@Override
		public IBinder onBind(Intent intent) {
			ContentResolver outgoingMessageCR = 
					appContext.getContentResolver();
			outgoingMessageCR.registerContentObserver(
					outgoingMessageUri, true,
					new OutgoingMessageContentObserver());
			return null;
		}
		
	}
	
	public static class OutgoingMessageContentObserver extends ContentObserver
	{
		public OutgoingMessageContentObserver() {super(null);}
		
		public void onChange(boolean selfChange)
		{
			int sentcounter = 0;
			super.onChange(selfChange);
			smsSentCursor = appContext.getContentResolver().query(
					outgoingMessageUri, projections, selection, 
					selectionArgs, sortOrder);
			smsSentCursor.moveToFirst();	//last message received
			smsSentTo = smsSentCursor.
					getString(smsSentCursor.getColumnIndex("address"));
			smsSentText = smsSentCursor.
					getString(smsSentCursor.getColumnIndex("body"));
			sentcounter++;
			Logger.Debug("sentcounter::::"+sentcounter);
//			dbsent = Integer.parseInt(
//					DBAdapter.retrieveAValue(DBConstants.SENTMSG));
//			storetotal = dbsent + sentcounter;
//			DBAdapter.updateValues(DBConstants.SENTMSG, 
//					String.valueOf(storetotal));
		}
		
	}

	/**
	 * @return the smsSentTo
	 */
	public static String getSmsSentTo() 
	{
		return smsSentTo;
	}

	/**
	 * @return the smsSentText
	 */
	public static String getSmsSentText() 
	{
		return smsSentText;
	}
}

