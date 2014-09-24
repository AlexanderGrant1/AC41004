<<<<<<< HEAD
package com.team8.potatodoctor.Activities.MenuBarActivities;
=======
package com.team8.potatodoctor.activities.MenuBarActivities;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
>>>>>>> 49a6e515221470020e4beb26a9d5e3fbd6158a46

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.Models.AppUpdater;

import com.team8.potatodoctor.R;

public class UpdateActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		
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
	        	

	        	Toast.makeText(getApplicationContext(), "Update complete!", Toast.LENGTH_LONG);
	        	  
	        	
	            UpdateActivity.this.finish();

	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
		
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
}
