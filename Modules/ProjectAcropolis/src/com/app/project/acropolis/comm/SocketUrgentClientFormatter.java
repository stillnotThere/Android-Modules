/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * SocketUrgentClientFormatter
 * com.app.project.acropolis.comm
 * SocketUrgentClientFormatter.java
 * Created - 2013-11-29 3:11:18 PM	
 * Modified - 2013-11-29 3:11:18 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.comm;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.ProjectAcropolisActivity;
import com.app.project.acropolis.database.PersistedData;

/**
 * @author CPH-iMac
 *
 */
public class SocketUrgentClientFormatter implements Runnable
{

	Context _context = ProjectAcropolisActivity.getContext();
	Thread clientConnThread = null;
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat(
			GlobalConstants.TIMESTAMP_PATTERN);
	
	String formattedData = "";

	String db_ph = "";
	String device_timeStamp = "";
	String server_timeStamp = "";
	String db_roam = "";
	String db_down = "";
	String db_up= "";
	String db_rcv = "";
	String db_snt = "";
	String db_out = "";
	String db_in = "";
	
	String f_ph = "";
	String f_roam = "";
	String f_dtime = "";
	String f_stime = "";
	String f_lat = "";
	String f_lng = "";
	String f_acc = "";
	String f_down = "";
	String f_up = "";
	String f_rcv = "";
	String f_snt = "";
	String f_in = "";
	String f_out = "";
	
	public SocketUrgentClientFormatter(Context context) 
	{
		_context = context;
	}
	
	public void run()
	{
			collectData();
			openClientConnection();
			//		sendData();
	}
	
	public void collectData()
	{
		device_timeStamp = sdf.format(Calendar.getInstance().getTime());
		server_timeStamp = sdf.format(
				Calendar.getInstance(GlobalConstants.SERVER_TIMEZONE).getTime());
		
		db_ph = new PersistedData().fetchData(GlobalConstants.PersistenceConstants.PHONENUMBER);
//		db_roam = new PersistedData().fetchData(GlobalConstants.PersistenceConstants.ROAMING);
		db_down = new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_DOWNLOADED);
		db_up = new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_UPLOADED);
		db_rcv = new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_RECEIVED);
		db_snt = new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_SENT);
		db_in = new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_INCOMING);
		db_out = new PersistedData().fetchData(GlobalConstants.PersistenceConstants.LOCAL_OUTGOING);
		
		f_roam = (new GlobalConstants().checkRoaming(_context) ? "true" : "false");
		f_ph = db_ph;
		f_dtime = device_timeStamp;
		f_stime = server_timeStamp;
		f_lat = GlobalConstants.LAT;
		f_lng = GlobalConstants.LNG;
		f_acc = GlobalConstants.ACC;
		f_down = GlobalConstants.DOWNLOAD.concat(db_down);
		f_up = GlobalConstants.UPLOAD.concat(db_up);
		f_rcv = GlobalConstants.RECEIVED.concat(db_rcv);
		f_snt = GlobalConstants.SENT.concat(db_snt);
		f_in = GlobalConstants.INCOMING.concat(db_in);
		f_out = GlobalConstants.OUTGOING.concat(db_out);
		
		formattedData = GlobalConstants.START + GlobalConstants.VERSION + GlobalConstants.DELIM + 
				GlobalConstants.DATASTREAM + GlobalConstants.DELIM + f_ph + GlobalConstants.DELIM + 
				f_dtime + GlobalConstants.DELIM + f_stime  + GlobalConstants.DELIM + f_roam + GlobalConstants.DELIM + 
				f_lat + GlobalConstants.DELIM + f_lng + GlobalConstants.DELIM +	f_acc + GlobalConstants.DELIM + 
				f_down + GlobalConstants.DELIM + f_up + GlobalConstants.DELIM +
				f_rcv + GlobalConstants.DELIM + f_snt + GlobalConstants.DELIM +
				f_in + GlobalConstants.DELIM +	f_out + 
				GlobalConstants.END;
		DataTumblr.setSendUrgent(formattedData);
	}
	
	public void openClientConnection()
	{
		clientConnThread = new Thread(new SocketClientConnector.SokcetClientUrgentConnector());
		clientConnThread.start();
	}
	
//	public void sendData()
//	{
//		if(clientConnThread.isAlive() && !clientConnThread.isInterrupted()) 
//		{
//			DataTumblr.setSendClientData(formattedData);
//			clientConnector.sendMessage();
//		}
//	}
	
}
