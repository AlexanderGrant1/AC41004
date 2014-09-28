package com.team8.potatodoctor.activities.menu_bar_activities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.team8.potatodoctor.R;

/**
 * Opens camera, take a photo then upload the image to an application of user's choice.
 * 
 * Referenced from: http://stackoverflow.com/questions/10679571/calling-camera-from-an-activity-capturing-an-image-and-uploading-to-a-server
 */
public class ImageShareActivity extends Activity{

	final String TEMP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+System.currentTimeMillis()+".jpeg";

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageshare);
		
		createTempFolderIfNotExists();
		
		//Check for a network connection before proceeding.
		if(isNetworkConnected())
		{
			displayCamera();
		}	
		else
		{
			showImageShareNetworkErrorDialog();
		}
		
		disableHardwareMenuKey();
	}  
	
	/**
	 * Create a new folder to store new images if it does not exist.
	 */
	public void createTempFolderIfNotExists()
	{
		File tempFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/pd_temp");
		if(!tempFolder.exists())
		{
			try {
				tempFolder.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Start new intent to open up the camera.
	 */
	public void displayCamera()
	{
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
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(resultCode == -1)
		{
			Intent picMessageIntent = new Intent(android.content.Intent.ACTION_SEND);            
	        picMessageIntent.setType("image/jpeg");
	        picMessageIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(TEMP_PATH)));
	        Toast.makeText(this, "Saved to: "+TEMP_PATH, Toast.LENGTH_SHORT).show();
	        startActivity(Intent.createChooser(picMessageIntent, "Send Picture Using: "));
		}
		ImageShareActivity.this.finish();
		Log.w("hello", "resultCode = "+resultCode + " requestCode = "+requestCode);
	    		
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
	
	/**
	 * Check if the device is connected to a network.
	 * 
	 * Referenced from: http://stackoverflow.com/questions/9570237/android-check-internet-connection
	 * 
	 * @param true/false if the device to connected to a network.
	 */
	private boolean isNetworkConnected() 
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}  
	 	
	/**
	 * Display dialog to tell user to connect to internet.
	 */
	public void showImageShareNetworkErrorDialog()
	{
		// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setCancelable(false);
				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage("This feature requires connectivity to the internet. Please connect to a Wi-Fi or turn on Mobile Data.")
				       .setTitle("No Internet Connection Detected")

				// 3. set the Positive button option
				.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which) {
						//Close the activity
						finish();
					}
				}) //End of .setPositiveButton()
								
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();				
	}
}
