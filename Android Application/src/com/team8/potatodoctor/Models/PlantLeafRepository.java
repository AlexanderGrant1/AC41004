package com.team8.potatodoctor.Models;

import java.util.LinkedList;

import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlantLeafRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	public PlantLeafRepository(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);	
	}
	

    public LinkedList<PlantLeafSymptomsEntity> getAllPlantLeafs() {
        LinkedList<PlantLeafSymptomsEntity> plantLeafs = new LinkedList<PlantLeafSymptomsEntity>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_PlantLeaf", null);

        if (cursor.moveToFirst()) {
            do {
            	PlantLeafSymptomsEntity plantLeaf = new PlantLeafSymptomsEntity();
            	plantLeaf.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            	plantLeaf.setName(cursor.getString(cursor.getColumnIndex("Name")));
            	plantLeaf.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
            	plantLeafs.add(plantLeaf);
            }
            while (cursor.moveToNext());

        }
        db.close();
        return plantLeafs;
    }
	
	public void insertPlantLeaf(PlantLeafSymptomsEntity pest)
	{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", pest.getId());
			values.put("Name", pest.getName());
			values.put("Description", pest.getDescription());
			db.insert("potato_PlantLeaf", null, values);
			db.close();
	}
	
    public LinkedList<PhotoLinkerEntity> getAllPlantLeafPhotoLinkers() {
        LinkedList<PhotoLinkerEntity> plantLeafLinkers = new LinkedList<PhotoLinkerEntity>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_PlantLeaf_photo", null);

        if (cursor.moveToFirst()) {
            do {
            	PhotoLinkerEntity plantLeafLinker = new PhotoLinkerEntity();
            	plantLeafLinker.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            	plantLeafLinker.setEntryId(cursor.getInt(cursor.getColumnIndex("PlantLeafId")));
            	plantLeafLinker.setPhotoId(cursor.getInt(cursor.getColumnIndex("PhotoId")));
            	plantLeafLinkers.add(plantLeafLinker);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return plantLeafLinkers;
    }
    
    private LinkedList<Integer> getPhotoIdsForPlantLeaf(PlantLeafSymptomsEntity plantLeaf) {
        LinkedList<Integer> photoIds = new LinkedList<Integer>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT photoId FROM potato_PlantLeaf_photo WHERE Id = "+plantLeaf.getId(), null);

        if (cursor.moveToFirst()) {
            do {
            	photoIds.add(cursor.getInt(cursor.getColumnIndex("photoId")));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return photoIds;
    }
	
	public void insertPlantLeafPhotoLinker(PhotoLinkerEntity linker)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Id", linker.getId());
		values.put("PhotoId", linker.getPhotoId());
		values.put("PlantLeafId", linker.getEntryId());
		db.insert("potato_PlantLeaf_photo", null, values);
		db.close();
	}
	
	//public LinkedList<PhotoEntity> getAllPlantLeafPhotos(PlantLeafSymptomsEntity plantLeafSymptom)
//	{
//		LinkedList<Integer> photoIds = getPhotoIdsForPlantLeaf(plantLeafSymptom);
		
//	}
}
