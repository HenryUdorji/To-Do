package com.henryudorji.todoapp.utils

//
// Created by  on 4/28/2021.
//
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(message: String? = null, data: T? = null): Resource<T>(data, message)
    class Error<T>(message: String): Resource<T>(null, message)
    class Loading<T>: Resource<T>()
}
