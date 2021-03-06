package com.team8.potatodoctor.models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.os.AsyncTask;
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
	public String fetch(String mediaUrl, String folderPath) 
	{
		Log.w("hello", "DOWNLOADING "+mediaUrl + " to "+ folderPath);
		try
		{
			String imageName = getMediaNameAndExtensionFromURL(mediaUrl);
			URL url = new URL (mediaUrl); 
			InputStream input = url.openStream(); 
			File f = new File(folderPath); 
			if(!f.isDirectory())
			{ 
				f.mkdir();
			}
		    OutputStream output = new FileOutputStream (folderPath+"/"+imageName);         
	        byte[] buffer = new byte[2040];         
	        int bytesRead = 0;         
	        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
	                output.write(buffer, 0, bytesRead);            
	        }
	        output.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return e.getMessage();
		}

        return "";
	}
	
	private String getMediaNameAndExtensionFromURL(String mediaURL)
	{
		String[] parts = mediaURL.split("/");
		return parts[parts.length-1];
	}
}
