package com.team8.potatodoctor;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

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
								
				//TODO: Change to switch statement
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
					Intent intentTuber = new Intent(getApplicationContext(),TuberSymptomActivity.class);
					startActivity(intentTuber);
				}
			}

		});
		Log.d("Problem Determination", "onCreate() EXIT");
	}
	

}
