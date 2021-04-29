package com.henryudorji.todoapp.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created on 4/28/2021.
//
@Entity(tableName = "profile")
data class Profile(
        @PrimaryKey
        var id: Int,
        val username: String,
        val passcode: Int? = null,
        val image: Bitmap? = null
)
