/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * ConnStarted
 * com.app.project.acropolis.comm
 * ConnStarted.java
 * Created - 2013-11-01 12:35:20 PM	
 * Modified - 2013-11-01 12:35:20 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.comm;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.app.project.acropolis.GlobalConstants;

/**
 * @author CPH-iMac
 *
 */
public class ClientConnector implements Runnable
{

	private Socket clientSocket = null;

	public void run()
	{
		try 
		{
			InetAddress clientInet = InetAddress.getByName(GlobalConstants.ServerIP);
			clientSocket = new Socket(clientInet,GlobalConstants.SocketClientPORT);
			clientSocket.setSoLinger
			(GlobalConstants.SocketClientLINGER,
					GlobalConstants.SocketClientLINGER_TIMEOUT);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	protected void sendMessage()
	{
		try {
			if(clientSocket.isConnected())
			{
				PrintWriter printOUT =
						new PrintWriter( new BufferedOutputStream(clientSocket.getOutputStream()));
				printOUT.println(DataTumblr.getSendClientData());
			}
			else 
			{
				run();
			}
		} catch(UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	public void closeConnection()
	{
		try {
			clientSocket.close();
		} catch(UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

}
