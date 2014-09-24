package com.team8.potatodoctor.activities.menu_bar_activities;

import java.lang.reflect.Field;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.database_objects.TutorialEntity;
import com.team8.potatodoctor.models.repositories.PestRepository;
import com.team8.potatodoctor.models.repositories.PlantLeafRepository;
import com.team8.potatodoctor.models.repositories.TuberRepository;
import com.team8.potatodoctor.models.repositories.TutorialRepository;
  
public class SearchActivity extends Activity {

	public TableLayout pestTable;// = (TableLayout)findViewById(R.id.pest_results);
	public TableLayout plantleafTable;// = (TableLayout)findViewById(R.id.plantleaf_results);
	public TableLayout tuberTable;// = (TableLayout)findViewById(R.id.tuber_results);
	public TableLayout tutorialTable;// = (TableLayout)findViewById(R.id.tutorial_results);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		Log.v("Problem Determination", "SearchActivity.onCreate() ENTRY");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Log.v("Problem Determination", "SearchActivity.onCreate() EXIT");
		
		final EditText searchBox = (EditText)findViewById(R.id.search_box);
		searchBox.addTextChangedListener(new TextWatcher() {

	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	        	//TODO Nothing
	        }

	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	        	String query = searchBox.getText().toString();
	        	searchDatabase(query);	        	
	        }

			@Override
			public void afterTextChanged(Editable s) {
				String query = searchBox.getText().toString();
	        	searchDatabase(query);
			} 

	    });
		
		
		pestTable = (TableLayout)findViewById(R.id.pest_results);
		plantleafTable = (TableLayout)findViewById(R.id.plantleaf_results);
		tuberTable = (TableLayout)findViewById(R.id.tuber_results);
		tutorialTable = (TableLayout)findViewById(R.id.tutorial_results);

		disableHardwareMenuKey();
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
	 * Takes entered query and search all database tables for matches.
	 */
	public void searchDatabase(String query)
	{
		LinkedList<PestEntity> pestResults = new LinkedList<PestEntity>();
		LinkedList<PlantLeafEntity> plantleafResults = new LinkedList<PlantLeafEntity>();
		LinkedList<TuberEntity> tuberResults = new LinkedList<TuberEntity>();
		LinkedList<TutorialEntity> videoResults = new LinkedList<TutorialEntity>();
		
		PestRepository pestSearcher = new PestRepository(this);
		PlantLeafRepository plantleafSearcher = new PlantLeafRepository(this);
		TuberRepository tuberSearcher = new TuberRepository(this);
		TutorialRepository videoSearcher = new TutorialRepository(this);
				
		pestResults = pestSearcher.searchPests(query);
		plantleafResults = plantleafSearcher.searchPlantLeafSymptoms(query);
		tuberResults = tuberSearcher.searchTubers(query);
		//videoResults = videoSearcher.searchTutorials(query);
		
		displaySearchResults(pestResults, plantleafResults, tuberResults, videoResults);
	}
	
	/*
	 * Recieves LinkList(s) for DB Entities and populate the corresponding tablelayouts.
	 */
	public void displaySearchResults(LinkedList<PestEntity> pests, LinkedList<PlantLeafEntity> plants, LinkedList<TuberEntity> tubers, LinkedList<TutorialEntity> tutorials)
	{
		pestTable.removeAllViews();
		plantleafTable.removeAllViews();
		tuberTable.removeAllViews();
		tutorialTable.removeAllViews();
		if(!pests.isEmpty())
		{
			//pestTable
			for(PestEntity pest : pests)
			{
				//Create new Table Row, to be added to pestTable.
				TableRow row = new TableRow(this);
				
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView pestObject = new TextView(this);
				pestObject.setText(pest.getName()); 
				
				//Add the Textview to the TableRow
			    row.addView(pestObject);
			    
			    //Add the Table Row to the Pest Table
			    pestTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
		}
		
		if(!plants.isEmpty())
		{
			//plantleafTable
			for(PlantLeafEntity plant : plants)
			{
				//Create new Table Row, to be added to plantleafTable.
				TableRow row = new TableRow(this);
				
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView plantObject = new TextView(this);
				plantObject.setText(plant.getName()); 
				
				//Add the Textview to the TableRow
			    row.addView(plantObject);
			    
			    //Add the Table Row to the PlantLeaf Table
			    plantleafTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
		}
		
		if(!tubers.isEmpty())
		{
			//tuberTable
			for(TuberEntity tuber : tubers)
			{
				//Create new Table Row, to be added to tuberTable.
				TableRow row = new TableRow(this);
				
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView tuberObject = new TextView(this);
				tuberObject.setText(tuber.getName()); 
				
				//Add the Textview to the TableRow
			    row.addView(tuberObject);
			    
			    //Add the Table Row to the Tuber Table
			    tuberTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
		}
		
		if(!tutorials.isEmpty())
		{
			//tutorialTable
			for(TutorialEntity tutorial : tutorials)
			{
				//Create new Table Row, to be added to tuberTable.
				TableRow row = new TableRow(this);
				
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView tutorialObject = new TextView(this);
				tutorialObject.setText(tutorial.getName()); 
				
				//Add the Textview to the TableRow
			    row.addView(tutorialObject);
			    
			    //Add the Table Row to the Tuber Table
			    tutorialTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
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
