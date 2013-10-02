package com.acropolis.module.message;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	public final String MSG_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	public Intent intent = null;
	public static Context appContext = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		appContext = getApplicationContext();
		
		IntentFilter intentFilter = new IntentFilter(MSG_RECEIVED);
		intent = registerReceiver(new IncomingMessageReceiver(),
				intentFilter,"android.permission.RECEIVE_SMS",null);
		Logger.Debug("sms receiver registered");
		
		Intent outmsgService = new Intent(getApplicationContext(),OutgoingMessagingService.class);
		getApplicationContext().startService(outmsgService);
		Logger.Debug("sms service enabled");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onBackPressed()
	{
		Logger.Debug("Back pressed");
	}
	
	public static Context getAppContext()
	{
		return appContext;
	}
	
	
}
