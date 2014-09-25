package com.team8.potatodoctor.activities.menu_bar_activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.team8.potatodoctor.R;

public class ImageShareActivity extends Activity{

	final String TEMP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.jpeg";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageshare);
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.jpeg");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.w("hello","hello");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(TEMP_PATH))); 
		startActivityForResult(intent, 0);
		disableHardwareMenuKey();
	}  
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(resultCode == -1)
		{
			Intent picMessageIntent = new Intent(android.content.Intent.ACTION_SEND);            
	        picMessageIntent.setType("image/jpeg");
	        picMessageIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(TEMP_PATH)));
	        startActivity(Intent.createChooser(picMessageIntent, "Send Picture Using: "));
	        ImageShareActivity.this.finish();
		}
		Log.w("hello", "resultCode = "+resultCode + " requestCode = "+requestCode);
	    		
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private PictureCallback mPicture = new PictureCallback() {

	    @Override
	    public void onPictureTaken(byte[] data, Camera camera) {
	        Log.w("hello", "Getting output media file");
	        File pictureFile = new File(TEMP_PATH);
	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	            ImageShareActivity.this.finish();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	    }
	};
	

	
	/*
	 * Disable Hardware Menu Button on phones. Force Menu drop down on Action Bar.
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
