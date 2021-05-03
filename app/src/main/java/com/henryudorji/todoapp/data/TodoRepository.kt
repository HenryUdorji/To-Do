package com.henryudorji.todoapp.data

import com.henryudorji.todoapp.data.model.Profile
import com.henryudorji.todoapp.data.model.Todo

//
// Created on 4/28/2021.
//
class TodoRepository(
        private val db: TodoDatabase
) {

    suspend fun todoInsert(todo: Todo) = db.getTodoDao().todoInsert(todo)

    suspend fun todoUpdate(todo: Todo) = db.getTodoDao().todoUpdate(todo)

    suspend fun delete(todo: Todo) = db.getTodoDao().deleteTodo(todo)

    fun getAllTodo() = db.getTodoDao().getAllTodo()


    /**
     * User Profile
     */
    suspend fun profileInsert(profile: Profile) = db.getTodoDao().profileInsert(profile)

    suspend fun profileUpdate(profile: Profile) = db.getTodoDao().profileUpdate(profile)

    fun getProfile() = db.getTodoDao().getProfile()
}