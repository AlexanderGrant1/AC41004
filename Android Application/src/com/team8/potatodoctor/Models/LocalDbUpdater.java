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
import com.team8.potatodoctor.DatabaseObjects.TutorialEntity;
import com.team8.potatodoctor.Models.Repositories.PestRepository;
import com.team8.potatodoctor.Models.Repositories.PhotoRepository;
import com.team8.potatodoctor.Models.Repositories.PlantLeafRepository;
import com.team8.potatodoctor.Models.Repositories.TuberRepository;
import com.team8.potatodoctor.Models.Repositories.TutorialRepository;

public class LocalDbUpdater {

	private Context context;
	private TuberRepository tuberRepository;
	private PestRepository pestRepository;
	private PlantLeafRepository plantLeafRepository;
	private PhotoRepository photoRepository;
	private TutorialRepository tutorialRepository;
	private DataFetcher dataFetcher;
	public LocalDbUpdater(Context context)
	{
		this.context = context;
		tuberRepository = new TuberRepository(context);
		pestRepository = new PestRepository(context);
		plantLeafRepository = new PlantLeafRepository(context);
		photoRepository = new PhotoRepository(context);
		tutorialRepository = new TutorialRepository(context);
		dataFetcher = new DataFetcher();
	}
	
	public void updateTuberTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute("http://beberry.lv/potato/api/tuber").get();
		LinkedList<TuberSymptomEntity> tuberSymptoms = dataFetcher.parseTuberSymptoms(response);
		for(TuberSymptomEntity tuber : tuberSymptoms)
		{
			tuberRepository.insertTuber(tuber);
		}
		
		LinkedList<PhotoLinkerEntity> tuberLinkers = dataFetcher.parsePhotoLinker(response);
		for(PhotoLinkerEntity linker : tuberLinkers)
		{
			tuberRepository.insertTuberPhotoLinker(linker);
		}
		
		LinkedList<PhotoEntity> tuberPhotos = dataFetcher.parsePhotos(response);
		for(PhotoEntity tuberPhoto : tuberPhotos)
		{
			photoRepository.insertPhoto(tuberPhoto);
		}
	}
	
	public void updatePestTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute("http://beberry.lv/potato/api/pest").get();
		LinkedList<PestEntity> pests = dataFetcher.parsePests(response);
		for(PestEntity pest : pests)
		{
			pestRepository.insertPest(pest);
		}
		
		LinkedList<PhotoLinkerEntity> pestLinkers = dataFetcher.parsePhotoLinker(response);
		for(PhotoLinkerEntity linker : pestLinkers)
		{
			pestRepository.insertPestPhotoLinker(linker);
		}
		
		LinkedList<PhotoEntity> pestPhotos = dataFetcher.parsePhotos(response);
		for(PhotoEntity pestPhoto : pestPhotos)
		{
			photoRepository.insertPhoto(pestPhoto);
		}
	}
	
	public void updatePlantLeafTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute("http://beberry.lv/potato/api/plantleaf").get();
		LinkedList<PlantLeafSymptomsEntity> plantLeafSymptoms = dataFetcher.parsePlantAndLeafSymptoms(response);
		for(PlantLeafSymptomsEntity plantLeafSymptom : plantLeafSymptoms)
		{
			plantLeafRepository.insertPlantLeaf(plantLeafSymptom);
		}
		
		LinkedList<PhotoLinkerEntity> plantLeafSymptomLinkers = dataFetcher.parsePhotoLinker(response);
		for(PhotoLinkerEntity linker : plantLeafSymptomLinkers)
		{
			plantLeafRepository.insertPlantLeafPhotoLinker(linker);
		}
		
		LinkedList<PhotoEntity> plantLeafSymptomPhotos = dataFetcher.parsePhotos(response);
		for(PhotoEntity plantLeafSymptomPhoto : plantLeafSymptomPhotos)
		{
			photoRepository.insertPhoto(plantLeafSymptomPhoto);
		}
	}
	
	public void updateTutorialTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute("http://beberry.lv/potato/api/tutorial").get();
		LinkedList<TutorialEntity> tutorials = dataFetcher.parseTutorials(response);
		for(TutorialEntity tutorial : tutorials)
		{
			tutorialRepository.insertTutorial(tutorial);
		}
	}
}
