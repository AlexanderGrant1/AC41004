package com.team8.potatodoctor.activities.menu_bar_activities;
 
import java.lang.reflect.Field;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.team8.potatodoctor.activities.TutorialActivity;
import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.database_objects.TutorialEntity;
import com.team8.potatodoctor.models.repositories.PestRepository;
import com.team8.potatodoctor.models.repositories.PlantLeafRepository;
import com.team8.potatodoctor.models.repositories.TuberRepository;
import com.team8.potatodoctor.models.repositories.TutorialRepository;

/**
 * Searches the local database for matches with the user's search query.
 * Returns results separated into tables.
 */
public class SearchActivity extends Activity {

	public TableLayout searchTable;
	private TuberRepository tuberRepository;
	private PlantLeafRepository plantLeafRepository;
	private PestRepository pestRepository;
	private TutorialRepository tutorialRepository;
	private String query = "";
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		tuberRepository = new TuberRepository(getApplicationContext());
		plantLeafRepository = new PlantLeafRepository(getApplicationContext());
		pestRepository = new PestRepository(getApplicationContext());
		tutorialRepository = new TutorialRepository(getApplicationContext());
		
		final EditText searchBox = (EditText)findViewById(R.id.search_box);
		searchBox.addTextChangedListener(new TextWatcher() {

	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	        	//TODO Nothing
	        }

	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
				query = searchBox.getText().toString();
		    	searchDatabase(query);     		    	
	        }

			@Override
			public void afterTextChanged(Editable s) {
				//String query = searchBox.getText().toString();
	        	//searchDatabase(query);
			} 
	    });
			
		searchTable = (TableLayout)findViewById(R.id.search_results);
		getActionBar().setDisplayHomeAsUpEnabled(true);
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
	    	//this.startActivity(new Intent(this, SearchActivity.class));
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
	 * Takes entered query and search all database tables for matches.
	 */
	public void searchDatabase(String query)
	{
		query = query.replace("'", "");
		LinkedList<PestEntity> pestResults = new LinkedList<PestEntity>();
		LinkedList<PlantLeafEntity> plantleafResults = new LinkedList<PlantLeafEntity>();
		LinkedList<TuberEntity> tuberResults = new LinkedList<TuberEntity>();
		LinkedList<TutorialEntity> tutorialResults = new LinkedList<TutorialEntity>();
		
				
		pestResults = pestRepository.searchPests(query);
		plantleafResults = plantLeafRepository.searchPlantLeafSymptoms(query);
		tuberResults = tuberRepository.searchTubers(query);
		tutorialResults = tutorialRepository.searchTutorials(query);
		if(!query.equals(""))
		{
			displaySearchResults(pestResults, plantleafResults, tuberResults, tutorialResults);
		}
		else
		{
			searchTable.removeAllViews();
		}
	}
	
	/**
	 * Recieves LinkList(s) for DB Entities and populate the corresponding tablelayouts.
	 */
	public void displaySearchResults(LinkedList<PestEntity> pests, LinkedList<PlantLeafEntity> plants, LinkedList<TuberEntity> tubers, LinkedList<TutorialEntity> tutorials)
	{
		searchTable.removeAllViews();
		if(pests.isEmpty() && plants.isEmpty() && tubers.isEmpty() && tutorials.isEmpty())
		{
			createTableSectionHeader("No search results found.");
			return;
		}
		if(!pests.isEmpty())
		{
			createTableSectionHeader("Pests");
			
		    //pestTable
			for(PestEntity pest : pests)
			{
				final int count = pestRepository.getIndexOfPestByName(pest.getName());
				createSearchResult(pest.getName(),"potato_Pest",count);
			}
		}
		
		if(!plants.isEmpty())
		{
			createTableSectionHeader("Plant/Leaf Symptoms");
		    
			//plantleafTable
			for(PlantLeafEntity plant : plants)
			{
				final int count = plantLeafRepository.getIndexOfPlantLeafByName(plant.getName());
				createSearchResult(plant.getName(),"potato_PlantLeaf",count);
			}
		}
		
		if(!tubers.isEmpty())
		{
			createTableSectionHeader("Tubers");
		    
			//tuberTable
			for(TuberEntity tuber : tubers)
			{
				final int count = tuberRepository.getIndexOfTuberByName(tuber.getName());
				createSearchResult(tuber.getName(),"potato_Tuber",count);
			}
		}
		
		if(!tutorials.isEmpty())
		{
			createTableSectionHeader("Tutorials");
			for(TutorialEntity tutorial : tutorials)
			{
				final int count = tutorialRepository.getIndexOfTutorialByName(tutorial.getName());
				//Create new Table Row, to be added to tuberTable.
			}
		}		
	}
	
	/** Adds a header to the search display
	 * 
	 * @param title The title displayed for the header
	 */
	private void createTableSectionHeader(String title) {
		TableRow headerRow = new TableRow(this);
		headerRow.setPadding(40, 50, 10, 5);
		headerRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		TextView headerTitle = new TextView(this);
		
		headerTitle.setTextSize(24);
		headerTitle.setText(title); 
		headerTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		headerRow.addView(headerTitle);
		
		searchTable.addView(headerRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}
	
	/** Adds a search result to the search display
	 * 
	 * @param name The name to display on the search result
	 * @param type The type passed to the object description page
	 * @param position The index of the object in the object description page
	 */
	private void createSearchResult(String name, final String type, final int position)
	{
		//Create new Table Row, to be added to pestTable.
		TableRow row = new TableRow(this);
		row.setPadding(40, 25, 10, 5);
		row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		//Create a TextView to hold pest details.
		TextView searchResult = new TextView(this);
		searchResult.setTextSize(18);
		searchResult.setText(name); 
		searchResult.setTextColor(Color.WHITE);
		row.setBackgroundColor(Color.DKGRAY);
		
		//Add the Textview to the TableRow
	    row.addView(searchResult);
	    row.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
	        	Intent intentObjectDescription = new Intent(getApplicationContext(),ObjectDescriptionActivity.class);
	        	
	        	//Add additional parameters to intent for queries and information.
	        	intentObjectDescription.putExtra("Type", type); //DB Table name.
	        	intentObjectDescription.putExtra("Position", position); //DB Table row index.
	        	
	    		startActivity(intentObjectDescription);     					
			}			    	
	    });
	    //Add the Table Row to the Pest Table
	    searchTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
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
