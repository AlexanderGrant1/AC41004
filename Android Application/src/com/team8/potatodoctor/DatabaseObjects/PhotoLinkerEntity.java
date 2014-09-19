package com.team8.potatodoctor.DatabaseObjects;

public class PhotoLinkerEntity {

	private int Id;
	private int EntryId;
	private int PhotoId;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getEntryId() {
		return EntryId;
	}
	public void setEntryId(int entryId) {
		EntryId = entryId;
	}
	public int getPhotoId() {
		return PhotoId;
	}
	public void setPhotoId(int photoId) {
		PhotoId = photoId;
	}	
}
