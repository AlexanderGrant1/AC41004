package com.team8.potatodoctor.Models.Repositories;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;

public class PhotoRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	private final String CREATE_PHOTO_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Photo` ("+
			" `Id` smallint unsigned NOT NULL,"+
			"`Name` varchar(37) NOT NULL,"+
			"UNIQUE(`Name`),"+
			"PRIMARY KEY(`Id`)"+
			");";
	
	private final String DROP_PHOTO_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_Photo`";
	
	private final String CLEAR_PHOTO_TABLE = "DELETE FROM `potato_Photo`";
	
	public PhotoRepository(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);	
	}
	
	public void createPhotoTableIfNotExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CREATE_PHOTO_TABLE);
		db.close();
	}
	
	public void clearPhotoTable()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CLEAR_PHOTO_TABLE);
		db.close();
	}
	
	public void dropPhotoTableIfExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(DROP_PHOTO_TABLE_IF_EXISTS);
		db.close();
	}
	
    public LinkedList<PhotoEntity> getAllPhotos() {
        LinkedList<PhotoEntity> photos = new LinkedList<PhotoEntity>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_photo", null);

        if (cursor.moveToFirst()) {
            do {
            	PhotoEntity pest = new PhotoEntity();
                pest.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                pest.setFullyQualifiedPath(cursor.getString(cursor.getColumnIndex("Name")));
                photos.add(pest);
            }
            while (cursor.moveToNext());

        }
        db.close();
        return photos;
    }
	
	public void insertPhoto(PhotoEntity photo)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Id", photo.getId());
		values.put("Name", photo.getFullyQualifiedPath());
		db.insert("potato_Photo", null, values);
		db.close();
	}
}
