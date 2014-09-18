package com.team8.potatodoctor;

import java.io.IOException;
import java.util.LinkedList;
import org.json.JSONException;

import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.Models.HttpGetRequest;
import com.team8.potatodoctor.Models.DataFetcher;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		Log.d("Problem Determination", "onCreate() ENTRY");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatabaseManager dbHelper = new DatabaseManager(getApplicationContext());
		dbHelper.createTables();
		try
		{
			String response = new HttpGetRequest().execute("http://beberry.lv/potato/api/pest").get();
			DataFetcher jsonParser = new DataFetcher();
			LinkedList<PestEntity> pests = jsonParser.parsePests(response);
			for(int i = 0; i < pests.size(); i++)
			{
				dbHelper.InsertPest(pests.get(i));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Intent intentCategoriesList = new Intent(getApplicationContext(),CategoriesListActivity.class);
		startActivity(intentCategoriesList);
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
