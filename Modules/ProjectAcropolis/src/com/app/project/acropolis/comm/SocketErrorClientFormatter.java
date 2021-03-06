/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * SocketErrorClientFormatter
 * com.app.project.acropolis.comm
 * SocketErrorClientFormatter.java
 * Created - 2013-11-29 12:29:58 PM	
 * Modified - 2013-11-29 12:29:58 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.comm;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.Logger;
import com.app.project.acropolis.database.PersistedData;


/**
 * @author CPH-iMac
 *
 */
public class SocketErrorClientFormatter implements Runnable
{
	SocketClientConnector clientConnector = new SocketClientConnector();
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat(
			GlobalConstants.TIMESTAMP_PATTERN);
	
	Context context = null;
	String formattedErrorStream = "";
	boolean errorStream = false;
	String e_f_ph = "";
	String e_f_roam = "";
	String e_f_dtime = "";
	String e_f_stime = "";
	String e_f_lat = "";
	String e_f_lng = "";
	String e_f_acc = "";
	String e_f_down = "";
	String e_f_up = "";
	String e_f_rcv = "";
	String e_f_snt = "";
	String e_f_in = "";
	String e_f_out = "";
	
	String errorMsg = "";
	
	public SocketErrorClientFormatter(Context _context,boolean error)
	{
		context = _context;
		errorStream = error;
	}
	
	public void run()
	{
		if(errorStream)
		{
			formatError();
			openAndSend();
			close();
		}
		else
		{
			Logger.Debug("no error...misfire");
		}
		
	}
	
	public void formatError()
	{
		errorMsg = DataTumblr.getErrorMsg();
		e_f_ph = new PersistedData().fetchData( GlobalConstants.PersistenceConstants.PHONENUMBER);
		e_f_roam = String.valueOf(new GlobalConstants().checkRoaming(context));
		e_f_dtime = sdf.format(Calendar.getInstance().getTime());
		e_f_stime = sdf.format(Calendar.getInstance(GlobalConstants.SERVER_TIMEZONE).getTime());
		e_f_lat = GlobalConstants.LAT;
		e_f_lng = GlobalConstants.LNG;
		e_f_acc = GlobalConstants.ACC;
		e_f_down = new PersistedData().fetchData( GlobalConstants.PersistenceConstants.LOCAL_DOWNLOADED);
		e_f_up = new PersistedData().fetchData( GlobalConstants.PersistenceConstants.LOCAL_UPLOADED);
		e_f_rcv = new PersistedData().fetchData( GlobalConstants.PersistenceConstants.LOCAL_RECEIVED);
		e_f_snt = new PersistedData().fetchData( GlobalConstants.PersistenceConstants.LOCAL_SENT);
		e_f_in = new PersistedData().fetchData( GlobalConstants.PersistenceConstants.LOCAL_INCOMING);
		e_f_out = new PersistedData().fetchData( GlobalConstants.PersistenceConstants.LOCAL_OUTGOING);
		
		formattedErrorStream = GlobalConstants.START +
				GlobalConstants.VERSION + GlobalConstants.DELIM + 
				GlobalConstants.ERRORSTREAM + GlobalConstants.DELIM +
				e_f_ph + GlobalConstants.DELIM + 
				e_f_dtime + GlobalConstants.DELIM +
				e_f_stime  + GlobalConstants.DELIM +
				errorMsg + GlobalConstants.DELIM +
				e_f_roam + GlobalConstants.DELIM + 
				e_f_lat + GlobalConstants.DELIM + 
				e_f_lng + GlobalConstants.DELIM +
				e_f_acc + GlobalConstants.DELIM + 
				e_f_down + GlobalConstants.DELIM +
				e_f_up + GlobalConstants.DELIM +
				e_f_rcv + GlobalConstants.DELIM +
				e_f_snt + GlobalConstants.DELIM +
				e_f_in + GlobalConstants.DELIM + 
				e_f_out +
				GlobalConstants.END;
		
		DataTumblr.setErrorStream(formattedErrorStream);
	}
	
	public void openAndSend()
	{
		Handler errorConnHandler = new Handler();
		errorConnHandler.post(new SocketErrorConnector.Connector());
	}
	
	public void close()
	{
		SocketErrorConnector.closeConnection();
	}
	
}