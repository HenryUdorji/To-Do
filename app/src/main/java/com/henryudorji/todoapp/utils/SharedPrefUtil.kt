package com.henryudorji.todoapp.utils

import com.henryudorji.todoapp.BaseApplication

//
// Created by hash on 4/23/2021.
//
object SharedPrefUtil {

    fun putStringInPref(key: String, value: String) {
        BaseApplication.sharedPref.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun putBooleanInPref(key: String, value: Boolean) {
        BaseApplication.sharedPref.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun getBooleanFromPref(key: String): Boolean {
        return BaseApplication.sharedPref.getBoolean(key, false)
    }

    fun getStringFromPref(key: String): String {
        return BaseApplication.sharedPref.getString(key, "")!!
    }
}