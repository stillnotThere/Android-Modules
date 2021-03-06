/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * DBErrorTransactionListener
 * com.acropolis.radio.module.model
 * DBErrorTransactionListener.java
 * Created - 2013-08-23 3:51:06 PM	
 * Modified - 2013-08-23 3:51:06 PM
 * TODO
 * NOTES - 
 */
package com.acropolis.radio.module.model;

import com.acropolis.radio.module.logger.Logger;

import android.database.sqlite.SQLiteTransactionListener;

/**
 * @author CPH-iMac
 *
 */
public class DBErrorTransactionListener implements SQLiteTransactionListener
{

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteTransactionListener#onBegin()
	 */
	@Override
	public void onBegin() 
	{
		Logger.Verbose("transaction performing over CORRUPTED DB");
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteTransactionListener#onCommit()
	 */
	@Override
	public void onCommit() 
	{
		Logger.Verbose("transaction commited over CORRUPTED DB");
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteTransactionListener#onRollback()
	 */
	@Override
	public void onRollback() 
	{
		Logger.Verbose("transaction dirty over CORRUPTED DB");
	}

}
