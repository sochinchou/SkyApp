package com.sky.moduleui;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class CalendarContentProvider extends ContentProvider {
	public static final String TAG = "MyContentProviderSimple";
	
	public static final String AUTHORITY = "com.skyworthauto.calendar.provider";
	private static final UriMatcher uriMatcher;
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "calendar", DBItem.ITEMS);
		uriMatcher.addURI(AUTHORITY, "calendar/#", DBItem.ITEM_ID);
	}

	
	// --------------------------- Database Helper

    private static class DatabaseHelper extends SQLiteOpenHelper{
        private static final String DATABASE_NAME = "calendar.db";
        private static final int DATABASE_VERSION = 1;
       
		  DatabaseHelper(Context context) {
		   super(context, DATABASE_NAME, null, DATABASE_VERSION);
		  }
		  
		  @Override
		  public void onCreate(SQLiteDatabase db) {
		   db.execSQL("CREATE TABLE " + DBItem.TABLE_NAME + " ("
		     + DBItem._ID + " INTEGER PRIMARY KEY,"
		     + DBItem.TITLE + " TEXT,"
		     + DBItem.CONTENT+ " TEXT,"
		     + DBItem.DATE+ " TEXT,"
		     + DBItem.TIME + " TEXT"
		     + ");");

		  }
		  
		  @Override
		  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		            db.execSQL("DROP TABLE IF EXISTS " + DBItem.TABLE_NAME);
		            onCreate(db);
		  }
     
    }

	// ----------------------------------------------------------------------

	private SQLiteDatabase mDb;

	// ---------------------------- onCreate ------------------------------
	@Override
	public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper helper = new DatabaseHelper(context);
		mDb = helper.getWritableDatabase();
		// Log.d(TAG, "knowledge provider onCreate() >>>>>");
		return (mDb == null) ? false : true;
	}

	// ---------------------------- query ------------------------------
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// Log.d(TAG, "query() >>>>> " + uri);
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		switch (uriMatcher.match(uri)) {
		case DBItem.ITEM_ID:
			qb.setTables(DBItem.TABLE_NAME);
			qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
			break;

		case DBItem.ITEMS:
			qb.setTables(DBItem.TABLE_NAME);
			if (TextUtils.isEmpty(sortOrder))
				sortOrder = DBItem.DEFAULT_SORT_ORDER;
			break;

		default:
			throw new IllegalArgumentException("Unknown URI : " + uri);
		}
		// ---register to watch a content URI for changes---
		Cursor c = qb.query(mDb, projection, selection, selectionArgs, null, null, sortOrder); // requery
																								// is
																								// deprecated
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	// ---------------------------- getType ------------------------------
	@Override
	public String getType(Uri uri) {
		// Log.d(TAG, "getType() >>>>> " + uri);
		switch (uriMatcher.match(uri)) {
		case DBItem.ITEM_ID:
			return DBItem.CONTENT_ITEM_TYPE;
		case DBItem.ITEMS:
			return DBItem.CONTENT_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI : " + uri);
		}
	}

	// ---------------------------- insert ------------------------------
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// Log.d(TAG, "insert() >>>>> " + uri);
		long rowId = 0L;
		Uri resultUri = null;

		switch (uriMatcher.match(uri)) {
		case DBItem.ITEMS:
			rowId = mDb.insert(DBItem.TABLE_NAME, null, values);
			if (rowId > 0) {
				resultUri = ContentUris.withAppendedId(DBItem.CONTENT_URI, rowId);
			}
			break;

		default:
			throw new IllegalArgumentException("Unknown URI : " + uri);
		}

		if (resultUri != null) {
			getContext().getContentResolver().notifyChange(uri, null);
			return resultUri;
		}
		throw new SQLException("Failed to insert into : " + uri);

	}

	// ---------------------------- delete ------------------------------
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Log.d(TAG, "delete() >>>>> " + uri);
		int count = 0;

		switch (uriMatcher.match(uri)) {
		case DBItem.ITEM_ID:
			count = mDb.delete(DBItem.TABLE_NAME, BaseColumns._ID + "=" + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
			break;

		case DBItem.ITEMS:
			count = mDb.delete(DBItem.TABLE_NAME, selection, selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI : " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	// ---------------------------- update ------------------------------
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// Log.d(TAG, "update() >>>>> " + uri);
		int count = 0;

		switch (uriMatcher.match(uri)) {
		case DBItem.ITEM_ID:
			count = mDb.update(DBItem.TABLE_NAME, values, BaseColumns._ID + "=" + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
			break;

		case DBItem.ITEMS:
			count = mDb.update(DBItem.TABLE_NAME, values, selection, selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI : " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	/*
	 * if (c.moveToFirst()) { do { } while (c.moveToNext()); }
	 */



	


}