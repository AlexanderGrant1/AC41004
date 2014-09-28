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
			TableRow headerRow = new TableRow(this);
			headerRow.setPadding(40, 25, 10, 5);
			headerRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView pestTitle = new TextView(this);
			pestTitle.setTextSize(24);
			pestTitle.setText("No results found."); 
			pestTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			headerRow.addView(pestTitle);
		    searchTable.addView(headerRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			return;
		}
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
			for(PestEntity pest : pests)
			{
				
				final int count = pestRepository.getIndexOfPestByName(pest.getName());;
				//Create new Table Row, to be added to pestTable.
				TableRow row = new TableRow(this);
				row.setPadding(40, 25, 10, 5);
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView pestObject = new TextView(this);
				pestObject.setTextSize(18);
				pestObject.setText(pest.getName()); 
				pestObject.setTextColor(Color.WHITE);
				row.setBackgroundColor(Color.DKGRAY);
				
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
			for(PlantLeafEntity plant : plants)
			{
				final int count = plantLeafRepository.getIndexOfPlantLeafByName(plant.getName());
				//Create new Table Row, to be added to plantleafTable.
				TableRow row = new TableRow(this);
				row.setPadding(40, 25, 10, 5);				
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				
				//Create a TextView to hold pest details.
				TextView plantObject = new TextView(this);
				plantObject.setTextSize(18);
				plantObject.setText(plant.getName()); 
				plantObject.setTextColor(Color.WHITE);
				row.setBackgroundColor(Color.DKGRAY);
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
				final int count = tuberRepository.getIndexOfTuberByName(tuber.getName());
				//Create new Table Row, to be added to tuberTable.
				TableRow row = new TableRow(this);
				row.setPadding(40, 25, 10, 5);
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				//Create a TextView to hold pest details.
				TextView tuberObject = new TextView(this);
				tuberObject.setTextSize(18);
				tuberObject.setText(tuber.getName()); 
				tuberObject.setTextColor(Color.WHITE);
				row.setBackgroundColor(Color.DKGRAY);
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
				final int count = tutorialRepository.getIndexOfTutorialByName(tutorial.getName());
				//Create new Table Row, to be added to tuberTable.
				TableRow row = new TableRow(this);
				row.setPadding(40, 25, 10, 5);
				row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				row.setBackgroundColor(Color.DKGRAY);

				//Create a TextView to hold pest details.
				TextView tutorialObject = new TextView(this);
				tutorialObject.setTextSize(18);
				tutorialObject.setText(tutorial.getName()); 
				tutorialObject.setTextColor(Color.WHITE);
				
				//Add the Textview to the TableRow
			    row.addView(tutorialObject);
			    row.setOnClickListener(new OnClickListener()
			    {

					@Override
					public void onClick(View v) {
						Intent tutorialActivity = new Intent(getApplicationContext(),TutorialActivity.class);
						tutorialActivity.putExtra("Position", count); //DB Table row index.
			        	
			    		startActivity(tutorialActivity); 
						
					}		    	
			    });
			    
			    //Add the Table Row to the Tuber Table
			    searchTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
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
