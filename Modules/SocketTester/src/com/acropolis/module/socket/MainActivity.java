package com.acropolis.module.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	public static Context context = null;
	
	final static String TAG = "SocketClient@@@@";

	public Handler updateScreen = null;
	public static String msg = "";
	public static EditText txt = null;
	public static TextView serverTxt = null;
	public static Button btn = null;
	public static Socket socket = null;
	public final static String domain = "http://cphcloud.biz";
	public final static String ip = "99.229.28.101";
	public final static int port = 44444;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this.getApplicationContext();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		updateScreen = new Handler();
//		ServerSock serverSock = new ServerSock();
//		new Thread(serverSock).start();
		btn = (Button) findViewById(R.id.button1);
		txt = (EditText) findViewById(R.id.editText1);
		serverTxt = (TextView) findViewById(R.id.textView1);
		msg = txt.getText().toString();
		new WLANStuff();
	}

	public static void setMsg(String msg)
	{
		serverTxt.setText(msg);
	}
	
	//Button#onClick event
	public void sendMsg(View view)
	{
		openConnection();
		msg = txt.getText().toString(); 
		try {
			PrintWriter print = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			print.println(msg);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
	}

	public static void openConnection()
	{
		try
		{
			socket = new Socket(ip,port);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static void closeConnection()
	{
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Context getContext()
	{
		return context;
	}
	
	public class ServerSock implements Runnable
	{
		ServerSocket serverSocket = null;
		Socket sock = null;

		final int serverPort= 2345;

		public void run()
		{
			try {
				serverSocket = new ServerSocket(serverPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(!Thread.currentThread().isInterrupted())
			{
				try {
					sock = serverSocket.accept();
					CommSock comm = new CommSock(sock);
					new Thread(comm).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public class CommSock implements Runnable 
	{
		private Socket sock = null;
		private BufferedReader in = null;

		public CommSock(Socket socket)
		{
			sock = socket;
			try {
				in = new BufferedReader
						(new InputStreamReader(sock.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run()
		{
			while(!Thread.currentThread().isInterrupted())
			{
				String read;
				try {
					read = in.readLine();
					updateScreen.post(new UpdateScreen(read));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class UpdateScreen implements Runnable
	{
		private String msg = null;

		public UpdateScreen(String str)
		{
			msg = str;
		}

		public void run()
		{
			serverTxt.setText("ServerMsg:::"+msg+"\n");
		}
	}

}
