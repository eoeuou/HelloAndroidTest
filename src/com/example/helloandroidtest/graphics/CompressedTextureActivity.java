package com.example.helloandroidtest.graphics;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

public class CompressedTextureActivity extends Activity {

	private GLSurfaceView mGLView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGLView = new ClearGLSurfaceView(this);
		setContentView(mGLView);
		mGLView.requestFocus();
		mGLView.setFocusableInTouchMode(true);
	}

}

class ClearGLSurfaceView extends GLSurfaceView {
	ClearRenderer mRenderer;

	public ClearGLSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mRenderer = new ClearRenderer();
		setRenderer(mRenderer);
	}

	public boolean onTouchEvent(final MotionEvent event) {
		queueEvent(new Runnable() {
			public void run() {
				mRenderer.setColor(event.getX() / getWidth(), event.getY()
						/ getHeight(), 1.0f);
			}
		});

		return true;
	}

}

class ClearRenderer implements GLSurfaceView.Renderer {

	float rotateTri, rotateQuad;
	int one = 0x11000;

	// // 三角形的一个顶点
	// private IntBuffer triggerBuffer = IntBuffer.wrap(new int[] { 0, one, 0,
	// // 上顶点
	// -one, -one, 0, // 左顶点
	// one, -one, 0 // 右下点
	// });
	// private IntBuffer colorBuffer = IntBuffer.wrap(new int[] { one, 0, 0,
	// one,
	// 0, one, 0, one, 0, 0, one, one });
	int[] colorArray = { one, 0, 0, one, 0, one, 0, one, 0, 0, one, one };
	int[] triggerArray = { 0, one, 0, -one, -one, 0, one, -one, 0 };

	public Buffer bufferUtil(int[] arr) {
		IntBuffer mBuffer;

		// 先初始化buffer,数组的长度*4,因为一个int占4个字节
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// 数组排列用nativeOrder
		qbb.order(ByteOrder.nativeOrder());

		mBuffer = qbb.asIntBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);

		return mBuffer;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// 启用阴影平滑
		gl.glShadeModel(GL10.GL_SMOOTH);

		// 黑色背景
		gl.glClearColor(0, 0, 0, 0);

		// 设置深度缓存
		gl.glClearDepthf(1.0f);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 所作深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);

		// 告诉系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		// TODO Auto-generated method stub
		float ratio = (float) w / h;
		// 设置OpenGL场景的大小
		gl.glViewport(0, 0, w, h);
		// 设置投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 重置投影矩阵
		gl.glLoadIdentity();
		// 设置视口的大小 前四个参数去顶窗口的大小，分别是左，右，下，上，后两个参数分别是在场景中所能绘制深度的起点和终点
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		// 选择模型观察矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 重置模型观察矩阵
		gl.glLoadIdentity();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClearColor(mRed, mGreen, mBlue, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		// 左移 1.5 单位，并移入屏幕 6.0
		gl.glTranslatef(-1.5f, 0.0f, -6.0f); // 设置旋转
		gl.glRotatef(rotateTri, 0.0f, 1.0f, 0.0f);

		// 设置定点数组
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 设置颜色数组
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glColorPointer(4, GL10.GL_FIXED, 0, bufferUtil(colorArray));
		// 设置三角形顶点
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, bufferUtil(triggerArray));
		// 绘制三角形
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

		// 绘制三角形结束
		gl.glFinish();

		// 取消顶点数组
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

		// 改变旋转的角度
		rotateTri += 0.5f;
		rotateQuad -= 0.5f;

	}

	public void setColor(float r, float g, float b) {
		mRed = r;
		mGreen = g;
		mBlue = b;
	}

	private float mRed;
	private float mGreen;
	private float mBlue;
}