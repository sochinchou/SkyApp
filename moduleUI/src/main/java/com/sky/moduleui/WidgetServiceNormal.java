package com.sky.moduleui;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetServiceNormal extends Service{

	private static final String TAG = "WidgetServiceNormal";

	private Context mContext;
	private AppWidgetManager mAppWidgetManager;
	private RemoteViews viewsTime;

	private DecimalFormat mFormat8Hour = new DecimalFormat("##"); //  8小时制格式，有时是1位数字
	private DecimalFormat mFormat24hour = new DecimalFormat("00"); // 24小时制格式 ，固定2位数字
	
	private TimerTask mTimerTask;
	private Timer mTimer;
	
	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate () >>>>>");
		super.onCreate();
		mContext = this;
		mAppWidgetManager = AppWidgetManager.getInstance(mContext);
		viewsTime = new RemoteViews(mContext.getPackageName(), R.layout.widget_normal);

		
		mTimer = new Timer();
		mTimerTask = new ControlTask();

		// broadcast receiver
		/*
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filter.addAction(Intent.ACTION_LOCALE_CHANGED);
		registerReceiver(mReceiver, filter);
		*/
		

		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand() >>>>>");
		
		updateTimeWidget();

		
		// 如果以前有启动的Timer，取消，重新启动
		if(mTimer != null && mTimerTask != null){
			mTimer.cancel();
			mTimer = new Timer();
			mTimerTask = new ControlTask();
			mTimer.schedule(mTimerTask, 0, 500);
		}
		return super.onStartCommand(intent, flags, startId);
		
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy () >>>>>");
		super.onDestroy();
		
//		unregisterReceiver(mReceiver);
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind() >>>>>");
		return null;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "onUnbind() >>>>>");
		return super.onUnbind(intent);
	}
	
	
	private class ControlTask extends TimerTask{

		@Override
		public void run() {
			updateTimeWidget();
		}
	}
	
	// ************************************************************
	// Handler
	// ************************************************************
	private static final int MSG_HANDLER1 = 100;
	private static final int MSG_HANDLER2 = 101;
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO
			switch (msg.what) {
			case MSG_HANDLER1:
				break;

			case MSG_HANDLER2:
				break;
			}
		}
	};

	// ************************************************************
	// BroadcastReceiver
	// ************************************************************
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.d(TAG, "onReceive() >>> action = " + intent.getAction());
            
            if(intent.getAction().equals(Intent.ACTION_TIME_TICK) 
            		|| intent.getAction().equals(Intent.ACTION_DATE_CHANGED)
            		|| intent.getAction().equals(Intent.ACTION_TIME_CHANGED)
            		|| intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)
            		|| intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)){
            	updateTimeWidget();
            }
        }
    };
    

	private void updateTimeWidget(){
//		Log.d(TAG, "updateTimeWidget() >>>");
		Calendar calendar = Calendar.getInstance();
		int hour = 0;
		int minute = 0;
		String strTime = null;
		
		if(DateFormat.is24HourFormat(mContext)){
			hour = calendar.get(Calendar.HOUR_OF_DAY); 
			minute = calendar.get(Calendar.MINUTE);
			strTime = mFormat24hour.format(hour) + ":" + mFormat24hour.format(minute);
		}else{
			hour = calendar.get(Calendar.HOUR); 
			minute = calendar.get(Calendar.MINUTE);
			strTime = mFormat8Hour.format(hour) + ":" + mFormat24hour.format(minute);
		}
		
		viewsTime.setTextViewText(R.id.txtTime, strTime);
		
		mAppWidgetManager.updateAppWidget(new ComponentName(mContext, MyAppWidgetNormal.class), viewsTime);
	}
	

}
