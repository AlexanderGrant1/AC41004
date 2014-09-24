package com.team8.potatodoctor.activities;
 

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
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
import com.team8.potatodoctor.activities.menu_bar_activities.ExitActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.ImageShareActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SearchActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SettingsActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UpdateActivity;
import com.team8.potatodoctor.adapters.ImageAdapterMain;
import com.team8.potatodoctor.models.AppUpdater;
 
/*
 * Populates and displays the list of Categories on the main screen.
 */
public class CategoriesListActivity extends Activity
{
	ArrayList<String> categoriesNameList; 
	
	/*
	 * Displays ArrayList of values onto the UI.
	 */
	public void onCreate(Bundle saveInstanceState)
	{
		Log.d("Problem Determination", "onCreate() ENTRY");
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_main);
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
		
		disableHardwareMenuKey();
		
		Log.d("Problem Determination", "onCreate() EXIT"); 
				
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
			
		// Get the SearchView and set the searchable configuration
	    //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    //SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	
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
