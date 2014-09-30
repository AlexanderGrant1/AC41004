package com.team8.potatodoctor.models;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import android.content.Context;

import com.team8.potatodoctor.database_objects.TutorialLinker;
import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PhotoEntity;
import com.team8.potatodoctor.database_objects.PhotoLinkerEntity;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.database_objects.TutorialEntity;
import com.team8.potatodoctor.models.repositories.PestRepository;
import com.team8.potatodoctor.models.repositories.PhotoRepository;
import com.team8.potatodoctor.models.repositories.PlantLeafRepository;
import com.team8.potatodoctor.models.repositories.TuberRepository;
import com.team8.potatodoctor.models.repositories.TutorialRepository;
import com.team8.potatodoctor.utilities.Constants;

/**
 * Updates the local database with the data from the server.
 *
 */
public class LocalDbUpdater {
	
	private TuberRepository tuberRepository;
	private PestRepository pestRepository;
	private PlantLeafRepository plantLeafRepository;
	private PhotoRepository photoRepository;
	private TutorialRepository tutorialRepository;
	private DataFetcher dataFetcher;
	public LocalDbUpdater(Context context) 
	{ 
		tuberRepository = new TuberRepository(context);
		pestRepository = new PestRepository(context);
		plantLeafRepository = new PlantLeafRepository(context);
		photoRepository = new PhotoRepository(context);
		tutorialRepository = new TutorialRepository(context);
		dataFetcher = new DataFetcher(context);
	}
	
	/**
	 * Updates the local database with information about tubers and information about tuber photos.
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updateTuberTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute(Constants.TUBER_API_URL).get();
		LinkedList<TuberEntity> tuberSymptoms = dataFetcher.parseTuberSymptoms(response);
		for(TuberEntity tuber : tuberSymptoms)
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
		LinkedList<TutorialLinker> tuberTutorials = dataFetcher.parseTutorialLinker(response);
		for(TutorialLinker tuberTutorial : tuberTutorials)
		{
			tuberRepository.insertTuberTutorialLinker(tuberTutorial);
		}
	}
	
	/**
	 * Updates the local database with information about pests and information about pest photos.
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updatePestTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute(Constants.PEST_API_URL).get();
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
		
		LinkedList<TutorialLinker> pestTutorials = dataFetcher.parseTutorialLinker(response);
		for(TutorialLinker tuberTutorial : pestTutorials)
		{
			pestRepository.insertPestTutorialLinker(tuberTutorial);
		}
	}
	
	/**
	 * Updates the local database with information about plant leaf symptoms and information about plant leaf photos.
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updatePlantLeafTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute(Constants.PLANT_LEAF_API_URL).get();
		LinkedList<PlantLeafEntity> plantLeafSymptoms = dataFetcher.parsePlantAndLeafSymptoms(response);
		for(PlantLeafEntity plantLeafSymptom : plantLeafSymptoms)
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
		LinkedList<TutorialLinker> plantLeafTutorials = dataFetcher.parseTutorialLinker(response);
		for(TutorialLinker tuberTutorial : plantLeafTutorials)
		{
			plantLeafRepository.insertPlantLeafTutorialLinker(tuberTutorial);
		}
	}
	
	/**
	 * Updates the local database with information about tutorials and tutorial videos.
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updateTutorialTables() throws InterruptedException, ExecutionException
	{
		String response = new HttpGetRequest().execute(Constants.TUTORIAL_API_URL).get();
		LinkedList<TutorialEntity> tutorials = dataFetcher.parseTutorials(response);
		for(TutorialEntity tutorial : tutorials)
		{
			tutorialRepository.insertTutorial(tutorial);
		}
	}
}
