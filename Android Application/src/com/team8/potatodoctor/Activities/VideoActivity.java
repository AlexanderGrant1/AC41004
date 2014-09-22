package com.team8.potatodoctor.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.MenuBarActivities.ExitActivity;
import com.team8.potatodoctor.activities.MenuBarActivities.ImageShareActivity;
import com.team8.potatodoctor.activities.MenuBarActivities.SettingsActivity;
import com.team8.potatodoctor.activities.MenuBarActivities.UpdateActivity;

public class VideoActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		
		int position = 0;
		
		//Extract parameters from the intent.
	    Bundle extras = getIntent().getExtras();
	    if(extras !=null)
	    {
	    	position = extras.getInt("position");
	    }

		setupMediaPlayer(position);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	
	
	/*
	 * Refer to: http://code.tutsplus.com/tutorials/streaming-video-in-android-apps--cms-19888
	 */
	private void setupMediaPlayer(int position)
	{
		VideoView vidView = (VideoView)findViewById(R.id.myVideo);
		String vidAddress = "android.resource://com.team8.potatodoctor/raw/test_video"+position;
		Log.d("Problem Determination", Integer.toString(position));
		Uri vidUri = Uri.parse(vidAddress);
		
		vidView.setVideoURI(vidUri);
		MediaController vidControl = new MediaController(this);
		vidControl.setAnchorView(vidView);
		vidView.setMediaController(vidControl);
		vidView.start();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId())
	    {
	    case (R.id.action_imageshare):
	        this.startActivity(new Intent(this, ImageShareActivity.class));
	        return true;
	    case (R.id.action_update):
	        this.startActivity(new Intent(this, UpdateActivity.class));
	        return true;
	    case (R.id.action_settings):
	        this.startActivity(new Intent(this, SettingsActivity.class));
	        return true;
	    case (R.id.action_exit):
	        this.startActivity(new Intent(this, ExitActivity.class));
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}