package com.team8.potatodoctor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;
import com.team8.potatodoctor.Models.AppUpdater;
import com.team8.potatodoctor.Models.HttpGetRequest;
import com.team8.potatodoctor.Models.DataFetcher;
import com.team8.potatodoctor.Models.LocalDbUpdater;
import com.team8.potatodoctor.Models.Repositories.PestRepository;
import com.team8.potatodoctor.Models.Repositories.PlantLeafRepository;
import com.team8.potatodoctor.Models.Repositories.TuberRepository;

import android.os.Bundle;
import android.app.ActionBar;
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

		AppUpdater appUpdater = new AppUpdater(getApplicationContext());
		try {
			appUpdater.updateDatabaseTables();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Failed to update the database", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		PestRepository pestRepository = new PestRepository(getApplicationContext());
		TuberRepository tuberRepository = new TuberRepository(getApplicationContext());
		PlantLeafRepository plantLeafRepository = new PlantLeafRepository(getApplicationContext());
		Log.w("hello","hello1");
		LocalDbUpdater localDb = new LocalDbUpdater(getApplicationContext());
		try {
			localDb.updatePestTables();
			localDb.updatePlantLeafTables();
			localDb.updateTuberTables();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(PestEntity pest : pestRepository.getAllPests())
		{
			Log.w("hello",pest.getName());
			for(PhotoEntity photo : pest.getPhotos())
			{
				Log.w("hello", "Photos: "+photo.getName());
			}
		}
		
		for(PlantLeafSymptomsEntity plantLeaf : plantLeafRepository.getAllPlantLeafs())
		{
			Log.w("hello",plantLeaf.getName());
			for(PhotoEntity photo : plantLeaf.getPhotos())
			{
				Log.w("hello", "Photos: "+photo.getName());
			}
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
