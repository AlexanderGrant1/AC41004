package com.team8.potatodoctor.Models;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.util.Log;

import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;

public class LocalDbUpdater {

	private Context context;
	private DatabaseManager dbManager;
	private DataFetcher dataFetcher;
	public LocalDbUpdater(Context context)
	{
		this.context = context;
		dbManager = new DatabaseManager(this.context);
		dataFetcher = new DataFetcher();
	}
	
	public void updateTuberTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute("http://beberry.lv/potato/api/tuber").get();
		LinkedList<TuberSymptomEntity> tuberSymptoms = dataFetcher.parseTuberSymptoms(response);
		for(TuberSymptomEntity tuber : tuberSymptoms)
		{
			dbManager.insertTuber(tuber);
		}
		
		LinkedList<PhotoLinkerEntity> tuberLinkers = dataFetcher.parsePhotoLinker(response);
		for(PhotoLinkerEntity linker : tuberLinkers)
		{
			dbManager.insertTuberPhotoLinker(linker);
		}
		
		LinkedList<PhotoEntity> tuberPhotos = dataFetcher.parsePhotos(response);
		for(PhotoEntity tuberPhoto : tuberPhotos)
		{
			dbManager.insertPhoto(tuberPhoto);
		}
	}
	
	public void updatePestTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute("http://beberry.lv/potato/api/pest").get();
		LinkedList<PestEntity> pests = dataFetcher.parsePests(response);
		for(PestEntity pest : pests)
		{
			dbManager.insertPest(pest);
		}
		
		LinkedList<PhotoLinkerEntity> pestLinkers = dataFetcher.parsePhotoLinker(response);
		for(PhotoLinkerEntity linker : pestLinkers)
		{
			dbManager.insertTuberPhotoLinker(linker);
		}
		
		LinkedList<PhotoEntity> pestPhotos = dataFetcher.parsePhotos(response);
		for(PhotoEntity pestPhoto : pestPhotos)
		{
			dbManager.insertPhoto(pestPhoto);
		}
	}
	
	public void updatePlantLeafTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute("http://beberry.lv/potato/api/plantleaf").get();
		LinkedList<PlantLeafSymptomsEntity> plantLeafSymptoms = dataFetcher.parsePlantAndLeafSymptoms(response);
		for(PlantLeafSymptomsEntity plantLeafSymptom : plantLeafSymptoms)
		{
			dbManager.insertPlantLeaf(plantLeafSymptom);
		}
		
		LinkedList<PhotoLinkerEntity> plantLeafSymptomLinkers = dataFetcher.parsePhotoLinker(response);
		for(PhotoLinkerEntity linker : plantLeafSymptomLinkers)
		{
			dbManager.insertTuberPhotoLinker(linker);
		}
		
		LinkedList<PhotoEntity> plantLeafSymptomPhotos = dataFetcher.parsePhotos(response);
		for(PhotoEntity plantLeafSymptomPhoto : plantLeafSymptomPhotos)
		{
			dbManager.insertPhoto(plantLeafSymptomPhoto);
		}
	}
}
