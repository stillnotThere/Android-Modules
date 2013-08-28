package com.acropolis.location.module.view;

import logger.Logger;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.ServiceState;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.acropolis.location.module.R;
import com.acropolis.location.module.controller.ControlSwitches;

public class LocationModuleActivity extends Activity {

	static Context _context = null;
	static Intent _intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Logger.Verbose("LocationModule activity loaded");
		getAllServiceState();
		_context = this.getApplicationContext();
		_intent = this.getIntent();
		Logger.Debug(this.getClass().toString());
		ControlSwitches.setupMainBoard();
		ControlSwitches.switchON();

		//		String[] allServiceState = getAllServiceState();
		//		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
		//				getApplicationContext(),
		//				android.R.layout.simple_list_item_1,
		//				allServiceState);
		//		ListView listView = (ListView) findViewById(android.R.id.list);
		//		listView.setAdapter(arrayAdapter);

	}

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
			Logger.Verbose("ServiceState null");
		//		return stateInfos;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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