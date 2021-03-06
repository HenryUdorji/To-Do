package com.henryudorji.todoapp.base

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

//
// Created by hash on 4/23/2021.
//
class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    companion object {
        lateinit var sharedPref: SharedPreferences
    }
}