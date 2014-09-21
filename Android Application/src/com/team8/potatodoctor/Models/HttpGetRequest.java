package com.team8.potatodoctor.Models;


import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
public class HttpGetRequest extends AsyncTask<String, Void, String>
{
	@Override
	/*
	 * @param params first string is the url, second string is the type of request e.g POST, PUT, GET, DELETE
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	protected String doInBackground(String... params) 
	{
		return httpGetRequest(params[0]);
	}
	private static String httpGetRequest(String url)
	{
		try
		{			
			 HttpClient httpclient = new DefaultHttpClient();
			 HttpGet httpGet = new HttpGet(url);
			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
			 return httpclient.execute(httpGet, responseHandler);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}