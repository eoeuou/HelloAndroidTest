#!/bin/bash
#./build_native.sh

PATH=$PATH:/opt/software/apache-ant-1.8.2/bin:/opt/dev/android-sdk-linux_x86/platform-tools/

ant clean
ant uninstall

ant debug
ant installd

#ant release
#ant installr

adb shell am start -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -n com.youhao.HelloWorld/com.youhao.NativeActivity

#adb logcat -s *:I
#adb logcat | grep 'D/cocos2d-x'

