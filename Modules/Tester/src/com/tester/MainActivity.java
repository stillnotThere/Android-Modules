package com.tester;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity 
{

	final String TAG = "##TEST";
	EditText input = null;
	TextView output_tu = null;
	TextView output_ceil = null;
	SharedPrefs sp = new SharedPrefs(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		input = (EditText) findViewById(R.id.inSec);
		output_tu = (TextView) findViewById(R.id.resultMins_timeunit);
		output_ceil = (TextView) findViewById(R.id.result_ceil);
		
		
		
		
	}

	public String convertTU(String sec)
	{
		long min=0;
		long secs = Long.parseLong(sec);
		min = TimeUnit.MINUTES.convert(secs, TimeUnit.SECONDS);
		sp.setData(KEYS[1], (String.valueOf(min)));
		return String.valueOf(min);
	}
	
	public String convertCeil(String sec)
	{
		long min=0;
		long secs= Long.parseLong(sec);
		
		if(secs%60==0)
		{
			min = secs/60;
		}
		else
		{
			Log.v(TAG, String.valueOf(secs%60));
			min = (long) Math.abs(secs/60) + 1;
		}
		sp.setData(KEYS[2], (String.valueOf(min)));
		return String.valueOf(min);
	}
	
	public final String[] KEYS = {"input","output_tu","output_ceil"};
	
	public void convert(View v)
	{
		sp.setData(KEYS[0], input.getText().toString());
		output_tu.setText(convertTU(sp.getData(KEYS[0])));
		output_ceil.setText(convertCeil(sp.getData(KEYS[0])));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
