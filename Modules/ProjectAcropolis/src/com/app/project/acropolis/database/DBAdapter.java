/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DBAdapter
 * com.app.project.acropolis.database
 * DBAdapter.java
 * Created - 2013-10-11 4:10:54 PM	
 * Modified - 2013-10-11 4:10:54 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.database;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;

import com.app.project.acropolis.Logger;
import com.app.project.acropolis.ProjectAcropolisActivity;

/**
 * @author CPH-iMac
 *
 */
public class DBAdapter {

	protected static SQLiteDatabase openConnection(Context context) 
	{
		DBOpenHelper helper = new DBOpenHelper(context);
//		Logger.Debug(ProjectAcropolisActivity.getContext().getPackageName() + "\n" + ProjectAcropolisActivity.getContext().getPackageResourcePath());
		return helper.getWritableDatabase();
	}

	
	
	protected static boolean dropTables(Context context)
	{
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		helper.dropTables();
		helper.createTables();
		db.close();
		return true;
	}
	
	public static void deleteDB()
	{
		File file = ProjectAcropolisActivity.getContext().
				getDatabasePath(DBOpenHelper.DB_NAME);
		Logger.Debug("Deleted::"+file.delete());
	}
	
	public static void createDB(Context context)
	{
		DBOpenHelper dboh = new DBOpenHelper(context); 
		dboh.createTables();
		Logger.Debug("DB CREATED");
		putBlank(context);
		checkIntegrity(context);
	}
	
	public static void checkIntegrity(Context context)
	{
		Logger.Debug("CHECKING INTEGRITY");
		getValues(context);
	}
	
	public static boolean doesItExist(Context context)
	{
		boolean exists = false;
		File file = ProjectAcropolisActivity.getContext().getDatabasePath(DBOpenHelper.DB_NAME);
		if(file.exists())
		{
			exists = true;
			Logger.Debug("exists____size::"+file.length());
		}
		else 
		{
			exists = false;
			Logger.Debug("does not exist\nputting blank");
			putBlank(context);
		}
		return exists;
	}
	
	public static void checkDBState(Context context)
	{
//		DBOpenHelper _openHelper = new DBOpenHelper();
//		_openHelper.removeOld(_openHelper.getWritableDatabase());
		doesItExist(context);
	}

	public static void resetValues(Context context)
	{
		dropTables(context);
		putBlank(context);
		Logger.Debug("Values reset");
	}
	
