package com.team8.potatodoctor.Models;

import java.util.LinkedList;

import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TuberRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	public TuberRepository(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);	
	}
	
	 public LinkedList<TuberSymptomEntity> getAllTubers() {
	        LinkedList<TuberSymptomEntity> tubers = new LinkedList<TuberSymptomEntity>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT * FROM potato_Tuber", null);

	        if (cursor.moveToFirst()) {
	            do {
	            	TuberSymptomEntity tuber = new TuberSymptomEntity();
	            	tuber.setId(cursor.getInt(cursor.getColumnIndex("Id")));
	            	tuber.setName(cursor.getString(cursor.getColumnIndex("Name")));
	                tuber.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
	                tubers.add(tuber);
	            }
	            while (cursor.moveToNext());

	        }
	        db.close();
	        return tubers;
	    }
		
		public void insertTuber(TuberSymptomEntity tuber)
		{
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("Id", tuber.getId());
				values.put("Name", tuber.getName());
				values.put("Description", tuber.getDescription());
				db.insert("potato_Tuber", null, values);
				db.close();
		}
		
	    public LinkedList<PhotoLinkerEntity> getAllTuberPhotoLinkers() {
	        LinkedList<PhotoLinkerEntity> tuberLinkers = new LinkedList<PhotoLinkerEntity>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT * FROM potato_Tuber_photo", null);

	        if (cursor.moveToFirst()) {
	            do {
	            	PhotoLinkerEntity pest = new PhotoLinkerEntity();
	                pest.setId(cursor.getInt(cursor.getColumnIndex("Id")));
	                pest.setEntryId(cursor.getInt(cursor.getColumnIndex("PestId")));
	                pest.setPhotoId(cursor.getInt(cursor.getColumnIndex("PhotoId")));
	                tuberLinkers.add(pest);
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return tuberLinkers;
	    }
		
		public void insertTuberPhotoLinker(PhotoLinkerEntity linker)
		{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", linker.getId());
			values.put("PhotoId", linker.getPhotoId());
			values.put("TuberId", linker.getEntryId());
			db.insert("potato_Tuber_photo", null, values);
			db.close();
		}
}
