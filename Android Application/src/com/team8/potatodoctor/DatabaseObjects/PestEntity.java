package com.team8.potatodoctor.DatabaseObjects;

public class PestEntity {
	
	private int Id;
	private String Name;
	private String Description;
	
	public void setId(int Id)
	{
		this.Id = Id;
	}
	
	public int getId()
	{
		return this.Id;
	}
	
	public String getName()
	{
		return Name;
	}
	
	public void setName(String Name)
	{
		this.Name = Name;
	}
	
	public void setDescription(String Description)
	{
		this.Description = Description;
	}
	
	public String getDescription()
	{
		return Description;
	}
}
