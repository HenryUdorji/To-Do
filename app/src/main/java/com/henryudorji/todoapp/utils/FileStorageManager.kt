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
class FileStorageManager() {

    companion object {
        private val TAG = "FileStorageManager"

        fun saveImageToInternalStorage(context: Context, bitmap: Bitmap, fileName: String): String? {
            try {
                context.openFileOutput(fileName, Context.MODE_PRIVATE).use { fileOutputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
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

    /*private val TAG = "FileStorageManager"


    fun saveFile(bitmap: Bitmap) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(createNewFile())
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        }catch (e: Exception) {
            Log.d(TAG, "saveFile: ${e.stackTrace}")
        }finally {
            try {
                fileOutputStream?.close()
            }catch (io: IOException) {
                Log.d(TAG, "saveFile: ${io.stackTrace}")
            }
        }
    }

    private fun createNewFile(): File {
        val directory: File? = if (external) {
            getAlbumStorage(directoryName)
        }else {
            context.getDir(directoryName, Context.MODE_PRIVATE)
        }
        if (!directory?.exists()!! and !directory.mkdirs()) {
            Log.e(TAG, "createNewFile: Error Creating directory $directory", )
        }
        return File(directory, fileName)
    }

    private fun getAlbumStorage(directoryName: String): File? {
        return null
    }

    fun loadBitmap(): Bitmap? {
        var inputStream: FileInputStream? = null
        try {
            inputStream = FileInputStream(createNewFile())
            return BitmapFactory.decodeStream(inputStream)
        }catch (e: Exception) {
            Log.e(TAG, "loadBitmap: ${e.stackTrace}", )
        }finally {
            try {
                inputStream?.close()
            }catch (io: IOException) {
                Log.d(TAG, "saveFile: ${io.stackTrace}")
            }
        }
        return null
    }*/


}