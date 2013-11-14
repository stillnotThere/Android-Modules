/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * ServerFormatter
 * com.app.project.acropolis.comm
 * ServerFormatter.java
 * Created - 2013-11-04 11:16:17 AM	
 * Modified - 2013-11-04 11:16:17 AM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.comm;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class SocketClientFormatter implements Runnable 
{
	SocketClientConnector clientConnector = new SocketClientConnector();
	Thread clientConnThread = null;
	DBAdapter dbAdapter = new DBAdapter();
	
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
	String f_dtime = "";
	String f_stime = "";
	String f_roam = "";
	String f_lat = "";
	String f_lng = "";
	String f_acc = "";
	String f_down = "";
	String f_up = "";
	String f_rcv = "";
	String f_snt = "";
	String f_in = "";
	String f_out = "";
	
	
	public void run()
	{
		collectData();
		opensendConnection();
//		sendData();
		startLooping();
	}
	
	public void collectData()
	{
		device_timeStamp = sdf.format(Calendar.getInstance().getTime());
		server_timeStamp = sdf.format(
				Calendar.getInstance(GlobalConstants.SERVER_TIMEZONE).getTime());
		
		db_ph = DBAdapter.getValue(DBOpenHelper.PHONENUMBER);
		db_roam = DBAdapter.getValue(DBOpenHelper.ROAMING);
		db_down = DBAdapter.getValue(DBOpenHelper.LOCAL_DOWNLOADED);
		db_up = DBAdapter.getValue(DBOpenHelper.LOCAL_UPLOADED);
		db_rcv = DBAdapter.getValue(DBOpenHelper.LOCAL_RECEIVED);
		db_snt = DBAdapter.getValue(DBOpenHelper.LOCAL_SENT);
		db_in = DBAdapter.getValue(DBOpenHelper.LOCAL_INCOMING);
		db_out = DBAdapter.getValue(DBOpenHelper.LOCAL_OUTGOING);
		
		f_ph = db_ph;
		f_dtime = device_timeStamp;
		f_stime = server_timeStamp;
		f_roam = db_roam;
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
	}
	
	public void opensendConnection()
	{
		DataTumblr.setSendClientData(formattedData);
		clientConnThread = new Thread(new SocketClientConnector.Connector());
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
	
	public void closeConnection()
	{
		SocketClientConnector.closeConnection();
	}
	
	public void startLooping()
	{
		GlobalConstants.socketClientHandler.postDelayed(this, 2*60*60*1000);//2hrs
	}
	
	
}
