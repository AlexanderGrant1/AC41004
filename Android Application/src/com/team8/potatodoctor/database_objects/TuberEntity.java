package com.team8.potatodoctor.database_objects;

import java.util.LinkedList;

/**
 * Represents a tuber entry in the database.
 */
public class TuberEntity implements IDatabaseObject {
	
	private int id;
	private String name;
	private String description;
	private LinkedList<PhotoEntity> photos;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LinkedList<PhotoEntity> getPhotos() {
		return photos;
	}
	public void setPhotos(LinkedList<PhotoEntity> photos) {
		this.photos = photos;
	}
	
}
