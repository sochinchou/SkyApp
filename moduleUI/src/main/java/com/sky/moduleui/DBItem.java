package com.sky.moduleui;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DBItem implements BaseColumns {
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String DATE = "date";
	public static final String TIME = "time"; 

	//------------------------------------------------------------------------------------------------------------------------------------
	public static final String TABLE_NAME = "calendar_table";
	public static final Uri CONTENT_URI = Uri.parse("content://" + CalendarContentProvider.AUTHORITY + "/calendar");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.skyworthauto.calendar";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.skyworthauto.calendar";
	public static final int ITEMS = 201;
	public static final int ITEM_ID = 202;
	public static final String DEFAULT_SORT_ORDER = _ID + " DESC"; // ASC
	public static final String SORT_ORDER_DETAIL = DATE + " DESC"; // ASC
	public static final String SORT_ORDER_TODAY = TIME + " ASC"; // ASC
	public static final String[] PROJECTION = { _ID, TITLE, CONTENT, DATE, TIME };

	public static CalendarItem getItem(Context context, long id) {
		CalendarItem item = null;
		Cursor cursor = context.getContentResolver().query(CONTENT_URI,
				PROJECTION, DBItem._ID + "=" + id, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			try {
				String title = cursor.getString(cursor.getColumnIndex(TITLE));
				String content = cursor.getString(cursor.getColumnIndex(CONTENT));
				String date = cursor.getString(cursor.getColumnIndex(DATE));
				String time = cursor.getString(cursor.getColumnIndex(TIME));
				item = new CalendarItem(id, title, content, date, time);
			} finally {
				cursor.close();
			}
		}

		return item;
	}
	
	public static Uri insertItem(Context context, ContentValues values) {
		Uri uri = context.getContentResolver().insert(CONTENT_URI, values);
		context.getContentResolver().notifyChange(CONTENT_URI, null);
		return uri;
	}
	
	public static int updateItem(Context context, long id, ContentValues values) {
		Uri uri = Uri.withAppendedPath(CONTENT_URI, id + "");
//		Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
		int count = context.getContentResolver().update(uri, values, null, null);
		context.getContentResolver().notifyChange(CONTENT_URI, null);
		return count;
	}

	public static int deleteItem(Context context, long id) {
		Uri uri = Uri.withAppendedPath(CONTENT_URI, id + "");
		int count = context.getContentResolver().delete(uri, null, null);
		context.getContentResolver().notifyChange(CONTENT_URI, null);
		return count;
	}

	public static int clearItem(Context context) {
		int count = context.getContentResolver().delete(CONTENT_URI, null, null);
		context.getContentResolver().notifyChange(CONTENT_URI, null);
		return count;
	}
	
	public static boolean isItemExist(Context context, String title, String content) {
		int count = 0;
		String selection = DBItem.TITLE + "='" + title + "' AND " + DBItem.CONTENT + "='" + content + "'";
		Cursor cursor = context.getContentResolver().query(CONTENT_URI, PROJECTION, selection, null, null);
		
		if(cursor != null){
			try{
				count = cursor.getCount();
			}finally{
				cursor.close();
			}
		}
		
		return count > 0;
	}

	public static int getCount(Context context, String key, String value) {
		int count = 0;
		Cursor cursor = context.getContentResolver().query(CONTENT_URI, PROJECTION, key + "='" + value + "'", null, null);
		
		if(cursor != null){
			try{
				count = cursor.getCount();
			}finally{
				cursor.close();
			}
		}
		
		return count;
		
	}

}