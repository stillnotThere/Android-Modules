package com.acropolis.module.call;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;


public class MainActivity extends Activity {

	static Context context = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
//		getEverything();
//		checkPreReq();
		Intent serviceIntent = new Intent(this,CallerService.class);
		context.startService(serviceIntent);
	}
	
	public static Context getContext()
	{
		return context;
	}

	public void getEverything()
	{
//		int i=0;
		Cursor logCursor = this.getContentResolver().query(
//				Uri.parse("content://call_log"),
				CallLog.Calls.CONTENT_URI,
				new String[]{"*"},
				null,null,null);
		
		if(logCursor != null)
		{
			Logger.Debug("ColumnCount::"+logCursor.getColumnCount());
			logCursor.moveToFirst();
			for(int i=0;i<logCursor.getColumnCount();i++)
			{
				Logger.Debug(logCursor.getColumnName(i));
				logCursor.moveToNext();
//				i++;
			}
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}