/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * IncomingMessageReceiver
 * com.acropolis.module.messagemodule
 * IncomingMessageReceiver.java
 * Created - 2013-10-02 3:00:06 PM	
 * Modified - 2013-10-02 3:00:06 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.module.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author CPH-iMac
 *
 */
public class IncomingMessageReceiver extends BroadcastReceiver
{

	public static int counter = 0;

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		// TODO Auto-generated method stub

		if(intent.getAction() == ("android.provider.Telephony.SMS_RECEIVED"))
		{
			counter = counter + 1;
			Logger.Debug("Message received::" + counter);
		}
	}

}
