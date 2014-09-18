package com.team8.potatodoctor;

import com.team8.adapters.PlantImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GridviewItemActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		String type = "";
		int position = 0;
	    Bundle extras = getIntent().getExtras();
	    if(extras !=null)
	    {
	    	type = extras.getString("type");
	    	position = extras.getInt("position");
	    }
	    
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
	
}
