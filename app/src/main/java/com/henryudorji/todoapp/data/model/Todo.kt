package com.henryudorji.todoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created on 4/28/2021.
//
@Entity(tableName = "todo")
data class Todo(
        var title: String? = null,
        var date: Long = 0L,
        @ColumnInfo(name = "alarm_time")
        var alarmTime: Long = 0L,
        @ColumnInfo(name = "remind_me")
        var remindMe: Boolean = false,
        var category: Category = Category.Work,
        var priority: Priority = Priority.Low,
        @ColumnInfo(name = "task_state")
        var taskState: TaskState = TaskState.Pending,
) {
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
}
