package com.sky.moduleui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAppWidgetNormal extends AppWidgetProvider{

	private static final String TAG = "MyAppWidgetNormal";
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d(TAG, "onUpdate() >>>>>");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.d(TAG, "onDeleted() >>>>>");
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		Log.d(TAG, "onEnabled() >>>>>");
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		Log.d(TAG, "onDisabled() >>>>>");
		super.onDisabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive() >>>>>");
		super.onReceive(context, intent);
	}
	
	
}