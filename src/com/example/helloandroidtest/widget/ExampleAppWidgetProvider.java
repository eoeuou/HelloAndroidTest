/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.helloandroidtest.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.helloandroidtest.R;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
	// log tag
	private static final String TAG = "ExampleAppWidgetProvider";

	public static String CLICK_ACTION = "com.example.helloandroidtest.CLICK";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);

		Log.d(TAG, "onReceive==============>");
		
		if (intent.getAction().equals(CLICK_ACTION)) {
			Toast.makeText(context, "OK!!!!!", Toast.LENGTH_LONG).show();

			// final Intent intent2 = new Intent(context, MainActivity.class);
			// intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(intent2);

			Intent intent2 = new Intent(Intent.ACTION_MAIN);
			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent2.putExtra("scene", "news");
			ComponentName cn = new ComponentName("com.youhao.DoctorOnline",
					"com.youhao.DoctorOnline.DoctorOnlineActivity");
			intent2.setComponent(cn);
			context.startActivity(intent2);

			// Intent intent1 = new
			// Intent("com.example.helloandroidtest.start");
			// context.startService(intent1);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		// NewsService.updateAppWidgetIds(appWidgetIds);
		// context.startService(new Intent(context, NewsService.class));

		final int N = appWidgetIds.length;
		Log.d(TAG, "onUpdate:" + N);
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			String titlePrefix = "";
			updateAppWidget(context, appWidgetManager, appWidgetId, titlePrefix);
		}

		// context.startService(new Intent(context,
		// KKOrientationChangeService.class));
	}

	static int value = 1;

	static void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId,
			String titlePrefix) {
		Log.d(TAG, "updateAppWidget appWidgetId=" + appWidgetId
				+ " titlePrefix=" + titlePrefix);

		Intent intent = new Intent(context, MyWidgetService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

		RemoteViews views1 = new RemoteViews(context.getPackageName(),
				R.layout.appwidget_provider);
		views1.setTextViewText(R.id.appwidget_text, "已经更新" + value++);
		views1.setRemoteAdapter(R.id.news_list, intent);

		Intent actClick = new Intent(context, ExampleAppWidgetProvider.class);
		actClick.setAction(CLICK_ACTION);
		PendingIntent pending = PendingIntent.getBroadcast(context, 0,
				actClick, 0);
		views1.setOnClickPendingIntent(R.id.arrow, pending);

		appWidgetManager.updateAppWidget(appWidgetId, views1);
		appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
				R.id.news_list);
	}

}
