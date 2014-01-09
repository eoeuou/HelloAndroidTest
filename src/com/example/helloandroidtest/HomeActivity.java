package com.example.helloandroidtest;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.activities.loaders.LoaderActivity;
import com.example.api.activities.loaders.LoaderCustomActivity;

public class HomeActivity extends BaseActivity implements OnItemClickListener {

	private static final String[] mStrings = { "WindowManagerTest",
			"LoaderTest", "LoaderCustomTest" };
	private static final Class<?>[] mClasses = { MainActivity.class,
			LoaderActivity.class, LoaderCustomActivity.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		ListView homeListView = (ListView) findViewById(R.id.homeList);
		homeListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mStrings));
		homeListView.setOnItemClickListener(this);

		int iconId = this.getResources().getIdentifier("icon", "drawable-mdpi",
				this.getPackageName());

		Notification notification = new Notification(iconId, "",
				System.currentTimeMillis());

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		String info = (String) textView.getText();
		Toast.makeText(this, textView.getText(), Toast.LENGTH_LONG).show();
		int index = 0;
		for (String string : mStrings) {
			if (string.equals(info)) {
				break;
			}
			index++;
		}
		startActivity(new Intent(this, mClasses[index]));
	}

	@Override
	public Class getActivityClass() {
		// TODO Auto-generated method stub
		return HomeActivity.class;
	}
}
