package com.codExalters.androidutilities

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics


/**
 * Created by codexalters on 6/2/18.
 */
object DisplayUtils {

    //conversion method
    fun convertPixelsToDp(px: Int, context: Context): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (px / (metrics.densityDpi / 160f)).toInt()
    }

    fun convertDpToPixel(dp: Int, context: Context): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.toInt()
    }

    fun getDisplayWidth(context: Activity): Int {
        val displayMetrics = DisplayMetrics()
        context.windowManager.getDefaultDisplay().getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getDisplayHeight(context: Activity): Int {
        val displayMetrics = DisplayMetrics()
        context.windowManager.getDefaultDisplay().getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getDisplayWidthInDP(context: Activity): Int {
        return convertPixelsToDp(getDisplayWidth(context), context)
    }

    fun getDisplayHeightInDP(context: Activity): Int {
        return convertPixelsToDp(getDisplayHeight(context), context)
    }



}