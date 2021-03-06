package com.acropolis.radio.module;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.acropolis.module.listener.minutes.R;
import com.acropolis.radio.module.global.Common;
import com.acropolis.radio.module.global.DBConstants;
import com.acropolis.radio.module.logger.Logger;
import com.acropolis.radio.module.model.DBAdapter;
import com.acropolis.radio.module.monitor.controller.RadioEngine;

public class RadioModuleActivity extends Activity
{

	static Context appContext = null;
	static Intent appIntent = null;

	String fetchedCallType = "";
	String fetchedCallDuration = "";
	String fetchedCallTimeStamp = "";

	//Minutes
	TextView inValue = null;
	TextView outValue = null;
	TextView totalVoiceValue = null;

	//Messages
	TextView rcvValue = null;
	TextView sntValue = null;
	TextView totalMsgValue = null;

	//Data
	TextView downValue = null;
	TextView upValue = null;
	TextView totalDataValue = null;

	DBAdapter dbAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_minutes_module);
		
		inValue = (TextView) this.findViewById(R.id.valueIn);
		outValue = (TextView) this.findViewById(R.id.valueOut);
		totalVoiceValue = (TextView) this.findViewById(R.id.txtVTotal);

		rcvValue = (TextView) this.findViewById(R.id.valueRcv);
		sntValue = (TextView) this.findViewById(R.id.valueSnt);
		totalMsgValue = (TextView) this.findViewById(R.id.valueMTotal);

		downValue = (TextView) this.findViewById(R.id.valueDown);
		upValue = (TextView) this.findViewById(R.id.valueUp);
		totalDataValue = (TextView) this.findViewById(R.id.valueDTotal);
		
		appContext = getApplicationContext();
		appIntent = getIntent();
		
		dbAdapter = new DBAdapter(getApplicationContext());
		FirstRunDBSetup();
		RadioEngine.igniteEngine();
		StartMessagingEngine();
//		fetchCurrentMonitoredValues();
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
		Logger.Debug("FirstRunDBSetup");
		if(dbAdapter.isEmpty())
		{
			Logger.Debug("DB empty");
			dbAdapter.insertValues(DBConstants.ID,
					Common.DUMMY_ID);
			dbAdapter.insertValues(DBConstants.TIMESTAMP, 
					Common.DUMMY_TIMESTAMP);
			dbAdapter.insertValues(DBConstants.PHONENUMBER, 
					Common.getDevicePhoneNumber(this));
			dbAdapter.insertValues(DBConstants.ROAMING, 
					Common.DUMMY_ROAMING);
			dbAdapter.insertValues(DBConstants.INCOMINGCALL, 
					Common.DUMMY_INCOMING);
			dbAdapter.insertValues(DBConstants.OUTGOINGCALL, 
					Common.DUMMY_OUTGOING);
			dbAdapter.insertValues(DBConstants.RECEIVEDMSG, 
					Common.DUMMY_RECEIVED);
			dbAdapter.insertValues(DBConstants.SENTMSG, 
					Common.DUMMY_SENT);
			dbAdapter.insertValues(DBConstants.DOWNLOADED,
					Common.DUMMY_DOWNLOADED);
			dbAdapter.insertValues(DBConstants.UPLOADED, 
					Common.DUMMY_UPLOADED);
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

	/**
	 * Retrieve current monitored values
	 * Accessed by onClick#refreshValues & onCreate
	 * @return true
	 */
	public boolean fetchCurrentMonitoredValues()
	{
		/*Minutes*/
		long incomingMin = ConvertToMinutes( 
				Long.parseLong(
						dbAdapter.retrieveAValue(DBConstants.INCOMINGCALL)));
		long outgoingMin = ConvertToMinutes(
				Long.parseLong(
						dbAdapter.retrieveAValue(DBConstants.OUTGOINGCALL)));
		long totalMin = incomingMin + outgoingMin;

		inValue.setText(String.valueOf(incomingMin));
		outValue.setText(String.valueOf(outgoingMin));
		totalVoiceValue.setText(String.valueOf(totalMin));
		
		/*Messages*/
		long recvMsg = Long.parseLong(dbAdapter.retrieveAValue(DBConstants.RECEIVEDMSG));
		long sntMsg = Long.parseLong(dbAdapter.retrieveAValue(DBConstants.SENTMSG));
		long totalMsgs = recvMsg + sntMsg;
		
		rcvValue.setText(String.valueOf(recvMsg));
		sntValue.setText(String.valueOf(sntMsg));
		totalMsgValue.setText(String.valueOf(totalMsgs));
		
		/*Data*/
		long downloadData = ConvertToKB(
				Long.parseLong(
						dbAdapter.retrieveAValue(DBConstants.DOWNLOADED)));
		long uploadData = ConvertToKB(
				Long.parseLong(
						dbAdapter.retrieveAValue(DBConstants.UPLOADED)));
		long totalData = downloadData + uploadData;
		
		downValue.setText(String.valueOf(downloadData));
		upValue.setText(String.valueOf(uploadData));
		totalDataValue.setText(String.valueOf(totalData));
		
		return true;
	}

	public long ConvertToMinutes(long seconds)
	{
		return TimeUnit.MINUTES.convert(seconds, TimeUnit.SECONDS);
	}

	public long ConvertToKB(long bytes)
	{
		return (long) Math.ceil(bytes/(1024));
	}

	/**
	 * R.id.bttnRefresh onClick
	 */
	public void refreshValues(View v)
	{
		Toast.makeText(getApplicationContext(), "Refreshing values", Toast.LENGTH_LONG).show();
//		fetchCurrentMonitoredValues();
	}

}
