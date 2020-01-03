#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_blongho_currencyconverter_Converter_stringFromJNI(
    JNIEnv *env,
    jobject /* this */) {
  std::string key = "24db62a8f20a3398fc42";
  return env->NewStringUTF(key.c_str());
}
