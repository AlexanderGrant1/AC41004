package com.team8.potatodoctor.Models;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.team8.potatodoctor.Constants;

public class LocalFileUpdater {
	
	public void fetchPestImages() throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.PEST_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		JSONArray photos = obj.getJSONArray("Photos");
		for(int i = 0; i < photos.length(); i++)
		{
			new MediaFetcher().execute(photoPath + photos.getJSONObject(i).getString("ImageName"),"Pests");
		}
	}
	
	public void fetchPestImage(String imageName) throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.PEST_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		new MediaFetcher().execute(photoPath + imageName,"Pests");
	}
	
	public void fetchTuberImages() throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.TUBER_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		JSONArray photos = obj.getJSONArray("Photos");
		for(int i = 0; i < photos.length(); i++)
		{
			new MediaFetcher().execute(photoPath + photos.getJSONObject(i).getString("ImageName"),"Tubers");
		}
	}
	
	public void fetchTuberImage(String imageName) throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.TUBER_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		new MediaFetcher().execute(photoPath + imageName,"Tubers");
	}
	
	public void fetchPlantLeafImages() throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.PLANT_LEAF_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		JSONArray photos = obj.getJSONArray("Photos");
		for(int i = 0; i < photos.length(); i++)
		{
			new MediaFetcher().execute(photoPath + photos.getJSONObject(i).getString("ImageName"),"PlantLeaf");
		}
	}
	
	public void fetchPlantLeafImage(String imageName) throws InterruptedException, ExecutionException, JSONException
	{

		String response = new HttpGetRequest().execute(Constants.PLANT_LEAF_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		new MediaFetcher().execute(photoPath + imageName,"PlantLeaf");
	}
	

	
	private String cleanJSONUrl(String jsonURL)
	{
		return jsonURL.replace("\\", "");
	}
}
