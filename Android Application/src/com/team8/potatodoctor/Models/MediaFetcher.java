package com.team8.potatodoctor.Models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class MediaFetcher extends AsyncTask<String, Void, String>
{
	@Override
	/*
	 * @param params first string is the url, second string is the type of request e.g POST, PUT, GET, DELETE
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	protected String doInBackground(String... params) 
	{
		if(params.length == 1)
		{
			return fetch(params[0], "");
		}
		return fetch(params[0], params[1]);
	}
	
	//Referenced http://stackoverflow.com/questions/16414515/save-image-from-url-to-sdcard
	public String fetch(String mediaUrl, String sdCardFolder)//sdCardFolder is the folder to store the image in on the sd card e.g Pests
	{
		String filepath = "";
		try
		{   
		  URL url = new URL(mediaUrl);
		  HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		  urlConnection.setRequestMethod("GET");
		  urlConnection.setDoOutput(true);                   
		  urlConnection.connect();                  
		  File SDCardFolder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/"+sdCardFolder);
		  if(!SDCardFolder.isDirectory())
		  {
			  SDCardFolder.mkdir();
		  }
		  Log.w("hello", "SDCard folder = "+SDCardFolder);
		  String filename=getMediaNameAndExtensionFromURL(mediaUrl);   
		  Log.w("hello","Local filename:"+filename);
		  File file = new File(SDCardFolder,filename);
		  if(file.createNewFile())
		  {
		    file.createNewFile();
		  }                 
		  FileOutputStream fileOutput = new FileOutputStream(file);
		  InputStream inputStream = urlConnection.getInputStream();
		  int totalSize = urlConnection.getContentLength();
		  int downloadedSize = 0;   
		  byte[] buffer = new byte[10240];
		  int bufferLength = 0;
		  while ( (bufferLength = inputStream.read(buffer)) > 0 ) 
		  {                 
		    fileOutput.write(buffer, 0, bufferLength);                  
		    downloadedSize += bufferLength;                 
		    //Log.w("Progress: downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
		  }             
		  fileOutput.close();
		  if(downloadedSize==totalSize) filepath=file.getPath();    
		} 
		catch (MalformedURLException e) 
		{
		  e.printStackTrace();
		} 
		catch (IOException e)
		{
		  filepath=null;
		  e.printStackTrace();
		}
		Log.w("hello", "filepath = "+filepath);
		return filepath;
	}
	
	private String getMediaNameAndExtensionFromURL(String mediaURL)
	{
		String[] parts = mediaURL.split("/");
		return parts[parts.length-1];
	}
}
