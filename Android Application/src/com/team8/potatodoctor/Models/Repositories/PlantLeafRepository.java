package com.team8.potatodoctor.Models.Repositories;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;

public class PlantLeafRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_PLANT_LEAF_TABLE = "CREATE TABLE IF NOT EXISTS `potato_PlantLeaf` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(50) NOT NULL,"+
	"`Description` text NOT NULL,"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CLEAR_PLANT_LEAF_TABLE = "DELETE FROM `potato_PlantLeaf`";
	
	private static final String DROP_PLANT_LEAF_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_PlantLeaf`";
	
	private static final String CREATE_PLANT_LEAF_PHOTO_TABLE = "CREATE TABLE IF NOT EXISTS `potato_PlantLeaf_photo` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`PlantLeafId` smallint unsigned NOT NULL,"+
	"`PhotoId` smallint unsigned NOT NULL,"+
	"PRIMARY KEY(`Id`));";	
	
	private static final String CLEAR_PLANT_LEAF_PHOTO_TABLE = "DELETE FROM `potato_PlantLeaf_photo`";
	
	private static final String DROP_PLANT_LEAF_PHOTO_TABLE_EXISTS = "DROP TABLE IF EXISTS `potato_PlantLeaf_photo`";
	
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
	
	public void createPlantLeafTablesIfNotExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CREATE_PLANT_LEAF_TABLE);
		db.execSQL(CREATE_PLANT_LEAF_PHOTO_TABLE);
		db.close();
	}
	
	public void dropPlantLeafTablesIfExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(DROP_PLANT_LEAF_TABLE_IF_EXISTS);
		db.execSQL(DROP_PLANT_LEAF_PHOTO_TABLE_EXISTS);
		db.close();
	}
	
	public void clearPlantLeafTables()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CLEAR_PLANT_LEAF_TABLE);
		db.execSQL(CLEAR_PLANT_LEAF_PHOTO_TABLE);
		db.close();
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
            	plantLeaf.setPhotos(getPlantLeafPhotos(plantLeaf));
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
	
	 private LinkedList<Integer> getPestPhotoLinkersForPlantLeaf(PlantLeafSymptomsEntity plantLeaf) {
	        LinkedList<Integer> photoIds = new LinkedList<Integer>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT photoId FROM potato_PlantLeaf_photo WHERE PlantLeafId = "+plantLeaf.getId(), null);

	        if (cursor.moveToFirst()) {
	            do {
	            	photoIds.add(cursor.getInt(cursor.getColumnIndex("PhotoId")));
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return photoIds;
	    }
		
		public LinkedList<PhotoEntity> getPlantLeafPhotos(PlantLeafSymptomsEntity plantLeaf)
		{
			LinkedList<Integer> photoIds = getPestPhotoLinkersForPlantLeaf(plantLeaf);
			if(photoIds.size() == 0)
			{
				return new LinkedList<PhotoEntity>();
			}
			String SQLQuery = "SELECT * FROM potato_Photo WHERE Id = ";
			for(int i = 0; i < photoIds.size(); i++)
			{
				SQLQuery+= photoIds.get(i).toString();
				if(i < photoIds.size() - 1)
				{
					SQLQuery+= " OR Id = ";
				}
			}
			LinkedList<PhotoEntity> photos = new LinkedList<PhotoEntity>();
	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery(SQLQuery, null);

	        if (cursor.moveToFirst()) {
	            do {
	            	PhotoEntity photo = new PhotoEntity();
	            	photo.setId(cursor.getInt(cursor.getColumnIndex("Id")));
	            	photo.setFullyQualifiedPath(Environment.getExternalStorageDirectory()+"/PlantLeafs/"+cursor.getString(cursor.getColumnIndex("Name")));
	            	photos.add(photo);
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return photos;
		}
}
