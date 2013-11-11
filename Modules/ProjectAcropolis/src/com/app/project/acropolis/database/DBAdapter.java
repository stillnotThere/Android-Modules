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

	final static String table = "MONITORED_VALUES";

	protected static SQLiteDatabase openConnection() 
	{
		DBOpenHelper helper = new DBOpenHelper(ProjectAcropolisActivity.getContext());
//		Logger.Debug(ProjectAcropolisActivity.getContext().getPackageName() + "\n" + ProjectAcropolisActivity.getContext().getPackageResourcePath());
		return helper.getWritableDatabase();
	}

	protected static boolean dropTables()
	{
		DBOpenHelper helper = new DBOpenHelper(ProjectAcropolisActivity.getContext());
		SQLiteDatabase db = helper.getWritableDatabase();
		helper.dropTables(db);
		helper.createTables(db);
		db.close();
		
		return true;
	}
	
	public static boolean doesItExist()
	{
		boolean exists = false;
		File file = ProjectAcropolisActivity.getContext().getDatabasePath(DBOpenHelper.DB_NAME);
		if(file.exists())
		{
			exists = true;
			Logger.Debug("exists____size::"+file.length());
		}
		else 
			exists = false;
		
		return exists;
	}
	
	public static void checkDBState()
	{
		if(doesItExist())
		{
			if(isEmpty())
			{
				Logger.Debug("inserting blank");
				putBlank();
			}
		}
		else
		{
			DBOpenHelper openHelper = new DBOpenHelper(ProjectAcropolisActivity.getContext());
			SQLiteDatabase _db = openHelper.getWritableDatabase();
			openHelper.createTables(_db);
			_db.close();
			putBlank();
		}
	}

	public static void resetValues()
	{
		dropTables();
		putBlank();
		Logger.Debug("Values reset");
	}
	
	private static void putBlank()
	{
		SQLiteDatabase db = openConnection();
		db.beginTransaction();
		try {
			DBOpenHelper.blank_PHONENUMBER = 
					( (TelephonyManager) ProjectAcropolisActivity.getContext().
							getSystemService(Context.TELEPHONY_SERVICE)).
							getLine1Number();
			
			ContentValues cv = new ContentValues();			
			cv.put(DBOpenHelper.PHONENUMBER, DBOpenHelper.blank_PHONENUMBER);
			cv.put(DBOpenHelper.ROAMING, DBOpenHelper.blank_ROAMING);
			cv.put(DBOpenHelper.INCOMING, DBOpenHelper.blank_INCOMING);
			cv.put(DBOpenHelper.OUTGOING, DBOpenHelper.blank_OUTGOING);
			cv.put(DBOpenHelper.RECEIVED, DBOpenHelper.blank_RECEIVED);
			cv.put(DBOpenHelper.SENT, DBOpenHelper.blank_SENT);
			cv.put(DBOpenHelper.DOWNLOADED, DBOpenHelper.blank_DOWNLOADED);
			cv.put(DBOpenHelper.UPLOADED, DBOpenHelper.blank_UPLOADED);
			db.insert(table, null, cv);
			
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public static boolean isEmpty() 
	{
		SQLiteDatabase db = openConnection();
		boolean result;

		db.beginTransaction();	//EXCLUSIVE
		try
		{
			Cursor cursor = db.query(table, new String[] {"COUNT(*)"}, null, null, null, null, null);
			cursor.moveToFirst();
			if(cursor.getInt(0) == 0)
			{
				result = true;
			}
			else
			{
				result = false;
			}
			cursor.close();

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();	
		}
		return result;
	}

	public static long insert(ContentValues values) 
	{
		SQLiteDatabase db = openConnection();
		long result = 0;
		db.beginTransaction();
		try{
			db.delete(table, null, null);
			result = db.insert(table, null, values);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return result;
	}

	public static long update(ContentValues value)
	{
		SQLiteDatabase db = openConnection();
		long result=0;

		db.beginTransaction();
		try{
			result = db.update(table, value, null, null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();	
		}

		return result;
	}

	public static long delete()
	{
		SQLiteDatabase db = openConnection();
		db.beginTransaction();
		try{
			db.delete(table, null, null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return 0;
	}

	public static String getValue(String column)
	{
		String value = "";
		Cursor cursor = null;
		SQLiteDatabase db = openConnection();
		db.beginTransaction();
		try {
			cursor = db.query(table, new String[]{column},null, null, null, null, null);
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

	public static Cursor getValues() 
	{
		SQLiteDatabase db = openConnection();
		Cursor cursor =null;
		db.beginTransaction();
		try {
			cursor = db.query(table, null, null, null, null, null, null);
			cursor.moveToFirst();
		} finally {
			if(cursor!=null)
			{
				cursor.close();
			}
			db.endTransaction();
			db.close();
		}
		return cursor;
	}
}
