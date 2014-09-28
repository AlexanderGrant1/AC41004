package com.team8.potatodoctor.models;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.team8.potatodoctor.utilities.Constants;

/**
 * Provides methods for downloading individual files from the server
 *
 */
public class LocalFileUpdater {
	
	private Context context;
	public LocalFileUpdater(Context context)
	{
		this.context = context;
	}
	
	/**
	 * Pulls down a pest image from the server with the given image name.
	 * 
	 * @param imageName The name and extension of the image to download.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws JSONException
	 */
	public void fetchPestImage(String imageName) throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.PEST_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		new MediaFetcher().execute(photoPath + imageName,context.getFilesDir()+"/Pests");
	}
	
	
	/**
	 * Pulls down a tuber image from the server with the given image name.
	 * 
	 * @param imageName The name and extension of the image to download.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws JSONException
	 */
	public void fetchTuberImage(String imageName) throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.TUBER_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		new MediaFetcher().execute(photoPath + imageName,context.getFilesDir()+"/Tubers");
	}
	
	/**
	 * Pulls down a plant/leaf symptom image from the server with the given image name.
	 * 
	 * @param imageName The name and extension of the image to download.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws JSONException
	 */
	public void fetchPlantLeafImage(String imageName) throws InterruptedException, ExecutionException, JSONException
	{
		String response = new HttpGetRequest().execute(Constants.PLANT_LEAF_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String photoPath = cleanJSONUrl(obj.getString("PhotoPath"));
		new MediaFetcher().execute(photoPath + imageName,context.getFilesDir()+"/PlantLeaf");
	}
	
	/**
	 * @param VideoName The name and extension of the video to download
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws JSONException
	 */
	public void fetchTutorialVideo(String VideoName) throws InterruptedException, ExecutionException, JSONException 
	{
		String response = new HttpGetRequest().execute(Constants.TUTORIAL_API_URL).get();
		JSONObject obj = new JSONObject(response);
		String videoPath = cleanJSONUrl(obj.getString("VideoPath"));
		new MediaFetcher().execute(videoPath + VideoName,context.getFilesDir()+"/Tutorials");
	}
	
	/**
	 * Removes all of the backslashes present in a JSON url
	 * 
	 * @param jsonURL A url taken from a JSON string
	 * @return Returns the cleaned JSON url
	 */
	private String cleanJSONUrl(String jsonURL)
	{
		return jsonURL.replace("\\", "");
	}
}
