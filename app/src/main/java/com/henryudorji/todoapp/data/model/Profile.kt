package com.henryudorji.todoapp.data.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created on 4/28/2021.
//
@Entity(tableName = "profile")
data class Profile(
        @PrimaryKey
        var id: Int,
        var username: String,
        @ColumnInfo(name = "image_name")
        var imageName: String
)
