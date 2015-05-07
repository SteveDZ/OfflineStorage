#include <string.h>
#include <jni.h>

jstring Java_be_ordina_offlinestorage_activity_OfflineStorageActivity_getPasswordFromNativeInterface(JNIEnv* env, jobject x) {
    return (*env)->NewStringUTF(env, "password123");
}

jstring Java_be_ordina_offlinestorage_fragment_EncryptedInternalStorageFragment_getPasswordFromNativeInterface(JNIEnv* env, jobject x){
    return (*env)->NewStringUTF(env, "password123");
}