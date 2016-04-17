package tr.xip.dinnermenu.ext

import android.util.Log
import tr.xip.dinnermenu.BuildConfig

val TAG = "G-Dom Menu"

inline fun logv(lambda: () -> String) {
    Log.v(TAG, lambda())
}

inline fun logd(lambda: () -> String) {
    if (BuildConfig.DEBUG) Log.d(TAG, lambda())
}

inline fun logi(lambda: () -> String) {
    Log.i(TAG, lambda())
}

inline fun logw(lambda: () -> String) {
    Log.w(TAG, lambda())
}

inline fun loge(lambda: () -> String) {
    Log.e(TAG, lambda())
}

inline fun logwtf(lambda: () -> String) {
    Log.wtf(TAG, lambda())
}