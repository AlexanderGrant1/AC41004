package com.team8.potatodoctor.database_objects;

/**
 * Represents a photo entry in the database.
 */
public class PhotoEntity {

	private int id;
	private String fullyQualifiedPath;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullyQualifiedPath() {
		return fullyQualifiedPath;
	}
	public void setFullyQualifiedPath(String fullyQualifiedPath) {
		this.fullyQualifiedPath = fullyQualifiedPath;
	}
}
