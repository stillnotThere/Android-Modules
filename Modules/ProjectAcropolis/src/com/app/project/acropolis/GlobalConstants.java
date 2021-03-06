/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * GlobalConstants
 * com.app.project.acropolis.comm
 * GlobalConstants.java
 * Created - 2013-11-01 1:32:06 PM	
 * Modified - 2013-11-01 1:32:06 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis;

import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.telephony.TelephonyManager;

import com.app.project.acropolis.comm.DataTumblr;
import com.app.project.acropolis.comm.SocketClientFormatter;
import com.app.project.acropolis.comm.SocketErrorClientFormatter;

/**
 * @author CPH-iMac
 *
 */
public class GlobalConstants 
{
	public static String FontName = "font/Vera.ttf";
	public static int BOLD = 1;
	public static int ITALIC = 2;
	public static int BOLD_ITALIC = 1 | 2;

	public static int counter=0;
	public static long lastCallTime = 0;
	
	public static class PlanChargeContants
	{
		/*Local Rates*/
		public final static double LocalVoiceRate = 0.10;		//incoming free(general)
		public final static double LocalMessageRate = 0.10;	//some plan 250 free after 10cents/msg
		public final static double LocalDataRate = 0.06;		//500MB free after 6cent/MB

		/*Roaming Rates*/
		public final static double RoamingVoiceRate = 2.00;
		public final static double RoamingMessageRate = 0.60;
		public final static double RoamingDataRate = 5.00;
	}

	public static class LaunchDialogBox
	{
		public static final String ApplicationPlanDialogMsg = "";
		public static final String ApplicationContactCPH = 
				"Application is designed to work seamlessly while integrating " +
						"your active mobile plan. To partake in that feature, contact us \n" +
						"http://www.cphinc.ca";
	}

	public static class PersistenceConstants
	{
		//		public static final String _DEFAULT_VALUE = "check again later...";
		public static final String _DEFAULT_VALUE = "0";
		public static final String _RESET_VALUES = "0";
		/**
		 * Columns KEYS
		 */
		public static final String PHONENUMBER = "PHONE_NUM";
		public static final String ROAMING = "ROAMING";
		public static final String LOCAL_INCOMING = "LOCAL_INCOMING";
		public static final String LOCAL_OUTGOING = "LOCAL_OUTGOING";
		public static final String LOCAL_RECEIVED = "LOCAL_RECEIVED";
		public static final String LOCAL_SENT = "LOCAL_SENT";
		public static final String LOCAL_DOWNLOADED = "LOCAL_DOWNLOADED";
		public static final String LOCAL_UPLOADED = "LOCAL_UPLOADED";
		public static final String ROAM_INCOMING = "ROAMING_INCOMING";
		public static final String ROAM_OUTGOING = "ROAMING_OUTGOING";
		public static final String ROAM_RECEIVED = "ROAMING_RECEIVED";
		public static final String ROAM_SENT = "ROAMING_SENT";
		public static final String ROAM_DOWNLOADED = "ROAMING_DOWNLOADED";
		public static final String ROAM_UPLOADED = "ROAMING_UPLOADED";
		public static final String BILL_DATE = "BILL_DATE";
		public static final String PLAN_LOCAL_INCOMING = "PLAN_LOCAL_INCOMING";
		public static final String PLAN_LOCAL_OUTGOING = "PLAN_LOCAL_OUTGOING";
		public static final String PLAN_LOCAL_RECEIVED = "PLAN_LOCAL_RECEIVED";
		public static final String PLAN_LOCAL_SENT = "PLAN_LOCAL_SENT";
		public static final String PLAN_LOCAL_DOWNLOADED = "PLAN_LOCAL_DOWNLOADED";
		public static final String PLAN_LOCAL_UPLOADED = "PLAN_LOCAL_UPLOADED";
		public static final String PLAN_ROAM_INCOMING = "PLAN_ROAMING_INCOMING";
		public static final String PLAN_ROAM_OUTGOING = "PLAN_ROAMING_OUTGOING";
		public static final String PLAN_ROAM_RECEIVED = "PLAN_ROAMING_RECEIVED";
		public static final String PLAN_ROAM_SENT = "PLAN_ROAMING_SENT";
		public static final String PLAN_ROAM_DOWNLOADED = "PLAN_ROAMING_DOWNLOADED";
		public static final String PLAN_ROAM_UPLOADED = "PLAN_ROAMING_UPLOADED";
	}

