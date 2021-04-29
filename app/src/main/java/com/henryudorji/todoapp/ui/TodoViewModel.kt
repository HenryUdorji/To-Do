package com.henryudorji.todoapp.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henryudorji.todoapp.data.TodoRepository
import com.henryudorji.todoapp.data.model.Profile
import com.henryudorji.todoapp.utils.Resource
import kotlinx.coroutines.launch

//
// Created by hash on 4/28/2021.
//
class TodoViewModel(
        private val todoRepository: TodoRepository
): ViewModel() {

    private val _profileResponse: MutableLiveData<Resource<Profile>> = MutableLiveData()
    val profileResponse: LiveData<Resource<Profile>>
        get() = _profileResponse


    /**
     * Profile
     */
    fun profileUpsert(profile: Profile) = viewModelScope.launch {
        todoRepository.profileUpsert(profile)
    }

    fun getProfile() = todoRepository.getProfile()

    fun validateUserInput(bitmap: Bitmap?, username: String, passcode: Int?) {
        if (username.isEmpty()) {
            _profileResponse.postValue(Resource.Error("Username should not be empty"))
        }else {
            _profileResponse.postValue(Resource.Success("Profile setup successful"))
            profileUpsert(Profile(1,username, passcode, bitmap))
        }
    }

}