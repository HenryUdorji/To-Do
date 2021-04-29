package com.henryudorji.todoapp.utils

//
// Created by  on 4/28/2021.
//
sealed class Resource<T>(
        val message: String? = null
) {
    class Success<T>(message: String? = null): Resource<T>(message)
    class Error<T>(message: String? = null): Resource<T>(message)
    class Loading<T>: Resource<T>()
}
