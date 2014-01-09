package com.example.helloandroidtest.widget;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

public class KKOrientationChangeService extends Service {
	private static final String TAG = "ExampleAppWidgetProvider";

	private static final String BCAST_CONFIGCHANGED = "android.intent.action.CONFIGURATION_CHANGED";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate()");
		IntentFilter filter = new IntentFilter();
		filter.addAction(BCAST_CONFIGCHANGED);
		this.registerReceiver(mBroadcastReceiver, filter);
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy()");
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Log.d(TAG, "onStart()");
	}

	public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent myIntent) {

			if (myIntent.getAction().equals(BCAST_CONFIGCHANGED)) {

				Log.d(TAG, "received->" + BCAST_CONFIGCHANGED);

				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
					Log.d(TAG, "LANDSCAPE");
				} else {
					Log.d(TAG, "PORTRAIT");
				}

			}
		}
	};

}
