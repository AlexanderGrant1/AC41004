package com.team8.potatodoctor.Models;


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

	private static final String CREATE_PHOTO_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Photo` ("+
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
			db.insert("potato_Pest", null, values);
			db.close();
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
	
	public void insertPhoto(PhotoEntity photo)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Id", photo.getId());
		values.put("Name", photo.getName());
		db.insert("potato_Photo", null, values);
		db.close();
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
	
}