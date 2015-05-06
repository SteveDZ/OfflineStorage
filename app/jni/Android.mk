LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# Here we give our module name and source file(s)
LOCAL_SRC_FILES := local-module.c
LOCAL_MODULE    := local-module

include $(BUILD_SHARED_LIBRARY)