/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * SharedPrefs
 * com.tester
 * SharedPrefs.java
 * Created - 2013-12-02 5:17:47 PM	
 * Modified - 2013-12-02 5:17:47 PM
 * TODO
 * NOTES - 
 */
package com.tester;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author CPH-iMac
 *
 */
public class SharedPrefs 
{
	final String TAG = this.getClass().getSimpleName();
	private Context _context = null;
	private final String name = "test_sp";
	
	private final String defValue = "0";
	
	public SharedPrefs(Context context)
	{
		_context = context;
	}
	
	private SharedPreferences getSharedPrefs()
	{
		return _context.getSharedPreferences(name, Context.MODE_PRIVATE|Context.MODE_MULTI_PROCESS);
	}
	
	public String getData(String key)
	{
		String val = "";
		SharedPreferences sp = getSharedPrefs();
		val = sp.getString(key, defValue);
		Log.v(TAG, val);
		return val;
	}
	
	public void setData(String key,String data)
	{
		SharedPreferences sp = getSharedPrefs();
		sp.edit().putString(key, data).commit();
	}
	
}
