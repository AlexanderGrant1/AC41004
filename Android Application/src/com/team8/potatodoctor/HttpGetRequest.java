package com.team8.potatodoctor;


import java.io.IOException;
import java.util.LinkedList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
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
			Log.w("hello",url);
			Log.w("hello","debug0");
			 HttpClient httpclient = new DefaultHttpClient();
			 Log.w("hello","debug1");
			 HttpGet httpGet = new HttpGet(url);
			 Log.w("hello","debug2");
			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
			 Log.w("hello","debug3");
			 String response = httpclient.execute(httpGet, responseHandler);
			 Log.w("hello","debug4");
			 return response;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			Log.w("hello",e.getMessage());
			return null;
		}
	}
}