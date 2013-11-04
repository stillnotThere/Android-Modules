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

import android.os.Handler;

/**
 * @author CPH-iMac
 *
 */
public class GlobalConstants 
{
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
	
}