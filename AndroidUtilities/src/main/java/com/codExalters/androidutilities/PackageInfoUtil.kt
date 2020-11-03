package com.codExalters.androidutilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.util.Log


object PackageInfoUtil {

    fun getPackageInfo(context: Context): PackageInfo? {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: NameNotFoundException) {
            Log.e("PreferenceUtils", " : ${e.printStackTrace()} ")
            null
        }
    }

    fun getPackageName(context: Context): String {
        return try {
            val pInfo = getPackageInfo(context)
            pInfo!!.packageName
        } catch (e: NameNotFoundException) {
            Log.e("PreferenceUtils", " : ${e.printStackTrace()} ")
            ""
        }
    }

    fun getAppVersionCode(context: Context): Int {

        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionCode
        } catch (e: NameNotFoundException) {
            Log.e("PreferenceUtils", " : ${e.printStackTrace()} ")
            -1
        }

    }

    fun getAppVersionName(context: Context): String {

        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionName
        } catch (e: NameNotFoundException) {
            Log.e("PreferenceUtils", " : ${e.printStackTrace()} ")
            ""
        }

    }

    fun isAppLatest(
        currentAppVersionName: String,
        latestVersionFromServer: String
    ): Boolean {


        if (latestVersionFromServer.isEmpty() || currentAppVersionName.isEmpty()) {
            return true
        }

        Log.i(
            "PackageInfoUtil",
            "isAppVersionIsLatest : C: $currentAppVersionName &  L :$latestVersionFromServer"
        )

        var currentVersionSegments = currentAppVersionName.trim().split(".")

        if (currentVersionSegments.isEmpty()) {
            currentVersionSegments = currentVersionSegments.plus("0")
            currentVersionSegments = currentVersionSegments.plus("0")
        }

        if (currentVersionSegments.size == 1) {
            currentVersionSegments = currentVersionSegments.plus("0")
        }

        var latestVersionSegments = latestVersionFromServer.trim().split(".")
        if (latestVersionSegments.isEmpty()) {
            latestVersionSegments = latestVersionSegments.plus("0")
            latestVersionSegments = latestVersionSegments.plus("0")
        }

        if (latestVersionSegments.size == 1) {
            latestVersionSegments = latestVersionSegments.plus("0")
        }

        Log.d(
            "PackageInfoUtil",
            "isAppVersionIsLatest : ${currentVersionSegments.toList()} , ${latestVersionSegments.toList()} "
        )

        return if (currentVersionSegments[0] > latestVersionSegments[0]) {
            true
        } else if (currentVersionSegments[0] < latestVersionSegments[0]) {
            false
        } else if (currentVersionSegments[0] == latestVersionSegments[0]
            && currentVersionSegments[1].toInt() < latestVersionSegments[1].toInt()
        ) {
            false
        } else if (currentVersionSegments[0] == latestVersionSegments[0]
            && currentVersionSegments[1].toInt() >= latestVersionSegments[1].toInt()
        ) {
            true
        } else {
            true
        }














        return false

    }


    fun openGooglePlay(activity: Activity?) {
        if (activity == null) {
            return
        }
        val appPackageName = getPackageName(activity)
        try {

            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse("market://details?id=$appPackageName")
            intent.`package` = "com.android.vending"
            activity.startActivity(intent)
        } catch (e: android.content.ActivityNotFoundException) {

            try {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (e: android.content.ActivityNotFoundException) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }

    }
}