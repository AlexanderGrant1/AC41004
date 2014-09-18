package com.team8.potatodoctor;

import com.team8.adapters.TuberImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TuberSymptomActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new TuberImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
	        }

	    });
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
