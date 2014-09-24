package com.team8.potatodoctor.Models;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.team8.potatodoctor.Constants;

public class LocalFileUpdater {
	
	private Context context;
	public LocalFileUpdater(Context context)
	{
		this.context = context;
	}
	
	public void fetchPestImage(String imageName) throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.PEST_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		new MediaFetcher().execute(photoPath + imageName,context.getFilesDir()+"/Pests");
	}
	
	public void fetchTuberImage(String imageName) throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.TUBER_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		new MediaFetcher().execute(photoPath + imageName,context.getFilesDir()+"/Tubers");
	}
	
	public void fetchPlantLeafImage(String imageName) throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.PLANT_LEAF_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		new MediaFetcher().execute(photoPath + imageName,context.getFilesDir()+"/PlantLeaf");
	}
	
	public void fetchTutorialVideo(String VideoName) throws InterruptedException, ExecutionException, JSONException 
	{
		String response = new HttpGetRequest().execute(Constants.TUTORIAL_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String videoPath = cleanJSONUrl(obj.getString("VideoPath"));
		new MediaFetcher().execute(videoPath + VideoName,context.getFilesDir()+"/Tutorials");
	}
	
	private String cleanJSONUrl(String jsonURL)
	{
		return jsonURL.replace("\\", "");
	}
}
