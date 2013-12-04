package com.app.project.acropolis;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.app.project.acropolis.controller.ServiceHandler;
import com.app.project.acropolis.database.PersistedData;

public class ProjectAcropolisActivity extends Activity {

	public static Context context = null; 
	final String roamingText = "Roaming ::: ";

	/*Font substitutions*/
	TextView tvincoming;
	TextView tvoutgoing;
	TextView tvvoiceTotal;
	TextView tvroamvoiceTotal;
	
	TextView tvrcv;
	TextView tvsnt;
	TextView tvmsgTotal;
	TextView tvroammsgTotal;
	
	TextView tvdown;
	TextView tvup;
	TextView tvdataTotal;
	TextView tvroamdataTotal;
	
	TextView tvlocalcharge;
	TextView tvroamcharge;
	TextView localCharge;
	TextView roamCharge;
	
	/*DB Values place-holder*/
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
		init();
		context = getApplicationContext();
		activity = this;

		setContentView(R.layout.activity_main);
		Intent serviceIntent = new Intent(this,ServiceHandler.class);
		this.startService(serviceIntent);

		tvincoming = (TextView) findViewById(R.id.txtIn);
		tvoutgoing = (TextView) findViewById(R.id.txtOut);
		tvvoiceTotal = (TextView) findViewById(R.id.txtVTotal);
		tvroamvoiceTotal = (TextView) findViewById(R.id.txtRVoice);
		tvrcv = (TextView) findViewById(R.id.txtRcv);
		tvsnt = (TextView) findViewById(R.id.txtSnt);
		tvmsgTotal = (TextView) findViewById(R.id.txtMTotal);
		tvroammsgTotal = (TextView) findViewById(R.id.txtRMTotal);
		tvdown = (TextView) findViewById(R.id.txtDown);
		tvup = (TextView) findViewById(R.id.txtUpload);
		tvdataTotal = (TextView) findViewById(R.id.txtDTotal);
		tvroamdataTotal = (TextView) findViewById(R.id.txtRDTotal);

		tvlocalcharge = (TextView) findViewById(R.id.chargePlan);
		tvroamcharge = (TextView) findViewById(R.id.chargeRoaming);
		localCharge = (TextView) findViewById(R.id.valueChargePlan);
		roamCharge = (TextView) findViewById(R.id.valueChargeRoaming);
		
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
		
