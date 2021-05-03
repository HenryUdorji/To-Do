package com.henryudorji.todoapp.ui

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.henryudorji.todoapp.base.BaseApplication
import com.henryudorji.todoapp.data.TodoRepository
import com.henryudorji.todoapp.data.model.Profile
import com.henryudorji.todoapp.utils.Constants.PROFILE_SETUP_IS_DONE
import com.henryudorji.todoapp.utils.FileStorageManager
import com.henryudorji.todoapp.utils.Resource
import com.henryudorji.todoapp.utils.getBooleanFromPref
import kotlinx.coroutines.launch

//
// Created by hash on 4/28/2021.
//
class TodoViewModel(
        private val todoRepository: TodoRepository,
        application: Application
): AndroidViewModel(application) {

    private val TAG = "TodoViewModel"
    private val _profileResponse: MutableLiveData<Resource<Profile>> = MutableLiveData()
    val profileResponse: LiveData<Resource<Profile>>
        get() = _profileResponse


    /**
     * Profile
     */
    private fun profileInsert(profile: Profile) = viewModelScope.launch {
        try {
            todoRepository.profileInsert(profile)
        }catch (e: Exception) {
            Log.d(TAG, "profileInsert: ${e.message}")
        }
    }

    private fun profileUpdate(profile: Profile) = viewModelScope.launch {
        try {
            todoRepository.profileUpdate(profile)
        }catch (e: Exception) {
            Log.d(TAG, "profileUpdate: ${e.message}")
        }

    }

    fun getProfile() = todoRepository.getProfile()

    fun validateUserInput(bitmap: Bitmap, username: String) {
        if (username.isEmpty()) {
            _profileResponse.postValue(Resource.Error("Username should not be empty"))
        }else {
            viewModelScope.launch {
                FileStorageManager.saveImageToInternalStorage(
                        getApplication<BaseApplication>(), bitmap, "${username}_1.png")
            }

            if (!getApplication<BaseApplication>().getBooleanFromPref(PROFILE_SETUP_IS_DONE)) {
                //Showing the profile fragment for the first time
                profileInsert(Profile(1, username, "${username}_1.png"))
                Log.d(TAG, "validateUserInput: Profile insert")
            }else {
                profileUpdate(Profile(1, username, "${username}_1.png"))
                Log.d(TAG, "validateUserInput: Profile update")
            }
            _profileResponse.postValue(Resource.Success("Profile setup successful"))
        }
    }

}