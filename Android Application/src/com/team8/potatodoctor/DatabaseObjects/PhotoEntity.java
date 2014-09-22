package com.team8.potatodoctor.DatabaseObjects;

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
