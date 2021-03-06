package com.acropolis.module.message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
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
		checkPreReqs();
		appContext = getApplicationContext();
		
//		IntentFilter intentFilter = new IntentFilter(MSG_RECEIVED);
//		intent = registerReceiver(new IncomingMessageReceiver(),
//				intentFilter,"android.permission.RECEIVE_SMS",null);
//		Logger.Debug("sms receiver registered");
		
		Intent outmsgService = new Intent(this,OutgoingMessagingService.class);
		this.startService(outmsgService);
		Logger.Debug("sms service enabled");
		
	}
	
	public void checkPreReqs()
	{
		Uri msgUri = Uri.parse("content://sms");
		String colNames = "Columns::";
		Cursor msgCursor = getApplicationContext().
				getContentResolver().
				query(msgUri, new String[]{"*"}, 
						null, null, null);
		if(msgCursor.moveToFirst())
		{
			for(int i=0;i<msgCursor.getColumnCount();i++)
			{
				colNames = colNames.concat(msgCursor.getColumnName(i) +
						"\t" + msgCursor.getType(i) + 
						"\n");
			}
			Logger.Debug(colNames);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static Context getAppContext()
	{
		return appContext;
	}
	
	
}
