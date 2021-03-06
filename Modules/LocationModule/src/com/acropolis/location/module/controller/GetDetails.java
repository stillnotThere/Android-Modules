/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * GetDetails
 * com.acropolis.location.module.controller
 * GetDetails.java
 * Created - 2013-08-29 4:18:00 PM	
 * Modified - 2013-08-29 4:18:00 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.location.module.controller;

/**
 * @author CPH-iMac
 *
 */
public class GetDetails 
{
	public static boolean locationChipAvailable = false;
	public static double accuracy = 0;
	public static double latitude = 0;
	public static double longitude = 0;
	
	/**
	 * @param _locationChipAvailable the locationChipAvailable to set
	 */
	public static void setLocationChipAvailable(boolean _locationChipAvailable) 
	{
		locationChipAvailable = _locationChipAvailable;
	}
	
	/**
	 * @return the locationChipAvailable
	 */
	public static boolean isLocationChipAvailable()
	{
		return locationChipAvailable;
	}

	/**
	 * @param _accuracy the accuracy to set
	 */
	public static void setAccuracy(double _accuracy) 
	{
		accuracy = _accuracy;
	}
	
	/**
	 * @return the accuracy
	 */
	public static double getAccuracy() 
	{
		return accuracy;
	}

	/**
	 * @param _latitude
	 */
	public static void setLatitude(double _latitude) 
	{
		latitude = _latitude;
	}
	
	/**
	 * @return the latitude
	 */
	public static double getLatitude() 
	{
		return latitude;
	}
	
	/**
	 * @param _longitude
	 */
	public static void setLongitude(double _longitude) 
	{
		longitude = _longitude;
	}
	
	/**
	 * @return the longitude
	 */
	public static double getLongitude() 
	{
		return longitude;
	}
	
}
