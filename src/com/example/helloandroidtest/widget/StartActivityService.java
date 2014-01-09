package com.example.helloandroidtest.widget;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StartActivityService extends Service {

	private static final String TAG = "ExampleAppWidgetProvider";

	@Override
	public IBinder onBind(Intent paramIntent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.v(TAG, "onCreate");

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.v(TAG, "StartActivityService");

		Intent intent2 = new Intent(Intent.ACTION_MAIN);
		intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ComponentName cn = new ComponentName("com.youhao.kkTouchFantasy",
				"com.youhao.kkTouchFantasy.kkTouchFantasy");
		intent2.setComponent(cn);

		this.startActivity(intent2);
	}

}