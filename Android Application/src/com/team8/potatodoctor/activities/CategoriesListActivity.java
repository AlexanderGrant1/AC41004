package com.team8.potatodoctor.activities;
 
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.team8.potatodoctor.activities.menu_bar_activities.UpdateActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UserGuideActivity;
import com.team8.potatodoctor.adapters.ImageAdapterMain;

/*
 * Populates and displays the list of Categories on the main screen.
 */
public class CategoriesListActivity extends Activity
{
	ArrayList<String> categoriesNameList;
	
	/*
	 * Display Categories and checks if the database is empty.
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle saveInstanceState)
	{
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_category_selection);
		
		//If the app has empty tables or fresh install/user cleared all app data
		//Bring up dialog asking user to Update.
		createCategories();
		
		if(!checkDatabase())
		{
			//Prompt user to update and disable features.
			alertUser();
		}	
	
		disableHardwareMenuKey();			
	}
	

	/*
	 * Creates the Menu Bar.
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	
		return true;
	}
	
	/*
	 * Event Handlers for Menu Bar.
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		boolean isUpdated = checkDatabase();
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
	        
	    case (R.id.action_userguide):	        
    		if(isUpdated)
    		{
    			this.startActivity(new Intent(this, UserGuideActivity.class));
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
				if(checkDatabase())
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
				else
				{
					Toast.makeText(getApplicationContext(), "Please update to proceed", Toast.LENGTH_LONG).show();
				}
			}

		}); 
	}
	
	/*
	 * Check if Database is completely empty I.e. fresh install/user cleared data.
	 * 
	 *  @return isDBUpdated represents if the Datbase is empty.
	 */
	private boolean checkDatabase()
	{
		Boolean isDBEmpty = true;
		
		File db = new File(getApplicationContext().getDatabasePath("potato.db").getAbsolutePath());
		isDBEmpty = db.exists();
		//Check if the db file on the device is null.
		//if any are null, then set isDBEmpty to false.
		
		return isDBEmpty;
	}
	
	/*
	 * Displays a Dialog asking user to update the application
	 * when the Database is null.
	 * 
	 * Referenced from: http://developer.android.com/guide/topics/ui/dialogs.html
	 */
	private void alertUser()
	{
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		
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
