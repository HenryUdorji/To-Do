package com.henryudorji.todoapp.data.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created on 4/28/2021.
//
@Entity(tableName = "todo")
data class Todo(
        val title: String,
        val description: String,
        val date: Long,
        @ColumnInfo(name = "alarm_time")
        val alarmTime: Long? = null,
        val category: String,
        val priority: Int,
        @ColumnInfo(name = "todo_audio_path")
        val todoAudioPath: String? = null,
        @ColumnInfo(name = "todo_image")
        val todoImage: Bitmap? = null,
        val location: String? = null
) {
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
}
