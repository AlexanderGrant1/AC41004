package com.team8.potatodoctor.Models.Repositories;

import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;
import com.team8.potatodoctor.DatabaseObjects.TutorialEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TutorialRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_TUTORIAL_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Tutorial` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(50) NOT NULL,"+
	"`Description` text NOT NULL,"+
	"`VideoName` varchar(20),"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`));";
	
	private static final String DROP_TUTORIAL_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_Tutorial`";
	
	private static final String CLEAR_TUTORIAL_TABLE = "DELETE FROM `potato_Tutorial`";
	
	public TutorialRepository(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);	
	}
	
	public void createTutorialTableIfNotExists()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(CREATE_TUTORIAL_TABLE);
		db.close();
	}
	
	public void dropTutorialTableIfExists()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(DROP_TUTORIAL_TABLE_IF_EXISTS);
		db.close();
	}
	
	public void clearTutorialTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(CLEAR_TUTORIAL_TABLE);
		db.close();
	}
	
	public void insertTutorial(TutorialEntity tutorial)
	{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", tutorial.getId());
			values.put("Name", tutorial.getName());
			values.put("Description", tutorial.getDescription());
			values.put("VideoName", tutorial.getVideoName());
			db.insert("potato_Tutorial", null, values);
			db.close();
	}
}
