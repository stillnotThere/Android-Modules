/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * CheckPlan
 * com.app.project.acropolis.monitor.bill
 * CheckPlan.java
 * Created - 2013-11-13 3:16:43 PM	
 * Modified - 2013-11-13 3:16:43 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.monitor.plan;

import java.text.SimpleDateFormat;

import com.app.project.acropolis.database.DBAdapter;
import com.app.project.acropolis.database.DBOpenHelper;

/**
 * @author CPH-iMac
 *
 */
public class CheckPlan 
{
	
	public static void checkBillDate()
	{
		//TODO
		SimpleDateFormat sdf = new SimpleDateFormat("");
		
		long BillDate = 0; //milli
		String formattedBD = DBAdapter.getValue(DBOpenHelper.BILL_DATE);
//		long longDB_date = 
	}
	
	
	private static long fetchPlan(String key)
	{
		long db_val = 0;
		db_val = Long.parseLong(DBAdapter.getValue(key));
		return db_val;
	}
	
	public static void compare_set(String key,String recorded)
	{
		long storedValue = fetchPlan(key);
	}
	
	
}
