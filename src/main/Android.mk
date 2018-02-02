LOCAL_PATH:= $(call my-dir)

#include $(call all-makefiles-under,$(LOCAL_PATH)/watchfacemanager)

# CLEAR_VARS 变量由Build System提供。并指向一个指定的GNU Makefile，负责清理多余的LOCAL_xxx.
# 这个清理动作是必须的，因为所有的编译控制文件由同一个GNU Make解析和执行，其变量是全局的
# 所以清理后才能避免相互影响 比如LOCAL_MODULE, LOCAL_SRC_FILES, LOCAL_STATIC_LIBRARIES
include $(CLEAR_VARS)

#######################################################################
LOCAL_MODULE_TAGS := optional

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := constraint-layout-solver:libs/constraint-layout-solver-1.0.2.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += support-annotations:libs/support-annotations-25.3.1.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += mstarc_os_api:libs/mstarc_os_api.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += mirror-1.6.1:libs/mirror-1.6.1.jar
# http/database/gson/rxjava
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += converter-gson:libs/converter-gson-2.3.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += converter-scalars:libs/converter-scalars-2.3.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += greendao:libs/greendao-3.2.2.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += greendao-api:libs/greendao-api-3.2.2.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += gson:libs/gson-2.8.2.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += okhttp:libs/okhttp-3.9.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += okio:libs/okio-1.13.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += retrofit:libs/retrofit-2.3.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += rxjava:libs/rxjava-2.1.5.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += retrofit2-rxjava2-adapter:libs/retrofit2-rxjava2-adapter-1.0.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += adapter-rxjava2:libs/adapter-rxjava2-2.3.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += android_service:libs/android_service.jar

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += constraint-layout:libs/constraint-layout-1.0.2.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += support-v4:libs/support-v4-25.3.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += support-compat:libs/support-compat-25.3.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += support-core-ui:libs/support-core-ui-25.3.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += support-core-utils:libs/support-core-utils-25.3.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += support-fragment:libs/support-fragment-25.3.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += appcompat-v7:libs/appcompat-v7-22.2.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += design:libs/design-22.2.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += recyclerview-v7:libs/recyclerview-v7-25.3.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += recyclerview-swipe:libs/recyclerview-swipe-1.1.4.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += recyclerview-carousel:libs/recyclerview-carousel-1.2.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += yviewpager:libs/yviewpager-release.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += superadapter:libs/superadapter-3.6.8.aar
# rxAndroid/mvp
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += mvp-framework:libs/mvp-framework.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += rxandroid:libs/rxandroid-2.0.1.aar

include $(BUILD_MULTI_PREBUILT)
#######################################################################

include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional

#LOCAL_REQUIRED_MODULES := libandroid_servers framework-api
LOCAL_SHARED_LIBRARIES := lib.suhen

LOCAL_STATIC_JAVA_AAR_LIBRARIES := constraint-layout
LOCAL_STATIC_JAVA_AAR_LIBRARIES += support-v4
LOCAL_STATIC_JAVA_AAR_LIBRARIES += support-compat
LOCAL_STATIC_JAVA_AAR_LIBRARIES += support-core-ui
LOCAL_STATIC_JAVA_AAR_LIBRARIES += support-core-utils
LOCAL_STATIC_JAVA_AAR_LIBRARIES += support-fragment
LOCAL_STATIC_JAVA_AAR_LIBRARIES += appcompat-v7
LOCAL_STATIC_JAVA_AAR_LIBRARIES += design
LOCAL_STATIC_JAVA_AAR_LIBRARIES += recyclerview-v7
LOCAL_STATIC_JAVA_AAR_LIBRARIES += recyclerview-swipe
LOCAL_STATIC_JAVA_AAR_LIBRARIES += recyclerview-carousel
LOCAL_STATIC_JAVA_AAR_LIBRARIES += yviewpager
LOCAL_STATIC_JAVA_AAR_LIBRARIES += superadapter
LOCAL_STATIC_JAVA_AAR_LIBRARIES += mvp-framework
LOCAL_STATIC_JAVA_AAR_LIBRARIES += rxandroid

LOCAL_STATIC_JAVA_LIBRARIES := constraint-layout-solver
LOCAL_STATIC_JAVA_LIBRARIES += support-annotations
LOCAL_STATIC_JAVA_LIBRARIES += mstarc_os_api
LOCAL_STATIC_JAVA_LIBRARIES += mirror-1.6.1
LOCAL_STATIC_JAVA_LIBRARIES += converter-gson
LOCAL_STATIC_JAVA_LIBRARIES += converter-scalars
LOCAL_STATIC_JAVA_LIBRARIES += greendao
LOCAL_STATIC_JAVA_LIBRARIES += greendao-api
LOCAL_STATIC_JAVA_LIBRARIES += gson
LOCAL_STATIC_JAVA_LIBRARIES += okhttp
LOCAL_STATIC_JAVA_LIBRARIES += okio
LOCAL_STATIC_JAVA_LIBRARIES += retrofit
LOCAL_STATIC_JAVA_LIBRARIES += rxjava
LOCAL_STATIC_JAVA_LIBRARIES += retrofit2-rxjava2-adapter
LOCAL_STATIC_JAVA_LIBRARIES += adapter-rxjava2
LOCAL_STATIC_JAVA_LIBRARIES += android_service


LOCAL_SRC_FILES := $(call all-java-files-under, java)
LOCAL_SRC_FILES += $(call all-Iaidl-files-under, aidl)
LOCAL_RESOURCE_DIR := $(addprefix $(LOCAL_PATH)/, res)
#LOCAL_RESOURCE_DIR := $(addprefix $(LOCAL_PATH)/, $(MSTARC_INTERNAL_RESOURCE_VERSION))

LOCAL_DX_FLAGS := --multi-dex

LOCAL_PACKAGE_NAME := ZLauncher
LOCAL_CERTIFICATE := platform

LOCAL_PROGUARD_FLAG_FILES := proguard.flags

#######################################################################
LOCAL_AAPT_FLAGS := \
                  --auto-add-overlay \
                  --extra-packages android.support.constraint \
                  --extra-packages android.support.v4 \
                  --extra-packages android.support.compat \
                  --extra-packages android.support.coreui \
                  --extra-packages android.support.coreutils \
                  --extra-packages android.support.fragment \
                  --extra-packages android.support.v7.appcompat \
                  --extra-packages android.support.design \
                  --extra-packages android.support.v7.recyclerview \
                  --extra-packages com.yanzhenjie.recyclerview.swipe \
                  --extra-packages com.azoft.carousellayoutmanager \
                  --extra-packages cn.youngkaaa.yviewpager \
                  --extra-packages org.byteam.superadapter \
                  --extra-packages com.tz.architect.mvpframework \
                  --extra-packages io.reactivex.android \

#######################################################################
LOCAL_DEX_PREOPT := false
include $(BUILD_PACKAGE)

