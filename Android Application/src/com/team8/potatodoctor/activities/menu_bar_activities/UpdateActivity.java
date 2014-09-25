package com.team8.potatodoctor.activities.menu_bar_activities;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.VideoGridActivity;
import com.team8.potatodoctor.models.AppUpdater;

public class UpdateActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		
		disableHardwareMenuKey();
		
		if(isNetworkConnected())
		{
			updateApplication();
		}
		else
		{
			Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
		
		 
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
	    case (R.id.action_search):
	        this.startActivity(new Intent(this, SearchActivity.class));
	        return true;
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
	
	/*
	 * Referenced from: http://stackoverflow.com/questions/9570237/android-check-internet-connection
	 */
	private boolean isNetworkConnected() 
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}   
	
	/*
	 * Prompt user to download update if available.
	 */
	private void updateApplication()
	{
		new AlertDialog.Builder(this)
		.setTitle("Update application")
		.setMessage("Are you sure you want to update this application? There may be extra data charges.")
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// continue with update
				AppUpdater apUp = new AppUpdater(getApplicationContext());

				try {
					apUp.updateDatabaseTables();
					apUp.updateLocalFiles();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Toast.makeText(getApplicationContext(), "Update completed", Toast.LENGTH_LONG).show();

				UpdateActivity.this.finish();

			}
		})
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				finish();
			}
		})
		.setIcon(android.R.drawable.ic_dialog_alert)
		.show();
	}
	
	/*
	 * Disable Hardware Menu Button on phones. Force Menu drop down on Action Bar.
	 */
	private void disableHardwareMenuKey()
	{
		try
		{
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}
	}
}
