package com.team8.potatodoctor.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.Models.Repositories.PestRepository;
import com.team8.potatodoctor.Models.Repositories.PlantLeafRepository;
import com.team8.potatodoctor.Models.Repositories.TuberRepository;
import com.team8.potatodoctor.adapters.GalleryImageAdapter;

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
	
	//Array of images for the selected image.
	private Integer[] mImageIds = {
		R.drawable.ic_shiba1,
		R.drawable.ic_shiba2,
		R.drawable.ic_shiba3,
		R.drawable.ic_shiba4,
	};
	
	//TextView to contain text for specific Pest/Disease.
	TextView textView;
	
	
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
        textView.setMovementMethod(new ScrollingMovementMethod());
	}

  
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	private void setImageGallery()
	{
		//Set up the Image Gallery.
	    Gallery gallery = (Gallery) findViewById(R.id.imageGallery);
        selectedImage=(ImageView)findViewById(R.id.imageSelected);
        gallery.setSpacing(1);
        Bundle extras = getIntent().getExtras();
        
        PestEntity currentPest = pestRepository.getAllPests().get(extras.getInt("Position"));
        gallery.setAdapter(new GalleryImageAdapter(this,currentPest));
        if(currentPest.getPhotos().size() > 0)
        {
        	selectedImage.setImageURI(Uri.parse(currentPest.getPhotos().get(0).getName()));
        }
        

        //Set up Event Listener for Images.
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
             
                Bundle extras = getIntent().getExtras();
                
                PestEntity currentPest = pestRepository.getAllPests().get(extras.getInt("Position"));
                selectedImage.setImageURI(Uri.parse(currentPest.getPhotos().get(position).getName()));
            }
        });
	}
}
