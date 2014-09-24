package com.team8.potatodoctor.Models;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.team8.potatodoctor.Constants;
import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberEntity;
import com.team8.potatodoctor.Models.Repositories.*;

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
	
	public int getNumberOfPhotosToDownload()
	{
		try {
			updateDatabaseTables();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		int count = 0;
		File pestDir = new File(context.getFilesDir() + "/Pests");
		if(pestDir.isDirectory())
		{
			LinkedList<String> fileNames = getImagesInFolder("Pests");
			LinkedList<String> serverPestPhotos = new LinkedList<String>();
			for(PestEntity pest : pestRepository.getAllPests())
			{
				for(PhotoEntity photo : pest.getPhotos())
				{
					serverPestPhotos.add(getImageNameAndExtensionFromFilePath(photo.getFullyQualifiedPath()));
				}
			}
			for(String serverPhoto : serverPestPhotos)
			{
				if(!fileNames.contains(serverPhoto))
				{
					count++;
				}
			}
		}
		File tuberDir = new File(context.getFilesDir() + "/Tubers");
		if(tuberDir.isDirectory())
		{
			LinkedList<String> fileNames = getImagesInFolder("Tubers");
			LinkedList<String> serverPestPhotos = new LinkedList<String>();
			for(TuberEntity tuber : tuberRepository.getAllTubers())
			{
				for(PhotoEntity photo : tuber.getPhotos())
				{
					serverPestPhotos.add(getImageNameAndExtensionFromFilePath(photo.getFullyQualifiedPath()));
				}
			}
			for(String serverPhoto : serverPestPhotos)
			{
				if(!fileNames.contains(serverPhoto))
				{
					count++;
				}
			}
		}
		File plantLeafDir = new File(context.getFilesDir() + "/PlantLeaf");
		if(plantLeafDir.isDirectory())
		{
			LinkedList<String> fileNames = getImagesInFolder("PlantLeaf");
			LinkedList<String> serverPestPhotos = new LinkedList<String>();
			for(PlantLeafEntity plantLeaf : plantLeafRepository.getAllPlantLeafs())
			{
				for(PhotoEntity photo : plantLeaf.getPhotos())
				{
					serverPestPhotos.add(getImageNameAndExtensionFromFilePath(photo.getFullyQualifiedPath()));
				}
			}
			for(String serverPhoto : serverPestPhotos)
			{
				if(!fileNames.contains(serverPhoto))
				{
					count++;
				}
			}
		}
		return count;
	}
	
	public void updateLocalFiles() throws InterruptedException, ExecutionException, JSONException
	{
		deleteUnusedPestPhotos();
		deleteUnusedTuberPhotos();
		deleteUnusedPlantLeafPhotos();
		for(PestEntity pest : pestRepository.getAllPests())
		{
			for(PhotoEntity photo : pest.getPhotos())
			{
				String imageNameAndExtension = getImageNameAndExtensionFromFilePath(photo.getFullyQualifiedPath());
				if(!imageExists(imageNameAndExtension,"Pests"))
				{
					localFileUpdater.fetchPestImage(imageNameAndExtension);
				}
			}
		}
		for(TuberEntity tuber : tuberRepository.getAllTubers())
		{
			for(PhotoEntity photo : tuber.getPhotos())
			{
				String imageNameAndExtension = getImageNameAndExtensionFromFilePath(photo.getFullyQualifiedPath());
				if(!imageExists(imageNameAndExtension,"Tubers"))
				{
					localFileUpdater.fetchTuberImage(imageNameAndExtension);
				}
			}
		}
		for(PlantLeafEntity plantLeaf : plantLeafRepository.getAllPlantLeafs())
		{
			for(PhotoEntity photo : plantLeaf.getPhotos())
			{
				String imageNameAndExtension = getImageNameAndExtensionFromFilePath(photo.getFullyQualifiedPath());
				if(!imageExists(imageNameAndExtension,"PlantLeaf"))
				{
					localFileUpdater.fetchPlantLeafImage(imageNameAndExtension);
				}
			}
		} 
	} 
	//REFACTOR ME
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
					serverPhotos.add(getImageNameAndExtensionFromFilePath(photo.getFullyQualifiedPath()));
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
	//REFACTOR ME
	private void deleteUnusedTuberPhotos()
	{
		File dir = new File(context.getFilesDir() + "/" +"Tubers");
		if(dir.isDirectory())
		{
			LinkedList<String> fileNames = getImagesInFolder("Tubers");
			LinkedList<String> serverPhotos = new LinkedList<String>();
			for(TuberEntity tuber : tuberRepository.getAllTubers())
			{
				for(PhotoEntity photo : tuber.getPhotos())
				{
					serverPhotos.add(getImageNameAndExtensionFromFilePath(photo.getFullyQualifiedPath()));
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
	//REFACTOR ME
	private void deleteUnusedPlantLeafPhotos()
	{
		File dir = new File(context.getFilesDir() + "/" +"PlantLeaf");
		if(dir.isDirectory())
		{
			LinkedList<String> fileNames = getImagesInFolder("PlantLeaf");
			LinkedList<String> serverPhotos = new LinkedList<String>();
			for(PlantLeafEntity plantLeaf : plantLeafRepository.getAllPlantLeafs())
			{
				for(PhotoEntity photo : plantLeaf.getPhotos())
				{
					serverPhotos.add(getImageNameAndExtensionFromFilePath(photo.getFullyQualifiedPath()));
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
	
	public LinkedList<String> getImagesInFolder(String folder)
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
	
	private String getImageNameAndExtensionFromFilePath(String fullyQualifiedPath)
	{
		String[] splitPath = fullyQualifiedPath.split("/");
		return splitPath[splitPath.length - 1];
	}
	
	private boolean imageExists(String imageName, String folderName)
	{
		File dir = new File(context.getFilesDir() + "/" +folderName);
		if(dir.isDirectory())
		{
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


