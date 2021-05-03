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
        var title: String,
        var description: String,
        var date: Long,
        @ColumnInfo(name = "alarm_time")
        var alarmTime: Long? = null,
        var category: Category,
        var priority: Priority,
        var isTaskCompleted: Boolean,
        @ColumnInfo(name = "todo_audio_path")
        var todoAudioPath: String? = null,
        @ColumnInfo(name = "todo_image_path")
        var todoImagePath: String? = null
) {
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
}