	public static final double PlanThreshold = 0.00;//TODO

	public static Handler socketClientHandler = new Handler();
	public static Handler socketServerHandler = new Handler();
	public static Handler socketErrorClientHandler = new Handler();

	//	public static final String ERRORSTREAM = "";
	//	public static final String DATASTREAM = "";

	public final static String[] CAN_OPERATORS = {"Rogers","TELUS","Bell"};
	public final static String ServerIP = "99.229.28.101";
	public final static int SocketClientPORT = 44444;

	public final static int SocketServerPort = 30000;

	public final static boolean SocketClientLINGER = true;
	public final static int SocketClientLINGER_TIMEOUT = 10;//10 seconds

	/**
	 * Timestamp format yyyyMMddHHmm(24hr)
	 */
	public final static String TIMESTAMP_PATTERN = "yyyyMMddHHmm";
	public final static String CPH_TIMEZONE = "GMT-5:00";
	public final static String CPH_PDT_TIMEZONE = "GMT-4:00";
	public final static TimeZone SERVER_TIMEZONE = 
			TimeZone.getTimeZone(CPH_TIMEZONE);


	public static boolean wasRoaming = false;
//	private Handler triggerHandler = new Handler();
	private final static int ROAMING_EXCEPTION = 101; //SOS
	private final static int ROAMING_CHANGE = 202;
	public static final int EXCEPTION_HANDLER = 911; 
	private String errorMsg = "";

	/**
	 * Server msg parsers
	 * 
	 * Format 
	 * 
	 * START+ DELIM+DATASTREAM+ DELIM+<PH NO>+ DELIM+<START>+ DELIM+<END>+ 
	 * DELIM+<ROAM>+ DELIM+LAT+ DELIM+LNG+ 
	 * DELIM+ DOWNLOAD+ DELIM+UPLOAD+
	 * DELIM+ RECEIVED+ DELIM+SENT+
	 * DELIM+ INCOMING+ DELIM+OUTGOING+
	 * END
	 */
	public final static String START = "#";
	public final static String END = "##";
	public final static String DELIM= "|";
	public final static String VERSION = "1.0.1";
	public final static String DATASTREAM = "DataStream";
	public final static String ERRORSTREAM = "ErrorStream";
	public final static String LAT = "67.43125";
	public final static String LNG = "-45.123456";
	public final static String ACC = "1234.1234";
	public final static String DOWNLOAD = "Down:";
	public final static String UPLOAD = "Up:";
	public final static String RECEIVED = "Received Msgs:";
	public final static String SENT = "Sent Msgs:";
	public final static String INCOMING = "Incoming Duration:";
	public final static String OUTGOING = "Outgoing Duration:";

	public ConnectivityManager connectivityManager = null;
	public static NetworkInfo networkInfo = null;
	public final static String MOBILE_NETWORK = "mobile";
	public final static String WIFI_NETWORK = "WIFI";

	public static Context globalContext = null;
	public static Context _context = null;
	public static Intent _intent = null;

	/**
	 * @return the globalContext
	 */
	public static final Context getGlobalContext()
	{
		return globalContext;
	}

	public static final TelephonyManager getGlobalTM()
	{
		return (TelephonyManager) getGlobalContext().getSystemService(Context.TELEPHONY_SERVICE);
	}

	/**
	 * @param context the globalContext to set
	 */
	public static final void setGlobalContext(Context context) 
	{
		GlobalConstants.globalContext = context;
	}

