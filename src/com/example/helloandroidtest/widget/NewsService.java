package com.example.helloandroidtest.widget;

import java.util.LinkedList;
import java.util.Queue;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

public class NewsService extends Service implements Runnable {
	private static final String TAG = "ExampleAppWidgetProvider";

	private static Queue<Integer> sAppWidgetIds = new LinkedList<Integer>();

	public static final String ACTION_UPDATE_ALL = "com.xxxx.news.UPDATE_ALL";

	private static boolean sThreadRunning = false;

	private static Object sLock = new Object();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static void updateAppWidgetIds(int[] appWidgetIds) {
		synchronized (sLock) {
			for (int appWidgetId : appWidgetIds) {
				sAppWidgetIds.add(appWidgetId);
			}
		}
	}

	public static int getNextWidgetId() {
		synchronized (sLock) {
			if (sAppWidgetIds.peek() == null) {
				return AppWidgetManager.INVALID_APPWIDGET_ID;
			} else {
				return sAppWidgetIds.poll();

			}
		}
	}

	private static boolean hasMoreUpdates() {
		synchronized (sLock) {
			boolean hasMore = !sAppWidgetIds.isEmpty();
			if (!hasMore) {
				sThreadRunning = false;
			}
			return hasMore;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		Log.d(TAG, "onStart NEWS service===================?");
		if (null != intent) {
			if (ACTION_UPDATE_ALL.equals(intent.getAction())) {
				AppWidgetManager widget = AppWidgetManager.getInstance(this);
				updateAppWidgetIds(widget.getAppWidgetIds(new ComponentName(
						this, ExampleAppWidgetProvider.class)));
			}
		}
		synchronized (sLock) {
			if (!sThreadRunning) {
				sThreadRunning = true;
				new Thread(this).start();
			}
		}
	}

	@Override
	public void run() {
		Log.d(TAG, "wo ri ni da ye!!!!!!!!");

		SharedPreferences setting = getSharedPreferences(
				"com.xxxx.news_preferences", 0);
		String updateTime = setting.getString("list_time", "1800000");
		String updateUrl = setting.getString("list_site", "xxxxxxxxx");

		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		RemoteViews updateViews = null;

		while (hasMoreUpdates()) {
			int appWidgetId = getNextWidgetId();
			// List<RssNews> listNews = NewsContenttList.getNewsList(updateUrl);
			// if (listNews != null) {
			// updateViews = ExampleAppWidgetProvider.updateAppWidget(this,
			// listNews);
			// }
			ExampleAppWidgetProvider.updateAppWidget(this, appWidgetManager,
					appWidgetId, "");
			if (updateViews != null) {
				appWidgetManager.updateAppWidget(appWidgetId, updateViews);
			}
		}

		Intent updateIntent = new Intent(ACTION_UPDATE_ALL);
		updateIntent.setClass(this, NewsService.class);
		PendingIntent pending = PendingIntent.getService(this, 0, updateIntent,
				0);

		Time time = new Time();
		long nowMillis = System.currentTimeMillis();
		time.set(nowMillis + Long.parseLong(updateTime));
		long updateTimes = time.toMillis(true);
		Log.d(TAG, "request next update at " + updateTimes);

		AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarm.set(AlarmManager.RTC_WAKEUP, updateTimes, pending);
		stopSelf();
	}

}
