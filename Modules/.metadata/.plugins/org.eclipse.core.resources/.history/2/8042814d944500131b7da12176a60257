/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * ServerConnector
 * com.app.project.acropolis.comm
 * ServerConnector.java
 * Created - 2013-11-01 1:34:29 PM	
 * Modified - 2013-11-01 1:34:29 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.app.project.acropolis.GlobalConstants;

/**
 * @author CPH-iMac
 *
 */
public class ServerConnector implements Runnable 
{
	private ServerSocket serverSocket = null;
	private Socket listenSocket = null;

	public void run()
	{
		try {
			serverSocket = new ServerSocket(GlobalConstants.SocketServerPort);
		} catch(UnknownHostException e1) {
			e1.printStackTrace();
		} catch(IOException e2) {
			e2.printStackTrace();
		}
		while(!Thread.currentThread().isInterrupted())
		{
			try {
				listenSocket = serverSocket.accept();
				ListenServer listen = new ListenServer(listenSocket);
				new Thread(listen).start();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}

	}

	private class ListenServer implements Runnable 
	{
		Socket activeSocket = null;
		BufferedReader serverMsgStream = null;

		public ListenServer(Socket _socket) 
		{
			this.activeSocket = _socket;
			try {
				serverMsgStream = new BufferedReader(
						new InputStreamReader(activeSocket.getInputStream()));
			} catch(IOException e1) {
				e1.printStackTrace();
			}
		}

		public void run()
		{
			String readServerMsg = ""; 
			while(!Thread.currentThread().isInterrupted())
			{
				try {
					readServerMsg = serverMsgStream.readLine();
					DataTumblr.setReceivedServerData(readServerMsg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
