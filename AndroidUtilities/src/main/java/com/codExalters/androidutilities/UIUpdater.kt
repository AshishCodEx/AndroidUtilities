package com.codExalters.androidutilities

import android.os.Handler
import android.os.Looper

/**
 * Created by Ashish on 16/7/19.
 */


class UIUpdater(uiUpdater: Runnable, interval: Long) {

    // Create a Handler that uses the Main Looper to run in
    private val mHandler = Handler(Looper.getMainLooper())

    private var mStatusChecker: Runnable? = null
    private var UPDATE_INTERVAL = 5000L

    init {
        UPDATE_INTERVAL = interval
        mStatusChecker = object : Runnable {
            override fun run() {
                // Run the passed runnable
                uiUpdater.run()
                // Re-run it after the update interval
                mHandler.postDelayed(this, UPDATE_INTERVAL)
            }
        }

    }

    /**
     * Starts the periodical update routine (mStatusChecker
     * adds the callback to the handler).
     */
    @Synchronized
    fun startUpdates() {
        mStatusChecker?.run()
    }

    /**
     * Stops the periodical update routine from running,
     * by removing the callback.
     */
    @Synchronized
    fun stopUpdates() {
        if (mStatusChecker != null)
            mHandler.removeCallbacks(mStatusChecker!!)
    }
}