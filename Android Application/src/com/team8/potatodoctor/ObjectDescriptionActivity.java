package com.team8.potatodoctor;

import java.io.File;

import com.team8.adapters.GalleryImageAdapter;
import com.team8.adapters.PlantImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/*
 * Generalised class to extract information from the database related to a specific Pest/Symptom. 
 */
public class ObjectDescriptionActivity extends Activity
{
	//ImageView for full sized image when selected.
	ImageView selectedImage;
	
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
		
		String type = "";
		int position = 0;
		
		//Extract parameters from the intent.
	    Bundle extras = getIntent().getExtras();
	    if(extras !=null)
	    {
	    	type = extras.getString("Type");
	    	position = extras.getInt("Position");
	    }

	    //Set label on the Action Bar with Pest/Symptom Name.
	    //TODO set label.
	    
	    //Setup ImageGallery
	    setImageGallery();
	    
        //Find TextView and allow scrolling.
        textView = (TextView)findViewById(R.id.textViewItem);
        textView.setMovementMethod(new ScrollingMovementMethod());
	}

  
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		/*
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		*/
		return true;
	}
	
	private void setImageGallery()
	{
		//Set up the Image Gallery.
	    Gallery gallery = (Gallery) findViewById(R.id.imageGallery);
        selectedImage=(ImageView)findViewById(R.id.imageSelected);
        gallery.setSpacing(1);
        gallery.setAdapter(new GalleryImageAdapter(this));
        selectedImage.setImageResource(mImageIds[0]);

        //Set up Event Listener for Images.
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
             
                // Show the selected Image
                selectedImage.setImageResource(mImageIds[position]);
            }
        });
	}
}