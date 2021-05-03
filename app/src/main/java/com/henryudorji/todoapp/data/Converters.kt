package com.henryudorji.todoapp.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

//
// Created by hash on 4/28/2021.
//
class Converters {

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        return byteArray?.size?.let { BitmapFactory.decodeByteArray(byteArray, 0, it) }
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}