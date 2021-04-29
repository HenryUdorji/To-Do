package com.henryudorji.todoapp.data

import com.henryudorji.todoapp.data.model.Profile
import com.henryudorji.todoapp.data.model.Todo

//
// Created on 4/28/2021.
//
class TodoRepository(
        private val db: TodoDatabase
) {
    suspend fun upsert(todo: Todo) = db.getTodoDao().upsert(todo)

    suspend fun delete(todo: Todo) = db.getTodoDao().deleteTodo(todo)

    fun getAllTodo() = db.getTodoDao().getAllTodo()


    /**
     * User Profile
     */
    suspend fun profileUpsert(profile: Profile) = db.getTodoDao().profileUpsert(profile)

    fun getProfile() = db.getTodoDao().getProfile()
}