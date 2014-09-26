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
import android.os.Looper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.CategoriesListActivity;
import com.team8.potatodoctor.models.AppUpdater;

public class UpdateActivity extends Activity{
	ProgressBar spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		spinner = (ProgressBar)findViewById(R.id.progress);
		spinner.setVisibility(View.INVISIBLE);
		disableHardwareMenuKey();
		//TextView instructions = (TextView)findViewById(R.id.updateInstructions);
		//Check for internet connection before proceeding.
		if(isNetworkConnected())
		{
			updateApplication();
			//doUpdate();

		}
		else
		{
			showUpdateNetworkErrorDialog();
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
        switch (item.getItemId()) {
        case android.R.id.home:
            this.finish();
            return true;
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
	    	Intent intent = new Intent(Intent.ACTION_MAIN); 
	    	intent.addCategory(Intent.CATEGORY_HOME);
	    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    	startActivity(intent);
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
	 * Display dialog to connect to internet.
	 */
	public void showUpdateNetworkErrorDialog()
	{
		// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage("This feature requires connectivity to the internet. Please connect to a Wi-Fi or turn on Mobile Data.")
				       .setTitle("No Internet Connection Detected")

				// 3. set the Positive button option
				.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which) {
						//Close the activity
						finish();
					}
				}) //End of .setPositiveButton()
								
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();				
	}
	
	/*
	 * Prompt user to download update if available.
	 */
	private void updateApplication()
	{
		//new AlertDialog.Builder(this)
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Update application");
		builder.setMessage("Are you sure you want to update this application? There may be extra data charges.");
		builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// continue with update
				//doUpdate();

				//Toast.makeText(getApplicationContext(), "Update completed", Toast.LENGTH_LONG).show();
				//startActivity(new Intent(getBaseContext(),CategoriesListActivity.class)); 
				//finish();
				spinner.setVisibility(View.VISIBLE);

				dialog.dismiss();
			    new Thread(new Runnable() {
			        public void run() {
			        	Looper.prepare();
			        	doUpdate();
			        }
			    }).start();

			}
		})
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				startActivity(new Intent(getBaseContext(),CategoriesListActivity.class)); 
				finish();
			}
		})
		.setIcon(android.R.drawable.ic_dialog_alert)
		.show();
	}
	
	private void doUpdate()
	{
		//Toast.makeText(getApplicationContext(), "Updating...", Toast.LENGTH_LONG).show();
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
		
		Runnable show_toast = new Runnable()
		{
		    public void run()
		    {
		        Toast.makeText(UpdateActivity.this, "Update completed", Toast.LENGTH_LONG)
		                    .show();
		    }
		};
		
		UpdateActivity.this.runOnUiThread(show_toast);
		finish();
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
