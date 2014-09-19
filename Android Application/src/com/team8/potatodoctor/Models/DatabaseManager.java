package com.team8.potatodoctor.Models;


import java.util.LinkedList;

import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** Helper to the database, manages versions and creation */
public class DatabaseManager extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	private final String CREATE_PHOTO_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Photo` ("+
	" `Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(37) NOT NULL,"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`)"+
	");";
	
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
	
	private static final String CREATE_PLANT_LEAF_TABLE = "CREATE TABLE IF NOT EXISTS `potato_PlantLeaf` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(50) NOT NULL,"+
	"`Description` text NOT NULL,"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CREATE_PLANT_LEAF_PHOTO_TABLE = "CREATE TABLE IF NOT EXISTS `potato_PlantLeaf_photo` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`PlantLeafId` smallint unsigned NOT NULL,"+
	"`PhotoId` smallint unsigned NOT NULL,"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CREATE_TUBER_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Tuber` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(50) NOT NULL,"+
	"`Description` text NOT NULL,"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CREATE_TUBER_PHOTOS = "CREATE TABLE IF NOT EXISTS `potato_Tuber_photo` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`TuberId` smallint unsigned NOT NULL,"+
	"`PhotoId` smallint unsigned NOT NULL,"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CREATE_TUTORIAL_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Tutorial` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(50) NOT NULL,"+
	"`Description` text NOT NULL,"+
	"`VideoName` varchar(20),"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`));";
	
	
	
	public DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);	
	}
	
	public void createTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(CREATE_PEST_TABLE);
		db.execSQL(CREATE_PHOTO_TABLE);
		db.execSQL(CREATE_PEST_PHOTOS_TABLE);
		db.execSQL(CREATE_PLANT_LEAF_TABLE);
		db.execSQL(CREATE_PLANT_LEAF_PHOTO_TABLE);
		db.execSQL(CREATE_TUBER_TABLE);
		db.execSQL(CREATE_TUBER_PHOTOS);
		db.execSQL(CREATE_TUTORIAL_TABLE);
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
        return plantLeafLinkers;
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
	
}