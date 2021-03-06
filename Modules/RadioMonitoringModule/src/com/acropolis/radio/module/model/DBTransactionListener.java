/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DBTransactionListener
 * com.acropolis.radio.module.model
 * DBTransactionListener.java
 * Created - 2013-08-23 3:54:56 PM	
 * Modified - 2013-08-23 3:54:56 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.radio.module.model;

import android.database.sqlite.SQLiteTransactionListener;

import com.acropolis.radio.module.logger.Logger;

/**
 * @author CPH-iMac
 *
 */
public class DBTransactionListener implements SQLiteTransactionListener 
{

	static boolean dbcorrupted = false;
	
	long begincount = 0;
	long commitcount = 0;
	long rollbackcount = 0;
	long maxcount = Long.MAX_VALUE;
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteTransactionListener#onBegin()
	 */
	@Override
	public void onBegin() 
	{
		begincount++;
		Logger.Debug("transactions \n\tcount::: \"" + begincount + "\" ");
		
		if(begincount == maxcount)
		{
			Logger.Debug("flushing counts" +
					"\n\tBeginCount::"+begincount+
					"\n\tCommitCount::"+commitcount+
					"\n\tRollbackCount::"+rollbackcount);
		}
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteTransactionListener#onCommit()
	 */
	@Override
	public void onCommit() 
	{
		commitcount++;
		if(begincount==commitcount)
		{
			Logger.Debug("commit successfull \n\t" +
					"CommitCount::: \"" + commitcount + "\" ");
		}
		else
		{
			Logger.Debug("discrepancy detected\n\t" +
					"CommitCount::: \"" + commitcount + "\" ");
			dbcorrupted = true;
		}
	}
    
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteTransactionListener#onRollback()
	 */
	@Override
	public void onRollback() 
	{
		Throwable throwit = new Throwable("databaseismessedup");
		Logger.whatTHEhell("shit shit!!!, transaction dirty",throwit);
		dbcorrupted = true;
	}

	public static boolean isCorrupted()
	{
		return dbcorrupted;
	}
	
}