	public static void putBlank(Context context)
	{
		DBOpenHelper openHelper = new DBOpenHelper(context);
		SQLiteDatabase db = openHelper.getWritableDatabase();
		openHelper.createTables();
		db.beginTransaction();
		try {
			DBOpenHelper.blank_PHONENUMBER = 
					( (TelephonyManager) ProjectAcropolisActivity.getContext().
							getSystemService(Context.TELEPHONY_SERVICE)).
							getLine1Number();
			
			ContentValues cv = new ContentValues();			
			cv.put(DBOpenHelper.PHONENUMBER, DBOpenHelper.blank_PHONENUMBER);
			cv.put(DBOpenHelper.ROAMING, DBOpenHelper.blank_ROAMING);
			cv.put(DBOpenHelper.BILL_DATE, DBOpenHelper.blank_BILL_DATE);
			//local
			cv.put(DBOpenHelper.LOCAL_INCOMING, DBOpenHelper.blank_LOCAL_INCOMING);
			cv.put(DBOpenHelper.LOCAL_OUTGOING, DBOpenHelper.blank_LOCAL_OUTGOING);
			cv.put(DBOpenHelper.LOCAL_RECEIVED, DBOpenHelper.blank_LOCAL_RECEIVED);
			cv.put(DBOpenHelper.LOCAL_SENT, DBOpenHelper.blank_LOCAL_SENT);
			cv.put(DBOpenHelper.LOCAL_DOWNLOADED, DBOpenHelper.blank_LOCAL_DOWNLOADED);
			cv.put(DBOpenHelper.LOCAL_UPLOADED, DBOpenHelper.blank_LOCAL_UPLOADED);
			//roaming
			cv.put(DBOpenHelper.ROAM_INCOMING, DBOpenHelper.blank_ROAMING_INCOMING);
			cv.put(DBOpenHelper.ROAM_OUTGOING, DBOpenHelper.blank_ROAMING_OUTGOING);
			cv.put(DBOpenHelper.ROAM_RECEIVED, DBOpenHelper.blank_ROAMING_RECEIVED);
			cv.put(DBOpenHelper.ROAM_SENT, DBOpenHelper.blank_ROAMING_SENT);
			cv.put(DBOpenHelper.ROAM_DOWNLOADED, DBOpenHelper.blank_ROAMING_DOWNLOADED);
			cv.put(DBOpenHelper.ROAM_UPLOADED, DBOpenHelper.blank_ROAMING_UPLOADED);
			//plan
				//local
			cv.put(DBOpenHelper.PLAN_LOCAL_INCOMING, DBOpenHelper.blank_PLAN_LOCAL_INCOMING);
			cv.put(DBOpenHelper.PLAN_LOCAL_OUTGOING, DBOpenHelper.blank_PLAN_LOCAL_OUTGOING);
			cv.put(DBOpenHelper.PLAN_LOCAL_RECEIVED, DBOpenHelper.blank_PLAN_LOCAL_RECEIVED);
			cv.put(DBOpenHelper.PLAN_LOCAL_SENT, DBOpenHelper.blank_PLAN_LOCAL_SENT);
			cv.put(DBOpenHelper.PLAN_LOCAL_DOWNLOADED, DBOpenHelper.blank_PLAN_LOCAL_DOWNLOADED);
			cv.put(DBOpenHelper.PLAN_LOCAL_UPLOADED, DBOpenHelper.blank_PLAN_LOCAL_UPLOADED);
				//roam
			cv.put(DBOpenHelper.PLAN_ROAM_INCOMING, DBOpenHelper.blank_PLAN_ROAMING_INCOMING);
			cv.put(DBOpenHelper.PLAN_ROAM_OUTGOING, DBOpenHelper.blank_PLAN_ROAMING_OUTGOING);
			cv.put(DBOpenHelper.PLAN_ROAM_RECEIVED, DBOpenHelper.blank_PLAN_ROAMING_RECEIVED);
			cv.put(DBOpenHelper.PLAN_ROAM_SENT, DBOpenHelper.blank_PLAN_ROAMING_SENT);
			cv.put(DBOpenHelper.PLAN_ROAM_DOWNLOADED, DBOpenHelper.blank_PLAN_ROAMING_DOWNLOADED);
			cv.put(DBOpenHelper.PLAN_ROAM_UPLOADED, DBOpenHelper.blank_PLAN_ROAMING_UPLOADED);
			
			db.insert(DBOpenHelper.TBL, null, cv);
			
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
	}

	public static long insert(Context context,ContentValues values) 
	{
		SQLiteDatabase db = openConnection(context);
		long result = 0;
		db.beginTransaction();
		try{
			db.delete(DBOpenHelper.TBL, null, null);
			result = db.insert(DBOpenHelper.TBL, null, values);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return result;
	}

	public static long update(Context context,ContentValues value)
	{
		SQLiteDatabase db = openConnection(context);
		long result=0;

		db.beginTransaction();
		try{
			result = db.update(DBOpenHelper.TBL, value, null, null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();	
		}

		return result;
	}

	public static long delete(Context context)
	{
		SQLiteDatabase db = openConnection(context);
		db.beginTransaction();
		try{
			db.delete(DBOpenHelper.TBL, null, null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return 0;
	}

	public static String getValue(Context context,String column)
	{
		String value = "";
		Cursor cursor = null;
		SQLiteDatabase db = openConnection(context);
		db.beginTransaction();
		try {
			cursor = db.query(DBOpenHelper.TBL, new String[]{column},null, null, null, null, null);
			cursor.moveToFirst();
			value = cursor.getString(cursor.getColumnIndex(column));
			db.setTransactionSuccessful();
		} finally {
			if(cursor!=null)
			{
				cursor.close();
			}
			db.endTransaction();
			db.close();
		}
		return value;
	}

	public static String[] getValues(Context context) 
	{
		SQLiteDatabase db = openConnection(context);
		Cursor cursor=null;
		String[] all = null;
		int counter = 0;
		db.beginTransaction();
		try {
			cursor = db.query(DBOpenHelper.TBL,new String[] {"*"}, null, null, null, null, null);
			cursor.moveToFirst();
			while(cursor.isLast())
			{
//				all[counter] = cursor.getString(counter);
//				Logger.Debug(all[counter]);
				++counter;
				cursor.moveToNext();
			}
			Logger.Debug("count::"+counter);
		} finally {
			if(cursor!=null)
			{
				cursor.close();
			}
			db.endTransaction();
			db.close();
		}
		return all;
	}
}
