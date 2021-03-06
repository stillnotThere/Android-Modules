/**
 * @author Rohan K.M rohan.mahendroo@gmail.com
 * PersistenceData
 * com.app.project.acropolis.database
 * PersistenceData.java
 * Created - 2013-12-02 5:44:24 PM	
 * Modified - 2013-12-02 5:44:24 PM
 * TODO
 * NOTES - 
 */
package com.app.project.acropolis.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.app.project.acropolis.GlobalConstants;
import com.app.project.acropolis.ProjectAcropolisActivity;

/**
 * @author CPH-iMac
 *
 */
public class PersistedData
{
	private Context context = ProjectAcropolisActivity.getContext();
	
	private final String NAME = "ProjectAcropolis_SP";
	
	private SharedPreferences getAppSharedPreferences()
	{
		return context.getSharedPreferences(
				NAME, Context.MODE_MULTI_PROCESS|Context.MODE_PRIVATE);
	}
	
	public void putData(String key, String data)
	{
		SharedPreferences sp = getAppSharedPreferences();
		sp.edit().putString(key, data).commit();
	}
	
	public String fetchData(String key)
	{
		String val = "";
		SharedPreferences sp = getAppSharedPreferences();
		val = sp.getString(
				key, GlobalConstants.PersistenceConstants._DEFAULT_VALUE);
		return val;
	}
	
	public boolean resetData()
	{
		SharedPreferences.Editor editor = getAppSharedPreferences().edit().clear();
		String blank_PHONENUMBER = 
				( (TelephonyManager) context.
						getSystemService(Context.TELEPHONY_SERVICE)).
						getLine1Number();
		editor.putString(GlobalConstants.PersistenceConstants.PHONENUMBER, blank_PHONENUMBER);
		editor.putString(GlobalConstants.PersistenceConstants.ROAMING, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.BILL_DATE, GlobalConstants.PersistenceConstants._RESET_VALUES);
		//local
		editor.putString(GlobalConstants.PersistenceConstants.LOCAL_INCOMING, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.LOCAL_OUTGOING, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.LOCAL_RECEIVED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.LOCAL_SENT, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.LOCAL_DOWNLOADED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.LOCAL_UPLOADED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		//roaming
		editor.putString(GlobalConstants.PersistenceConstants.ROAM_INCOMING, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.ROAM_OUTGOING, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.ROAM_RECEIVED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.ROAM_SENT, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.ROAM_DOWNLOADED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.ROAM_UPLOADED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		//plan
		//local
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_LOCAL_INCOMING, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_LOCAL_OUTGOING, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_LOCAL_RECEIVED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_LOCAL_SENT, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_LOCAL_DOWNLOADED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_LOCAL_UPLOADED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		//roam
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_ROAM_INCOMING, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_ROAM_OUTGOING, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_ROAM_RECEIVED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_ROAM_SENT, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_ROAM_DOWNLOADED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.putString(GlobalConstants.PersistenceConstants.PLAN_ROAM_UPLOADED, GlobalConstants.PersistenceConstants._RESET_VALUES);
		editor.commit();
		return true;
	}
	
}
