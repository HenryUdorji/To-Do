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

    @Insert
    suspend fun todoInsert(todo: Todo)

    @Update
    suspend fun todoUpdate(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo ORDER BY date DESC")
    fun getAllTodo(): LiveData<List<Todo>>

    /**
     * User Profile
     */
    @Insert
    suspend fun profileInsert(profile: Profile)

    @Update
    suspend fun profileUpdate(profile: Profile)

    @Query("UPDATE profile SET username = :username WHERE id = :id")
    suspend fun updateProfileUsername(id: Int, username: String)

    @Query("UPDATE profile SET image_name = :imageName WHERE id = :id")
    suspend fun updateProfileImage(id: Int, imageName: String)

    @Query("SELECT * FROM profile")
    fun getProfile(): LiveData<Profile>

}