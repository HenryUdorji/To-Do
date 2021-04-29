package com.henryudorji.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.henryudorji.todoapp.data.TodoRepository

//
// Created by hash on 4/28/2021.
//
@Suppress("UNCHECKED_CAST")
class TodoViewModelFactory(
        private val todoRepository: TodoRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(todoRepository) as T
    }
}