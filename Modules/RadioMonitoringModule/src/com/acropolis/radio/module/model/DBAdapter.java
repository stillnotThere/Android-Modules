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

import com.acropolis.radio.module.global.DBConstants;
import com.acropolis.radio.module.logger.Logger;

/**
 * @author CPH-iMac
 *
 */
public class DBAdapter 
{

	protected Context context = null;
	/*SQLiteDatabaseOpenHelper and args*/
	protected DBOpenHelper dbOpenHelper = null;
	/*SQLiteDatabase and args*/

	public DBAdapter(Context _context)
	{
		context = _context;
	}
	
	public SQLiteDatabase OpenDB()
	{
		dbOpenHelper = new DBOpenHelper(context);
		return dbOpenHelper.getWritableDatabase();
	}

	public   void CloseDB(SQLiteDatabase _db)
	{
		_db.close();
	}

	public boolean isEmpty()
	{
		Logger.Verbose("isEmpty");
		boolean dbEmpty = false;
		SQLiteDatabase db = OpenDB();
//		db.beginTransactionWithListener(dbTransactionListener);
		Cursor cursor = db.query(DBConstants.tableName, 
				new String[]{DBConstants.EMPTY_QUERY}, 
				null, null, null, null, null); 
		cursor.moveToFirst();
		if(cursor.getInt(0) == 0)
			dbEmpty = true;
		else
			dbEmpty = false;
		
		cursor.close();
		CloseDB(db);
		Logger.Debug("empty=="+String.valueOf(dbEmpty));
		return dbEmpty;
	}

	public   boolean insertValues(String colKey,String colValue)
	{
		boolean success = true;
		SQLiteDatabase db = OpenDB();
			ContentValues contentValues = new ContentValues();
			contentValues.put(colKey, colValue);
			db.insert(DBConstants.tableName, null,contentValues);
			CloseDB(db);
		return success;
	}

	public   boolean updateValues(String colKey,String colValue)
	{
		boolean success = true;
		SQLiteDatabase db = OpenDB();
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
	public   String[] retrieveAllValues()
	{
		String[] selectAll = new String[200];
		int colCount = 0;
		SQLiteDatabase db = OpenDB();
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
	 * @param ID, columnindex
	 * @return selectOne
	 */
	public   String retrieveAValue(String colKey)
	{
		String selectOne = "";
		SQLiteDatabase db = OpenDB();
		Cursor cursor = db.rawQuery(DBConstants.SELECTINDIVIDUAL, 
				new String[] {colKey});
		cursor.moveToFirst();
		selectOne = cursor.getString(cursor.getColumnCount());
		CloseDB(db);
		return selectOne;
	}

	public   boolean deleteValues()
	{
		boolean success = true;
		SQLiteDatabase db = OpenDB();
		db.delete(DBConstants.tableName, null, null);
		CloseDB(db);
		return success;
	}
}
