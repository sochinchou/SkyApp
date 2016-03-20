package com.sky.moduleui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;




import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

public class CalendarWidgetProvider extends AppWidgetProvider{

	private static final String TAG = "CalendarWidget";
	public static final String ACTION_UPDATE_CALENDAR = "com.skyworhauto.updatecalendar";
	public static final String ACTION_SHOW_CALENDAR = "com.skyworhauto.showcalendar";
	
	private Context mContext;
	private Dialog mDialog;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d(TAG, "onUpdate() >>>>>");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		mContext = context;
		updateRemoteViews(context);



	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.d(TAG, "onDeleted() >>>>>");
		super.onDeleted(context, appWidgetIds);
		// zhou
//		if(mAlarmManager != null){
//			mAlarmManager.cancel(mAlarmSender);
//		}
//		Log.d(TAG, "onDeleted() >>> cancel alarm");
	}

	@Override
	public void onEnabled(Context context) {
		Log.d(TAG, "onEnabled() >>>>>");
		super.onEnabled(context);
		mContext = context;

	}

	@Override
	public void onDisabled(Context context) {
		Log.d(TAG, "onDisabled() >>>>>");
		super.onDisabled(context);
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		

		
		super.onReceive(context, intent);

		
	}

	public static void updateRemoteViews(Context context){
		Log.d(TAG, "updateRemoteViews()>>>>>");
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		
		// set remoteviews adapter
		Intent intentRemote = new Intent(context, CalendarRemoteService.class);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_multi);
		views.setRemoteAdapter(R.id.listCalendar, intentRemote);
		views.setEmptyView(R.id.listCalendar, R.id.empty_view);


        
        ComponentName componentName =  new ComponentName(context, CalendarWidgetProvider.class);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(componentName), R.id.listCalendar);
        
		appWidgetManager.updateAppWidget(componentName, views);
	}
	
	
	

	
	

	
}