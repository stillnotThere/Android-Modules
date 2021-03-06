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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.ClosedByInterruptException;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.Logger;

/**
 * @author CPH-iMac
 *
 */
public class SocketClientConnector
{
	public static Socket clientSocket = null;
	public static Socket clientUrgentSocket = null;

	public static class Connector implements Runnable 
	{
		public void run()
		{
			try 
			{
				if(!WLANStuff.onCPHWLAN())
				{
					InetAddress clientInet = InetAddress.getByName(GlobalConstants.ServerIP);
					clientSocket = new Socket(clientInet,GlobalConstants.SocketClientPORT);
					sendMessage();
					closeConnection();
				}
			} catch (ConnectException e3) {
				e3.printStackTrace();


			}catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void closeConnection()
	{
		try {
			clientSocket.close();
			Logger.Debug("connection closed");
			if(Thread.currentThread().isAlive())
				Thread.currentThread().interrupt();
		} catch(UnknownHostException e1) {
			e1.printStackTrace();
		} catch(ClosedByInterruptException e3) {
			e3.printStackTrace();
			Logger.Debug("ClientConnection closed\n");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	public static void sendMessage()
	{
		try {
			Logger.Debug("$$$$   writing on server    $$$$");
			PrintWriter printOUT =
					new PrintWriter(
							new BufferedWriter(
									new OutputStreamWriter(
											clientSocket.getOutputStream())),
											true);
			
			
			printOUT.println(DataTumblr.getSendClientData());
		} catch(UnknownHostException e1) {
			e1.printStackTrace();
		} catch(ClosedByInterruptException e3) {
			e3.printStackTrace();
			Logger.Debug("ClientConnection closed\n");
		} catch (IOException e2) {
			e2.printStackTrace();
		} 
	}

	public static class SokcetClientUrgentConnector implements Runnable
	{
		public void run()
		{
			try	{
				InetAddress urgentInet = InetAddress.getByName(GlobalConstants.ServerIP);
				clientUrgentSocket = new Socket(urgentInet,GlobalConstants.SocketClientPORT);
				clientUrgentSocket.setKeepAlive(true);
				sendUrgentMsg();
				closeUrgentConnection();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			} finally {
				try {
					clientUrgentSocket.close();
				} catch (UnknownHostException k1) {
					k1.printStackTrace();
				} catch(IOException k2) {
					k2.printStackTrace();
				}
			}
		}

		public void sendUrgentMsg() throws UnknownHostException,IOException
		{
			PrintWriter printWriter = new PrintWriter(
					new BufferedWriter( 
							new OutputStreamWriter( 
									clientUrgentSocket.getOutputStream()
									)
							)
					,true);
			printWriter.println(DataTumblr.getSendUrgent());
		}

		public void closeUrgentConnection()
		{
			try {
				clientUrgentSocket.close();
			} catch (UnknownHostException e0) {
				e0.printStackTrace();
				Logger.Debug(e0.getLocalizedMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
				Logger.Debug(e1.getLocalizedMessage());
			}
		}

	}

}
