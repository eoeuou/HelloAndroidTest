package com.example.helloandroidtest.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.widget.Button;

import com.example.helloandroidtest.R;

public class AnimationActivity extends Activity implements OnClickListener {
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		button = (Button) this.findViewById(R.id.finish);
		button.setOnClickListener(this);
		button.setPivotX(0.5f);
		button.setPivotY(0.5f);

		int red = (int) (Math.random() * 255);
		int green = (int) (Math.random() * 255);
		int blue = (int) (Math.random() * 255);
		int color = 0xff000000 | red << 16 | green << 8 | blue;
		Log.i("info", "red" + red + "green" + green + "blue" + blue);
		Log.i("info", "color" + color);

	}

	public void animationTest1() {

		ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
		animation.setDuration(1000);
		animation.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub

				button.setScaleX(1);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});

		animation.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Log.i("update",
						((Float) animation.getAnimatedValue()).toString());
				float value = (Float) animation.getAnimatedValue();
				if (value > 0 && value < 1) {
					button.setScaleX(value);
				}
			}
		});
		animation.setInterpolator(new CycleInterpolator(3));
		animation.start();

	}

	public void animationTest2() {
		ObjectAnimator oa = ObjectAnimator.ofFloat(button, "alpha", 0f, 1f);
		oa.setDuration(1000);
		oa.start();
	}

	public void animationTest3() {
		Keyframe kf0 = Keyframe.ofInt(0, 400);
		Keyframe kf1 = Keyframe.ofInt(0.25f, 200);
		Keyframe kf2 = Keyframe.ofInt(0.5f, 400);
		Keyframe kf4 = Keyframe.ofInt(0.75f, 100);
		Keyframe kf3 = Keyframe.ofInt(1f, 500);
		PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(
				"height", kf0, kf1, kf2, kf4, kf3);
		ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(
				button, pvhRotation);
		rotationAnim.setDuration(2000);
		rotationAnim.setInterpolator(new AccelerateInterpolator());
		rotationAnim.start();
	}

	@Override
	public void onClick(View paramView) {
		animationTest3();

	}
}
