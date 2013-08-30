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

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;

import com.acropolis.location.module.view.LocationModuleActivity;

/**
 * @author CPH-iMac
 *
 */
public class LocationHub 
{
	
	static Context _context = null;
	static Intent _intent = null;
	static LocationManager locationManager = null;
	static Criteria criteria = null;
	static String provider = null;
	static LocationChangeListener locationListener = null;
	static long timeIntervals = 0;	//milliseconds
	static float distanceIntervals = 0;

	protected static void setupCriteria()
	{
		criteria = new Criteria();
		criteria.isCostAllowed();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
	}
	
	protected static void setupManager()
	{
		locationManager = (LocationManager) _context.
				getSystemService(Context.LOCATION_SERVICE);
		provider = locationManager.getBestProvider(criteria, false);
		locationListener = new LocationChangeListener();
		locationManager.requestLocationUpdates(
				provider,
				timeIntervals, 
				distanceIntervals, 
				locationListener );
	}
	
	protected static void start()
	{
		_context = LocationModuleActivity.getAppContext();
		_intent = LocationModuleActivity.getAppIntent();
		setupCriteria();
		setupManager();
	}
	
	protected static void stop()
	{
		locationManager.removeUpdates(locationListener);
	}
	
}
