/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * LocationListener
 * com.acropolis.location.module.controller
 * LocationListener.java
 * Created - 2013-08-21 2:05:48 PM	
 * Modified - 2013-08-21 2:05:48 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.location.module.controller;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationChangeListener implements LocationListener 
{

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location location) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String provider) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		// TODO Auto-generated method stub
		
	}

}
