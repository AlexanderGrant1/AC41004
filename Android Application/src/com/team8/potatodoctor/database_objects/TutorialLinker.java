package com.team8.potatodoctor.database_objects;

public class TutorialLinker {
	
	private int Id;
	private int EntryId;
	private int TutorialId;
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
	public int getTutorialId() {
		return TutorialId;
	}
	public void setTutorialId(int tutorialId) {
		TutorialId = tutorialId;
	}

}
