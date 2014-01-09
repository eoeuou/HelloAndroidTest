package com.example.helloandroidtest.jni;

public class JNITest {

	private native String printJNI(String inputStr);

	static {
		System.loadLibrary("HelloAndroidTest");
	}
	
	public String getJni(){
		return printJNI("123");
	}
}
