package com.module.tester.mncmcc;

import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	final String TAG = "MCCnMNC";
	
	TextView tvMCC = null;
	TextView tvMNC = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvMCC = (TextView) findViewById(R.id.valmcc);
		tvMNC = (TextView) findViewById(R.id.valmnc);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public void refresh(View view)
	{
		Context context = getApplicationContext();
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		String mccmnc = tm.getNetworkOperator();
		String mcc = null;
		String mnc = null;
		
		if(mccmnc.length() <= 6)
		{
			mcc = mccmnc.substring(0, 3);
			mnc = mccmnc.substring(3, mccmnc.length());
		}
		
		Log.i(TAG, tm.getNetworkOperator());
		Log.i(TAG, "MCC::"+mcc);
		Log.i(TAG, "MNC::"+mnc);
		
		tvMCC.setText(mcc);
		tvMNC.setText(mnc);
 	}
	
}
