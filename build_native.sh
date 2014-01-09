# set params
#NDK_ROOT=/opt/dev/android-ndk-r9
PROJECT_NAME=`pwd | sed -e 's/\/opt\/dev\/workspace\///g' | sed -e 's/\/android//g'`
echo "project=$PROJECT_NAME"
NDK_ROOT=/opt/dev/android-ndk-r8d
TEMPLATE_ROOT=../../HelloWorld
GAME_ROOT=..
GAME_ANDROID_ROOT=$GAME_ROOT/android
RESOURCE_ROOT=$GAME_ROOT/Resources

export NDK_MODULE_PATH=/opt/dev/workspace/kaka2dx

# make sure assets is exist
if [ -d $GAME_ANDROID_ROOT/assets ]; then
    rm -rf $GAME_ANDROID_ROOT/assets
fi

mkdir $GAME_ANDROID_ROOT/assets
rm -rf libs/*.jar

# copy resources
for file in $RESOURCE_ROOT/*
do
    if [ -d $file ]; then
        cp -rf $file $GAME_ANDROID_ROOT/assets
    fi

    if [ -f $file ]; then
        cp $file $GAME_ANDROID_ROOT/assets
    fi
done

#cp $TEMPLATE_ROOT/android/jni/*.mk $GAME_ANDROID_ROOT/jni
#cp $TEMPLATE_ROOT/android/jni/native/*.mk $GAME_ANDROID_ROOT/jni/native
cp $TEMPLATE_ROOT/android/jni/native/*.h $GAME_ANDROID_ROOT/jni/native
cp $TEMPLATE_ROOT/android/jni/native/*.cpp $GAME_ANDROID_ROOT/jni/native
cp $TEMPLATE_ROOT/android/src/com/youhao/*.java $GAME_ANDROID_ROOT/src/com/youhao
mkdir $GAME_ANDROID_ROOT/src/com/youhao/$PROJECT_NAME
mkdir $GAME_ANDROID_ROOT/src/com/youhao/$PROJECT_NAME/wxapi
cp $TEMPLATE_ROOT/android/src/com/youhao/HelloWorld/wxapi/*.java $GAME_ANDROID_ROOT/src/com/youhao/$PROJECT_NAME/wxapi

# build
$NDK_ROOT/ndk-build NDK_DEBUG=1 -C $GAME_ANDROID_ROOT $*
#$NDK_ROOT/ndk-build -C $GAME_ANDROID_ROOT $*
#cp $NDK_MODULE_PATH/third_party/libraries/android/armeabi-v7a/libnative_camera*.so $GAME_ANDROID_ROOT/libs/armeabi-v7a
#cp $NDK_MODULE_PATH/third_party/libraries/android/x86/libnative_camera*.so $GAME_ANDROID_ROOT/libs/x86
cp $TEMPLATE_ROOT/android/libs/*.jar $GAME_ANDROID_ROOT/libs

sed -i "s/HelloWorld/$PROJECT_NAME/g" $GAME_ANDROID_ROOT/src/com/youhao/KKShareDialog.java
sed -i "s/HelloWorld/$PROJECT_NAME/g" $GAME_ANDROID_ROOT/src/com/youhao/$PROJECT_NAME/wxapi/Util.java
sed -i "s/HelloWorld/$PROJECT_NAME/g" $GAME_ANDROID_ROOT/src/com/youhao/$PROJECT_NAME/wxapi/WXEntryActivity.java

