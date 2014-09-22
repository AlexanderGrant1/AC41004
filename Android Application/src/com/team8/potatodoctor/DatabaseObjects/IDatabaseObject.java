package com.team8.potatodoctor.DatabaseObjects;

import java.util.LinkedList;

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
