package com.team8.potatodoctor.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

import com.team8.potatodoctor.R;

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
		/*
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		*/
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
}