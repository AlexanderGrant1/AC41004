package com.team8.potatodoctor.Models;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PestPhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;
import com.team8.potatodoctor.DatabaseObjects.TutorialEntity;

import android.util.Log;

public class DataFetcher {
	
	public LinkedList<TuberSymptomEntity> parseTuberSymptoms(String Message)
	{
		LinkedList<TuberSymptomEntity> tuberSymptoms = new LinkedList<TuberSymptomEntity>();
		try {
			JSONObject obj = new JSONObject(Message);
			JSONArray arr = obj.getJSONArray("Entries");
			for(int i = 0; i < arr.length(); i++)
			{
				TuberSymptomEntity tuberSymptom = new TuberSymptomEntity();
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
	
	public LinkedList<PhotoLinkerEntity> parsePhotoLinker(String Message)
	{
		LinkedList<PhotoLinkerEntity> tuberSymptomsLinker = new LinkedList<PhotoLinkerEntity>();
		try {
			JSONObject obj = new JSONObject(Message);
			JSONArray arr = obj.getJSONArray("PhotoLinker");
			Log.w("hello", "length = "+arr.length());
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
	
	public LinkedList<PhotoEntity> parsePhotos(String Message)
	{
		LinkedList<PhotoEntity> photos = new LinkedList<PhotoEntity>();
		try {
			JSONObject obj = new JSONObject(Message);
			JSONArray arr = obj.getJSONArray("Photos");
			for(int i = 0; i < arr.length(); i++)
			{
				PhotoEntity photo = new PhotoEntity();
				photo.setId(Integer.parseInt(arr.getJSONObject(i).getString("Id")));
				photo.setName(arr.getJSONObject(i).getString("ImageName"));
				photos.add(photo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return photos;
	}
	
	public LinkedList<PestEntity> parsePests(String Message)
	{
		LinkedList<PestEntity> pests = new LinkedList<PestEntity>();
		try {
			JSONObject obj = new JSONObject(Message);
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
	
	public LinkedList<PlantLeafSymptomsEntity> parsePlantAndLeafSymptoms(String Message)
	{
		LinkedList<PlantLeafSymptomsEntity> plantLeafSymptoms = new LinkedList<PlantLeafSymptomsEntity>();
		try {
			JSONObject obj = new JSONObject(Message);
			JSONArray arr = obj.getJSONArray("Entries");
			for(int i = 0; i < arr.length(); i++)
			{
				PlantLeafSymptomsEntity pest = new PlantLeafSymptomsEntity();
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
	
	public LinkedList<TutorialEntity> parseTutorials(String Message)
	{
		LinkedList<TutorialEntity> tutorials = new LinkedList<TutorialEntity>();
		try {
			JSONObject obj = new JSONObject(Message);
			JSONArray arr = obj.getJSONArray("Entries");
			for(int i = 0; i < arr.length(); i++)
			{
				TutorialEntity tutorial = new TutorialEntity();
				tutorial.setId(Integer.parseInt(arr.getJSONObject(i).getString("Id")));
				tutorial.setName(arr.getJSONObject(i).getString("Name"));
				tutorial.setDescription(arr.getJSONObject(i).getString("Description"));
				tutorial.setVideoName(arr.getJSONObject(i).getString("VideoName"));
				tutorials.add(tutorial);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tutorials;
	}
}
