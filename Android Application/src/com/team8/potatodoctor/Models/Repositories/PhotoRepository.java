package com.team8.potatodoctor.Models.Repositories;

import java.util.LinkedList;

import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
	}
	
	public void clearPhotoTable()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM potato_Photo");
	}
	
    public LinkedList<PhotoEntity> getAllPhotos() {
        LinkedList<PhotoEntity> photos = new LinkedList<PhotoEntity>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_photo", null);

        if (cursor.moveToFirst()) {
            do {
            	PhotoEntity pest = new PhotoEntity();
                pest.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                pest.setName(cursor.getString(cursor.getColumnIndex("Name")));
                photos.add(pest);
            }
            while (cursor.moveToNext());

        }
        return photos;
    }
	
	public void insertPhoto(PhotoEntity photo)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Id", photo.getId());
		values.put("Name", photo.getName());
		db.insert("potato_Photo", null, values);
		db.close();
	}
}
