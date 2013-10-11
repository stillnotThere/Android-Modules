package com.acropolis.module.call;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


public class MainActivity extends Activity {

	static Context context = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
//		checkPreReq();
		Intent serviceIntent = new Intent(this,CallerService.class);
		context.startService(serviceIntent);
	}
	
	public static Context getContext()
	{
		return context;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}