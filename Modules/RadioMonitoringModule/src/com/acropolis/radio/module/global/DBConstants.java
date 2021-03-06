/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DBConstants
 * com.acropolis.radio.module.model
 * DBConstants.java
 * Created - 2013-08-26 12:18:04 PM	
 * Modified - 2013-08-26 12:18:04 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.radio.module.global;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.acropolis.radio.module.model.DBAdapter;

/**
 * @author CPH-iMac
 *
 */
public final class DBConstants 
{

	/*Database details*/
	public final static String dbName = "RadioModuleDatabase";
	public final static String tableName = "MonitoredRadioCollector";
	public final static String dbPath = 
			Environment.getDataDirectory( )+
			"/data/databases/"+dbName;
	
	
	/*SQLiteDatabase args*/
	/*	@see android.database.sqlite.SQLiteDatabase.*	*/
	public final static SQLiteDatabase.CursorFactory cursorFactory = null;
	public final static int dbVersion = 1;
	
	
	/*SQLite Transact queries*/
	public final static String CREATETABLE =
			"CREATE TABLE " + tableName +
			"( " +
			"_id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0," +
			"timestamp TEXT NOT NULL DEFAULT 0," +
			"phonenumber TEXT NOT NULL DEFAULT 0," +
			"roaming TEXT NOT NULL DEFAULT 0," +
			"incomingcall TEXT NOT NULL DEFAULT 0," +
			"outgoingcall TEXT NOT NULL DEFAULT 0," +
			"receivedmsg TEXT NOT NULL DEFAULT 0," +
			"sentmsg TEXT NOT NULL DEFAULT 0," +
			"downloaded TEXT NOT NULL DEFAULT 0," +
			"uploaded TEXT NOT NULL DEFAULT 0 " +
			")";
	public final static String EMPTY_QUERY = "COUNT(*)";
	public final static String DROPTABLE = "";
	//to be implemented with SQLiteDatabase#rawQuery()
	public final static String SELECTALL = "SELECT * FROM " + tableName;
	public final static String SELECTINDIVIDUAL = "SELECT ? FROM " + tableName;
	
	
	/*COLUMN INDEXES*/
	public final static String ID = "_id";
	public final static String TIMESTAMP = "timestamp";
	public final static String PHONENUMBER = "phonenumber";
	public final static String ROAMING = "roaming";
	public final static String INCOMINGCALL = "incomingcall";
	public final static String OUTGOINGCALL = "outgoingcall";
	public final static String RECEIVEDMSG = "receivedmsg";
	public final static String SENTMSG = "sentmsg";
	public final static String DOWNLOADED = "downloaded";
	public final static String UPLOADED = "uploaded";

//	
//	public void setupDBAdapter(Context context)
//	{
//		DBAdapter dbAdapter = new DBAdapter(context);
//		
//	}
//	
//	public SQLiteDatabase getDBAdapter()
//	{
//		return dbAdapter;
//	}
	
}