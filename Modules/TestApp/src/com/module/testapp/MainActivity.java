package com.module.testapp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	static Context appContext=null;
	static Intent appIntent=null;
	
//	ListView packageList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		appContext = this.getApplicationContext();
		appIntent = this.getIntent();
		
		setContentView(R.layout.activity_main);
		ListView packageList = (ListView) this.findViewById(R.id.listPackages);
		packageList.setBackgroundColor(Color.BLACK);
		String[] allProvidersStr = new String[20];
		allProvidersStr = getAllPackagesFromDevice();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(appContext,
				android.R.layout.simple_list_item_1,allProvidersStr);
		packageList.setAdapter(adapter);
	}

	public String[] getAllPackagesFromDevice()
	{
		String[] devicePkgs = new String[20];
		int numbOfPackages = 0;
		List<PackageInfo> PKGInfo = 
				(List<PackageInfo>) 
				appContext.getPackageManager().
				getInstalledPackages(PackageManager.GET_PROVIDERS);
		for(PackageInfo pkgInfo : PKGInfo)
		{
			ProviderInfo[] providers = pkgInfo.providers;
			if(providers!=null)
			{
				for(ProviderInfo providerInfo : providers)
				{
					if(numbOfPackages<20)
					{
						devicePkgs[numbOfPackages] = providerInfo.authority;
						numbOfPackages++;
					}
				}
			}
		}
		return devicePkgs;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
