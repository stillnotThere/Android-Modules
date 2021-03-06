/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * SocketErrorConnector
 * com.app.project.acropolis.comm
 * SocketErrorConnector.java
 * Created - 2013-11-29 1:06:00 PM	
 * Modified - 2013-11-29 1:06:00 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.comm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.Logger;

/**
 * @author CPH-iMac
 *
 */
public class SocketErrorConnector 
{

	static Socket errorSocket = new Socket();

	public static class Connector implements Runnable
	{
		public void run()
		{
			try {
				InetAddress serverInet = InetAddress.getByName(GlobalConstants.ServerIP);
				errorSocket = new Socket(serverInet,GlobalConstants.SocketClientPORT);
				sendErrorStream();
				closeConnection();
			} catch (ConnectException e0) {
				e0.printStackTrace();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void sendErrorStream()
	{
		try {
			OutputStream out = errorSocket.getOutputStream();
			OutputStreamWriter outWriter = new OutputStreamWriter(out);
			BufferedWriter bufferedWriter = new BufferedWriter(outWriter);
			PrintWriter printer = new PrintWriter(bufferedWriter,true);
			printer.println(DataTumblr.getErrorStream());
		} catch (UnknownHostException e0) {
			e0.printStackTrace();
			Logger.Debug(e0.getLocalizedMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
			Logger.Debug(e1.getLocalizedMessage());
		}
	}

	public static void closeConnection()
	{
		try {
			errorSocket.close();
		} catch(Exception e) {
			Logger.Debug(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}