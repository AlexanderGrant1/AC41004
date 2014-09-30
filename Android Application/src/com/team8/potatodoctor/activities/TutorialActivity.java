package com.team8.potatodoctor.activities;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.MediaController;
import android.widget.VideoView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.activities.menu_bar_activities.ImageShareActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.SearchActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UpdateActivity;
import com.team8.potatodoctor.activities.menu_bar_activities.UserGuideActivity;
import com.team8.potatodoctor.models.repositories.TutorialRepository;

/**
 * Displays the selected video.
 * Refer to: http://code.tutsplus.com/tutorials/streaming-video-in-android-apps--cms-19888
 */
public class TutorialActivity extends Activity
{
	private TutorialRepository tutorialRepository;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
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

		disableHardwareMenuKey();

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	/**
	 * Creates the Media Player to show the videos.
	 * 
	 * Refer to: http://code.tutsplus.com/tutorials/streaming-video-in-android-apps--cms-19888
	 */
	private void setupMediaPlayer(int position)
	{
		tutorialRepository.getAllTutorials();
		String videoPath = tutorialRepository.getAllTutorials().get(position).getFullyQualifiedPath();
		VideoView vidView = (VideoView)findViewById(R.id.myVideo);
		Uri vidUri = Uri.parse(videoPath);

		vidView.setVideoURI(vidUri);
		MediaController vidControl = new MediaController(this);
		vidControl.setAnchorView(vidView);
		vidView.setMediaController(vidControl);
		vidView.start();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
        switch (item.getItemId()) {
        case (R.id.action_home):
        	this.startActivity(new Intent(this, CategoriesListActivity.class));
        	this.finish();
        	return true;
        case android.R.id.home:
            this.finish();
            return true;
	    case (R.id.action_search):
	        this.startActivity(new Intent(this, SearchActivity.class));
	        return true;
	    case (R.id.action_imageshare):
	    	 this.startActivity(new Intent(this, ImageShareActivity.class));
	        return true;
	    case (R.id.action_update):
	        this.startActivity(new Intent(this, UpdateActivity.class));
	        return true;
	    case (R.id.action_userguide):
	        this.startActivity(new Intent(this, UserGuideActivity.class));
	        return true;
	    case (R.id.action_exit):
	    	Intent intent = new Intent(Intent.ACTION_MAIN); 
	    	intent.addCategory(Intent.CATEGORY_HOME);
	    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    	startActivity(intent);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    
        }
	}

	/**
	 * Disable Hardware Menu Button on phones. Force Menu drop down on Action Bar.
	 * 
	 * Referenced from: http://stackoverflow.com/questions/9286822/how-to-force-use-of-overflow-menu-on-devices-with-menu-button
	 */
	private void disableHardwareMenuKey()
	{
		try
		{
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
}
