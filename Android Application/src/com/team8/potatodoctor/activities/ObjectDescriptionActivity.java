package com.team8.potatodoctor.activities;

import java.lang.reflect.Field;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.menu_bar_activities.ImageShareActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SearchActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UpdateActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UserGuideActivity;
import com.team8.potatodoctor.adapters.GalleryImageAdapter;
import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.database_objects.TutorialEntity;
import com.team8.potatodoctor.models.repositories.PestRepository;
import com.team8.potatodoctor.models.repositories.PlantLeafRepository;
import com.team8.potatodoctor.models.repositories.TuberRepository;

/**
 * Generalised class to extract information from the database related to a specific Pest/Symptom. 
 */
@SuppressWarnings("deprecation")
public class ObjectDescriptionActivity extends Activity
{
	//ImageView for full sized image when selected.
	ImageView selectedImage;
	
	private PestRepository pestRepository;
	private TuberRepository tuberRepository;
	private PlantLeafRepository plantLeafRepository;
	
	//Navigation buttons to other objects.
	Button rightButton;
	Button leftButton;
	TableLayout tutorialLayout;
	
	//TextView to contain text for specific Pest/Disease.
	TextView textView;
	String type = "";
	int position = 0;
	
	/**
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_description);
		pestRepository = new PestRepository(getApplicationContext());
		tuberRepository = new TuberRepository(getApplicationContext());
		plantLeafRepository = new PlantLeafRepository(getApplicationContext());
		
		tutorialLayout = (TableLayout)findViewById(R.id.tutorials);
	
		//Extract parameters from the intent.
	    Bundle extras = getIntent().getExtras();
	    if(extras !=null)
	    {
	    	type = extras.getString("Type");
	    	position = extras.getInt("Position");
	    }
	    
		displayObjectDetails();
		createLeftRightButtons();	    
	   
        getActionBar().setDisplayHomeAsUpEnabled(true);
        disableHardwareMenuKey();    
     
	}
	
	/**
	 * Adds related tutorial videos to the table.
	 * @param name Name of the tutorial video
	 * @param position Position index of the table row
	 */
	private void addRelatedTutorial(String name, final int position)
	{
		TableRow row = new TableRow(this);
		row.setPadding(40, 25, 10, 5);

		//Create a TextView to hold pest details.
		TextView tutorialObject = new TextView(this);
		tutorialObject.setTextSize(18);
		tutorialObject.setText(name); 
		tutorialObject.setTextColor(Color.WHITE);
		
		//Add the Textview to the TableRow
	    row.addView(tutorialObject);
	    row.setOnClickListener(new OnClickListener()
	    {

			@Override
			public void onClick(View v) {
				Intent tutorialActivity = new Intent(getApplicationContext(),TutorialActivity.class);
				tutorialActivity.putExtra("Position", position); //DB Table row index.
	        	
	    		startActivity(tutorialActivity); 
				
			}		    	
	    });
	    tutorialLayout.addView(row);
	}
	
	/**
	 * Create the Left/Right Navigation buttons.
	 */
	private void createLeftRightButtons() 
	{
		leftButton = (Button)findViewById(R.id.leftButton);
	    rightButton = (Button)findViewById(R.id.rightButton);
	    updateLeftRightButtons();
	    
	    leftButton.setOnClickListener(new OnClickListener()
	    {
			@Override
			public void onClick(View v) {
				moveLeft();				
			}
	    });
	    

	    rightButton.setOnClickListener(new OnClickListener()
	    {
			@Override
			public void onClick(View v) {
				moveRight();
				
			}
	    });		
	}
	
	/**
	 * Check if there is an previous object in the list to display.
	 * 
	 * @return true if position is not 0.
	 */
	private boolean canMoveLeft()
	{
		return position > 0;
	}
		
