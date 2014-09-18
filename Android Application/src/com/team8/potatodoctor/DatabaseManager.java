package com.team8.potatodoctor;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** Helper to the database, manages versions and creation */
public class DatabaseManager extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "kudu.db";
	private static final int DATABASE_VERSION = 1;
	public Session ns = new Session();

	private static final String CREATE_PHOTO_TABLE = "CREATE TABLE IF NOT EXISTS `potato_Photo` ("+
" `Id` smallint unsigned NOT NULL,"+
"`Name` varchar(20) NOT NULL,"+
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
	
	//SessionTable
	public static final String SESSION_TABLE = "session";
	//SessionColumns
	public static final String SESSION_UUID = "session_uuid";
	public static final String SESSION_USERNAME = "session_username";
	//SessionTable - Create Statement
	public static final String CREATE_TABLE_SESSION = "CREATE TABLE IF NOT EXISTS "
			+SESSION_TABLE+ "(" + SESSION_USERNAME + " TEXT PRIMARY KEY,"
			+SESSION_UUID+ " TEXT" + ")";
	
	private String[] allSessionColumns = new String[] { SESSION_USERNAME, SESSION_UUID };
	public DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SESSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + SESSION_TABLE);
		onCreate(db);	
	}
	
	public void createTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(CREATE_TABLE_SESSION);
		db.execSQL(CREATE_PEST_TABLE);
		db.execSQL(CREATE_PHOTO_TABLE);
		db.execSQL(CREATE_PEST_PHOTOS_TABLE);
		db.execSQL(CREATE_PLANT_LEAF_TABLE);
		db.execSQL(CREATE_PLANT_LEAF_PHOTO_TABLE);
		db.execSQL(CREATE_TUBER_TABLE);
		db.execSQL(CREATE_TUBER_PHOTOS);
		db.execSQL(CREATE_TUTORIAL_TABLE);
	}
	
	/*
	 * SESSION TABLE
	 */
	public void insertSession(String uuid, String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SESSION_UUID, uuid);
		values.put(SESSION_USERNAME, username);
		db.insert(SESSION_TABLE, null, values);
		ns.setUsername(username);
		ns.setUuid(uuid);
		db.close();
	}
	
	public Session getSession(String uuid, String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(SESSION_TABLE, allSessionColumns, null, null, null, null, null);
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToFirst();
			String sessionUsername = cursor.getString(0);
			String sessionUUID = cursor.getString(1);
			
			ns.setUsername(sessionUsername);
			ns.setUuid(sessionUUID);
			db.close();
			return ns;
		} else {
			insertSession(uuid, username);
		}
		return null;
	}
	
	public Session checkSessionExists() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(SESSION_TABLE, allSessionColumns, null, null, null, null, null);
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToFirst();
			return ns;
		}
		ns.setUsername(null);
		ns.setUuid(null);
		return ns;
	}
	
	public void deleteSession(String uuid, String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(SESSION_TABLE, SESSION_USERNAME+" = ?", new String[] { username });
		ns.setUsername(null);
		ns.setUuid(null);
		db.close();
	}
	
}