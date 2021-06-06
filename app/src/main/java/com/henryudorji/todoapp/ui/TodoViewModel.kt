package com.henryudorji.todoapp.ui

import android.app.Application
import android.graphics.Bitmap
import android.os.Build
import androidx.lifecycle.*
import com.henryudorji.todoapp.base.BaseApplication
import com.henryudorji.todoapp.data.TodoRepository
import com.henryudorji.todoapp.data.model.Category
import com.henryudorji.todoapp.data.model.Priority
import com.henryudorji.todoapp.data.model.Todo
import com.henryudorji.todoapp.utils.ImageStorageManager
import com.henryudorji.todoapp.utils.Resource
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
    val userImageData: LiveData<Resource<Bitmap>> = _userImageData

    private var _dateSelected: MutableLiveData<String?> = MutableLiveData()
    val dateSelected: LiveData<String?> = _dateSelected

    private var _millisToHours: MutableLiveData<String?> = MutableLiveData()
    val millisToHours: LiveData<String?> = _millisToHours

    private var _validateTodoInput: MutableLiveData<Resource<String>> = MutableLiveData()
    val  validateTodoInput: LiveData<Resource<String>> = _validateTodoInput



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
        if (dateTimeInMillis != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTimeInMillis), ZoneId.systemDefault())
                _dateSelected.postValue(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            } else {
                val dateTime = SimpleDateFormat("yyyy-MM-dd")
                _dateSelected.postValue(dateTime.format(Date(dateTimeInMillis)))
            }
        }else _dateSelected.postValue(null)
    }

    fun convertMillisToHoursAndMinutes(millis: Long?) {
        if (millis != null) {
            val hour = millis.div(3600)
            val minute = millis.div(60000)
            val hourAsText = if (hour < 10) "0$hour" else hour
            val minuteAsText = if (minute < 10) "0$minute" else minute
            _millisToHours.postValue("$hourAsText:$minuteAsText")
        }else _millisToHours.postValue(null)
    }

    fun convertHoursAndMinutesToMillis(hour: Int, minute: Int): Long {
        //val minuteInMillis = minute * 60000
        val hoursInMillis = hour * 3600
        return hoursInMillis.toLong()
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute
        _millisToHours.postValue("$hourAsText:$minuteAsText")
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

    fun validateTodoInput(todoTitle: String, remindMe: Boolean, date: Long, time: Long?,
                          category: Category, priority: Priority, updateTodo: Todo?) {
        if (remindMe) {
            if (todoTitle.isEmpty() || time == 0L || date == 0L) {
                _validateTodoInput.postValue(Resource.Error("Title, Date and Alarm should all be " +
                        "set"))
            } else {
                if (updateTodo == null) {
                    insertTodo(Todo(0, todoTitle, date, time!!, remindMe, category, priority))
                }else updateTodo(Todo(updateTodo.id, todoTitle, date, time!!, remindMe, category, priority))
            }
        }else {
            if (todoTitle.isEmpty() || date == 0L) {
                _validateTodoInput.postValue(Resource.Error("Title and Date should all be set"))
            } else {
                if (updateTodo == null) {
                    insertTodo(Todo(0, todoTitle, date, 0L, remindMe, category, priority))
                }else updateTodo(Todo(updateTodo.id, todoTitle, date, 0L, remindMe, category, priority))
            }
        }
    }

}