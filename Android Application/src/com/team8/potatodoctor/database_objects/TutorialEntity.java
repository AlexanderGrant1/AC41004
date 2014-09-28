package com.team8.potatodoctor.database_objects;

/**
 * Represents a tutorial entry in the database.
 */
public class TutorialEntity {

	private int Id;
	private String Name;
	private String Description;
	private String FullyQualifiedPath;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getFullyQualifiedPath() {
		return FullyQualifiedPath;
	}
	public void setFullyQualifiedPath(String fullyQualifiedPath) {
		FullyQualifiedPath = fullyQualifiedPath;
	}

	
}
