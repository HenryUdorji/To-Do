package com.henryudorji.todoapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.henryudorji.todoapp.data.TodoRepository

//
// Created by  on 4/28/2021.
//
abstract class BaseFragment<VM: ViewModel, B: ViewBinding, R: TodoRepository> : Fragment() {

    protected lateinit var binding: ViewBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = getBinding(inflater, container)
        return binding.root
    }

    abstract fun getViewModel() : Class<VM>

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getRepository(): R
}