package com.team8.potatodoctor.Models;

import java.util.concurrent.ExecutionException;

import android.content.Context;

import com.team8.potatodoctor.Models.Repositories.*;

public class AppUpdater {

	private PestRepository pestRepository;
	private PhotoRepository photoRepository;
	private TuberRepository tuberRepository;
	private TutorialRepository tutorialRepository;
	private PlantLeafRepository plantLeafRepository;
	private LocalDbUpdater localDbUpdater;
	
	public AppUpdater(Context context)
	{
		pestRepository = new PestRepository(context);
		photoRepository = new PhotoRepository(context);
		tuberRepository = new TuberRepository(context);
		tutorialRepository = new TutorialRepository(context);
		plantLeafRepository = new PlantLeafRepository(context);
		localDbUpdater = new LocalDbUpdater(context);
	}
	public void updateDatabaseTables() throws InterruptedException, ExecutionException
	{
		pestRepository.createPestTablesIfNotExists();
		pestRepository.clearPestTables();
		localDbUpdater.updatePestTables();
		
		photoRepository.createPhotoTableIfNotExists();
		photoRepository.clearPhotoTable();
		
		tuberRepository.createTuberTablesIfNotExists();
		tuberRepository.clearTuberTables();
		localDbUpdater.updateTuberTables();
		
		tutorialRepository.createTutorialTableIfNotExists();
		tutorialRepository.clearTutorialTable();
		localDbUpdater.updateTutorialTables();
		
		plantLeafRepository.createPlantLeafTablesIfNotExists();
		plantLeafRepository.clearPlantLeafTables();
		localDbUpdater.updatePlantLeafTables();
	}
	
	public void updateLocalFiles()
	{
		
	}
}
