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

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.telephony.TelephonyManager;

import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class GlobalConstants 
{
	public static final double PlanThreshold = 0.00;//TODO
	
	public static Handler socketClientHandler = new Handler();
	
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
	public NetworkInfo networkInfo = null;

	public static Context _context = null;
	public static Intent _intent = null;
	
	/**
	 * Checks roaming operator if applicable
	 * @return true if Roaming, false if Local
	 */
	public static boolean checkRoaming()
	{
		_context = ProjectAcropolisActivity.getContext();
		TelephonyManager _tm = (TelephonyManager)
				_context.getSystemService(Context.TELEPHONY_SERVICE);
		boolean ret = false;
		if(String.valueOf(_tm.isNetworkRoaming())!=null)
			ret = changeRoaming();
		return ret;
	}

	public static boolean changeRoaming()
	{
		_context = ProjectAcropolisActivity.getContext();
		TelephonyManager _tm = (TelephonyManager)
				_context.
				getSystemService(Context.TELEPHONY_SERVICE);
		if(_tm.getNetworkOperatorName().length()>0 ||
				_tm.getNetworkOperatorName()!=null)
		{
			for(int i=0;i<CAN_OPERATORS.length;i++)
			{
				if(!_tm.
						getNetworkOperatorName().
						equalsIgnoreCase(CAN_OPERATORS[i]))
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
		ContentValues cv = new ContentValues();
		cv.put(DBOpenHelper.ROAMING, String.valueOf(roaming));
		DBAdapter.update(cv);
		return roaming;
	}

	
}
