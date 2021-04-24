package com.henryudorji.todoapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.databinding.FragmentAddTodoBinding
import com.henryudorji.todoapp.databinding.FragmentSetupBinding

//
// Created by hash on 4/23/2021.
//
class AddTodoFragment : Fragment(R.layout.fragment_add_todo){
    private lateinit var binding: FragmentAddTodoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTodoBinding.bind(view)
    }
}