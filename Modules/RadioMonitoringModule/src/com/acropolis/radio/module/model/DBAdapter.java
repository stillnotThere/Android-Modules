/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DB
 * com.acropolis.radio.module.model
 * DB.java
 * Created - 2013-08-22 3:07:43 PM	
 * Modified - 2013-08-22 3:07:43 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.radio.module.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.acropolis.radio.module.RadioModuleActivity;
import com.acropolis.radio.module.global.DBConstants;

/**
 * @author CPH-iMac
 *
 */
public class DBAdapter 
{

	protected Context context = null;
	/*SQLiteDatabaseOpenHelper and args*/
	protected static DBOpenHelper dbOpenHelper = null;
	/*SQLiteDatabase and args*/
	private static SQLiteDatabase corruptdb = null;
	protected static DBTransactionListener dbTransactionListener = 
			new DBTransactionListener();
	protected static DBErrorHandler dbErrorHandler = new DBErrorHandler();

	
	public static SQLiteDatabase OpenDB()
	{
		dbOpenHelper = new DBOpenHelper(RadioModuleActivity.getAppContext());
		return dbOpenHelper.getWritableDatabase();
	}

	public static void CloseDB(SQLiteDatabase _db)
	{
		_db.close();
	}


	public static boolean isEmpty()
	{
		boolean dbEmpty = false;
		String[] selectAll = new String[200];
		int colCount = 0;
		SQLiteDatabase db = OpenDB();
		db.beginTransactionWithListener(dbTransactionListener);
		Cursor cursor = db.rawQuery(DBConstants.SELECTALL, null);
		cursor.moveToFirst();
		if(cursor.getCount() != 0)
		{
			dbEmpty = true;
		}
		else
		{
			dbEmpty = false;
		}
			
		cursor.close();
		CloseDB(db);

		return dbEmpty;
	}

	public static boolean insertValues(String colKey,String colValue)
	{
		boolean success = true;
		SQLiteDatabase db = OpenDB();
//		db.beginTransactionWithListener(dbTransactionListener);
//		if(dbTransactionListener.isCorrupted())
//		{
//			///ISSSUEEEEE!!!!!!!!!!!!!!!!!!!!!
//			corruptdb = db;
//			dbErrorHandler.onCorruption(corruptdb);
//		}
			ContentValues contentValues = new ContentValues();
			contentValues.put(colKey, colValue);
			db.insert(DBConstants.tableName, null,contentValues);
			CloseDB(db);
		return success;
	}

	public static boolean updateValues(String colKey,String colValue)
	{
		boolean success = true;
		SQLiteDatabase db = OpenDB();
		db.beginTransactionWithListener(dbTransactionListener);
		ContentValues contentValues = new ContentValues();
		contentValues.put(colKey, colValue);
		db.update(DBConstants.tableName, contentValues, null, null);
		CloseDB(db);
		return success;
	}

	/**
	 * Runs query "SELECT * FROM monitoredradiocollector"
	 * @return selectAll
	 */
	public static String[] retrieveAllValues()
	{
		String[] selectAll = new String[200];
		int colCount = 0;
		SQLiteDatabase db = OpenDB();
		db.beginTransactionWithListener(dbTransactionListener);
		Cursor cursor = db.rawQuery(DBConstants.SELECTALL, null);
		cursor.moveToFirst();
		while(cursor.getColumnCount() == colCount)
		{
			selectAll[colCount] = (String) cursor.getString(colCount);
			colCount++;
		}
		cursor.close();
		CloseDB(db);
		return selectAll;
	}

	/**
	 * Fetch value from req row and column index
	 * @param id, columnindex
	 * @return selectOne
	 */
	public static String retrieveAValue(String colKey)
	{
		String selectOne = "";
		SQLiteDatabase db = OpenDB();
		db.beginTransactionWithListener(dbTransactionListener);
		Cursor cursor = db.rawQuery(DBConstants.SELECTINDIVIDUAL, 
				new String[] {colKey});
		cursor.moveToFirst();
		selectOne = cursor.getString(cursor.getColumnCount());
		CloseDB(db);
		return selectOne;
	}

	public static boolean deleteValues()
	{
		boolean success = true;
		SQLiteDatabase db = OpenDB();
		db.beginTransactionWithListener(dbTransactionListener);
		db.delete(DBConstants.tableName, null, null);
		CloseDB(db);
		return success;
	}
}
