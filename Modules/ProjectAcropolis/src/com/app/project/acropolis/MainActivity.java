package com.app.project.acropolis;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

public class MainActivity extends Activity {

	public static Context context = null; 
	final String roamingText = "Roaming ::: ";

	TextView roaming;
	TextView incoming;
	TextView outgoing;
	TextView received;
	TextView sent;
	TextView downloaded;
	TextView uploaded;

	DBAdapter dbAdapter = null;
	Handler screenHandler = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.activity_main);

		Intent serviceIntent = new Intent(this,ServiceHandler.class);
		this.startService(serviceIntent);
		
		dbAdapter = new DBAdapter(this);

		roaming = (TextView) findViewById(R.id.strRoaming);
		incoming = (TextView) findViewById(R.id.valueIn);
		outgoing = (TextView) findViewById(R.id.valueOut);
		received = (TextView) findViewById(R.id.valueRcv);
		sent = (TextView) findViewById(R.id.valueSnt);
		downloaded = (TextView) findViewById(R.id.valueDown);
		uploaded = (TextView) findViewById(R.id.valueUp);

		updateScreen();
		screenHandler = new Handler();
		screenHandler.post(refreshScreen);
	}

	public Runnable refreshScreen = new Runnable() 
	{
		public void run()
		{
			updateScreen();
			screenHandler.postDelayed(this, 10*1000);
		}
	};

	public void updateScreen()
	{
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		roaming.setText("Roaming ::: " + isRoaming());
		
		String phoneNumber = tm.getLine1Number();
		if(dbAdapter.isEmpty())
		{
			ContentValues cv = new ContentValues();
			cv.put(DBOpenHelper.PHONENUMBER, phoneNumber);
			cv.put(DBOpenHelper.ROAMING, "fetching");
			cv.put(DBOpenHelper.INCOMING, "0");
			cv.put(DBOpenHelper.OUTGOING, "0");
			cv.put(DBOpenHelper.RECEIVED, "0");
			cv.put(DBOpenHelper.SENT, "0");
			cv.put(DBOpenHelper.DOWNLOADED, "0");
			cv.put(DBOpenHelper.UPLOADED, "0");
			dbAdapter.insert(cv);
		}
		else
		{
			incoming.setText(dbAdapter.getValue(DBOpenHelper.INCOMING));
			outgoing.setText(dbAdapter.getValue(DBOpenHelper.OUTGOING));
			received.setText(dbAdapter.getValue(DBOpenHelper.RECEIVED));
			sent.setText(dbAdapter.getValue(DBOpenHelper.SENT));
			
			downloaded.setText(
					humanReadableByteCount(Long.parseLong(dbAdapter.getValue(DBOpenHelper.DOWNLOADED)),true));
			uploaded.setText(
					humanReadableByteCount(Long.parseLong(dbAdapter.getValue(DBOpenHelper.UPLOADED)),true));
		}
		
	}

	 @Override
	  protected void onResume() {
	    super.onResume();
	    bindService(new Intent(this, ServiceHandler.class), mConnection,
	        Context.BIND_AUTO_CREATE);
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    unbindService(mConnection);
	  }
	
	ServiceHandler s;
	
	private ServiceConnection mConnection = new ServiceConnection() {

	    public void onServiceConnected(ComponentName className, IBinder binder) {
	      s = ((ServiceHandler.ServiceBinder) binder).getService();
	      Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
	          .show();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	      s = null;
	    }
	  };
	
	
	
	public final String[] CAN_OPERATORS = {"Rogers","TELUS"};
	public String isRoaming()
	{
		String roam = "";
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		for(int i=0;i<CAN_OPERATORS.length;i++)
		{
			if(telephonyManager.
					getNetworkOperatorName().equalsIgnoreCase(CAN_OPERATORS[i]))
				roam = "NO";
			else
				roam = "YES";
		}
		return roam;
	}
	
	public static String humanReadableByteCount(long bytes, boolean si) 
	{
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static Context getContext()
	{
		return context;
	}

}
