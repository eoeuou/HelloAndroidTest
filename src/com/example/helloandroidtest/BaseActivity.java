package com.example.helloandroidtest;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

	private static BaseActivity m_instance;

	public abstract Class getActivityClass();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		m_instance = this;
	}

	public static BaseActivity getInstance() {
		return m_instance;
	}
}
