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

import com.app.project.acropolis.Logger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper 
{

	/**
	 * Columns KEYS
	 */
	public static final String PHONENUMBER = "PHONE_NUM";
	public static final String ROAMING = "ROAMING";
	public static final String LOCAL_INCOMING = "LOCAL_INCOMING";
	public static final String LOCAL_OUTGOING = "LOCAL_OUTGOING";
	public static final String LOCAL_RECEIVED = "LOCAL_RECEIVED";
	public static final String LOCAL_SENT = "LOCAL_SENT";
	public static final String LOCAL_DOWNLOADED = "LOCAL_DOWNLOADED";
	public static final String LOCAL_UPLOADED = "LOCAL_UPLOADED";
	public static final String ROAM_INCOMING = "ROAM_INCOMING";
	public static final String ROAM_OUTGOING = "ROAM_OUTGOING";
	public static final String ROAM_RECEIVED = "ROAM_RECEIVED";
	public static final String ROAM_SENT = "ROAM_SENT";
	public static final String ROAM_DOWNLOADED = "ROAM_DOWNLOADED";
	public static final String ROAM_UPLOADED = "ROAM_UPLOADED";
	public static final String BILL_DATE = "BILL_DATE";
	public static final String PLAN_LOCAL_INCOMING = "PLAN_LOCAL_INCOMING";
	public static final String PLAN_LOCAL_OUTGOING = "PLAN_LOCAL_OUTGOING";
	public static final String PLAN_LOCAL_RECEIVED = "PLAN_LOCAL_RECEIVED";
	public static final String PLAN_LOCAL_SENT = "PLAN_LOCAL_SENT";
	public static final String PLAN_LOCAL_DOWNLOADED = "PLAN_LOCAL_DOWNLOADED";
	public static final String PLAN_LOCAL_UPLOADED = "PLAN_LOCAL_UPLOADED";
	public static final String PLAN_ROAM_INCOMING = "PLAN_ROAMING_INCOMING";
	public static final String PLAN_ROAM_OUTGOING = "PLAN_ROAMING_OUTGOING";
	public static final String PLAN_ROAM_RECEIVED = "PLAN_ROAMING_RECEIVED";
	public static final String PLAN_ROAM_SENT = "PLAN_ROAMING_SENT";
	public static final String PLAN_ROAM_DOWNLOADED = "PLAN_ROAMING_DOWNLOADED";
	public static final String PLAN_ROAM_UPLOADED = "PLAN_ROAMING_UPLOADED";

	/**
	 * BLANK VALUES
	 */
	//LOCAL
	protected static String blank_PHONENUMBER = "";
	protected final static String blank_ROAMING = "FALSE";
	protected final static String blank_LOCAL_INCOMING = "0";
	protected final static String blank_LOCAL_OUTGOING = "0";
	protected final static String blank_LOCAL_RECEIVED = "0";
	protected final static String blank_LOCAL_SENT = "0";
	protected final static String blank_LOCAL_DOWNLOADED = "0";
	protected final static String blank_LOCAL_UPLOADED = "0";
	//ROAMING
	protected final static String blank_ROAMING_INCOMING = "0";
	protected final static String blank_ROAMING_OUTGOING = "0";
	protected final static String blank_ROAMING_RECEIVED = "0";
	protected final static String blank_ROAMING_SENT = "0";
	protected final static String blank_ROAMING_DOWNLOADED = "0";
	protected final static String blank_ROAMING_UPLOADED = "0";
	//PLAN
	protected final static String blank_BILL_DATE = "0";
		//LOCAL
	protected final static String blank_PLAN_LOCAL_INCOMING = "0";
	protected final static String blank_PLAN_LOCAL_OUTGOING = "0";
	protected final static String blank_PLAN_LOCAL_RECEIVED = "0";
	protected final static String blank_PLAN_LOCAL_SENT = "0";
	protected final static String blank_PLAN_LOCAL_DOWNLOADED = "0";
	protected final static String blank_PLAN_LOCAL_UPLOADED = "0";
		//ROAMING
	protected final static String blank_PLAN_ROAMING_INCOMING = "0";
	protected final static String blank_PLAN_ROAMING_OUTGOING = "0";
	protected final static String blank_PLAN_ROAMING_RECEIVED = "0";
	protected final static String blank_PLAN_ROAMING_SENT = "0";
	protected final static String blank_PLAN_ROAMING_DOWNLOADED = "0";
	protected final static String blank_PLAN_ROAMING_UPLOADED = "0";
	
	public static final String DB_NAME = "PROJECTACROPOLIS_DB";
	public static final int DB_VERSION = 1;

	public static final String OLD_TBL = "MONITORED_VALUES";
	public static final String TBL = "APP_DB";
	public static final String ROAM_TBL = "MONITORED_ROAM_VALUES";
	public static final String PLAN_TBL = "BILL_PLAN";

	public final String QUERY_TBL = 
			"CREATE TABLE IF NOT EXISTS " + TBL + " (" +
			"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"PHONE_NUM TEXT NOT NULL, " +
			"ROAMING TEXT, " +
			"LOCAL_INCOMING TEXT NOT NULL, " +
			"LOCAL_OUTGOING TEXT NOT NULL, " +
			"LOCAL_RECEIVED TEXT NOT NULL, " +
			"LOCAL_SENT TEXT NOT NULL, " +
			"LOCAL_DOWNLOADED TEXT NOT NULL, " +
			"LOCAL_UPLOADED TEXT NOT NULL," +
			"ROAMING_INCOMING TEXT NOT NULL, " +
			"ROAMING_OUTGOING TEXT NOT NULL, " +
			"ROMAING_RECEIVED TEXT NOT NULL, " +
			"ROAMING_SENT TEXT NOT NULL, " +
			"ROAMING_DOWNLOADED TEXT NOT NULL, " +
			"ROAMING_UPLOADED TEXT NOT NULL," +
			"BILL_DATE TEXT NOT NULL," +
			"PLAN_INCOMING TEXT NOT NULL," +
			"PLAN_OUTGOING TEXT NOT NULL," +
			"PLAN_RECEIVED TEXT NOT NULL," +
			"PLAN_SENT TEXT NOT NULL," +
			"PLAN_DOWNLOADED TEXT NOT NULL," +
			"PLAN_ROAMING_INCOMING TEXT NOT NULL," +
			"PLAN_ROAMING_OUTGOING TEXT NOT NULL," +
			"PLAN_ROAMING_RECEIVED TEXT NOT NULL," +
			"PLAN_ROAMING_SENT TEXT NOT NULL," +
			"PLAN_ROAMING_DOWNLOADED TEXT NOT NULL," +
			"PLAN_ROAMING_UPLOADED TEXT NOT NULL" +
			")";
	
	public final String DROP_TABLE = "DROP TABLE "+ TBL;
	public final String DROP_OLD = "DROP TABLE IF EXISTS " + OLD_TBL;
		
	public DBOpenHelper(Context context) 
	{
		super(context, DB_NAME, null, DB_VERSION);
	}

	public void createTables(SQLiteDatabase db) 
	{
		db.execSQL(this.QUERY_TBL);
	}

	public void removeOld(SQLiteDatabase db)
	{
		db.execSQL(DROP_OLD);
	}
	
	public void dropTables(SQLiteDatabase db) 
	{
		db.execSQL(DROP_TABLE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		removeOld(db);
		createTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		dropTables(db);
		createTables(db);
	}
}
