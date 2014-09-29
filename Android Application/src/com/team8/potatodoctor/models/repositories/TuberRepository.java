package com.team8.potatodoctor.models.repositories;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.team8.potatodoctor.database_objects.TutorialLinker;
import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PhotoEntity;
import com.team8.potatodoctor.database_objects.PhotoLinkerEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.database_objects.TutorialEntity;

public class TuberRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_TUBER_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Tuber` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(50) NOT NULL,"+
	"`Description` text NOT NULL,"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`));";
	
	private static final String DROP_TUBER_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_Tuber`";
	
	private static final String CLEAR_TUBER_TABLE = "DELETE FROM `potato_Tuber`";
	
	private static final String CREATE_TUBER_PHOTOS = "CREATE TABLE IF NOT EXISTS `potato_Tuber_photo` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`TuberId` smallint unsigned NOT NULL,"+
	"`PhotoId` smallint unsigned NOT NULL,"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CREATE_TUBER_TUTORIAL_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Tuber_tutorial` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`PestId` smallint unsigned NOT NULL,"+
	"`TutorialId` smallint unsigned NOT NULL,"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CLEAR_TUBER_PHOTO_TABLE = "DELETE FROM `potato_Tuber_photo`";
	
	private static final String DROP_TUBER_PHOTOS_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_Tuber_photo`";
	
	private static final String DROP_TUBER_TUTORIAL_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_Tuber_tutorial`";
	
	private Context context;
	
	public TuberRepository(Context context) {
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
	 * Creates a tuber table and a tuber photo table if it doesn't already exist.
	 */
	public void createTuberTablesIfNotExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CREATE_TUBER_TABLE);
		db.execSQL(CREATE_TUBER_TUTORIAL_TABLE);
		db.execSQL(CREATE_TUBER_PHOTOS);
		db.close();
	}
	
	/**
	 * Drops the tuber and tuber photo linker tables from the database if they exist.
	 */
	public void dropTuberTablesIfExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(DROP_TUBER_TABLE_IF_EXISTS);
		db.execSQL(DROP_TUBER_PHOTOS_TABLE_IF_EXISTS);
		db.execSQL(DROP_TUBER_TUTORIAL_TABLE_IF_EXISTS);
		db.close();
	}
	
	/**
	 * Clears both the tuber and tuber photo linker tables
	 */
	public void clearTuberTables()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CLEAR_TUBER_TABLE);
		db.execSQL(CLEAR_TUBER_PHOTO_TABLE);
		db.close();
	}
	
	/** Returns the index of a given tuber name in the database
	 * @param name
	 * @return
	 */
	public int getIndexOfTuberByName(String name)
	{
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_Tuber",null);
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
	
	/** Returns a linked list of tubers whose description and name contain given keywords.
	 * @param keywords A string of keywords to search a tuber's name and description for.
	 * @return A linked list of tubers whose description and name contain given keywords.
	 */
	public LinkedList<TuberEntity> searchTubers(String keywords)
	{
        LinkedList<TuberEntity> foundEntries = new LinkedList<TuberEntity>();

        SQLiteDatabase db = getWritableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * FROM potato_Tuber WHERE `Name` LIKE '%"+keywords+"%' OR `Description` LIKE '%"+keywords+"%'", null);

        if (cursor.moveToFirst()) {
            do {
            	TuberEntity tuber = new TuberEntity();
            	tuber.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            	tuber.setName(cursor.getString(cursor.getColumnIndex("Name")));
            	tuber.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
            	tuber.setPhotos(getTuberPhotos(tuber));
                foundEntries.add(tuber);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return foundEntries;
	}
	
	 /** Returns a linked list of tubers from the database.
	 * @return A linked list of tubers from the database.
	 */
	public LinkedList<TuberEntity> getAllTubers() {
	        LinkedList<TuberEntity> tubers = new LinkedList<TuberEntity>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT * FROM potato_Tuber", null);

	        if (cursor.moveToFirst()) {
	            do {
	            	TuberEntity tuber = new TuberEntity();
	            	tuber.setId(cursor.getInt(cursor.getColumnIndex("Id")));
	            	tuber.setName(cursor.getString(cursor.getColumnIndex("Name")));
	                tuber.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
	                tuber.setPhotos(getTuberPhotos(tuber));
	                tuber.setTutorials(getTuberTutorials(tuber));
	                tubers.add(tuber);
	            }
	            while (cursor.moveToNext());

	        }
	        db.close();
	        return tubers;
	    }
		
		/** Inserts a given tuber into the database.
		 * 
		 * @param tuber The tuber to add to the database.
		 */
		public void insertTuber(TuberEntity tuber)
		{
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("Id", tuber.getId());
				values.put("Name", tuber.getName());
				values.put("Description", tuber.getDescription());
				db.insert("potato_Tuber", null, values);
				db.close();
		}
		
		/** Inserts a tuber photo linker into the database
		 * 
		 * @param linker A tuber photo linker object to add to the database
		 */
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
		
		public void insertTuberTutorialLinker(TutorialLinker linker)
		{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", linker.getId());
			values.put("TutorialId", linker.getTutorialId());
			values.put("TuberId", linker.getEntryId());
			db.insert("potato_Tuber_tutorial", null, values);
			db.close();
		}
		
	    /** Returns all photo ids for a tuber.
	     * 
	     * @param tuber The tuber to get photo linkers for.
	     * @return A linked list of photo ids for a tuber.
	     */
	    private LinkedList<Integer> getPhotoIdsForTuber(TuberEntity tuber) {
	        LinkedList<Integer> photoIds = new LinkedList<Integer>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT photoId FROM potato_Tuber_photo WHERE TuberId = "+tuber.getId(), null);

	        if (cursor.moveToFirst()) {
	            do {
	            	photoIds.add(cursor.getInt(cursor.getColumnIndex("PhotoId")));
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return photoIds;
	    }
		
		/** Returns a linked list of photos for the given tuber.
		 * 
		 * @param tuber The tuber to get photos for.
		 * @return A linked list of photos for the given tuber.
		 */
		public LinkedList<PhotoEntity> getTuberPhotos(TuberEntity tuber)
		{
			LinkedList<Integer> photoIds = getPhotoIdsForTuber(tuber);
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
	            	photo.setFullyQualifiedPath(context.getFilesDir()+"/Tubers/"+cursor.getString(cursor.getColumnIndex("Name")));
	            	photos.add(photo);
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return photos;
		}
		
		 /** Returns all photo ids for a tuber.
	     * 
	     * @param tuber The tuber to get photo linkers for.
	     * @return A linked list of photo ids for a tuber.
	     */
	    private LinkedList<Integer> getTutorialIdsForTuber(TuberEntity tuber) {
	        LinkedList<Integer> photoIds = new LinkedList<Integer>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT TutorialId FROM potato_Tuber_tutorial WHERE TuberId = "+tuber.getId(), null);

	        if (cursor.moveToFirst()) {
	            do {
	            	photoIds.add(cursor.getInt(cursor.getColumnIndex("TutorialId")));
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return photoIds;
	    }
		
		/** Returns a linked list of photos for the given tuber.
		 * 
		 * @param tuber The tuber to get photos for.
		 * @return A linked list of photos for the given tuber.
		 */
		public LinkedList<TutorialEntity> getTuberTutorials(TuberEntity tuber)
		{
			LinkedList<Integer> tutorialIds = getTutorialIdsForTuber(tuber);
			if(tutorialIds.size() == 0)
			{
				return new LinkedList<TutorialEntity>();
			}
			String SQLQuery = "SELECT * FROM potato_Tutorial WHERE Id = ";
			for(int i = 0; i < tutorialIds.size(); i++)
			{
				SQLQuery+= tutorialIds.get(i).toString();
				if(i < tutorialIds.size() - 1)
				{
					SQLQuery+= " OR Id = ";
				}
			}
			LinkedList<TutorialEntity> tutorials = new LinkedList<TutorialEntity>();
	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery(SQLQuery, null);

	        if (cursor.moveToFirst()) {
	            do {
	            	TutorialEntity tutorial = new TutorialEntity();
	            	tutorial.setId(cursor.getInt(cursor.getColumnIndex("Id")));
	            	tutorial.setName(cursor.getString(cursor.getColumnIndexOrThrow("Name")));
	            	tutorial.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("Description")));
	            	tutorial.setFullyQualifiedPath(context.getFilesDir()+"/Tutorials/"+cursor.getString(cursor.getColumnIndex("VideoName")));
	            	tutorials.add(tutorial);
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return tutorials;
		}
}
