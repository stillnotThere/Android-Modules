/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DBHelper
 * com.acropolis.radio.module.model
 * DBHelper.java
 * Created - 2013-08-22 3:07:27 PM	
 * Modified - 2013-08-22 3:07:27 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.radio.module.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acropolis.radio.module.global.DBConstants;
import com.acropolis.radio.module.logger.Logger;

/**
 * @author CPH-iMac
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper
{
	String colName = "";
	String value = "";
	String whereCol = "";
	String whereArg = "";
	
	/**
	 *Default constructor
	 */
	
	/**
	 * @param context
	 */
	public DBOpenHelper(Context context) 
	{
		super(context, DBConstants.dbName,
				DBConstants.cursorFactory, DBConstants.dbVersion);
		Logger.Debug("DBOpenHelper()");
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(DBConstants.CREATETABLE);
		Logger.Debug("tables created\n"+this.getClass().toString());
	}
	
	public void onOpen(SQLiteDatabase db)
	{
		Logger.Debug("DB opened successfullly");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
