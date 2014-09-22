package com.team8.potatodoctor.Models;

import java.io.File;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;
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
		for(PestEntity pest : pestRepository.getAllPests())
		{
			for(PhotoEntity photo : pest.getPhotos())
			{
				String imageNameAndExtension = getImageNameAndExtensionFromFullyQualifiedPath(photo.getFullyQualifiedPath());
				if(!imageExists(imageNameAndExtension,"Pests"))
				{
					localFileUpdater.fetchPestImage(imageNameAndExtension);
				}
			}
		}
		for(TuberSymptomEntity tuber : tuberRepository.getAllTubers())
		{
			for(PhotoEntity photo : tuber.getPhotos())
			{
				String imageNameAndExtension = getImageNameAndExtensionFromFullyQualifiedPath(photo.getFullyQualifiedPath());
				if(!imageExists(imageNameAndExtension,"Tubers"))
				{
					localFileUpdater.fetchPestImage(imageNameAndExtension);
				}
			}
		}
		for(PlantLeafSymptomsEntity plantLeaf : plantLeafRepository.getAllPlantLeafs())
		{
			for(PhotoEntity photo : plantLeaf.getPhotos())
			{
				String imageNameAndExtension = getImageNameAndExtensionFromFullyQualifiedPath(photo.getFullyQualifiedPath());
				if(!imageExists(imageNameAndExtension,"PlantLeaf"))
				{
					localFileUpdater.fetchPestImage(imageNameAndExtension);
				}
			}
		} 
	}
	
	private String getImageNameAndExtensionFromFullyQualifiedPath(String fullyQualifiedPath)
	{
		String[] splitPath = fullyQualifiedPath.split("/");
		return splitPath[splitPath.length - 1];
	}
	
	private boolean imageExists(String imageName, String folderName)
	{
		File dir = new File(Environment.getExternalStorageDirectory() + "/" +folderName);
		if(dir.isDirectory())
		{
			Log.w("hello", dir.getAbsolutePath() + " is a directory");
			File[] directoryListing = dir.listFiles();
			  if (directoryListing != null) {
			    for (File child : directoryListing) { 
			    	if(child.getName().equals(imageName)) 
			    	{
			    		return true;
			    	}
			    }
			  }
		}
		return false;
	}
}
