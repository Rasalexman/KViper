package com.mincor.kviper.utils

import com.mincor.kviper.BuildConfig

/**
 * Created by Alex on 05.11.2015.
 */

object L {

    val DEFAULT_TAG = "-----> LOG"
    val ERROR_EXEPTION_TAG = "-----> ERROR"

    // Здесь же поселим стектрейс
    fun printStackTrace(t: Throwable) {
        if (BuildConfig.DEBUG)
            t.printStackTrace()
    }

    /**
     * DEBUG OUTPUT WITH NO TAG PARAMETER
     * @param msg
     * @return
     */
    fun d(msg: String): Int {
        return if (!BuildConfig.DEBUG) 0 else android.util.Log.d(DEFAULT_TAG, msg)
    }

    /**
     * DEBUG OUTPUT WITH NO TAG PARAMETER
     * @param args
     * @return
     */
    fun d(vararg args: Any): Int {
        if (!BuildConfig.DEBUG)
            return 0
        var msg = ""
        for (o in args) {
            msg += o.toString() + " "
        }
        return android.util.Log.d(DEFAULT_TAG, msg)
    }

    fun e(msg: String): Int {
        return if (!BuildConfig.DEBUG) 0 else android.util.Log.e(ERROR_EXEPTION_TAG, msg)
    }

    /**
     * DEBUG OUTPUT WITH NO TAG PARAMETER
     * @param msg
     * @return
     */
    fun i(tag: String, msg: String): Int {
        return if (!BuildConfig.DEBUG) 0 else android.util.Log.i(tag, msg)
    }
}// Для простого примера однообразно и длинновато, принцип был бы ясен
// на примере одного метода, зато этот код можно просто копипастнуть
