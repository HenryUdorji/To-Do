package com.henryudorji.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodo(id: Int?): LiveData<Todo?>

    @Query("SELECT * FROM todo ORDER BY date DESC")
    fun getAllTodo(): LiveData<List<Todo>>
}