/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * LocationProviderHub
 * com.acropolis.location.module.controller
 * LocationProviderHub.java
 * Created - 2013-08-29 3:37:46 PM	
 * Modified - 2013-08-29 3:37:46 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.location.module.controller;

import logger.Logger;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.acropolis.location.module.view.LocationModuleActivity;

/**
 * @author CPH-iMac
 *
 */
public class LocationHub extends Service
{
	public static int counter = 0;
	static Context _context = null;
	static Intent _intent = null;
	static LocationManager locationManager = null;
	static Criteria criteria = null;
	static String provider = null;
	static long timeIntervals = 10000;	//milliseconds
	static float distanceIntervals = 0;	//meters
	
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate()
	{
		Logger.Verbose("service onCreate");

		_context = this.getApplicationContext();
		setupCriteria();
		
		Logger.Verbose("fetching system service");
		locationManager = (LocationManager) _context.
				getSystemService(Context.LOCATION_SERVICE);
		provider = locationManager.getBestProvider(criteria, false);
		locationManager.requestLocationUpdates(
				provider,
				timeIntervals, 
				distanceIntervals, 
				new LocationChangeListener());
	}
	
	public int onStartCommand(Intent intent,int flags,int startId)
	{
		Logger.Verbose("service onStartCommand");
		return START_STICKY;
	}
	
	public void setupCriteria()
	{
		criteria = new Criteria();
		criteria.setCostAllowed(true);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		Logger.Verbose("Criteria set");
	}
	
	public void stop()
	{
		locationManager.removeUpdates(new LocationChangeListener());
		this.stopSelf();
		Logger.Verbose("stop");
	}
	
	public class LocationChangeListener implements LocationListener
	{

		/* (non-Javadoc)
		 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
		 */
		@Override
		public void onLocationChanged(Location location)
		{
			Logger.Debug(this.getClass()+"\nlocation contents::"+location.
					describeContents());
			
			
			Handler handler = LocationModuleActivity.screenHandler;
			
			double acc = location.getAccuracy();
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			
			GetDetails.setAccuracy(acc);
			GetDetails.setLatitude(lat);
			GetDetails.setLongitude(lng);
			
			++counter;
			Logger.Information("location updated" +
					"\nlatitude::"+ lat +
					"\nlongitude::"+ lng +
					"\naccuracy::"+ acc +
					"\ncounter:::"+ counter );
			
		}

		/* (non-Javadoc)
		 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) 
		{
			switch(status)
			{
			case LocationProvider.AVAILABLE:
				GetDetails.setLocationChipAvailable(true);
				Logger.Verbose("GPS Available");
				
			case LocationProvider.OUT_OF_SERVICE:
				GetDetails.setLocationChipAvailable(false);
				Logger.Verbose("GPS out-of-service");
				
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				GetDetails.setLocationChipAvailable(false);
				Logger.Verbose("GPS Unavailable");
			}
		}
		
		/* (non-Javadoc)
		 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
		 */
		@Override
		public void onProviderDisabled(String provider) {}

		/* (non-Javadoc)
		 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
		 */
		@Override
		public void onProviderEnabled(String provider) {}
		
	}

}