	/**
	 * Check if there is a child object in the list to display.
	 *  
	 * @return true if there is a child object.
	 */
	private boolean canMoveRight()
	{
		Boolean canMoveRight = true;
		
	    if(type.equals("potato_PlantLeaf"))
	    {
	    	if(position == plantLeafRepository.getAllPlantLeafs().size() - 1)
	    	{
	    		canMoveRight = false;
	    	}
	    }
	    else if(type.equals("potato_Pest"))
	    {
	    	if(position == pestRepository.getAllPests().size() - 1)
	    	{
	    		canMoveRight = false;
	    	}
	    }
	    else if(type.equals("potato_Tuber"))
	    {
	    	if(position == tuberRepository.getAllTubers().size() - 1)
	    	{
	    		canMoveRight = false;
	    	}
	    }
	    return canMoveRight;
	}
		
	/**
	 * Display the previous object in the list if exists.
	 */
	public void moveLeft()
	{
		if(canMoveLeft())
		{
			position--;
			updateLeftRightButtons();
			displayObjectDetails();	
		}
	}
	
	/**
	 * Display the next object in the list if exists.
	 */
	public void moveRight()
	{
		if(canMoveRight())
		{
			position++;
			updateLeftRightButtons();
			displayObjectDetails();	
		}
	}
	
	/**
	 * Show/Hides if end of list is reached.
	 */
	public void updateLeftRightButtons()
	{
		leftButton.setVisibility(View.VISIBLE);
		rightButton.setVisibility(View.VISIBLE);
		if(!canMoveLeft())
		{
			leftButton.setVisibility(View.INVISIBLE);
		}
		if(!canMoveRight())
		{
			rightButton.setVisibility(View.INVISIBLE);
		}
	}	
	
