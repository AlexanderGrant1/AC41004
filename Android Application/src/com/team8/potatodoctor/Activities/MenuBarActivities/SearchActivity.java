package com.team8.potatodoctor.Activities.MenuBarActivities;

import java.util.Arrays;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TableRow;
import android.widget.TextView;

import com.team8.potatodoctor.R;
 
public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		Log.v("Problem Determination", "SearchActivity.onCreate() ENTRY");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Log.v("Problem Determination", "SearchActivity.onCreate() EXIT");
		
		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if(Intent.ACTION_SEARCH.equals(intent.getAction()))
		{
			//Obtain user entered string
			String query = intent.getStringExtra(SearchManager.QUERY);
			Log.v("Search", "Query = "+query);
			
			//Do some code with query.
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    // Assumes current activity is the searchable activity
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
	    
		
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
	 * Main Search Query
	 */
	public void searchDatabase(String query)
	{
		
	}
	
}
