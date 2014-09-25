package com.team8.potatodoctor.activities.menu_bar_activities;
 
import java.lang.reflect.Field;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.CategoriesListActivity;
import com.team8.potatodoctor.activities.ObjectDescriptionActivity;
import com.team8.potatodoctor.activities.PestsActivity;
import com.team8.potatodoctor.activities.PlantSymptomActivity;
import com.team8.potatodoctor.activities.TuberSymptomActivity;
import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.database_objects.TutorialEntity;
import com.team8.potatodoctor.models.repositories.PestRepository;
import com.team8.potatodoctor.models.repositories.PlantLeafRepository;
import com.team8.potatodoctor.models.repositories.TuberRepository;
import com.team8.potatodoctor.models.repositories.TutorialRepository;
  
public class SearchActivity extends Activity {

	public TableLayout searchTable;
	
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
		
		
		searchTable = (TableLayout)findViewById(R.id.search_results);

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
		LinkedList<TutorialEntity> tutorialResults = new LinkedList<TutorialEntity>();
		
		PestRepository pestSearcher = new PestRepository(this);
		PlantLeafRepository plantleafSearcher = new PlantLeafRepository(this);
		TuberRepository tuberSearcher = new TuberRepository(this);
		TutorialRepository tutorialSearcher = new TutorialRepository(this);
				
		pestResults = pestSearcher.searchPests(query);
		plantleafResults = plantleafSearcher.searchPlantLeafSymptoms(query);
		tuberResults = tuberSearcher.searchTubers(query);
		tutorialResults = tutorialSearcher.searchTutorials(query);
		if(!query.equals(""))
		{
			displaySearchResults(pestResults, plantleafResults, tuberResults, tutorialResults);
		}
		else
		{
			searchTable.removeAllViews();
		}
	}
	
	/*
	 * Recieves LinkList(s) for DB Entities and populate the corresponding tablelayouts.
	 */
	public void displaySearchResults(LinkedList<PestEntity> pests, LinkedList<PlantLeafEntity> plants, LinkedList<TuberEntity> tubers, LinkedList<TutorialEntity> tutorials)
	{
		searchTable.removeAllViews();
		if(!pests.isEmpty())
		{
			TableRow headerRow = new TableRow(this);
			headerRow.setPadding(40, 25, 10, 5);
			headerRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView pestTitle = new TextView(this);
			pestTitle.setTextSize(24);
			pestTitle.setText("Pests"); 
			pestTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			headerRow.addView(pestTitle);
		    searchTable.addView(headerRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//pestTable
		    int position = 0;
			for(PestEntity pest : pests)
			{
				final int count = position;
				//Create new Table Row, to be added to pestTable.
				TableRow row = new TableRow(this);
				row.setPadding(40, 25, 10, 5);
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView pestObject = new TextView(this);
				pestObject.setTextSize(18);
				pestObject.setText(pest.getName()); 
				
				//Add the Textview to the TableRow
			    row.addView(pestObject);
			    row.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
			        	Intent intentObjectDescription = new Intent(getApplicationContext(),ObjectDescriptionActivity.class);
			        	
			        	//Add additional parameters to intent for queries and information.
			        	intentObjectDescription.putExtra("Type", "potato_Pest"); //DB Table name.
			        	intentObjectDescription.putExtra("Position", count); //DB Table row index.
			        	
			    		startActivity(intentObjectDescription);     
						
					}
			    	
			    });
			    //Add the Table Row to the Pest Table
			    searchTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			    position++;
			}
		}
		
		if(!plants.isEmpty())
		{
			TableRow headerRow = new TableRow(this);
			headerRow.setPadding(40, 50, 10, 5);
			headerRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView plantTitle = new TextView(this);
			plantTitle.setTextSize(24);
			plantTitle.setText("Plant/Leaf Symptoms"); 
			plantTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			headerRow.addView(plantTitle);
		    searchTable.addView(headerRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//plantleafTable
		    int position = 0;
			for(PlantLeafEntity plant : plants)
			{
				final int count = position;
				//Create new Table Row, to be added to plantleafTable.
				TableRow row = new TableRow(this);
				row.setPadding(40, 25, 10, 5);				
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView plantObject = new TextView(this);
				plantObject.setTextSize(18);
				plantObject.setText(plant.getName()); 
				
				//Add the Textview to the TableRow
			    row.addView(plantObject);
			    row.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
			        	Intent intentObjectDescription = new Intent(getApplicationContext(),ObjectDescriptionActivity.class);
			        	
			        	//Add additional parameters to intent for queries and information.
			        	intentObjectDescription.putExtra("Type", "potato_PlantLeaf"); //DB Table name.
			        	intentObjectDescription.putExtra("Position", count); //DB Table row index.
			        	
			    		startActivity(intentObjectDescription);     
						
					}
			    	
			    });
			    //Add the Table Row to the PlantLeaf Table
			    searchTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			    position++;
			}
		}
		
		if(!tubers.isEmpty())
		{
			TableRow headerRow = new TableRow(this);
			headerRow.setPadding(40, 50, 10, 5);
			headerRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tuberTitle = new TextView(this);
			tuberTitle.setTextSize(24);
			tuberTitle.setText("Tubers"); 
			tuberTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			headerRow.addView(tuberTitle);
		    searchTable.addView(headerRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//tuberTable
		    int position = 0;
			for(TuberEntity tuber : tubers)
			{
				final int count = position;
				//Create new Table Row, to be added to tuberTable.
				TableRow row = new TableRow(this);
				row.setPadding(40, 25, 10, 5);
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView tuberObject = new TextView(this);
				tuberObject.setTextSize(18);
				tuberObject.setText(tuber.getName()); 
				
				//Add the Textview to the TableRow
			    row.addView(tuberObject);
			    
			    row.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
			        	Intent intentObjectDescription = new Intent(getApplicationContext(),ObjectDescriptionActivity.class);
			        	
			        	//Add additional parameters to intent for queries and information.
			        	intentObjectDescription.putExtra("Type", "potato_Tuber"); //DB Table name.
			        	intentObjectDescription.putExtra("Position", count); //DB Table row index.
			        	
			    		startActivity(intentObjectDescription);     
						
					}
			    	
			    });
			    //Add the Table Row to the Tuber Table
			    searchTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			    position++;
			}
		}
		
		if(!tutorials.isEmpty())
		{
			TableRow headerRow = new TableRow(this);
			headerRow.setPadding(40, 50, 10, 5);
			headerRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView tutorialTitle = new TextView(this);
			tutorialTitle.setTextSize(24);
			tutorialTitle.setText("Tutorials"); 
			tutorialTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			headerRow.addView(tutorialTitle);
		    searchTable.addView(headerRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//tutorialTable
		    int position = 0;
			for(TutorialEntity tutorial : tutorials)
			{
				final int count = position;
				//Create new Table Row, to be added to tuberTable.
				TableRow row = new TableRow(this);
				row.setPadding(40, 25, 10, 5);
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView tutorialObject = new TextView(this);
				tutorialObject.setTextSize(18);
				tutorialObject.setText(tutorial.getName()); 
				
				//Add the Textview to the TableRow
			    row.addView(tutorialObject);
			    
			    position++;
			    
			    //Add the Table Row to the Tuber Table
			    searchTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
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
