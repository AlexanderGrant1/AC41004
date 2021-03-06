package com.team8.potatodoctor.database_objects;

import java.util.LinkedList;

/**
 * An interface that all database objects (Pest, PlantLeaf, Tuber) inherit from
 */
public interface IDatabaseObject {

	public int getId();
	public void setId(int id);
	
	public void setName(String name);
	public String getName(); 
	
	public void setDescription(String description);
	public String getDescription();
	
	public LinkedList<PhotoEntity> getPhotos();
	public void setPhotos(LinkedList<PhotoEntity> photos);
}
 