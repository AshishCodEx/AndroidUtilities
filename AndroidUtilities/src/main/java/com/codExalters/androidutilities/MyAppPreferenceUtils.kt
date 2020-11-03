package com.codExalters.androidutilities

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by codexalters on 12/3/18.
 */
object MyAppPreferenceUtils {

    private const val AUTH_TOKEN = "AUTH_TOKEN"
    private const val PUSH_TOKEN = "PUSH_TOKEN"
    private const val IS_LOGGED_IN = "IS_LOGGED_IN"
    private const val USER_SESSION: String = "USER_SESSION"
    private const val APK_VERSION_CODE = "APK_VERSION_CODE"
    private const val APK_LANGUAGE_CODE = "APK_LANGUAGE_CODE"
    private const val APK_FORCEFULLY_UPDATE_REQUIRED = "APK_FORCEFULLY_UPDATE_REQUIRED"
    private const val IS_LOCATION_SERVICE_RUNNING = "IS_LOCATION_SERVICE_RUNNING"
    private const val LATITUDE = "LATITUDE"
    private const val LONGITUDE = "LONGITUDE"
    private var currentLanguage = "en"

    fun getToken(context: Context): String {
        return PreferenceUtils.getString(context, AUTH_TOKEN, "")
    }

    fun saveToken(context: Context, token: String) {
        PreferenceUtils.setString(context, AUTH_TOKEN, "Bearer $token")
    }

    fun getPushToken(context: Context): String {
        return PreferenceUtils.getString(context, PUSH_TOKEN, "")
    }

    fun savePushToken(context: Context, pushToken: String) {
        PreferenceUtils.setString(context, PUSH_TOKEN, pushToken)
    }

    fun isLoggedIn(context: Context): Boolean {
        return PreferenceUtils.getBoolean(context, IS_LOGGED_IN, false)
    }

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        PreferenceUtils.setBoolean(context, IS_LOGGED_IN, isLoggedIn)
    }

    fun saveUserSession(context: Context, userModel: String) {
        PreferenceUtils.setString(context, USER_SESSION, userModel)
    }

    fun getUserSession(context: Context): String {
        return PreferenceUtils.getString(context, USER_SESSION, "")
    }

    fun clearLoginSession(context: Context) {
        PreferenceUtils.setClear(context)
    }

    fun setLatestAppVersion(context: Context, versionCode: String) {
        PreferenceUtils.setString(context, APK_VERSION_CODE, versionCode)

    }

    fun isAppUpdateRequire(context: Context): Boolean {

        val latestVersionCode =
            PreferenceUtils.getString(
                context,
                APK_VERSION_CODE,
                PackageInfoUtil.getAppVersionName(context)
            )
        return PackageInfoUtil.getAppVersionName(context) != latestVersionCode

    }

    fun isAppForcefullyUpdateRequired(context: Context): Boolean {
        return PreferenceUtils.getBoolean(context, APK_FORCEFULLY_UPDATE_REQUIRED, false)
    }

    fun setForceUpdateRequired(context: Context, b: Boolean) {
        PreferenceUtils.setBoolean(context, APK_FORCEFULLY_UPDATE_REQUIRED, b)
    }

    fun printHashKey(pContext: Context) {
        try {
            val info = pContext.getPackageManager().getPackageInfo(
                pContext.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.i(
                    "MyAppPreferenceUtils",
                    "printHashKey : ${Base64.encodeToString(md.digest(), Base64.DEFAULT)}"
                )
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getServiceRunningStatus(context: Context): Boolean {
        return PreferenceUtils.getBoolean(context, IS_LOCATION_SERVICE_RUNNING, false)
    }

    fun setServiceRunningStatus(context: Context, b: Boolean) {
        PreferenceUtils.setBoolean(context, IS_LOCATION_SERVICE_RUNNING, b)
    }

    fun getUserCurrentLocation(context: Context): Location {

        val location = Location("")
        location.latitude = PreferenceUtils.getString(context, LATITUDE, "0.00").toDouble()
        location.longitude = PreferenceUtils.getString(context, LONGITUDE, "0.00").toDouble()
        return location
    }

    fun setUserCurrentLocation(requireContext: Context, latitude: Double, longitude: Double) {
        PreferenceUtils.setString(
            requireContext,
            LATITUDE,
            latitude.toString()
        )
        PreferenceUtils.setString(
            requireContext,
            LONGITUDE,
            longitude.toString()
        )
    }

    fun setAppLanguage(context: Context, languageCode: String) {
        PreferenceUtils.setString(context, APK_LANGUAGE_CODE, languageCode)
    }

    fun getAppLanguage(context: Context): String {
        return PreferenceUtils.getString(
            context,
            APK_LANGUAGE_CODE,
            "en"
        )
    }

}