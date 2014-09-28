package com.team8.potatodoctor.models.repositories;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.team8.potatodoctor.database_objects.PhotoEntity;
import com.team8.potatodoctor.database_objects.PhotoLinkerEntity;
import com.team8.potatodoctor.database_objects.TuberEntity;

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
	
	private static final String CLEAR_TUBER_PHOTO_TABLE = "DELETE FROM `potato_Tuber_photo`";
	
	private static final String DROP_TUBER_PHOTOS_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_Tuber_photo`";
	
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
		
	    /** Returns all photo ids for a tuber.
	     * 
	     * @param tuber The tuber to get photo linkers for.
	     * @return A linked list of photo ids for a tuber.
	     */
	    private LinkedList<Integer> getPhotoLinkersForTuber(TuberEntity tuber) {
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
			LinkedList<Integer> photoIds = getPhotoLinkersForTuber(tuber);
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
}
