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

/**
 * @author CPH-iMac
 *
 */
public class GlobalConstants 
{
	public static final double PlanThreshold = 0.00;//TODO

	public static Handler socketClientHandler = new Handler();
	public static Handler socketServerHandler = new Handler();

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


	public static boolean roaming = false;

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
		return isMobile;
	}
	
	public static boolean isWLANOnRoaming(Context __context)
	{
		boolean isWLANonR = false;
		Context _context = __context;
			
		
		return isWLANonR;
	}
	
	/**
	 * Checks roaming operator if applicable
	 * @return true if Roaming, false if Local
	 */
	public boolean checkRoaming(Context __context)
	{
		boolean ret = false;
		_context = __context;//ProjectAcropolisActivity.getContext();
		ConnectivityManager _cm = (ConnectivityManager) 
				_context.
				getSystemService(Context.CONNECTIVITY_SERVICE);
//		_cm.get
		NetworkInfo _ni = _cm.getActiveNetworkInfo();
		if(_ni!=null)
		{
			Logger.Debug("");
		}
		else
		{
			Logger.Debug("networkInfo null");
		}
		Logger.Debug("CheckRoaming_context::\t"+_context.toString());
		Logger.Debug(_ni.getTypeName());
		if(_ni.isConnected())
		{
			Logger.Debug("ni connected");
		}
		TelephonyManager _tm = (TelephonyManager)
				_context.getSystemService(Context.TELEPHONY_SERVICE);
		if(String.valueOf(_tm.isNetworkRoaming())!=null)
			ret = changeRoaming(_tm,_ni);
		return ret;
	}

	private boolean changeRoaming(TelephonyManager tm,NetworkInfo ni)
	{
		roaming = false;
		_context = ProjectAcropolisActivity.getContext();
		TelephonyManager _tm = tm;
		NetworkInfo _ni = ni;

		if(_ni.isRoaming() )//|| _tm.isNetworkRoaming())
		{
			try {
				compareOperator(_tm);
			} catch(NullPointerException npe) {
				npe.printStackTrace();
				Logger.Debug(npe.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			roaming = false;
		}
		Logger.Debug("Roaming:::"+roaming+
				"\nNetworkRoaming():::"+_tm.isNetworkRoaming());
		return roaming;
	}

	private boolean compareOperator(TelephonyManager tm) throws Exception
	{
		boolean checkSame = false;
		TelephonyManager _tm = tm;
		if(_tm.getNetworkOperatorName().length()>0 && _tm.getNetworkOperatorName()!=null)
			for(int i=0;i<CAN_OPERATORS.length;i++)
			{
				if(_tm.
						getNetworkOperatorName().
						equalsIgnoreCase(CAN_OPERATORS[i])
						== false)
				{
					checkSame = true;
					Handler triggerRoaming = new Handler();
					triggerRoaming.post(new TriggerEvent());
					break;
				}
				else
				{
					checkSame = false;
				}
			}

		return checkSame;
	}

	private final class TriggerEvent implements Runnable
	{
		public void run()
		{
			//TODO urgent server comm open if DATA "ON" ROAMING else open socket on WLAN
			
		}
		
	}
	
}