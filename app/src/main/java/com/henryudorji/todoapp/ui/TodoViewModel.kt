package com.henryudorji.todoapp.ui

import android.app.Application
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.henryudorji.todoapp.base.BaseApplication
import com.henryudorji.todoapp.data.TodoRepository
import com.henryudorji.todoapp.data.model.Todo
import com.henryudorji.todoapp.utils.ImageStorageManager
import com.henryudorji.todoapp.utils.Resource
import com.henryudorji.todoapp.utils.getBooleanFromPref
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

//
// Created by hash on 4/28/2021.
//
class TodoViewModel(
    private val todoRepository: TodoRepository,
    application: Application
): AndroidViewModel(application) {

    private val TAG = "TodoViewModel"

    private var _userImageData: MutableLiveData<Resource<Bitmap>> = MutableLiveData()
    val userImageData: LiveData<Resource<Bitmap>>
        get() = _userImageData

    private var _dateSelected: MutableLiveData<Resource<String?>> = MutableLiveData()
    val dateSelected: LiveData<Resource<String?>>
        get() = _dateSelected

    private var _millisToHours: MutableLiveData<Resource<String?>> = MutableLiveData()
    val millisToHours: LiveData<Resource<String?>>
        get() = _millisToHours

    val todoResponse = MutableLiveData<Boolean>()


    fun insertTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.todoInsert(todo)
    }

    fun updateTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.todoUpdate(todo)
    }

    fun getTodo(id: Int?) = todoRepository.getTodo(id)

    fun getAllTodo() = todoRepository.getAllTodo()


    fun onDateSelected(dateTimeInMillis: Long?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTimeInMillis!!), ZoneId.systemDefault())
            val dateString = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            _dateSelected.postValue(Resource.Success(null, dateString))
        }else {
            val dateTime = SimpleDateFormat("yyyy-MM-dd")
            val dateString = dateTime.format(Date(dateTimeInMillis!!))
            _dateSelected.postValue(Resource.Success(null, dateString))
        }
    }

    fun convertMillisToHoursAndMinutes(millis: Long) {
        val hour = millis / 3600
        val minute = millis / 60000
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute
        _millisToHours.postValue(Resource.Success(null, "$hourAsText:$minuteAsText"))
    }

    fun saveUserImage(bitmap: Bitmap) {
        viewModelScope.launch {
            ImageStorageManager.saveImageToInternalStorage(
                getApplication<BaseApplication>(), bitmap, "userProfile.png")
        }
    }

    fun getUserImage() {
        viewModelScope.launch {
            val bitmap = ImageStorageManager.getImageFromInternalStorage(
                getApplication<BaseApplication>(), "userProfile.png")
            if (bitmap == null) {
                _userImageData.postValue(Resource.Error("Click on the image to setup your image!"))
            }else {
                _userImageData.postValue(Resource.Success(null, bitmap))
            }
        }
    }
}