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
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.acropolis.location.module.view.LocationModuleActivity;

/**
 * @author CPH-iMac
 *
 */
public class LocationHub 
{
	public static int counter = 0;
	static Context _context = null;
	static Intent _intent = null;
	static LocationManager locationManager = null;
	static Criteria criteria = null;
	static String provider = null;
	public static LocationChangeListener locationListener = null;
	static long timeIntervals = 0;	//milliseconds
	static float distanceIntervals = 0;

	public static void setupCriteria()
	{
		criteria = new Criteria();
		criteria.isCostAllowed();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
//		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
	}
	
	public static void setupManager()
	{
		locationManager = (LocationManager) _context.
				getSystemService(Context.LOCATION_SERVICE);
		provider = locationManager.getBestProvider(criteria, false);
		locationListener = new LocationChangeListener();
		locationManager.requestLocationUpdates(
				provider,
				timeIntervals, 
				distanceIntervals, 
				locationListener);
	}
	
	public static void start()
	{
		_context = LocationModuleActivity.getAppContext();
		_intent = LocationModuleActivity.getAppIntent();
		setupCriteria();
		setupManager();
	}
	
	public static void stop()
	{
		locationManager.removeUpdates(locationListener);
	}
	
	public static class LocationChangeListener implements LocationListener
	{

		/* (non-Javadoc)
		 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
		 */
		@Override
		public void onLocationChanged(Location location)
		{
			Logger.Debug(this.getClass()+"\nlocation contents::"+location.describeContents());
			
			GetDetails.setAccuracy(location.getAccuracy());
			GetDetails.setLatitude(location.getLatitude());
			GetDetails.setLongitude(location.getLongitude());
			
			++counter;
			Logger.Information("location updated" +
					"\nlatitude::"+location.getLatitude() +
					"\nlongitude::"+location.getLongitude() +
					"\naccuracy::"+location.getAccuracy() +
					"\ncounter:::"+counter);
			
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
				
			case LocationProvider.OUT_OF_SERVICE:
				GetDetails.setLocationChipAvailable(false);
				
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				GetDetails.setLocationChipAvailable(false);
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