package com.team8.potatodoctor.Models.Repositories;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;

public class PestRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_PEST_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Pest` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(50) NOT NULL,"+
	"`Description` text NOT NULL,"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CREATE_PEST_PHOTOS_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Pest_photo` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`PestId` smallint unsigned NOT NULL,"+
	"`PhotoId` smallint unsigned NOT NULL,"+
	"PRIMARY KEY(`Id`));";
	
	public PestRepository(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);	
	}
	
	public void createPestTablesIfNotExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CREATE_PEST_TABLE);
		db.execSQL(CREATE_PEST_PHOTOS_TABLE);
	}
	
	public void clearPestTables()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM potato_Pest");
		db.execSQL("DELETE FROM potato_Pest_photo");
	}
	
    public LinkedList<PestEntity> getAllPests() {
        LinkedList<PestEntity> pests = new LinkedList<PestEntity>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_Pest", null);

        if (cursor.moveToFirst()) {
            do {
                PestEntity pest = new PestEntity();
                pest.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                pest.setName(cursor.getString(cursor.getColumnIndex("Name")));
                pest.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                pest.setPhotos(getPlantLeafPhotos(pest));
                pests.add(pest);
            }
            while (cursor.moveToNext());
        }
        return pests;
    }
	
	public void insertPest(PestEntity pest)
	{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", pest.getId());
			values.put("Name", pest.getName());
			values.put("Description", pest.getDescription());
			db.insert("potato_Pest", null, values);
			db.close();
	}
	
	public void insertPestPhotoLinker(PhotoLinkerEntity linker)
	{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", linker.getId());
			values.put("PestId", linker.getEntryId());
			values.put("PhotoId", linker.getPhotoId());
			db.insert("potato_Pest_photo", null, values);
			db.close();
	}
	
    private LinkedList<Integer> getPestPhotoLinkersForPest(PestEntity pest) {
        LinkedList<Integer> photoIds = new LinkedList<Integer>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT photoId FROM potato_Pest_photo WHERE PestId = "+pest.getId(), null);

        if (cursor.moveToFirst()) {
            do {
            	photoIds.add(cursor.getInt(cursor.getColumnIndex("PhotoId")));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return photoIds;
    }
	
	public LinkedList<PhotoEntity> getPlantLeafPhotos(PestEntity pest)
	{
		LinkedList<Integer> photoIds = getPestPhotoLinkersForPest(pest);
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
            	photo.setName(cursor.getString(cursor.getColumnIndex("Name")));
            	photos.add(photo);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return photos;
	}
}
