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
		DatabaseManager dbHelper = new DatabaseManager(getApplicationContext());
		dbHelper.createTables();
		dbHelper.insertSession("hello", "mike coutts");
		Toast.makeText(getApplicationContext(), dbHelper.getSession("hello","mike coutts").getUsername(), Toast.LENGTH_LONG).show();
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
