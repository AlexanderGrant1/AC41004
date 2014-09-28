package com.team8.potatodoctor.models;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PhotoEntity;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.database_objects.TutorialEntity;
import com.team8.potatodoctor.models.repositories.PestRepository;
import com.team8.potatodoctor.models.repositories.PhotoRepository;
import com.team8.potatodoctor.models.repositories.PlantLeafRepository;
import com.team8.potatodoctor.models.repositories.TuberRepository;
import com.team8.potatodoctor.models.repositories.TutorialRepository;

/**
 * 
 */
public class AppUpdater {

	private PestRepository pestRepository;
	private PhotoRepository photoRepository;
	private TuberRepository tuberRepository;
	private TutorialRepository tutorialRepository;
	private PlantLeafRepository plantLeafRepository;
	private LocalDbUpdater localDbUpdater;
	private LocalFileUpdater localFileUpdater;
	private Context context;
	
	public AppUpdater(Context context)
	{
		pestRepository = new PestRepository(context);
		photoRepository = new PhotoRepository(context);
		tuberRepository = new TuberRepository(context);
		tutorialRepository = new TutorialRepository(context);
		plantLeafRepository = new PlantLeafRepository(context);
		localDbUpdater = new LocalDbUpdater(context);
		localFileUpdater = new LocalFileUpdater(context);
		this.context = context;
	}
	
	/**
	 * Clears all database tables and updates them with the latest data from the server.
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
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
	
	/** Downloads any new files that the app is missing and deletes the old content that is no longer used
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws JSONException
	 */
	public void updateLocalFiles() throws InterruptedException, ExecutionException, JSONException
	{
		deleteUnusedPestPhotos();
		deleteUnusedTuberPhotos();
		deleteUnusedPlantLeafPhotos();
		deleteUnusedTutorialVideos();
		for(PestEntity pest : pestRepository.getAllPests())
		{
			for(PhotoEntity photo : pest.getPhotos())
			{
				String imageNameAndExtension = getFileNameAndExtensionFromFilePath(photo.getFullyQualifiedPath());
				if(!fileExists(imageNameAndExtension,"Pests"))
				{
					localFileUpdater.fetchPestImage(imageNameAndExtension);
				}
			}
		}
		for(TuberEntity tuber : tuberRepository.getAllTubers())
		{
			for(PhotoEntity photo : tuber.getPhotos())
			{
				String imageNameAndExtension = getFileNameAndExtensionFromFilePath(photo.getFullyQualifiedPath());
				if(!fileExists(imageNameAndExtension,"Tubers"))
				{
					localFileUpdater.fetchTuberImage(imageNameAndExtension);
				}
			}
		}
		for(PlantLeafEntity plantLeaf : plantLeafRepository.getAllPlantLeafs())
		{
			for(PhotoEntity photo : plantLeaf.getPhotos())
			{
				String imageNameAndExtension = getFileNameAndExtensionFromFilePath(photo.getFullyQualifiedPath());
				if(!fileExists(imageNameAndExtension,"PlantLeaf"))
				{
					localFileUpdater.fetchPlantLeafImage(imageNameAndExtension);
				}
			}
		}
		for(TutorialEntity tutorial : tutorialRepository.getAllTutorials())
		{
			String videoNameAndExtension = getFileNameAndExtensionFromFilePath(tutorial.getFullyQualifiedPath());
			if(!fileExists(videoNameAndExtension,"Tutorials"))
			{
				localFileUpdater.fetchTutorialVideo(videoNameAndExtension);
			}
		}
	} 
	
	
	/**
	 * Deletes all pest photos that are no longer used by the app.
	 * 
	 */
	private void deleteUnusedPestPhotos()
	{
		File dir = new File(context.getFilesDir() + "/" +"Pests");
		if(dir.isDirectory())
		{
			LinkedList<String> fileNames = new LinkedList<String>();
			File[] directoryListing = dir.listFiles();
			for(File file : directoryListing)
			{
				fileNames.add(file.getName());
			}
			LinkedList<String> serverPhotos = new LinkedList<String>();
			for(PestEntity pest : pestRepository.getAllPests())
			{
				for(PhotoEntity photo : pest.getPhotos())
				{
					serverPhotos.add(getFileNameAndExtensionFromFilePath(photo.getFullyQualifiedPath()));
				}
			}
			for(String fileName : fileNames)
			{
				if(!serverPhotos.contains(fileName))
				{
					File f = new File(dir.getAbsoluteFile() + "/"+fileName);
					f.delete();
				}
			}
		}
		else
		{
			dir.mkdir();
		}
	}
	
