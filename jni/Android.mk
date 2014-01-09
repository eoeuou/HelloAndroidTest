LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE :=libHelloAndroidTest

LOCAL_SRC_FILES:= com_example_helloandroidtest_jni_JNITest.c

include $(BUILD_SHARED_LIBRARY)
