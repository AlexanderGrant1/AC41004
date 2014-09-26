package com.team8.potatodoctor.activities;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.menu_bar_activities.ImageShareActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SearchActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SettingsActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UpdateActivity;
import com.team8.potatodoctor.adapters.GalleryImageAdapter;
import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.models.repositories.PestRepository;
import com.team8.potatodoctor.models.repositories.PlantLeafRepository;
import com.team8.potatodoctor.models.repositories.TuberRepository;

/*
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
		
	//TextView to contain text for specific Pest/Disease.
	TextView textView;
	
    private GestureDetector gestureDetector;
	
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_description);
		pestRepository = new PestRepository(getApplicationContext());
		tuberRepository = new TuberRepository(getApplicationContext());
		plantLeafRepository = new PlantLeafRepository(getApplicationContext());
		String type = "";
		int position = 0;
		
		//Extract parameters from the intent.
	    Bundle extras = getIntent().getExtras();
	    if(extras !=null)
	    {
	    	type = extras.getString("Type");
	    	position = extras.getInt("Position");
	    }
	    
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
	    //Setup ImageGallery
	    setImageGallery();
	     
        //Find TextView and allow scrolling.
        textView = (TextView)findViewById(R.id.textViewItem);
        textView.setText(description);
        //textView.setMovementMethod(new ScrollingMovementMethod());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        
        disableHardwareMenuKey();
        
	}

  
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{	
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(), "Move left", Toast.LENGTH_LONG).show();
            }
            
            @Override
            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(), "Move Right", Toast.LENGTH_LONG).show();
            }
        };
		
		return true;
	}
	
	private void setImageGallery()
	{
		//Set up the Image Gallery.
	    Gallery gallery = (Gallery) findViewById(R.id.imageGallery);
        selectedImage=(ImageView)findViewById(R.id.imageSelected);
        gallery.setSpacing(1);
        gallery.setScaleX(1.7f);
        gallery.setScaleY(1.7f);
        gallery.setY(80f);
        Bundle extras = getIntent().getExtras();
        String type = extras.getString("Type");
        Log.w("hello","TYPE = "+type);
	    	if(type.equals("potato_Pest"))
	    	{
                PestEntity currentPest = pestRepository.getAllPests().get(extras.getInt("Position"));
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
	    		TuberEntity tuber = tuberRepository.getAllTubers().get(extras.getInt("Position"));
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
	    		PlantLeafEntity plantLeaf = plantLeafRepository.getAllPlantLeafs().get(extras.getInt("Position"));
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
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
             
        	    Bundle extras = getIntent().getExtras();
        	    String type;
        	    if(extras !=null)
        	    {
        	    	type = extras.getString("Type");
        	    	if(type.equals("potato_Pest"))
        	    	{
                        PestEntity currentPest = pestRepository.getAllPests().get(extras.getInt("Position"));
                        selectedImage.setImageURI(Uri.parse(currentPest.getPhotos().get(position).getFullyQualifiedPath()));
        	    	}
        	    	else if(type.equals("potato_Tuber"))
        	    	{
        	    		TuberEntity tuber = tuberRepository.getAllTubers().get(extras.getInt("Position"));
        	    		selectedImage.setImageURI(Uri.parse(tuber.getPhotos().get(position).getFullyQualifiedPath()));
        	    	}
        	    	else if(type.equals("potato_PlantLeaf"))
        	    	{
        	    		PlantLeafEntity plantLeaf = plantLeafRepository.getAllPlantLeafs().get(extras.getInt("Position"));
        	    		selectedImage.setImageURI(Uri.parse(plantLeaf.getPhotos().get(position).getFullyQualifiedPath()));
        	    	}
        	    	else
        	    	{
        	    		Log.w("hello", "not implemented");
        	    	}
        	    }

            }
        });
	}
	@Override
	public Intent getParentActivityIntent()
	{
		Bundle extras = getIntent().getExtras();
    	String type = extras.getString("Type");
    	if(type.equals("potato_Pest"))
    	{
    		return new Intent(this, PestsActivity.class);
    	}
    	else if(type.equals("potato_Tuber"))
    	{
    		return new Intent(this, TuberSymptomActivity.class);
    	}
    	else if(type.equals("potato_PlantLeaf"))
    	{
    		return new Intent(this, PlantSymptomActivity.class);
    	}
    	else
    	{
    		return new Intent(this, CategoriesListActivity.class);
    	}
	}
	
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
	    case (R.id.action_settings):
	        this.startActivity(new Intent(this, SettingsActivity.class));
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
}
