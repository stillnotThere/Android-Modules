package com.app.project.acropolis;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.app.project.acropolis.controller.ServiceHandler;
import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

public class ProjectAcropolisActivity extends Activity {

	public static Context context = null; 
	final String roamingText = "Roaming ::: ";

	TextView roaming;
	TextView incoming;
	TextView outgoing;
	TextView received;
	TextView sent;
	TextView downloaded;
	TextView uploaded;

	TextView voiceTotal;
	TextView msgTotal;
	TextView dataTotal;
	TextView voiceRTotal;
	TextView msgRTotal;
	TextView dataRTotal;
	
	Handler screenHandler = null;
	static Activity activity = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		activity = this;
//		DBAdapter.checkDBState();
//		DBAdapter.deleteDB();
//		DBAdapter.createDB();
//		DBAdapter.checkIntegrity();
//		DBAdapter.putBlank();
		setContentView(R.layout.activity_main);

		Intent serviceIntent = new Intent(this,ServiceHandler.class);
		this.startService(serviceIntent);

		roaming = (TextView) findViewById(R.id.strRoaming);
		incoming = (TextView) findViewById(R.id.valueIn);
		outgoing = (TextView) findViewById(R.id.valueOut);
		received = (TextView) findViewById(R.id.valueRcv);
		sent = (TextView) findViewById(R.id.valueSnt);
		downloaded = (TextView) findViewById(R.id.valueDown);
		uploaded = (TextView) findViewById(R.id.valueUp);

		voiceTotal = (TextView) findViewById(R.id.valueVTotal);
		msgTotal = (TextView) findViewById(R.id.valueMTotal);
		dataTotal = (TextView) findViewById(R.id.valueDTotal);

		voiceRTotal = (TextView) findViewById(R.id.valueRVTotal);
		msgRTotal = (TextView) findViewById(R.id.valueRMTotal);
		dataRTotal = (TextView) findViewById(R.id.valueRDTtotal);
		
//		DBAdapter.deleteDB();

		updateScreen();
		screenHandler = new Handler();
		screenHandler.post(refreshScreen);
	}

	public static Activity getActivity()
	{
		return activity;
	}
	
	public static String toastMsg = "";
	public static void postToast(String msg)
	{
		toastMsg = msg;
		getActivity().runOnUiThread(new Runnable() {
			public void run()
			{
				Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();				
			}
		});
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
		synchronized(this)
		{
//			DBAdapter.putBlank();
			int in = 0;
			int out = 0;
			int rcv = 0;
			int snt = 0;
			long down =0;
			long up = 0;

			int totalV = 0;
			int totalM = 0;
			long totalD = 0;
			
			int totalRV = 0;
			int totalRM = 0;
			long totalRD = 0;
			
//			roaming.setText("Roaming ::: " + isRoaming());
			in = Integer.parseInt(DBAdapter.getValue(DBOpenHelper.LOCAL_INCOMING));
			out = Integer.parseInt(DBAdapter.getValue(DBOpenHelper.LOCAL_OUTGOING));
			rcv = Integer.parseInt(DBAdapter.getValue(DBOpenHelper.LOCAL_RECEIVED));
			snt = Integer.parseInt(DBAdapter.getValue(DBOpenHelper.LOCAL_SENT));
			down = Long.parseLong(DBAdapter.getValue(DBOpenHelper.LOCAL_DOWNLOADED));
			up = Long.parseLong(DBAdapter.getValue(DBOpenHelper.LOCAL_UPLOADED));

			totalRV = 
					Integer.parseInt(DBAdapter.getValue(DBOpenHelper.ROAM_INCOMING)) 
					+
					Integer.parseInt(DBAdapter.getValue(DBOpenHelper.ROAM_OUTGOING));
			totalRM = 
					Integer.parseInt(DBAdapter.getValue(DBOpenHelper.ROAM_SENT)) 
					+
					Integer.parseInt(DBAdapter.getValue(DBOpenHelper.ROAM_RECEIVED));
			totalRD = 
					Long.parseLong(DBAdapter.getValue(DBOpenHelper.ROAM_DOWNLOADED)) 
					+
					Long.parseLong(DBAdapter.getValue(DBOpenHelper.ROAM_UPLOADED));
			
			totalV = in + out + totalRV; 
			totalM = rcv + snt + totalRM;
			totalD = down + up + totalRD;
			
			incoming.setText(String.valueOf(in));
			outgoing.setText(String.valueOf(out));
			received.setText(String.valueOf(rcv));
			sent.setText(String.valueOf(snt));

			downloaded.setText(
					humanReadableByteCount(down,true));
			uploaded.setText(
					humanReadableByteCount(up,true));

			voiceTotal.setText(String.valueOf(totalV));
			msgTotal.setText(String.valueOf(totalM));
			dataTotal.setText(humanReadableByteCount(totalD,true));
			
			voiceRTotal.setText(String.valueOf(totalRV));
			msgRTotal.setText(String.valueOf(totalRM));
			dataRTotal.setText(humanReadableByteCount(totalRD,true));
		}

	}

//	@Override
//	protected void onResume() {
//		super.onResume();
//		bindService(new Intent(this, ServiceHandler.class), mConnection,
//				Context.BIND_AUTO_CREATE);
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		unbindService(mConnection);
//	}
//	ServiceHandler s;
//	private ServiceConnection mConnection = new ServiceConnection() {
//
//		public void onServiceConnected(ComponentName className, IBinder binder) {
//			s = ((ServiceHandler.ServiceBinder) binder).getService();
//			Toast.makeText(ProjectAcropolisActivity.this, "Connected", Toast.LENGTH_SHORT)
//			.show();
//		}
//
//		public void onServiceDisconnected(ComponentName className) {
//			s = null;
//		}
//	};

	public String isRoaming()
	{
		String roam = "";
		TelephonyManager telephonyManager = (TelephonyManager)
				getSystemService(Context.TELEPHONY_SERVICE);
		ConnectivityManager connectivityManager = (ConnectivityManager)
				getSystemService(Context.CONNECTIVITY_SERVICE);
		int state = connectivityManager.getActiveNetworkInfo().
				getState().compareTo(NetworkInfo.State.CONNECTED);
//		Logger.Debug("connection state::(=0;connected)"+state);

		if(state==0)
		{
			for(int i=0;i<GlobalConstants.CAN_OPERATORS.length;i++)
			{
				if(telephonyManager.
						getNetworkOperatorName().
						equalsIgnoreCase(GlobalConstants.CAN_OPERATORS[i]))
				{
					roam = "NO";
					break;
				}
				else
					roam = "YES";
			}
			roam.concat(" "+state);
		}
		else
		{
			roam = "No service";
		}
		roam.concat("\nState:::"+state);
		return roam;
	}

	public static String humanReadableByteCount(long bytes, boolean si) 
	{
		int unit = si ? 1000 : 1024;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format(Locale.CANADA,"%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(R.id.menu_reset);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

//	public boolean onOptionsItemSelected(MenuItem item)
//	{
//		switch(item.getItemId())
//		{
//		case R.id.menu_reset:
//		{
//			DBAdapter.resetValues();
//		};
//		}
//		
//		return true;
//	}
	
	public static Context getContext()
	{
		return context;
	}

}
