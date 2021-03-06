///**
// * @author Rohan K.M rohan.mahendroo@gmail.com
// * DataInterecptor
// * com.acropolis.radio.module.monitoring.controller
// * DataInterecptor.java
// * Created - 2013-07-31 5:11:23 PM	
// * Modified - 2013-08-01 1:21:23 PM
// * TODO
// * NOTES - android.net.TrafficStats (bytes down/up since boot) 
// */
//package com.acropolis.radio.module.monitor.controller;
//
//import android.net.ConnectivityManager;
//import android.net.TrafficStats;
//import android.telephony.PhoneStateListener;
//import android.telephony.TelephonyManager;
//
//import com.acropolis.radio.module.global.DBConstants;
//import com.acropolis.radio.module.logger.Logger;
//import com.acropolis.radio.module.model.DBAdapter;
//
///**
// * @author CPH-iMac
// *
// */
//public class DataInterecptor  
//{
//
//	public DBAdapter dbAdapter;
//	
//	public static class DataActivityMonitor extends PhoneStateListener
//	{
//		public void onDataActivity(int direction)
//		{
//			dbAdapter = new DBAdapter(RadioModuleActivity.getAppContext());
//			if(RadioEngine.START_MONITORING)
//			{
//				switch(direction)
//				{
//				case TelephonyManager.DATA_ACTIVITY_INOUT:
//				{
//					Logger.Debug("duplex mode");
//					RadioEngine.DOWNLOADED=TrafficStats.getMobileRxBytes();
//					RadioEngine.UPLOADED=TrafficStats.getMobileTxBytes();
//					
//					long toStoreDown = 0;
//					long toStoreUp = 0;
//					long dbDown = Long.parseLong(dbAdapter.retrieveAValue(DBConstants.DOWNLOADED));
//					long dbUp = Long.parseLong(dbAdapter.retrieveAValue(DBConstants.UPLOADED));
//					long sessionDown = GetDetails.getSessionDownloadedBytes();
//					long sessionUp = GetDetails.getSessionUploadedBytes();
//
//					if(dbDown <= sessionDown)
//					{
//						toStoreDown = dbDown + sessionDown;
//						dbAdapter.insertValues(DBConstants.DOWNLOADED,
//								String.valueOf(toStoreDown));
//					}
//					else
//					{
//						toStoreDown = sessionDown;
//						dbAdapter.updateValues(DBConstants.DOWNLOADED, 
//								String.valueOf(toStoreDown));
//					}
//					if(dbUp <= sessionUp)
//					{
//						toStoreUp = dbUp + sessionUp;
//						dbAdapter.updateValues(DBConstants.UPLOADED, 
//								String.valueOf(toStoreUp));
//					}
//					else
//					{
//						toStoreUp = sessionUp;
//						dbAdapter.updateValues(DBConstants.UPLOADED, 
//								String.valueOf(toStoreUp));
//					}
//
//				};
//
//				case TelephonyManager.DATA_ACTIVITY_IN:
//				{
//					Logger.Debug("download mode");
//					RadioEngine.DOWNLOADED=TrafficStats.getMobileRxBytes();
//					
//					long toStoreDown = 0;
//					long dbDown = Long.parseLong(dbAdapter.retrieveAValue(DBConstants.DOWNLOADED));
//					long sessionDown = GetDetails.getSessionDownloadedBytes();
//					if(dbDown <= sessionDown)
//					{
//						toStoreDown = dbDown + sessionDown;
//						dbAdapter.insertValues(DBConstants.DOWNLOADED,
//								String.valueOf(toStoreDown));
//					}
//					else
//					{
//						toStoreDown = sessionDown;
//						dbAdapter.updateValues(DBConstants.DOWNLOADED, 
//								String.valueOf(toStoreDown));
//					}
//				};
//
//				case TelephonyManager.DATA_ACTIVITY_OUT:
//				{
//					Logger.Debug("upload mode");
//					RadioEngine.UPLOADED=TrafficStats.getMobileTxBytes();
//					long toStoreUp = 0;
//					long dbUp = Long.parseLong(dbAdapter.retrieveAValue(DBConstants.UPLOADED));
//					long sessionUp = GetDetails.getSessionUploadedBytes();
//					
//					if(dbUp <= sessionUp)
//					{
//						toStoreUp = dbUp + sessionUp;
//						dbAdapter.updateValues(DBConstants.UPLOADED, 
//								String.valueOf(toStoreUp));
//					}
//					else
//					{
//						toStoreUp = sessionUp;
//						dbAdapter.updateValues(DBConstants.UPLOADED, 
//								String.valueOf(toStoreUp));
//					}
//					
//				};
//
//				case TelephonyManager.DATA_ACTIVITY_DORMANT:
//				{Logger.Debug("dormant mode");};
//
//				case TelephonyManager.DATA_ACTIVITY_NONE:
//				{Logger.Debug("no activity");};
//
//				}
//			}
//		}
//	}
//
//	public static class DataConnectionState extends PhoneStateListener
//	{
//		/**
//		 * Checking data activity and then monitor
//		 */
//		public void onDataConnectionStateChanged(int state,int networkType)
//		{
//			switch(networkType)
//			{
//			case ConnectivityManager.TYPE_WIFI:
//			{RadioEngine.CURRENT_NETWORK_TYPE=RadioEngine.NETWORK_WIFI;Logger.Debug("WiFi");};
//			case ConnectivityManager.TYPE_MOBILE:
//			{RadioEngine.CURRENT_NETWORK_TYPE=RadioEngine.NETWORK_MOBILE;Logger.Debug("Mobile");};
//			case ConnectivityManager.TYPE_MOBILE_DUN:
//			{RadioEngine.CURRENT_NETWORK_TYPE=RadioEngine.NETWORK_MOBILE;Logger.Debug("Mobile DUN");};
//			case ConnectivityManager.TYPE_MOBILE_HIPRI:
//			{RadioEngine.CURRENT_NETWORK_TYPE=RadioEngine.NETWORK_MOBILE;Logger.Debug("Mobile HiPriority");};
//			case ConnectivityManager.TYPE_MOBILE_SUPL:
//			{RadioEngine.CURRENT_NETWORK_TYPE=RadioEngine.NETWORK_MOBILE;Logger.Debug("Mobile SUPL");};
//			case ConnectivityManager.TYPE_MOBILE_MMS:
//			{RadioEngine.CURRENT_NETWORK_TYPE=RadioEngine.NETWORK_MMS;Logger.Debug("MMS");};
//			}
//
//			switch(state)
//			{
//			case TelephonyManager.DATA_CONNECTED:
//			{RadioEngine.CURRENT_DATA_STATE=RadioEngine.DATA_ACTIVE;Logger.Debug("Data ACTIVE");};
//			case TelephonyManager.DATA_DISCONNECTED:
//			{RadioEngine.CURRENT_DATA_STATE=RadioEngine.DATA_INACTIVE;Logger.Debug("Data disconnected");};
//			case TelephonyManager.DATA_SUSPENDED:
//			{RadioEngine.CURRENT_DATA_STATE=RadioEngine.DATA_INACTIVE;Logger.Debug("Data suspended");};
//			}
//
//			if(RadioEngine.CURRENT_NETWORK_TYPE==RadioEngine.NETWORK_MOBILE ||
//					RadioEngine.CURRENT_DATA_STATE==RadioEngine.DATA_ACTIVE)
//				RadioEngine.START_MONITORING = true;
//		}
//	}
//
//}