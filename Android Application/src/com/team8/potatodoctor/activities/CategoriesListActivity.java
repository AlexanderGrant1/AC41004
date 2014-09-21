package com.team8.potatodoctor.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.Models.AppUpdater;
import com.team8.potatodoctor.Models.MediaFetcher;
import com.team8.potatodoctor.Models.Repositories.PestRepository;
import com.team8.potatodoctor.adapters.ImageAdapterMain;

/*
 * Populates and displays the list of Categories on the main screen.
 */
public class CategoriesListActivity extends Activity
{
	ArrayList<String> categoriesNameList;
	
	/*
	 * Displays ArrayList of values onto the UI.
	 */
	public void onCreate(Bundle saveInstanceState)
	{
		Log.d("Problem Determination", "onCreate() ENTRY");
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_main);
		//Get the reference of ListViewCategories
		final GridView categoriesGrid=(GridView)findViewById(R.id.gridview_main);
		categoriesGrid.setAdapter(new ImageAdapterMain(this)); 
		
		updateDB(saveInstanceState);
		
		//Create the Adapter to display ArrayList onto ListView.
		//ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesNameList);
		
		//Set the Adapter onto the ListView.
		//categoriesList.setAdapter(arrayAdapter);
		
		//Register onClickListener to handle click events on each item.
		categoriesGrid.setOnItemClickListener(new OnItemClickListener()
		{
			//Argument position gives the index of item which is clicked.
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
			{

				if(position == 0)
				{
					Intent intentPests = new Intent(getApplicationContext(),PestsActivity.class);
					startActivity(intentPests);
				}
				else if(position == 1)
				{
					Intent intentLeaves = new Intent(getApplicationContext(),PlantSymptomActivity.class);
					startActivity(intentLeaves);
				}
				else if(position == 2)
				{
					Intent intentTuber = new Intent(getApplicationContext(),TuberSymptomActivity.class);
					startActivity(intentTuber);
				}
				else if(position == 3)
				{
					Intent intentVideo = new Intent(getApplicationContext(),VideoGridActivity.class);
					startActivity(intentVideo);
				}
			}

		});
		Log.d("Problem Determination", "onCreate() EXIT");
	}
	
	private void updateDB(Bundle savedInstanceState)
	{
		new MediaFetcher().execute("http://www.rentokil.co.za/blog/wp-content/uploads/2013/08/Common-house-fly.jpg","Pests");
		AppUpdater appUpdater = new AppUpdater(getApplicationContext());
		try {
			appUpdater.updateDatabaseTables();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Failed to update the database", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} 
		PestRepository pestRepository = new PestRepository(getApplicationContext());
		for(PestEntity pest : pestRepository.getAllPests())
		{
			Log.w("hello",pest.getName());
			for(PhotoEntity photo : pest.getPhotos())
			{
				Log.w("hello", "Photos: "+photo.getName());
			}
		}
	}

}
