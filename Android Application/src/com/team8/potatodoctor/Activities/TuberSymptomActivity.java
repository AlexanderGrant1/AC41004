package com.team8.potatodoctor.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.Activities.MenuBarActivities.ExitActivity;
import com.team8.potatodoctor.Activities.MenuBarActivities.ImageShareActivity;
import com.team8.potatodoctor.Activities.MenuBarActivities.SettingsActivity;
import com.team8.potatodoctor.Activities.MenuBarActivities.UpdateActivity;
import com.team8.potatodoctor.Adapters.TuberImageAdapter;

public class TuberSymptomActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		setupGridView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	/*
	 * Constructs the Grid View and populates with Images via the TuberImageAdapter.
	 */
	private void setupGridView()
	{
		//Locate Grid View from .xml Layout.
		GridView gridview = (GridView) findViewById(R.id.gridview);
		
		//Attach TuberImageAdapter and adds Images.
	    gridview.setAdapter(new TuberImageAdapter(this));

	    //Setup Event Listener to direct user to information page.
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {	        	
	        	
	        	Intent intentObjectDescription = new Intent(getApplicationContext(),ObjectDescriptionActivity.class);
	        	
	        	intentObjectDescription.putExtra("Type", "potato_Tuber"); //DB Table name.
	        	intentObjectDescription.putExtra("Position", position); //DB Table row index.
	        	
	    		startActivity(intentObjectDescription);        	
	        }
	    });
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