	/**
	 * Displays the contents of the object onto the screen.
	 * Populates the image gallery and text.
	 */
	public void displayObjectDetails()
	{
	    String description = "";
	    String title = "";
	    
	    //Set the Title and Description depending on the intent parameters.
	    if(type.equals("potato_PlantLeaf"))
	    {
	    	title = plantLeafRepository.getAllPlantLeafs().get(position).getName();
	    	description = plantLeafRepository.getAllPlantLeafs().get(position).getDescription();
	    }
	    else if(type.equals("potato_Pest"))
	    {
	    	title = pestRepository.getAllPests().get(position).getName();
	    	description = pestRepository.getAllPests().get(position).getDescription();
	    }
	    else if(type.equals("potato_Tuber"))
	    {
	    	title = tuberRepository.getAllTubers().get(position).getName();
	    	description = tuberRepository.getAllTubers().get(position).getDescription();
	    }
	    
	    setTitle(title);
	    setImageGallery();
	    displayRelatedTutorials();
	    
        //Find TextView and allow scrolling.
        textView = (TextView)findViewById(R.id.textViewItem);
        textView.setText(description);
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
	 * Grab an entity and populate the Image Gallery with the first object.
	 */
	private void setImageGallery()
	{
		//Set up the Image Gallery.
	    Gallery gallery = (Gallery) findViewById(R.id.imageGallery);
        selectedImage=(ImageView)findViewById(R.id.imageSelected);
        gallery.setSpacing(1);
        gallery.setScaleX(1.7f);
        gallery.setScaleY(1.7f);
        gallery.setY(80f);
    	if(type.equals("potato_Pest"))
    	{
            PestEntity currentPest = pestRepository.getAllPests().get(position);
            gallery.setAdapter(new GalleryImageAdapter(this,currentPest));
            if(currentPest.getPhotos().size() > 0)
            {
            	selectedImage.setImageURI(Uri.parse(currentPest.getPhotos().get(0).getFullyQualifiedPath()));
            }
            else
            {
            	selectedImage.setImageResource(R.drawable.ic_default);
            }
             
    	} 
    	else if(type.equals("potato_Tuber"))
    	{
    		TuberEntity tuber = tuberRepository.getAllTubers().get(position);
            gallery.setAdapter(new GalleryImageAdapter(this,tuber));
    		if(tuber.getPhotos().size() > 0)
    		{
    			selectedImage.setImageURI(Uri.parse(tuber.getPhotos().get(0).getFullyQualifiedPath()));
    		}
            else
            {
            	selectedImage.setImageResource(R.drawable.ic_default);
            }
    	}
    	else if(type.equals("potato_PlantLeaf"))
    	{
    		PlantLeafEntity plantLeaf = plantLeafRepository.getAllPlantLeafs().get(position);
            gallery.setAdapter(new GalleryImageAdapter(this,plantLeaf));
    		if(plantLeaf.getPhotos().size() > 0)
    		{
    			selectedImage.setImageURI(Uri.parse(plantLeaf.getPhotos().get(0).getFullyQualifiedPath()));
    		}
            else
            {
            	selectedImage.setImageResource(R.drawable.ic_default);
            }
    	}
    
	    //Set up Event Listener for Images.
	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
		    	if(type.equals("potato_Pest"))
		    	{
	                PestEntity currentPest = pestRepository.getAllPests().get(position);
	                selectedImage.setImageURI(Uri.parse(currentPest.getPhotos().get(pos).getFullyQualifiedPath()));
		    	}
		    	else if(type.equals("potato_Tuber"))
		    	{
		    		TuberEntity tuber = tuberRepository.getAllTubers().get(position);
		    		selectedImage.setImageURI(Uri.parse(tuber.getPhotos().get(pos).getFullyQualifiedPath()));
		    	}
		    	else if(type.equals("potato_PlantLeaf"))
		    	{
		    		PlantLeafEntity plantLeaf = plantLeafRepository.getAllPlantLeafs().get(position);
		    		selectedImage.setImageURI(Uri.parse(plantLeaf.getPhotos().get(pos).getFullyQualifiedPath()));
		    	}
	        }
	    });
	}
	
	/**
	 * Display the related tutorial videos if exists in the object.
	 */
	private void displayRelatedTutorials()
	{
		tutorialLayout.removeAllViews();
		LinkedList<TutorialEntity> tutorials = new LinkedList<TutorialEntity>();
    	if(type.equals("potato_Pest"))
    	{
            PestEntity currentPest = pestRepository.getAllPests().get(position);
            if(currentPest.getTutorials().size() > 0)
            {
            	tutorials = currentPest.getTutorials();
            }
             
    	} 
    	else if(type.equals("potato_Tuber"))
    	{
    		TuberEntity tuber = tuberRepository.getAllTubers().get(position);
            if(tuber.getTutorials().size() > 0)
            {
            	tutorials = tuber.getTutorials();
            }
    	}
    	else if(type.equals("potato_PlantLeaf"))
    	{
    		PlantLeafEntity plantLeaf = plantLeafRepository.getAllPlantLeafs().get(position);
            if(plantLeaf.getTutorials().size() > 0)
            {
            	tutorials = plantLeaf.getTutorials();
            }
    	}
    	if(tutorials.size() > 0)
    	{
    		TextView tutorialTitle = new TextView(getApplicationContext());
    		tutorialTitle.setText("Related Tutorials");
    		tutorialTitle.setTextSize(24);
    		tutorialTitle.setTextColor(Color.parseColor("#FFFFFF"));
    		tutorialTitle.setGravity(Gravity.CENTER_HORIZONTAL);
    		tutorialLayout.addView(tutorialTitle);
    		tutorialLayout.addView(new TextView(getApplicationContext()));
    	}
    	for(TutorialEntity tutorial : tutorials)
		{
    		TextView relatedTutorial = new TextView(getApplicationContext());
    		relatedTutorial.setText(tutorial.getName());
    		relatedTutorial.setTextSize(18);
    		relatedTutorial.setTextColor(Color.parseColor("#0000AA"));
    		relatedTutorial.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    		relatedTutorial.setOnClickListener(new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					Intent tutorialActivity = new Intent(getApplicationContext(),TutorialActivity.class);
					tutorialActivity.putExtra("Position", position); //DB Table row index.
		        	
		    		startActivity(tutorialActivity); 
				}
    			
    		});
    		tutorialLayout.addView(relatedTutorial);
		}
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
