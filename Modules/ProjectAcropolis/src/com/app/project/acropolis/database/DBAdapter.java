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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author CPH-iMac
 *
 */
public class DBAdapter {
	private Context context;
	DBOpenHelper DB;
	SQLiteDatabase db;
	final String table = "MONITORED_VALUES";
	
	
	public DBAdapter(Context context) {
		this.context = context;
	}
	
	protected SQLiteDatabase openConnection() 
	{
		DBOpenHelper helper = new DBOpenHelper(context);
		return helper.getWritableDatabase();
	}
	
	public boolean isEmpty() 
	{
		db = openConnection();
		Cursor cursor = db.query(table, new String[] {"COUNT(*)"}, null, null, null, null, null);
		boolean result;
		
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
		db.close();
		return result;
	}
	
	public long insert(ContentValues values) 
	{
		db = openConnection();
		db.delete(table, null, null);
		long result = db.insert(table, null, values);
		db.close();
		return result;
	}
	
	public long update(ContentValues value)
	{
		db = openConnection();
		long result = db.update(table, value, null, null);
		db.close();
		return result;
	}
	
	public long delete(){
		db = openConnection();
		db.delete(table, null, null);
		db.close();
		return 0;
	}
	
	public String getValue(String column)
	{
		String value = "";
		db = openConnection();
		Cursor cursor = db.query(table, new String[]{column},null, null, null, null, null);
		cursor.moveToFirst();
		value = cursor.getString(cursor.getColumnIndex(column));
		cursor.close();
		db.close();
		return value;
	}
	
	public Cursor getValues() 
	{
		db = openConnection();
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		cursor.moveToFirst();
		
		//TODO
		
		cursor.close();
		db.close();
		return cursor;
	}
}