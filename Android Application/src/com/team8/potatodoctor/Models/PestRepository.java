package com.team8.potatodoctor.Models;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;

public class PestRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
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
	
    public LinkedList<PhotoLinkerEntity> getAllPestPhotoLinkers() {
        LinkedList<PhotoLinkerEntity> pestLinkers = new LinkedList<PhotoLinkerEntity>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_Pest_photo", null);

        if (cursor.moveToFirst()) {
            do {
            	PhotoLinkerEntity pest = new PhotoLinkerEntity();
                pest.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                pest.setEntryId(cursor.getInt(cursor.getColumnIndex("PestId")));
                pest.setPhotoId(cursor.getInt(cursor.getColumnIndex("PhotoId")));
                pestLinkers.add(pest);
            }
            while (cursor.moveToNext());

        }
        return pestLinkers;
    }
	
	public void insertPestPhotoLinker(PhotoLinkerEntity linker)
	{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", linker.getId());
			values.put("PestId", linker.getEntryId());
			values.put("PhotoId", linker.getPhotoId());
			db.insert("potato_Pest", null, values);
			db.close();
	}
}