	public boolean isMobileNetworkType(Context __context)
	{
		boolean isMobile = false;
		try{
			Context _context = __context;//ProjectAcropolisActivity.getContext();
			ConnectivityManager _cm = (ConnectivityManager) 
					_context.getSystemService(Context.CONNECTIVITY_SERVICE);
			String type = "";
			networkInfo = _cm.getActiveNetworkInfo();
			if(networkInfo.
					getTypeName().
					equalsIgnoreCase(GlobalConstants.MOBILE_NETWORK))
			{
				isMobile = true;
			}
			else 
			{
				isMobile = false;
			}
			Logger.Debug("Network:::"+type+
					"\n\t\tisRoaming::::"+networkInfo.isRoaming());
		} catch(Exception e) {
			e.printStackTrace();
			errorMsg = e.getLocalizedMessage();
			DataTumblr.setErrorMsg(errorMsg);
//			triggerHandler.post(new TriggerEvent(ROAMING_EXCEPTION));
			isMobile = true;
		}
		return isMobile;
	}

	//	public static boolean isWLANOnRoaming(Context __context)
	//	{
	//		boolean isWLANonR = false;
	//		Context _context = __context;
	//			
	//		
	//		return isWLANonR;
	//	}

	
	public boolean isRoaming()
	{
		boolean roam = false;
	
		try {
		
		ConnectivityManager cm = 
				(ConnectivityManager) 
				ProjectAcropolisActivity.
				getContext().
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		if(ni!=null)
		{
			roam = ni.isRoaming();
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			roam = true;
		}
		return roam;
	}
	
	
	/**
	 * Checks roaming operator if applicable
	 * @return true if Roaming, false if Local
	 */
	public boolean checkRoaming(Context __context)
	{
		boolean roaming = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) __context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			TelephonyManager tm = (TelephonyManager) __context.getSystemService(Context.TELEPHONY_SERVICE);
			Logger.Debug(this.getClass().getSimpleName());
			if(ni.isRoaming() && ni.getTypeName().equalsIgnoreCase(MOBILE_NETWORK))
			{
				Logger.Debug("NetworkType:::"+ni.getTypeName());
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
			Logger.Debug("roaming:::"+roaming);
		} catch (NullPointerException e1) {
			e1.getLocalizedMessage();
			e1.printStackTrace();

		} catch (Exception e2) {
			errorMsg = e2.getLocalizedMessage();
			DataTumblr.setErrorMsg(errorMsg);
			e2.printStackTrace();
//			triggerHandler.post(new TriggerEvent(ROAMING_EXCEPTION));
		}
//		if(roaming)
//		{
//			triggerHandler.post(new TriggerEvent(ROAMING_CHANGE));
//		}
//		else
//		{
//			if(wasRoaming)
//			{
//				triggerHandler.post(new TriggerEvent(ROAMING_CHANGE));
//			}
//		}
		wasRoaming = roaming;
		return roaming;
	}

	/**
	 * TODO  == urgent server comm open if ON ROAMING and DATA off then send ASAP on WLAN
	 */
	public static final class TriggerEvent implements Runnable
	{
		boolean roamingException = false;
		boolean roamingON_OFF = false;
		boolean appException = false;

		public TriggerEvent(int finger) 
		{
			switch (finger) 
			{
			case ROAMING_CHANGE:
				roamingON_OFF = true;
			case ROAMING_EXCEPTION:
				roamingException = true;
			case EXCEPTION_HANDLER:
				appException = true;
			}
		}

		public void run()
		{
			if(roamingException || appException)
			{
				Handler errorMsg = new Handler();
				errorMsg.post(
						new SocketErrorClientFormatter(
								ProjectAcropolisActivity.getContext(),true));
			}
			if(roamingON_OFF)
			{
				Handler roamingChangeH = new Handler();
				roamingChangeH.post(
						new SocketClientFormatter(
								ProjectAcropolisActivity.getContext()));
			}
		}

	}

}