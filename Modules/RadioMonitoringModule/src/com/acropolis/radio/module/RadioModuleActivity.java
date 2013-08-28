package com.acropolis.radio.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;

import com.acropolis.module.listener.minutes.R;
import com.acropolis.radio.module.global.Common;
import com.acropolis.radio.module.global.DBConstants;
import com.acropolis.radio.module.logger.Logger;
import com.acropolis.radio.module.model.DBAdapter;

public class RadioModuleActivity extends Activity {

	static Context appContext = null;
	static Intent appIntent = null;
	
	String fetchedCallType = "";
	String fetchedCallDuration = "";
	String fetchedCallTimeStamp = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		appContext = this.getApplicationContext();
		appIntent = this.getIntent();
		StartMessagingEngine();
		setContentView(R.layout.activity_minutes_module);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.minutes_module, menu);
		return true;
	}

	public void FirstRunDBSetup()
	{
		if(DBAdapter.isEmpty())
		{
			
			DBAdapter.insertValues(DBConstants.ID,Common.DUMMY_ID);
			DBAdapter.insertValues(DBConstants.TIMESTAMP, Common.DUMMY_TIMESTAMP);
			DBAdapter.insertValues(DBConstants.PHONENUMBER, 
					Common.getDevicePhoneNumber(this));
			DBAdapter.insertValues(DBConstants.ROAMING, "false");
			
			
		}
	}
	

	
	
	public static Intent getAppIntent()
	{
		return appIntent;
	}
	
	public static Context getAppContext()
	{
		return appContext;
	}
	
	public void DecorateScreen()
	{
		Logger.Information("--->>I#"+this.getClass()+"\ndecorating-screen");
		//TODO
	}
	
	private boolean StartMessagingEngine()
	{
		Logger.Debug("--->>D#"+this.getClass().toString());
		registerReceiver(
				new com.acropolis.radio.module.monitor.controller.
				MessageInterceptor.IncomingMessageReceiver(), 
				new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
		return true;
	}
	
}