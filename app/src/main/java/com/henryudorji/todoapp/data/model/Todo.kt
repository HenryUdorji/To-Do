package com.henryudorji.todoapp.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created on 4/28/2021.
//
@Entity(tableName = "todo")
data class Todo(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var title: String?,
        var date: Long = 0L,
        @ColumnInfo(name = "alarm_time")
        var alarmTime: Long = 0L,
        @ColumnInfo(name = "remind_me")
        var remindMe: Boolean = false,
        var category: Category = Category.Work,
        var priority: Priority = Priority.Low,
        @ColumnInfo(name = "task_state")
        var taskState: TaskState = TaskState.Pending,
): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readLong(),
                parcel.readLong(),
                parcel.readByte() != 0.toByte(),
                TODO("category"),
                TODO("priority"),
                TODO("taskState")) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(title)
                parcel.writeLong(date)
                parcel.writeLong(alarmTime)
                parcel.writeByte(if (remindMe) 1 else 0)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Todo> {
                override fun createFromParcel(parcel: Parcel): Todo {
                        return Todo(parcel)
                }

                override fun newArray(size: Int): Array<Todo?> {
                        return arrayOfNulls(size)
                }
        }
}
