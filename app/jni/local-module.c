#include <string.h>
#include <jni.h>

jstring Java_be_ordina_offlinestorage_activity_EncryptedInternalStorageActivity_getPasswordFromNativeInterface(JNIEnv* env, jobject x){
    return (*env)->NewStringUTF(env, "password123");
}

jstring Java_be_ordina_offlinestorage_activity_SqlCipherActivityWithNativeInterface_getPasswordFromNativeInterface(JNIEnv* env, jobject x){
    return (*env)->NewStringUTF(env, "password123");
}