	/**
	 * Deletes all tuber photos that are no longer used by the app.
	 * 
	 */
	private void deleteUnusedTuberPhotos()
	{
		File dir = new File(context.getFilesDir() + "/" +"Tubers");
		if(dir.isDirectory())
		{
			LinkedList<String> fileNames = getFilesInFolder("Tubers");
			LinkedList<String> serverPhotos = new LinkedList<String>();
			for(TuberEntity tuber : tuberRepository.getAllTubers())
			{
				for(PhotoEntity photo : tuber.getPhotos())
				{
					serverPhotos.add(getFileNameAndExtensionFromFilePath(photo.getFullyQualifiedPath()));
				}
			}
			for(String fileName : fileNames)
			{
				if(!serverPhotos.contains(fileName))
				{
					File f = new File(dir.getAbsoluteFile() + "/"+fileName);
					f.delete();
				}
			}
		}
		else
		{
			dir.mkdir();
		}
	}
	
	/**
	 * Deletes all plant/leaf symptom photos that are no longer used by the app.
	 * 
	 */
	private void deleteUnusedPlantLeafPhotos()
	{
		File dir = new File(context.getFilesDir() + "/" +"PlantLeaf");
		if(dir.isDirectory())
		{
			LinkedList<String> fileNames = getFilesInFolder("PlantLeaf");
			LinkedList<String> serverPhotos = new LinkedList<String>();
			for(PlantLeafEntity plantLeaf : plantLeafRepository.getAllPlantLeafs())
			{
				for(PhotoEntity photo : plantLeaf.getPhotos())
				{
					serverPhotos.add(getFileNameAndExtensionFromFilePath(photo.getFullyQualifiedPath()));
				}
			}
			for(String fileName : fileNames)
			{
				if(!serverPhotos.contains(fileName))
				{
					File f = new File(dir.getAbsoluteFile() + "/"+fileName);
					f.delete();
				}
			}
		}
		else
		{
			dir.mkdir();
		}
	}
	
	private void deleteUnusedTutorialVideos()
	{
		File dir = new File(context.getFilesDir() + "/" +"Tutorials");
		if(dir.isDirectory())
		{
			LinkedList<String> fileNames = getFilesInFolder("PlantLeaf");
			LinkedList<String> serverVideos = new LinkedList<String>();
			for(TutorialEntity tutorial : tutorialRepository.getAllTutorials())
			{
				serverVideos.add(getFileNameAndExtensionFromFilePath(tutorial.getFullyQualifiedPath()));
			}
			for(String fileName : fileNames)
			{
				if(!serverVideos.contains(fileName))
				{
					Log.w("hello", "deleted "+fileName);
					File f = new File(dir.getAbsoluteFile() + "/"+fileName);
					f.delete();
				}
			}
		}
		else
		{
			dir.mkdir();
		}
	}
	/**
	 * Returns a list of all images in a given folder in the application's file directory.
	 * 
	 */
	public LinkedList<String> getFilesInFolder(String folder)
	{
		File dir = new File(context.getFilesDir() + "/" +folder);
		LinkedList<String> fileNames = new LinkedList<String>();
		File[] directoryListing = dir.listFiles();
		for(File file : directoryListing)
		{
			fileNames.add(file.getName());
		}
		return fileNames;
	} 
	
	private String getFileNameAndExtensionFromFilePath(String fullyQualifiedPath)
	{
		String[] splitPath = fullyQualifiedPath.split("/");
		return splitPath[splitPath.length - 1];
	}
	
	private boolean fileExists(String fileName, String folderName)
	{
		File dir = new File(context.getFilesDir() + "/" +folderName);
		if(dir.isDirectory())
		{
			File[] directoryListing = dir.listFiles();
			  if (directoryListing != null) {
			    for (File child : directoryListing) { 
			    	if(child.getName().equals(fileName)) 
			    	{
			    		return true;
			    	}
			    }
			  }
		}
		return false;
	}
}


