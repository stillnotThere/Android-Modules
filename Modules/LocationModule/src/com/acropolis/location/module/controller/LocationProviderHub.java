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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;

import com.acropolis.location.module.view.LocationModuleActivity;

/**
 * @author CPH-iMac
 *
 */
public class LocationProviderHub 
{
	
	static Context _context = null;
	static LocationManager locationManager = null;
	static Criteria criteria = null;
	static LocationChangeListener locationListener = null;

	protected static void setupEssentials()
	{
		_context = LocationModuleActivity.getAppContext();
		criteria = new Criteria();
		criteria.isCostAllowed();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		locationManager = (LocationManager) _context.
				getSystemService(Context.LOCATION_SERVICE);
		locationListener = new LocationChangeListener();
		
		
	}
	
	protected static void start()
	{
		setupEssentials();
		
		
	}
	
	
	
	protected static void stop()
	{
		locationManager.removeUpdates(locationListener);
	}

	
	
	
}
