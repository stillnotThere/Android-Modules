package com.acropolis.playground.module.telephony.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.acropolis.playground.module.telephony.minutes.R;

public class TelephonyActivity extends Activity
{
	public static Context context;
	public static Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		context = this.getApplicationContext();
		intent = this.getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.telephony, menu);
        return true;
    }

	public static Context getAppContext()
	{
		return TelephonyActivity.context;
	}

	public static Intent getAppIntent()
	{
		return TelephonyActivity.intent;
	}

}
