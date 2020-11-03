package com.codExalters.androidutilities

import android.content.Context
import android.content.SharedPreferences


/**
 * Created by codexalters on 9/9/17.
 */

object PreferenceUtils {
    var sharedPreferences: SharedPreferences? = null

    private fun openPrefs(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            "AppPreference",
            Context.MODE_PRIVATE
        )
    }

    fun setInt(context: Context, key: String, value: Int) {
        openPrefs(context)
        val editor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        editor!!.putInt(key, value)
        editor.apply()
    }

    fun getInt(
        context: Context, key: String,
        defaultValue: Int
    ): Int {
        openPrefs(context)
        return sharedPreferences!!.getInt(key, defaultValue)
    }

    fun setString(context: Context, key: String, value: String) {
        openPrefs(context)
        val editor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        editor!!.putString(key, value)
        editor.apply()
    }

    fun getString(
        context: Context, key: String,
        defaultValue: String
    ): String {
        openPrefs(context)
        return sharedPreferences!!.getString(key, defaultValue)!!
    }

    fun setLong(context: Context, key: String, value: Long) {
        openPrefs(context)
        val editor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        editor!!.putLong(key, value)
        editor.apply()
    }

    fun getLong(
        context: Context, key: String,
        defaultValue: Long
    ): Long {
        openPrefs(context)
        return sharedPreferences!!.getLong(key, defaultValue)
    }

    fun setFloat(context: Context, key: String, value: Float) {
        openPrefs(context)
        val editor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        editor!!.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(
        context: Context, key: String,
        defaultValue: Float
    ): Float {
        openPrefs(context)
        return sharedPreferences!!.getFloat(key, defaultValue)
    }

    fun setBoolean(context: Context, key: String, value: Boolean) {
        openPrefs(context)
        val editor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        editor!!.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(
        context: Context, key: String,
        defaultValue: Boolean
    ): Boolean {
        openPrefs(context)
        return sharedPreferences!!.getBoolean(key, defaultValue)
    }

    fun setClear(context: Context) {
        openPrefs(context)
        val preferenceEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()

        preferenceEditor!!.clear()
        preferenceEditor.apply()
    }

    fun remove(context: Context, key: String) {
        openPrefs(context)
        val preferenceEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        preferenceEditor!!.remove(key)
        preferenceEditor.apply()
    }


    /* fun setLatestAppVersion(context: Context, versionCode: String) {
         openPrefs(context)
         val preferenceEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
         preferenceEditor!!.putString(Const.APK_VERSION_CODE, versionCode)
         preferenceEditor.apply()
     }

     fun isAppUpdateRequire(context: Context): Boolean {
         openPrefs(context)
         val latestVersionCode = sharedPreferences!!.getString(Const.APK_VERSION_CODE, PackageInfoUtil.getAppVersionName(context))
         if (latestVersionCode != null)
             return PackageInfoUtil.getAppVersionName(context).toDouble() < latestVersionCode.toDouble()
         else {
             return false
         }

     }

     fun isAppForcefullyUpdateRequired(context: Context): Boolean {
         openPrefs(context)
         return sharedPreferences!!.getBoolean(Const.APK_FORCEFULLY_UPDATE_REQUIRED, false)
     }

     fun setForceUpdateRequired(context: Context, b: Boolean) {
         openPrefs(context)
         val preferenceEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
         preferenceEditor!!.putBoolean(Const.APK_FORCEFULLY_UPDATE_REQUIRED, b)
         preferenceEditor.apply()
     }
 */

}
