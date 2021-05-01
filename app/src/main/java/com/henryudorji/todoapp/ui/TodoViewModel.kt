package com.henryudorji.todoapp.ui

import android.graphics.Bitmap
import androidx.lifecycle.*
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
    private fun profileInsert(profile: Profile) = viewModelScope.launch {
        todoRepository.profileInsert(profile)
    }

    private fun profileUpdate(profile: Profile) = viewModelScope.launch {
        todoRepository.profileUpdate(profile)
    }

    fun getProfile() = todoRepository.getProfile()

    fun validateUserInput(bitmap: Bitmap?, username: String, passcode: String?) {
        if (username.isEmpty()) {
            _profileResponse.postValue(Resource.Error("Username should not be empty"))
        }else {
            if (bitmap == null) {
                profileInsert(Profile(1,username, passcode, bitmap))
            }else {
                profileInsert(Profile(1,username, passcode, bitmap))
            }
            _profileResponse.postValue(Resource.Success("Profile setup successful"))
        }
    }

}