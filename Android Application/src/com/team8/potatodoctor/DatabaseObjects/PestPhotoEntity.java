package com.team8.potatodoctor.DatabaseObjects;

public class PestPhotoEntity {

	private int Id;
	private int PhotoId;
	private int PestId;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getPhotoId() {
		return PhotoId;
	}
	public void setPhotoId(int photoId) {
		PhotoId = photoId;
	}
	public int getPestId() {
		return PestId;
	}
	public void setPestId(int pestId) {
		PestId = pestId;
	}
}
