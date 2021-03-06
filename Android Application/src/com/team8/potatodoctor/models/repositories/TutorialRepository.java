package com.team8.potatodoctor.models.repositories;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.team8.potatodoctor.database_objects.TutorialEntity;

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
	
	@SuppressWarnings("unused")
	private Context context;
	
	public TutorialRepository(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);	
	}
	
	/**
	 * Creates a tutorial table in the local database if it doesn't already exist.
	 */
	public void createTutorialTableIfNotExists()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(CREATE_TUTORIAL_TABLE);
		db.close();
	}
	
	/**
	 * Drops a tutorial table in the local database if it doesn't already exist.
	 */
	public void dropTutorialTableIfExists()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(DROP_TUTORIAL_TABLE_IF_EXISTS);
		db.close();
	}
	
	/**
	 * Clears the tutorial table in the local database.
	 */
	public void clearTutorialTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(CLEAR_TUTORIAL_TABLE);
		db.close();
	}
	
	/** Returns the index of a tutorial in the local database with a given name.
	 * 
	 * @param name The name of the tutorial to find the index of.
	 * @return The index of the tutorial or -1 if the tutorial does not exist.
	 */
	public int getIndexOfTutorialByName(String name)
	{
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_Tutorial",null);
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(cursor.getColumnIndex("Name")).equals(name))
                {
                	return index;
                }
                index++;
            }
            while (cursor.moveToNext());
        }
        db.close();
        return -1;
	}
	
	 /** Returns a linked list of all tutorial objects in the local database.
	  * 
	 * @return A linked list of all tutorial objects in the local database.
	 */
	public LinkedList<TutorialEntity> getAllTutorials() {
	        LinkedList<TutorialEntity> tutorials = new LinkedList<TutorialEntity>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT * FROM potato_Tutorial", null);

	        if (cursor.moveToFirst()) {
	            do {
	            	TutorialEntity tutorial = new TutorialEntity();
	            	tutorial.setId(cursor.getInt(cursor.getColumnIndex("Id")));
	            	tutorial.setName(cursor.getString(cursor.getColumnIndex("Name")));
	            	tutorial.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
	            	tutorial.setFullyQualifiedPath(cursor.getString(cursor.getColumnIndex("VideoName")));
	            	Log.w("hello","SETTING PATH TO: "+cursor.getString(cursor.getColumnIndex("VideoName")));
	            	tutorials.add(tutorial);
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return tutorials;
	    }
	
	/** Inserts a tutorial into the local database.
	 * 
	 * @param tutorial The tutorial object to add to the database.
	 */
	public void insertTutorial(TutorialEntity tutorial)
	{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", tutorial.getId());
			values.put("Name", tutorial.getName());
			values.put("Description", tutorial.getDescription());
			values.put("VideoName", tutorial.getFullyQualifiedPath());
			db.insert("potato_Tutorial", null, values);
			db.close();
	}
	
	/** Returns a linked list of tutorial objects whose description or name matches the provided keywords.
	 * 
	 * @param keywords The keywords to search the tutorial objects' name and descriptions for.
	 * @return A linked list of tutorial objects whose description or name matches the provided keywords.
	 */
	public LinkedList<TutorialEntity> searchTutorials(String keywords)
	{
        LinkedList<TutorialEntity> foundEntries = new LinkedList<TutorialEntity>();

        SQLiteDatabase db = getWritableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * FROM potato_Tutorial WHERE `Name` LIKE '%"+keywords+"%' OR `Description` LIKE '%"+keywords+"%'", null);

        if (cursor.moveToFirst()) {
            do {
            	TutorialEntity tutorial = new TutorialEntity();
            	tutorial.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            	tutorial.setName(cursor.getString(cursor.getColumnIndex("Name")));
            	tutorial.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
            	//tutorial.setPhotos(getTutorialPhotos(tutorial));
                foundEntries.add(tutorial);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return foundEntries;
	}
	
	
}
