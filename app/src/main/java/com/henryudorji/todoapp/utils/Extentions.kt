package com.henryudorji.todoapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.base.BaseApplication

//
// Created by  on 4/28/2021.
//


fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}



/**
 * functions to write and get from SharedPreferences
 */
fun Context.putStringInPref(key: String, value: String) {
    BaseApplication.sharedPref.edit().apply {
        putString(key, value)
        apply()
    }
}

fun Context.putBooleanInPref(key: String, value: Boolean) {
    BaseApplication.sharedPref.edit().apply {
        putBoolean(key, value)
        apply()
    }
}

fun Context.getBooleanFromPref(key: String): Boolean {
    return BaseApplication.sharedPref.getBoolean(key, false)
}

fun Context.getStringFromPref(key: String): String {
    return BaseApplication.sharedPref.getString(key, "")!!
}

//Load image with Glide
fun ImageView.loadImage(bitmap: Bitmap?) {
    Glide.with(this)
            .load(bitmap)
            .placeholder(ResourcesCompat.getDrawable(resources, R.drawable.profile, null))
            .into(this)
}
