package com.team8.potatodoctor.activities;
 

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.menu_bar_activities.ImageShareActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SearchActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SettingsActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UpdateActivity;
import com.team8.potatodoctor.adapters.ImageAdapterMain;

/*
 * Populates and displays the list of Categories on the main screen.
 */
public class CategoriesListActivity extends Activity
{
	ArrayList<String> categoriesNameList;
	Boolean isUpdated = false;
	
	/*
	 * Displays ArrayList of values onto the UI.
	 */
	public void onCreate(Bundle saveInstanceState)
	{
		Log.d("Problem Determination", "onCreate() ENTRY");
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_main);
		
		//If the app has empty tables or fresh install/user cleared all app data
		//Bring up dialog asking user to Update.
		if(checkDatabase())
		{
			//Continue and create the UI.
			isUpdated = true;
			createCategories();
		}
		else
		{
			//Prompt user to update and disable features.
			isUpdated = false;
			alertUser();
		}	
		
		disableHardwareMenuKey();
		
		Log.d("Problem Determination", "onCreate() EXIT"); 
				
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
	    	if(isUpdated)
	    	{
	    		this.startActivity(new Intent(this, SearchActivity.class));
	    	}
	    	else
	    	{
	    		Toast.makeText(this, "Please update to proceed", Toast.LENGTH_LONG).show();
	    	}
	        return true;
	    case (R.id.action_imageshare):	    	 
	    	if(isUpdated)
	    	{
	    		this.startActivity(new Intent(this, ImageShareActivity.class));
	    	}
	    	else
	    	{
	    		Toast.makeText(this, "Please update to proceed", Toast.LENGTH_LONG).show();
	    	}
	        return true;
	    case (R.id.action_update):
	        this.startActivity(new Intent(this, UpdateActivity.class));
	        return true;
	    case (R.id.action_settings):	        
    		if(isUpdated)
    		{
    			this.startActivity(new Intent(this, SettingsActivity.class));
    		}
    		else
    		{
    			Toast.makeText(this, "Please update to proceed", Toast.LENGTH_LONG).show();
    		}
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
	
	/*
	 * Populate ListView with selectable categories.
	 */
	private void createCategories()
	{
		//Get the reference of ListViewCategories
				final GridView categoriesGrid=(GridView)findViewById(R.id.gridview_main);
				categoriesGrid.setAdapter(new ImageAdapterMain(this)); 

				//Register onClickListener to handle click events on each item.
				categoriesGrid.setOnItemClickListener(new OnItemClickListener()
				{
					//Argument position gives the index of item which is clicked.
					public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
					{

						if(position == 0)
						{
							Intent intentPests = new Intent(getApplicationContext(),PestsActivity.class);
							startActivity(intentPests);
						}
						else if(position == 1)
						{
							Intent intentLeaves = new Intent(getApplicationContext(),PlantSymptomActivity.class);
							startActivity(intentLeaves);
						}
						else if(position == 2)
						{
							Intent intentTuber = new Intent(getApplicationContext(),TuberSymptomActivity.class);
							startActivity(intentTuber);
						}
						else if(position == 3)
						{
							Intent intentVideo = new Intent(getApplicationContext(),VideoGridActivity.class);
							startActivity(intentVideo);
						}
					}

				}); 
	}
	
	/*
	 * Check if Database is completely empty i.e fresh install/user cleared data. 
	 */
	private boolean checkDatabase()
	{
		Boolean isDBUpdated = true;//true for live, false for testing.
		
		File db = new File(getApplicationContext().getDatabasePath("potato.db").getAbsolutePath());
		isDBUpdated = db.exists();
		//for each table in database, check if they're null.
		//if any are null, then set isUpdated to false.
		
		return isDBUpdated;
	}
	
	/*
	 * Referenced from: http://developer.android.com/guide/topics/ui/dialogs.html
	 */
	private void alertUser()
	{
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage("Please update Potato Doctor through using the Update feature or Google Play Store.")
		       .setTitle("Update Required")

		// 3. set the Positive button option
		.setPositiveButton("Update", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
				//Proceed and update.
				startActivity(new Intent(getBaseContext(),UpdateActivity.class));
			}
		}) //End of .setPositiveButton()
		
		// 4. set the Negative button option
		.setNeutralButton("Remind, me later", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing?				
			}
		})
		.setIcon(android.R.drawable.ic_dialog_alert)
		.show();
				
	} //End of alertUser()      
}
