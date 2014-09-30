package com.team8.potatodoctor.activities;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.menu_bar_activities.ImageShareActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SearchActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UserGuideActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UpdateActivity;
import com.team8.potatodoctor.adapters.TuberImageAdapter;

/**
 * Displays Gridview of Thumbnails for all Pests in the database.
 */
public class TuberSymptomActivity extends Activity
{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_selection);
		
		setupGridView();	
		disableHardwareMenuKey();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	/**
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
	        	
	        	//Add additional parameters to intent for queries and information.
	        	intentObjectDescription.putExtra("Type", "potato_Tuber"); //DB Table name.
	        	intentObjectDescription.putExtra("Position", position); //DB Table row index.
	        	
	    		startActivity(intentObjectDescription);        	
	        }
	    });
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
        switch (item.getItemId()) {
        case (R.id.action_home):
        	this.startActivity(new Intent(this, CategoriesListActivity.class));
        	this.finish();
        	return true;
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
	    case (R.id.action_userguide):
	        this.startActivity(new Intent(this, UserGuideActivity.class));
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
	
	/**
	 * Disable Hardware Menu Button on phones. Force Menu drop down on Action Bar.
	 * 
	 * Referenced from: http://stackoverflow.com/questions/9286822/how-to-force-use-of-overflow-menu-on-devices-with-menu-button
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
