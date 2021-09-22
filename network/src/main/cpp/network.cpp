#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
Java_com_shiraj_network_NetworkModule_baseUrlFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string baseUrl = "https://mock-live-lessons.herokuapp.com/api/v1/";
    return env->NewStringUTF(baseUrl.c_str());
}