		updateScreen();
		screenHandler = new Handler();
		screenHandler.post(refreshScreen);
		
	}

	/**
	 * Set "Vera.ttf" font
	 */
	private void setupApplicationFont()
	{
		Typeface tf =
				Typeface.createFromAsset(getAssets(),
						GlobalConstants.FontName);
		
		tvlocalcharge.setTypeface(tf);
		tvroamcharge.setTypeface(tf);
		localCharge.setTypeface(tf,GlobalConstants.BOLD_ITALIC);
		roamCharge.setTypeface(tf,GlobalConstants.BOLD_ITALIC);
		
		//strict font substitutions
		tvincoming.setTypeface(tf);
		tvoutgoing.setTypeface(tf);
		tvvoiceTotal.setTypeface(tf,GlobalConstants.BOLD);
		tvroamvoiceTotal.setTypeface(tf,GlobalConstants.BOLD);
		tvrcv.setTypeface(tf);
		tvsnt.setTypeface(tf);
		tvmsgTotal.setTypeface(tf,GlobalConstants.BOLD);
		tvroammsgTotal.setTypeface(tf,GlobalConstants.BOLD);
		tvdown.setTypeface(tf);
		tvup.setTypeface(tf);
		tvdataTotal.setTypeface(tf,GlobalConstants.BOLD);
		tvroamdataTotal.setTypeface(tf,GlobalConstants.BOLD);
		
		roaming.setTypeface(tf,GlobalConstants.BOLD_ITALIC);
		incoming.setTypeface(tf);
		outgoing.setTypeface(tf);
		received.setTypeface(tf);
		sent.setTypeface(tf);
		downloaded.setTypeface(tf);
		uploaded.setTypeface(tf);
		
		voiceTotal.setTypeface(tf);//,GlobalConstants.BOLD);
		msgTotal.setTypeface(tf);//,GlobalConstants.BOLD);
		dataTotal.setTypeface(tf);//,GlobalConstants.BOLD);
		
		voiceRTotal.setTypeface(tf);//,GlobalConstants.ITALIC);
		msgRTotal.setTypeface(tf);//,GlobalConstants.ITALIC);
		dataRTotal.setTypeface(tf);//,GlobalConstants.ITALIC);
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
	
	public boolean deleteSQLDB()
	{
		boolean deleted = false;
		String sqlDBNAME = "PROJECTACROPOLIS_DB";
		File file = getDatabasePath(sqlDBNAME);
		if(file.exists())
			deleted = file.delete();
		
		return deleted;
	}
	
	private final void init()
	{
		GlobalConstants.setGlobalContext(this);
		Logger.Debug("init() Context::::\t"+getApplicationContext().toString());
		deleteSQLDB();
	}

	public static Activity getActivity()
	{
		return activity;
	}
	
	public static String toastMsg = "";
	public static void postToast(String msg)
	{
		toastMsg = msg;
		getActivity().runOnUiThread(new Runnable() 
		{
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

	private void calculateCharges(long[] local,long[] roam)
	{
		long localVoice = local[0];
		long localMessage = local[1];
		long localData = local[2];
		
		long roamVoice = roam[0];
		long roamMessage = roam[1];
		long roamData = roam[2];
		
		double localVoiceCharge = 0;
		double localMsgCharge = 0;
		double localDataCharge = 0;
		double roamVoiceCharge = 0;
		double roamMsgCharge = 0;
		double roamDataCharge = 0;
		
		double localCost = 0;
		double roamCost = 0;
		
		DecimalFormat appDecimalFormat = new DecimalFormat("##.00");
		
		localVoiceCharge = localVoice * GlobalConstants.PlanChargeContants.LocalVoiceRate;
		localMsgCharge = localMessage * GlobalConstants.PlanChargeContants.LocalMessageRate;
		localDataCharge = formatData(localData) * GlobalConstants.PlanChargeContants.LocalDataRate;
		
		roamVoiceCharge = roamVoice * GlobalConstants.PlanChargeContants.RoamingVoiceRate;
		roamMsgCharge = roamMessage * GlobalConstants.PlanChargeContants.RoamingMessageRate;
		roamDataCharge = formatData(roamData) * GlobalConstants.PlanChargeContants.RoamingDataRate;
		Logger.Debug("localVoiceCharge:::\t"+appDecimalFormat.format(localVoiceCharge));
		Logger.Debug("localMsgCharge:::\t"+appDecimalFormat.format(localMsgCharge));
		Logger.Debug("localDataCharge:::\t"+appDecimalFormat.format(localDataCharge));
		
		
		localCost = localVoiceCharge + localMsgCharge + localDataCharge;
		roamCost = roamVoiceCharge + roamMsgCharge + roamDataCharge;
		Logger.Debug("localCost:::\t"+appDecimalFormat.format(localCost));
		
		localCharge.setText("$"+appDecimalFormat.format(localCost));
		roamCharge.setText("$"+appDecimalFormat.format(roamCost));
	}
	
	private double formatData(double bytes)
	{
		double formatted = 0;
		long unit = 1024*1024;
		formatted = Double.parseDouble(new DecimalFormat("#.00").format(bytes/unit));
		Logger.Debug(this.getClass().getSimpleName()+"\t\t"+formatted);
		return formatted;
	}
	
	public void updateScreen()
	{
		synchronized(this)
		{
//			new DBAdapter().putBlank();
			int in = 0;
			int out = 0;
			int rcv = 0;
			int snt = 0;
			long down =0;
			long up = 0;

			int totalV = 0;
			int totalM = 0;
			long totalD = 0;
			
			long totalLV = 0;
			long totalLM = 0;
			long totalLD = 0;
			
			int totalRV = 0;
			int totalRM = 0;
			long totalRD = 0;
			
			long[] planTotal = new long[3];
			long[] roamTotal = new long[3];
			
			roaming.setText("Roaming ::: " + isRoaming());
			roaming.setTypeface(Typeface.createFromAsset(getAssets(), GlobalConstants.FontName),GlobalConstants.BOLD | GlobalConstants.ITALIC);
			in = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_INCOMING));
			out = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_OUTGOING));
			rcv = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_RECEIVED));
			snt = Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_SENT));
			down = Long.parseLong(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_DOWNLOADED));
			up = Long.parseLong(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_UPLOADED));

			totalRV = 
					Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_INCOMING)) 
					+
					Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_OUTGOING));
			totalRM = 
					Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_SENT)) 
					+
					Integer.parseInt(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_RECEIVED));
			totalRD = 
					Long.parseLong(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_DOWNLOADED)) 
					+
					Long.parseLong(new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAM_UPLOADED));
			
			totalV = in + out + totalRV; 
			totalM = rcv + snt + totalRM;
			totalD = down + up + totalRD;
			
			/*Pass total arrays for cost calculation*/
			totalLV = in + out; 
			totalLM = rcv + snt;
			totalLD = down + up;
			
			planTotal[0] = totalLV;
			planTotal[1] = totalLM;
			planTotal[2] = totalLD;
			
			roamTotal[0] = totalRV;
			roamTotal[1] = totalRM;
			roamTotal[2] = totalRD;
			
			calculateCharges(planTotal,roamTotal);
			
			/*inserting monitored data in TextView*/
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
			
			/* "Vera.ttf" is set throughout */
			setupApplicationFont();
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
		boolean roaming = false;
		ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		
		if(ni.isRoaming())
		{
			if(tm.getNetworkOperatorName() != null)
			{
				for(int i=0;i<GlobalConstants.CAN_OPERATORS.length;i++)
				{
					if(tm.getNetworkOperator().equalsIgnoreCase(GlobalConstants.CAN_OPERATORS[i]))
					{
						roaming = true;
						break;
					}
					else
					{
						roaming = false;
					}
				}
			}
			else
			{
				roaming = true;
			}
		}
		else
		{
			roaming = false;
		}
		return ( roaming ? "Yes" : "No");
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
//		menu.add(R.menu.);
//		menu.add(R.id.menu_reset);
		getMenuInflater().inflate(R.menu.main, menu);
//		return super.onCreateOptionsMenu(menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.menu_reset:
		{
			new PersistedData().resetData();
			updateScreen();
			return true;
		}
		default :
		{
			return super.onOptionsItemSelected(item);			
		}
		}
	}
	
	public static Context getContext()
	{
		return context;
	}

}
