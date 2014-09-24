package com.team8.potatodoctor.activities;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.MediaController;
import android.widget.VideoView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.menu_bar_activities.ExitActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.ImageShareActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SettingsActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UpdateActivity;
import com.team8.potatodoctor.models.repositories.TutorialRepository;
//Extract parameters from the intent.
// Inflate the menu; this adds items to the action bar if it is present.
/*
	 * Refer to: http://code.tutsplus.com/tutorials/streaming-video-in-android-apps--cms-19888
	 */
public class TutorialActivity extends Activity
{
	private TutorialRepository tutorialRepository;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		tutorialRepository = new TutorialRepository(getApplicationContext());
		int position = 0;
		
		//Extract parameters from the intent.
	    Bundle extras = getIntent().getExtras();
	    if(extras !=null)
	    {
	    	position = extras.getInt("position");
	    }
	    setTitle(tutorialRepository.getAllTutorials().get(position).getName());
		setupMediaPlayer(position);
		
		//Disable Hardware Menu Button on phones. Force Menu drop down on Action Bar.
				try {
			        ViewConfiguration config = ViewConfiguration.get(this);
			        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			        if(menuKeyField != null) {
			            menuKeyField.setAccessible(true);
			            menuKeyField.setBoolean(config, false);
			        }
			    } catch (Exception ex) {
			        // Ignore
			    }
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
		tutorialRepository.getAllTutorials();
		String videoPath = tutorialRepository.getAllTutorials().get(position).getFullyQualifiedPath();
		VideoView vidView = (VideoView)findViewById(R.id.myVideo);
		Log.d("Problem Determination", Integer.toString(position));
		Log.w("hello","VIDEO PATH: "+videoPath);
		Uri vidUri = Uri.parse(videoPath);
		
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
	    case (R.id.action_search):
	        this.startActivity(new Intent(this, SettingsActivity.class));
	        return true;
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
