package com.henryudorji.todoapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

//
// Created by hash on 5/1/2021.
//
class ImageStorageManager() {

    companion object {
        private val TAG = "ImageStorageManager"

        fun saveImageToInternalStorage(context: Context, bitmap: Bitmap, fileName: String): String? {
            try {
                //@todo add a compression library
                context.openFileOutput(fileName, Context.MODE_PRIVATE).use { fileOutputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream)
                }
                return context.filesDir.absolutePath
            }catch (e: Exception) {
                Log.e(TAG, "saveFileToInternalStorage: ${e.stackTrace}")
            }
            return null
        }

        fun getImageFromInternalStorage(context: Context, imageName: String): Bitmap? {
            try {
                val directory = context.filesDir
                val file = File(directory, imageName)
                return BitmapFactory.decodeStream(FileInputStream(file))
            }catch (e: Exception) {
                Log.e(TAG, "getImageFromInternalStorage: ${e.stackTrace}")
            }
            return null

        }

        fun deleteImageFromInternalStorage(context: Context, imageName: String): Boolean {
            try {
                val dir = context.filesDir
                val file = File(dir, imageName)
                return file.delete()
            }catch (e: Exception) {
                Log.e(TAG, "deleteImageFromInternalStorage: ${e.stackTrace}")
            }
            return false
        }
    }
}