package com.team8.potatodoctor;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.MediaController;
import android.widget.VideoView;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;

public class VideoActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);

		//Setup Media Player
		setupMediaPlayer();
		
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
	
	private void setupMediaPlayer()
	{
		VideoView vidView = (VideoView)findViewById(R.id.myVideo);
		String vidAddress = "android.resource://com.team8.potatodoctor/raw/test_video";
		Uri vidUri = Uri.parse(vidAddress);
		
		vidView.setVideoURI(vidUri);
		vidView.start();
	}
}
