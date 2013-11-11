/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DatabaseOpenHelper
 * com.app.project.acropolis.database
 * DatabaseOpenHelper.java
 * Created - 2013-10-11 4:10:30 PM	
 * Modified - 2013-10-11 4:10:30 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.database;

import java.io.File;

import com.app.project.acropolis.Logger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper {

	public static final String PHONENUMBER = "PHONE_NUM";
	public static final String ROAMING = "ROAMING";
	public static final String INCOMING = "INCOMING";
	public static final String OUTGOING = "OUTGOING";
	public static final String RECEIVED = "RECEIVED";
	public static final String SENT = "SENT";
	public static final String DOWNLOADED = "DOWNLOADED";
	public static final String UPLOADED = "UPLOADED";

	//BLANK VALUES
	protected static String blank_PHONENUMBER = "";
	protected final static String blank_ROAMING = "FALSE";
	protected final static String blank_INCOMING = "0";
	protected final static String blank_OUTGOING = "0";
	protected final static String blank_RECEIVED = "0";
	protected final static String blank_SENT = "0";
	protected final static String blank_DOWNLOADED = "0";
	protected final static String blank_UPLOADED = "0";

	public static final String DB_NAME = "PROJECTACROPOLIS_DB";
	public static final int DB_VERSION = 1;

	public static final String TBL = "MONITORED_VALUES";


	public DBOpenHelper(Context context) 
	{
		super(context, DB_NAME, null, DB_VERSION);
	}

	public void createTables(SQLiteDatabase db) 
	{

		db.execSQL(
				"CREATE TABLE IF NOT EXISTS " + TBL + " (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"PHONE_NUM TEXT NOT NULL, " +
						"ROAMING TEXT, " +
						"INCOMING TEXT NOT NULL, " +
						"OUTGOING TEXT NOT NULL, " +
						"RECEIVED TEXT NOT NULL, " +
						"SENT TEXT, " +
						"DOWNLOADED TEXT, " +
						"UPLOADED TEXT " +
						")"
				);
	}


	public void dropTables(SQLiteDatabase db) 
	{
		db.execSQL("DROP TABLE " + TBL);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		createTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		dropTables(db);
		createTables(db);
	}
}
