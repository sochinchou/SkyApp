package com.sky.moduleui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class CalendarRemoteService extends RemoteViewsService
 {
	private static final String TAG = "CalendarRemoteService";
	private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
	
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new CalendarRemoteFactory(getApplicationContext(), intent);
	}
	
	
	class CalendarRemoteFactory implements RemoteViewsFactory{

	    private Context mContext;
		private ArrayList<String> mListStr = new ArrayList<String>();

		public CalendarRemoteFactory(Context context, Intent intent){
			mContext = context;
			for (int i = 0; i < 10; i ++){
				mListStr.add("i == " + i);
			}

		}
		
		@Override
		public void onCreate() {
			//Log.d(TAG, "onCreate( 1 ) >>>");
		}

		@Override
		public void onDataSetChanged() {
			//Log.d(TAG, "onDataSetChanged( 2 ) >>>");

		}

		@Override
		public boolean hasStableIds() {
			//Log.d(TAG, "hasStableIds( 3 ) >>>");
			return true;
		}
		
		@Override
		public int getViewTypeCount() {
			//Log.d(TAG, "getViewTypeCount( 4 ) >>>");
			return 1;
		}
		
		@Override
		public int getCount() {
			//Log.d(TAG, "getCount( 5 ) >>>");


			return mListStr.size();
		}
		
		@Override
		public RemoteViews getLoadingView() {
			//Log.d(TAG, "getLoadingView( 6 ) >>>");
			return null;
		}
		
		@Override
		public RemoteViews getViewAt(int position) {
			//Log.d(TAG, "getViewAt( 7 ) >>> position = " + position);
			


			RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.adapter_calendar_widget_list);
			
            rv.setTextViewText(R.id.txtWidgetList, mListStr.get(position));

            return rv;
		}

		@Override
		public long getItemId(int position) {
			//Log.d(TAG, "getItemId( 8 ) >>>");
			return position;
		}

		@Override
		public void onDestroy() {
			//Log.d(TAG, "onDestroy() >>>");

		}
		
	}

}