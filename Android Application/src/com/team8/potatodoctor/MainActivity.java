package com.team8.potatodoctor;

import java.io.IOException;
import java.util.LinkedList;
import org.json.JSONException;
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
		//DatabaseManager dbHelper = new DatabaseManager(getApplicationContext());
		//dbHelper.createTables();
		try
		{
			Log.w("hello",new HttpGetRequest().execute("http://beberry.lv/potato/api/pest").get());
			
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
