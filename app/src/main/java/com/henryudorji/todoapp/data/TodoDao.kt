package com.henryudorji.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.henryudorji.todoapp.data.model.Profile
import com.henryudorji.todoapp.data.model.Todo

//
// Created on 4/28/2021.
//
@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo ORDER BY date DESC")
    fun getAllTodo(): LiveData<List<Todo>>

    /**
     * User Profile
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun profileInsert(profile: Profile)

    @Update
    suspend fun profileUpdate(profile: Profile)

    @Query("SELECT * FROM profile")
    fun getProfile(): LiveData<Profile>

}