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
		final ListView categoriesList=(ListView)findViewById(R.id.listCategories);
		
		categoriesNameList = new ArrayList<String>();
		getCategoriesNames();
		
		//Create the Adapter to display ArrayList onto ListView.
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesNameList);
		
		//Set the Adapter onto the ListView.
		categoriesList.setAdapter(arrayAdapter);
		
		//Register onClickListener to handle click events on each item.
		categoriesList.setOnItemClickListener(new OnItemClickListener()
		{
			//Argument position gives the index of item which is clicked.
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
			{
				String selectedCategory=categoriesNameList.get(position);
				
				//TODO: Change to switch statement
				if(selectedCategory.equals("Pests"))
				{
					Intent intentPests = new Intent(getApplicationContext(),PestsActivity.class);
					startActivity(intentPests);
				}
				else if(selectedCategory.equals("Plant/Leaf Symptoms"))
				{
					Intent intentLeaves = new Intent(getApplicationContext(),PlantSymptomActivity.class);
					startActivity(intentLeaves);
				}
				else if(selectedCategory.equals("Tuber Symptoms"))
				{
					Intent intentTuber = new Intent(getApplicationContext(),TuberSymptomActivity.class);
					startActivity(intentTuber);
				}
				
			}

		});
		Log.d("Problem Determination", "onCreate() EXIT");
	}

	/*
	 * Populates the ArrayList with category values.
	 */
	private void getCategoriesNames() 
	{
		Log.d("Problem Determination", "getCategoriesNames() ENTRY");
		categoriesNameList.add("Pests");
		categoriesNameList.add("Plant/Leaf Symptoms");
		categoriesNameList.add("Tuber Symptoms");
		categoriesNameList.add("Tutorials");
		Log.d("Problem Determination", "getCategoriesNames() EXIT");
	}
	

}
