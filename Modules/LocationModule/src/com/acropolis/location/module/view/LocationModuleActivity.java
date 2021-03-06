package com.acropolis.location.module.view;

import java.util.TimerTask;

import logger.Logger;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.ServiceState;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.acropolis.location.module.R;
import com.acropolis.location.module.controller.GetDetails;
import com.acropolis.location.module.controller.LocationHub;

public class LocationModuleActivity extends Activity {

	static Context _context = null;
	static Context serviceContext = null;
	static Intent _intent = null;
	static Intent serviceIntent = null;

	
	public static Handler screenHandler = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_context = this.getApplicationContext();
		_intent = this.getIntent();
		Logger.Verbose("LocationModule activity loaded");

		serviceContext = _context;
		serviceIntent = new Intent(serviceContext,LocationHub.class);
		serviceContext.startService(serviceIntent);
		
		updateScreen();
		
		Logger.Verbose("Timer starting");

		screenHandler = new Handler();
		screenHandler.post(updateScreen);
//		new ScreenLooper().start();
	}

	public Runnable updateScreen = new Runnable() 
	{
		public void run()
		{
			updateScreen();
			screenHandler.postDelayed(this, 100*1000);
		}
	};
		
	public void updateScreen()
	{
		
		TextView latitudeText = (TextView) findViewById(R.id.txtLat);
		TextView longitudeText = (TextView) findViewById(R.id.txtLng);
		TextView accuracyText = (TextView) findViewById(R.id.txtAcc);
		final String waiting = "Waiting...";
		
		if(GetDetails.getLatitude()==0) 
		{
			latitudeText.setText(waiting);
			longitudeText.setText(waiting);
			accuracyText.setText(waiting);
		}
		else
		{
			latitudeText.setText(String.valueOf(GetDetails.getLatitude()));
			longitudeText.setText(String.valueOf(GetDetails.getLongitude()));
			accuracyText.setText(String.valueOf(GetDetails.getAccuracy()));
		}
	}
	
	public class ScreenLooper extends TimerTask//Thread 
	{
		public void run()
		{
			while(true)
			{
				updateScreen();
				try {
					Thread.sleep(100*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	public void getAllServiceState()
	{
		Logger.Verbose("getAllServiceState()");
		ServiceState serviceState = new ServiceState();
		if(serviceState!=null)
		{
			Logger.Verbose(serviceState.toString()+
					"\nContents::::"+serviceState.describeContents());
			String[] stateInfos = new String[20];
			stateInfos[0] = serviceState.getOperatorAlphaLong();
			stateInfos[1] = serviceState.getOperatorAlphaShort();
			stateInfos[2] = serviceState.getOperatorNumeric();
			stateInfos[3] = String.valueOf(serviceState.getIsManualSelection());
			stateInfos[4] = String.valueOf(serviceState.getRoaming());
			switch(serviceState.getState())
			{
			case ServiceState.STATE_EMERGENCY_ONLY:
				stateInfos[5] = "Emergency";
			case ServiceState.STATE_IN_SERVICE:
				stateInfos[5] = "In Service";
			case ServiceState.STATE_OUT_OF_SERVICE:
				stateInfos[5] = "OUT OF SERVICE";
			case ServiceState.STATE_POWER_OFF:
				stateInfos[5] = "POWER OFF";
			}
			int endCount = 0;
			while(stateInfos.length==endCount)
			{
				Logger.Verbose(stateInfos[endCount] + "\n");
				endCount++;
			}
		}
		else
		{
			Logger.Verbose("ServiceState null");
		}
		//		return stateInfos;
	}

	boolean startL = true;
	boolean stopL = true;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem menuItem)
	{
		boolean selected = false;
		
		
		if(menuItem.getItemId() == R.id.start_stop)
		{
			if(menuItem.getTitle() == 
					Resources.getSystem().getString(R.string.start))
			{
				menuItem.setTitle(R.string.stop);
				new LocationHub().stop();
				startL = false;
			}
			else if(menuItem.getTitle() == 
					Resources.getSystem().getString(R.string.stop))
			{
//				serviceContext.startService(serviceIntent);
				menuItem.setTitle(R.string.start);
				startL = true;
			}
		}
		
		return selected;
	}
	
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			new LocationHub().stop();
//			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void onBackPressed()
	{
		super.onBackPressed();
		Logger.Verbose("service kiiled");
		serviceContext.stopService(serviceIntent);
//		System.exit(0);
	}

	
	public static Context getAppContext()
	{
		return _context;
	}

	public static Intent getAppIntent()
	{
		return _intent;
	}

	
	
}