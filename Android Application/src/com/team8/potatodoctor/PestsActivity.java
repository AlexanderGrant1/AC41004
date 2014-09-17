package com.team8.potatodoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class PestsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		/*
		Log.d("Problem Determination", "PestsActivity,onCreate() ENTRY");

		
		
		displayPests();
		
		Log.d("Problem Determination", "PestsActivity.onCreate() EXIT");
		*/
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
	

	public void displayPests()
	{
		
	}
	
}
