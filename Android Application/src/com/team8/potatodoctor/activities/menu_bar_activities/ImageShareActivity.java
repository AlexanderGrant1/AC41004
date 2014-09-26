package com.team8.potatodoctor.activities.menu_bar_activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
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

public class ImageShareActivity extends Activity{

	final String TEMP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+System.currentTimeMillis()+".jpeg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageshare);
		Log.d("FilePath", ""+TEMP_PATH);
		deleteTempPictureIfExists();
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
	
	private void deleteTempPictureIfExists()
	{
		File temp = new File(TEMP_PATH);
		if(temp.exists())
		{
			temp.delete();
		}
	}
	
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
	
	/*
	 * Referenced from: http://stackoverflow.com/questions/9570237/android-check-internet-connection
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
	 	
	/*
	 * Display dialog to connect to internet.
	 */
	public void showImageShareNetworkErrorDialog()
	{
		// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

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
