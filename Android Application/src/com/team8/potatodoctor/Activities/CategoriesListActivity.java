package com.team8.potatodoctor.Activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.Adapters.ImageAdapterMain;
 
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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
