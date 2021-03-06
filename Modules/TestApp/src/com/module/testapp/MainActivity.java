package com.module.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

//	static Context appContext=null;
//	static Intent appIntent=null;
//	
////	ListView packageList = null;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) 
//	{
//		super.onCreate(savedInstanceState);
//		
//		appContext = this.getApplicationContext();
//		appIntent = this.getIntent();
//		
//		setContentView(R.layout.activity_main);
//		ListView packageList = (ListView) this.findViewById(R.id.listPackages);
//		packageList.setBackgroundColor(Color.BLACK);
//		String[] allProvidersStr = new String[20];
//		allProvidersStr = getAllPackagesFromDevice();
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(appContext,
//				android.R.layout.simple_list_item_1,allProvidersStr);
//		packageList.setAdapter(adapter);
//	}
//
//	public String[] getAllPackagesFromDevice()
//	{
//		String[] devicePkgs = new String[20];
//		int numbOfPackages = 0;
//		List<PackageInfo> PKGInfo = 
//				(List<PackageInfo>) 
//				appContext.getPackageManager().
//				getInstalledPackages(PackageManager.GET_PROVIDERS);
//		for(PackageInfo pkgInfo : PKGInfo)
//		{
//			ProviderInfo[] providers = pkgInfo.providers;
//			if(providers!=null)
//			{
//				for(ProviderInfo providerInfo : providers)
//				{
//					if(numbOfPackages<20)
//					{
//						devicePkgs[numbOfPackages] = providerInfo.authority;
//						numbOfPackages++;
//					}
//				}
//			}
//		}
//		return devicePkgs;
//	}

	private Handler handler;
	private Handler mainHandler;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Toast.makeText(MainActivity.this, String.valueOf(msg.what),
						Toast.LENGTH_SHORT).show();
			}
		};
		new MyLooper().start();
		new Worker().start();
//		new Worker().start();
//		new Worker().start();
	}

	
	// Has Handler in own thread, will batch update the main Activity
		private class MyLooper extends Thread {
			private int count = 0;
			private int border = 100;

			@Override
			public void run() {
				// Prepare the current thread to loop for events
				Looper.prepare();
				// Create a handler bound to this thread
				handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						Log.i("HANDLER", "MyLooper called");
						Bundle data = msg.getData();
						if (data != null) {
							count++;
							if (count >= border) {
								mainHandler.sendEmptyMessage(count);
								count = 0;
							}
						}
					}
				};
				Looper.loop();
				super.run();
			}
		}

		private class Worker extends Thread {
			@Override
			public void run() {
				while (true) {
					Message message = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("text", "New data");
					message.setData(bundle);
					handler.sendMessage(message);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}
	
}
