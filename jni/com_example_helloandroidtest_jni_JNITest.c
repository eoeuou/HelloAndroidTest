#include<jni.h>
#include<string.h>
#include"com_example_helloandroidtest_jni_JNITest.h"

JNIEXPORT jstring JNICALL Java_com_example_helloandroidtest_jni_JNITest_printJNI(
		JNIEnv* env, jobject thiz, jstring js) {
	return (*env)->NewStringUTF(env, (char*) "Hello,JNITest");
}
