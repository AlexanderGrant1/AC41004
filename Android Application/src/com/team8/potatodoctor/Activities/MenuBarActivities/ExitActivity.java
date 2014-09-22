package com.team8.potatodoctor.activities.MenuBarActivities;

import com.team8.potatodoctor.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ExitActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exit);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId())
	    {
	    case (R.id.action_imageshare):
	        this.startActivity(new Intent(this, ImageShareActivity.class));
	        return true;
	    case (R.id.action_update):
	        this.startActivity(new Intent(this, UpdateActivity.class));
	        return true;
	    case (R.id.action_settings):
	        this.startActivity(new Intent(this, SettingsActivity.class));
	        return true;
	    case (R.id.action_exit):
	        this.startActivity(new Intent(this, ExitActivity.class));
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}