/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DBErrorHandler
 * com.acropolis.radio.module.model
 * DBErrorHandler.java
 * Created - 2013-08-23 3:45:38 PM	
 * Modified - 2013-08-23 3:45:38 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.radio.module.model;

import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author CPH-iMac
 *
 */
public class DBErrorHandler implements DatabaseErrorHandler 
{

	private SQLiteDatabase corruptDB = null;

	/* (non-Javadoc)
	 * @see android.database.DatabaseErrorHandler#onCorruption(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCorruption(SQLiteDatabase _corruptDB)
	{
		corruptDB = _corruptDB;
		corruptDB.beginTransactionWithListener(new DBErrorTransactionListener());
//		new CopyCorruptedDB().start();
	}

//	private class CopyCorruptedDB extends Thread
//	{
//		public void run()
//		{
//			Logger.Debug("copying corrupted DB...");
//			File file = new File(DBConstants.dbPath+".db");
//			try {
//				FileReader fileReader = new FileReader(file);
//				
//
//			} catch (FileNotFoundException e) {
//				Logger.whatTHEhell("File not found\n"+e.getMessage(), e.getCause());
//			}
//		}
//	}

}
