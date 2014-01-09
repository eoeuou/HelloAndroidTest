package com.example.helloandroidtest;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.helloandroidtest.jni.JNITest;

public class MainActivity extends BaseActivity implements OnClickListener {
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams param;
	private FloatView mLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(TELEPHONY_SERVICE);

		TextView textView = (TextView) findViewById(R.id.info);
		textView.setText("deviceid=" + tm.getDeviceId() + "\nProduct Model: "
				+ android.os.Build.MODEL + "\nSDK="
				+ android.os.Build.VERSION.SDK + "\nRELEASE="
				+ android.os.Build.VERSION.RELEASE + "\nCODENAME="
				+ android.os.Build.VERSION.CODENAME + "\nINCREMENTAL="
				+ android.os.Build.VERSION.INCREMENTAL);

		JNITest test = new JNITest();
		textView.setText(test.getJni());

		mLayout = new FloatView(getApplicationContext());
		mLayout.setBackgroundResource(R.drawable.ic_launcher);

		Button button = (Button) this.findViewById(R.id.finish);
		button.setOnClickListener(this);

		mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		// 设置LayoutParams(全局变量）相关参数
		param = ((MyApplication) getApplication()).getMywmParams();
		param.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 系统提示类型,重要
		param.format = 1;
		param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
		param.flags = param.flags
				| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		param.flags = param.flags
				| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制
		param.alpha = 1.0f;
		param.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
		// 以屏幕左上角为原点，设置x、y初始值
		param.x = 0;
		param.y = 0;

		param.width = 140;
		param.height = 140;

		// 显示myFloatView图像
		mWindowManager.addView(mLayout, param);

		int i = 0x0001 | 0x0002 | 0x0010;
		Log.i("mainlog", "i=" + i + " 0x0001=" + 0x0001 + " 0x0002=" + 0x0002
				+ " 0x0010=" + 0x0010);

		EditText editText = (EditText) findViewById(R.id.edit_text);
		editText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, String.valueOf(actionId),
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		editText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, String.valueOf(keyCode),
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	public class FloatView extends View {
		private float mTouchStartX;
		private float mTouchStartY;
		private float x;
		private float y;

		private WindowManager wm = (WindowManager) getContext()
				.getApplicationContext().getSystemService(
						Context.WINDOW_SERVICE);
		private WindowManager.LayoutParams wmParams = ((MyApplication) getContext()
				.getApplicationContext()).getMywmParams();

		public FloatView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {

			// 获取相对屏幕的坐标，即以屏幕左上角为原点
			x = event.getRawX();
			y = event.getRawY() - 25; // 25是系统状态栏的高度
			Log.i("currP", "currX" + x + "====currY" + y);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 获取相对View的坐标，即以此View左上角为原点
				mTouchStartX = event.getX();
				mTouchStartY = event.getY();

				Log.i("startP", "startX" + mTouchStartX + "====startY"
						+ mTouchStartY);

				break;
			case MotionEvent.ACTION_MOVE:
				updateViewPosition();
				break;

			case MotionEvent.ACTION_UP:
				updateViewPosition();
				mTouchStartX = mTouchStartY = 0;
				mWindowManager.removeView(mLayout);
				break;
			}
			return true;
		}

		private void updateViewPosition() {

			// 更新浮动窗口位置参数
			wmParams.x = (int) (x - mTouchStartX);
			wmParams.y = (int) (y - mTouchStartY);
			wm.updateViewLayout(this, wmParams);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View paramView) {
		// TODO Auto-generated method stub

		this.finish();

	}

	@Override
	public Class getActivityClass() {
		// TODO Auto-generated method stub
		return MainActivity.class;
	}

}
