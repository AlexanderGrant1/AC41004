package com.team8.potatodoctor.Models;

import java.io.File;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.team8.potatodoctor.Models.Repositories.*;

public class AppUpdater {

	private PestRepository pestRepository;
	private PhotoRepository photoRepository;
	private TuberRepository tuberRepository;
	private TutorialRepository tutorialRepository;
	private PlantLeafRepository plantLeafRepository;
	private LocalDbUpdater localDbUpdater;
	private LocalFileUpdater localFileUpdater;
	
	public AppUpdater(Context context)
	{
		pestRepository = new PestRepository(context);
		photoRepository = new PhotoRepository(context);
		tuberRepository = new TuberRepository(context);
		tutorialRepository = new TutorialRepository(context);
		plantLeafRepository = new PlantLeafRepository(context);
		localDbUpdater = new LocalDbUpdater(context);
		localFileUpdater = new LocalFileUpdater();
	}
	public void updateDatabaseTables() throws InterruptedException, ExecutionException
	{
		photoRepository.dropPhotoTableIfExists();
		photoRepository.createPhotoTableIfNotExists();
		
		pestRepository.dropPestTableIfExists();
		pestRepository.createPestTablesIfNotExists();
		localDbUpdater.updatePestTables();
		
		tuberRepository.dropTuberTablesIfExists();
		tuberRepository.createTuberTablesIfNotExists();
		localDbUpdater.updateTuberTables();
		
		tutorialRepository.dropTutorialTableIfExists();
		tutorialRepository.createTutorialTableIfNotExists();
		localDbUpdater.updateTutorialTables();
		
		plantLeafRepository.dropPlantLeafTablesIfExists();
		plantLeafRepository.createPlantLeafTablesIfNotExists();
		localDbUpdater.updatePlantLeafTables();
	}
	
	public void updateLocalFiles() throws InterruptedException, ExecutionException, JSONException
	{
		localFileUpdater.fetchPestImages();
		localFileUpdater.fetchPlantLeafImages();
		localFileUpdater.fetchTuberImages();
	}
	
	private boolean checkImageExists(String imageName, String folderName)
	{
		File dir = new File(folderName);
		if(dir.isDirectory())
		{
			File[] directoryListing = dir.listFiles();
			  if (directoryListing != null) {
			    for (File child : directoryListing) {
			      if((child.getName()+"."+getFileExtension(child)).equals(imageName))
			      {
			    	  return true;
			      }
			    }
			  }
		}
		return false;
	}
	
	private String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}
}
