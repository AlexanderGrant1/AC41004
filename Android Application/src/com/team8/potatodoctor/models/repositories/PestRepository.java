package com.team8.potatodoctor.models.repositories;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.database_objects.PhotoEntity;
import com.team8.potatodoctor.database_objects.PhotoLinkerEntity;

public class PestRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_PEST_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Pest` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(50) NOT NULL,"+
	"`Description` text NOT NULL,"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CLEAR_PEST_TABLE = "DELETE FROM `potato_Pest`";
	
	private static final String DROP_PESTS_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_Pest`";
	
	private static final String CREATE_PEST_PHOTOS_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Pest_photo` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`PestId` smallint unsigned NOT NULL,"+
	"`PhotoId` smallint unsigned NOT NULL,"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CLEAR_PEST_PHOTOS_TABLE = "DELETE FROM `potato_Pest_photo`";
	
	private static final String DROP_PESTS_PHOTO_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_Pest_photo`";
	
	private Context context;
	
	public PestRepository(Context context) {
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
	 * Creates the pest table in the local database if it doesn't exist
	 */
	public void createPestTablesIfNotExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CREATE_PEST_TABLE);
		db.execSQL(CREATE_PEST_PHOTOS_TABLE);
		db.close();
	}
	
	/**
	 * Drops the local pest table in the database
	 */
	public void dropPestTableIfExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(DROP_PESTS_TABLE_IF_EXISTS);
		db.execSQL(DROP_PESTS_PHOTO_TABLE_IF_EXISTS);
		db.close();
	}
	
	/**
	 * Clears the local pest table
	 */
	public void clearPestTables()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CLEAR_PEST_TABLE);
		db.execSQL(CLEAR_PEST_PHOTOS_TABLE);
		db.close();
	}
	
	/** Returns the index in the database of a pest object
	 * 
	 * @param name The name of the pest object to get the index of
	 * @return The index of the pest object
	 */
	public int getIndexOfPestByName(String name)
	{
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_Pest",null);
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
	
	
	/** Returns a linked list of pest objects whose description or name matches supplied keywords
	 *  
	 * @param keywords Keywords to search pest objects with
	 * @return A linked list of pest objects that match the keywords provided
	 */
	public LinkedList<PestEntity> searchPests(String keywords)
	{
        LinkedList<PestEntity> foundPests = new LinkedList<PestEntity>();

        SQLiteDatabase db = getWritableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * FROM potato_Pest WHERE `Name` LIKE '%"+keywords+"%' OR `Description` LIKE '%"+keywords+"%'", null);

        if (cursor.moveToFirst()) {
            do {
                PestEntity pest = new PestEntity();
                pest.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                pest.setName(cursor.getString(cursor.getColumnIndex("Name")));
                pest.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                pest.setPhotos(getPestPhotos(pest));
                foundPests.add(pest);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return foundPests;
	}
	
    /** Returns a linked list of pest objects taken from the database
     * 
     * @return A linked list of pest objects taken from the database
     */
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
                pest.setPhotos(getPestPhotos(pest));
                pests.add(pest);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return pests;
    }
    
	
	/** Inserts a pest object into the local database
	 * 
	 * @param pest The pest to insert into the local database
	 */
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
			db.insert("potato_Pest_photo", null, values);
			db.close();
	}
	
    private LinkedList<Integer> getPestPhotoLinkersForPest(PestEntity pest) {
        LinkedList<Integer> photoIds = new LinkedList<Integer>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT photoId FROM potato_Pest_photo WHERE PestId = "+pest.getId(), null);

        if (cursor.moveToFirst()) {
            do {
            	photoIds.add(cursor.getInt(cursor.getColumnIndex("PhotoId")));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return photoIds;
    }
	
	/** Returns all photo objects associated with a pest
	 * 
	 * @param pest The pest to get photos for
	 * @return A linked list of photo objects associated with the given pest
	 */
	public LinkedList<PhotoEntity> getPestPhotos(PestEntity pest)
	{
		LinkedList<Integer> photoIds = getPestPhotoLinkersForPest(pest);
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
            	photo.setFullyQualifiedPath(context.getFilesDir()+"/Pests/"+cursor.getString(cursor.getColumnIndex("Name")));
            	photos.add(photo);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return photos;
	}
}
