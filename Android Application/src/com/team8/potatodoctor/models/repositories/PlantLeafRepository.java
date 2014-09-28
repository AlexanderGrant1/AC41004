package com.team8.potatodoctor.models.repositories;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.team8.potatodoctor.database_objects.PhotoEntity;
import com.team8.potatodoctor.database_objects.PhotoLinkerEntity;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;

public class PlantLeafRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_PLANT_LEAF_TABLE = "CREATE TABLE IF NOT EXISTS `potato_PlantLeaf` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`Name` varchar(50) NOT NULL,"+
	"`Description` text NOT NULL,"+
	"UNIQUE(`Name`),"+
	"PRIMARY KEY(`Id`));";
	
	private static final String CLEAR_PLANT_LEAF_TABLE = "DELETE FROM `potato_PlantLeaf`";
	
	private static final String DROP_PLANT_LEAF_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS `potato_PlantLeaf`";
	
	private static final String CREATE_PLANT_LEAF_PHOTO_TABLE = "CREATE TABLE IF NOT EXISTS `potato_PlantLeaf_photo` ("+
	"`Id` smallint unsigned NOT NULL,"+
	"`PlantLeafId` smallint unsigned NOT NULL,"+
	"`PhotoId` smallint unsigned NOT NULL,"+
	"PRIMARY KEY(`Id`));";	
	
	private static final String CLEAR_PLANT_LEAF_PHOTO_TABLE = "DELETE FROM `potato_PlantLeaf_photo`";
	
	private static final String DROP_PLANT_LEAF_PHOTO_TABLE_EXISTS = "DROP TABLE IF EXISTS `potato_PlantLeaf_photo`";
	
	private Context context;
	
	public PlantLeafRepository(Context context) {
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
	 * Creates a plant leaf table in the local database if it doesn't already exist
	 */
	public void createPlantLeafTablesIfNotExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CREATE_PLANT_LEAF_TABLE);
		db.execSQL(CREATE_PLANT_LEAF_PHOTO_TABLE);
		db.close();
	}
	
	/**
	 * Drops the plant leaf table in the local database if it already exists
	 */
	public void dropPlantLeafTablesIfExists()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(DROP_PLANT_LEAF_TABLE_IF_EXISTS);
		db.execSQL(DROP_PLANT_LEAF_PHOTO_TABLE_EXISTS);
		db.close();
	}
	
	/**
	 * Clears the plant leaf table and plant leaf linker table in the local database
	 */
	public void clearPlantLeafTables()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(CLEAR_PLANT_LEAF_TABLE);
		db.execSQL(CLEAR_PLANT_LEAF_PHOTO_TABLE);
		db.close();
	}

	/** Returns the index of plant leaf with the provided name in the local plant leaf table
	 * 
	 * @param name The name of the plant leaf to find the index of
	 * @return The index of the plant leaf with the given name
	 */
	public int getIndexOfPlantLeafByName(String name)
	{
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_PlantLeaf",null);
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
	
	/** Searches the local plant leaf table for plant leaf objects that match the given keywords
	 * 
	 * @param keywords The keywords to search the database for
	 * @return A linked list of plant leaf objects that match the given keywords
	 */
	public LinkedList<PlantLeafEntity> searchPlantLeafSymptoms(String keywords)
	{
        LinkedList<PlantLeafEntity> foundEntries = new LinkedList<PlantLeafEntity>();

        SQLiteDatabase db = getWritableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * FROM potato_PlantLeaf WHERE `Name` LIKE '%"+keywords+"%' OR `Description` LIKE '%"+keywords+"%'", null);

        if (cursor.moveToFirst()) {
            do {
            	PlantLeafEntity plantLeaf = new PlantLeafEntity();
            	plantLeaf.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            	plantLeaf.setName(cursor.getString(cursor.getColumnIndex("Name")));
            	plantLeaf.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                plantLeaf.setPhotos(getPlantLeafPhotos(plantLeaf));
                foundEntries.add(plantLeaf);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return foundEntries;
	}
	
	
    /** Returns a linked list of all plant leaf objects in the database
     * @return A linked list of all plant leaf objects in the database
     */
    public LinkedList<PlantLeafEntity> getAllPlantLeafs() {
        LinkedList<PlantLeafEntity> plantLeafs = new LinkedList<PlantLeafEntity>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM potato_PlantLeaf", null);

        if (cursor.moveToFirst()) {
            do {
            	PlantLeafEntity plantLeaf = new PlantLeafEntity();
            	plantLeaf.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            	plantLeaf.setName(cursor.getString(cursor.getColumnIndex("Name")));
            	plantLeaf.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
            	plantLeaf.setPhotos(getPlantLeafPhotos(plantLeaf));
            	plantLeafs.add(plantLeaf);
            }
            while (cursor.moveToNext());

        }
        db.close();
        return plantLeafs;
    }
	
	/** Inserts a plant leaf object into the database.
	 * 
	 * @param plantLeaf The plant leaf object to add to the database.
	 */
	public void insertPlantLeaf(PlantLeafEntity plantLeaf)
	{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", plantLeaf.getId());
			values.put("Name", plantLeaf.getName());
			values.put("Description", plantLeaf.getDescription());
			db.insert("potato_PlantLeaf", null, values);
			db.close();
	}
	
	/** Inserts a plant leaf photo linker into the database.
	 * 
	 * @param linker The linker to add to the database.
	 */
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
	
	 /** Returns a linked list of the photo ids for a plant leaf in the database.
	 * @param plantLeaf
	 * @return A linked list of the photo ids for a plant leaf in the database.
	 */
	private LinkedList<Integer> getPestPhotoLinkersForPlantLeaf(PlantLeafEntity plantLeaf) {
	        LinkedList<Integer> photoIds = new LinkedList<Integer>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT photoId FROM potato_PlantLeaf_photo WHERE PlantLeafId = "+plantLeaf.getId(), null);

	        if (cursor.moveToFirst()) {
	            do {
	            	photoIds.add(cursor.getInt(cursor.getColumnIndex("PhotoId")));
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return photoIds;
	    }
		
		/** Returns a linked list of photo objects for a plant leaf object.
		 * @param plantLeaf The plant leaf object to get all photos for.
		 * @return A linked list of photo objects for a plant leaf object.
		 */
		public LinkedList<PhotoEntity> getPlantLeafPhotos(PlantLeafEntity plantLeaf)
		{
			LinkedList<Integer> photoIds = getPestPhotoLinkersForPlantLeaf(plantLeaf);
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
	            	photo.setFullyQualifiedPath(context.getFilesDir()+"/PlantLeaf/"+cursor.getString(cursor.getColumnIndex("Name")));
	            	photos.add(photo);
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return photos;
		}
}
