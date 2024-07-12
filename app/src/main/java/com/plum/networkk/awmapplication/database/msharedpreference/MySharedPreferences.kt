package com.plum.networkk.awmapplication.database.msharedpreference

import android.content.Context

import android.content.SharedPreferences
import com.plum.networkk.awmapplication.constants.URLs


object MySharedPreferences {

    var SIGNED_IN_KEY = "mSignin"
    var USERNAME_KEY = "username"
    var PASSWORD_KEY = "password"
    var SIGNED_IN_AS_KEY = "signedinAs"
    var TOKEN_KEY = "mtoken_signingin"
    var COMPANY_ID = "company_id"

    var LockMode = "mLockMode"

    var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
//        sharedPreferences =
//            context.getSharedPreferences(
//                context.resources.getString(R.string.app_name),
//                Context.MODE_PRIVATE
//            )
        sharedPreferences = context.getSharedPreferences(URLs.MY_PREFERENCES, Context.MODE_PRIVATE)
    }


    fun editBooleanPreferences(key: String, flag: Boolean, mContext: Context) {
        init(mContext)
        sharedPreferences!!.edit().putBoolean(key, flag).apply()
    }

    fun getBooleanPreferences(key: String, mContext: Context): Boolean {
        init(mContext)
        return sharedPreferences!!.getBoolean(key, false)
    }

    fun editStringPreferences(key: String, value: String, mContext: Context) {
        init(mContext)
        sharedPreferences!!.edit().putString(key, value).apply()
    }

    fun getStringPreferences(key: String, mContext: Context, defaultValue: String): String? {
        init(mContext)
        return sharedPreferences!!.getString(key, defaultValue)
    }


    fun editNumPreferences(key: String, value: Long, mContext: Context) {
        init(mContext)
        sharedPreferences!!.edit().putLong(key, value).apply()
    }

    fun getNumPreferences(key: String, mContext: Context, defaultValue: Long): Long? {
        init(mContext)
        return sharedPreferences!!.getLong(key, defaultValue)
    }

}