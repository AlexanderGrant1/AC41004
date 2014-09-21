package com.team8.potatodoctor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.adapters.VideoAdapter;

public class VideoGridActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		setupGridView();
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

	/*
	 * Constructs the Grid View and populates with Images via the PestImageAdapter.
	 */
	private void setupGridView()
	{
		//Locate Grid View from .xml Layout.
		GridView gridview = (GridView) findViewById(R.id.gridview);
		
		//Attach TuberImageAdapter and adds Images.
	    gridview.setAdapter(new VideoAdapter(this));

	    //Setup Event Listener to direct user to information page.
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Intent intentVideoPlayer = new Intent(getApplicationContext(),VideoActivity.class);
	        	
	        	//TODO Check name of db table and remove this comment.
	        	intentVideoPlayer.putExtra("position", position); //DB Table name.
	        	
	    		startActivity(intentVideoPlayer);
	        }
	    });
	}
}
