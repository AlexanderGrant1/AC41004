package com.team8.potatodoctor.models;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PhotoEntity;
import com.team8.potatodoctor.database_objects.PhotoLinkerEntity;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.database_objects.TutorialEntity;

/**
 * Fetches data from the server
 *
 */
public class DataFetcher {
	
	private Context context;
	
	public DataFetcher(Context context)
	{
		this.context = context;
	}
	
	/** Returns all tuber symptoms stored on the server
	 * 
	 * @param tuberJSON A JSON giving tuber information pulled from the server
	 * @return A linked list of tubers
	 */
	public LinkedList<TuberEntity> parseTuberSymptoms(String tuberJSON)
	{
		LinkedList<TuberEntity> tuberSymptoms = new LinkedList<TuberEntity>();
		try {
			JSONObject obj = new JSONObject(tuberJSON);
			JSONArray arr = obj.getJSONArray("Entries");
			for(int i = 0; i < arr.length(); i++)
			{
				TuberEntity tuberSymptom = new TuberEntity();
				tuberSymptom.setId(Integer.parseInt(arr.getJSONObject(i).getString("Id")));
				tuberSymptom.setName(arr.getJSONObject(i).getString("Name"));
				tuberSymptom.setDescription(arr.getJSONObject(i).getString("Description"));
				tuberSymptoms.add(tuberSymptom);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tuberSymptoms;
	}
	
	/** Returns a linked list of photo linkers from a JSON string
	 * 
	 * @param JSON A JSON string to parse photo linkers from
	 * @return A linked list of photo linkers
	 */
	public LinkedList<PhotoLinkerEntity> parsePhotoLinker(String JSON)
	{
		LinkedList<PhotoLinkerEntity> tuberSymptomsLinker = new LinkedList<PhotoLinkerEntity>();
		try {
			JSONObject obj = new JSONObject(JSON);
			JSONArray arr = obj.getJSONArray("PhotoLinker");
			for(int i = 0; i < arr.length(); i++)
			{
				PhotoLinkerEntity tuberSymptom = new PhotoLinkerEntity();
				tuberSymptom.setId(Integer.parseInt(arr.getJSONObject(i).getString("Id")));
				tuberSymptom.setEntryId((Integer.parseInt(arr.getJSONObject(i).getString("EntryId"))));
				tuberSymptom.setPhotoId(Integer.parseInt(arr.getJSONObject(i).getString("PhotoId")));
				tuberSymptomsLinker.add(tuberSymptom);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tuberSymptomsLinker;
	}
	
	/** Returns a linked list of photo objects parsed from a JSON string
	 * @param jsonString A JSON string to parse for photos
	 * @return A linked list of photo objects
	 */
	public LinkedList<PhotoEntity> parsePhotos(String jsonString)
	{
		LinkedList<PhotoEntity> photos = new LinkedList<PhotoEntity>();
		try {
			JSONObject obj = new JSONObject(jsonString);
			JSONArray arr = obj.getJSONArray("Photos");
			for(int i = 0; i < arr.length(); i++)
			{
				PhotoEntity photo = new PhotoEntity();
				photo.setId(Integer.parseInt(arr.getJSONObject(i).getString("Id")));
				photo.setFullyQualifiedPath(arr.getJSONObject(i).getString("ImageName"));
				photos.add(photo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return photos;
	}
	
	/** Returns a linked list of pest objects parsed from a JSON string
	 *  
	 * @param pestJSON A JSON string to parse pests from
	 * @return A linked list of pest objects
	 */
	public LinkedList<PestEntity> parsePests(String pestJSON)
	{
		LinkedList<PestEntity> pests = new LinkedList<PestEntity>();
		try {
			JSONObject obj = new JSONObject(pestJSON);
			JSONArray arr = obj.getJSONArray("Entries");
			for(int i = 0; i < arr.length(); i++)
			{
				PestEntity pest = new PestEntity();
				pest.setId(Integer.parseInt(arr.getJSONObject(i).getString("Id")));
				pest.setName(arr.getJSONObject(i).getString("Name"));
				pest.setDescription(arr.getJSONObject(i).getString("Description"));
				pests.add(pest);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return pests;
	}
	
	/** Returns a linked list of plant leaf symptom objects parsed from a JSON string
	 * 
	 * @param plantLeafJSON A JSON string to parse tutorials from
	 * @return A linked list of plant/leaf symptom objects
	 */
	public LinkedList<PlantLeafEntity> parsePlantAndLeafSymptoms(String plantLeafJSON)
	{
		LinkedList<PlantLeafEntity> plantLeafSymptoms = new LinkedList<PlantLeafEntity>();
		try {
			JSONObject obj = new JSONObject(plantLeafJSON);
			JSONArray arr = obj.getJSONArray("Entries");
			for(int i = 0; i < arr.length(); i++)
			{
				PlantLeafEntity pest = new PlantLeafEntity();
				pest.setId(Integer.parseInt(arr.getJSONObject(i).getString("Id")));
				pest.setName(arr.getJSONObject(i).getString("Name"));
				pest.setDescription(arr.getJSONObject(i).getString("Description"));
				plantLeafSymptoms.add(pest);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return plantLeafSymptoms;
	}
	
	/** Returns a linked list of tutorial objects parsed from a JSON string
	 * 
	 * @param tutorialJSON A JSON string to parse tutorials from
	 * @return A linked list of tutorial objects
	 */
	public LinkedList<TutorialEntity> parseTutorials(String tutorialJSON)
	{
		LinkedList<TutorialEntity> tutorials = new LinkedList<TutorialEntity>();
		try {
			JSONObject obj = new JSONObject(tutorialJSON);
			JSONArray arr = obj.getJSONArray("Entries");
			for(int i = 0; i < arr.length(); i++)
			{
				TutorialEntity tutorial = new TutorialEntity();
				tutorial.setId(Integer.parseInt(arr.getJSONObject(i).getString("Id")));
				tutorial.setName(arr.getJSONObject(i).getString("Name"));
				tutorial.setDescription(arr.getJSONObject(i).getString("Description"));
				tutorial.setFullyQualifiedPath(context.getFilesDir() + "/Tutorials/"+arr.getJSONObject(i).getString("VideoName"));
				tutorials.add(tutorial);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tutorials;
	}
}
