package com.henryudorji.todoapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.databinding.FragmentAddTodoBinding
import com.henryudorji.todoapp.ui.MainActivity
import com.henryudorji.todoapp.ui.TodoViewModel

//
// Created by hash on 4/23/2021.
//
class AddTodoFragment : Fragment(R.layout.fragment_add_todo){
    private lateinit var binding: FragmentAddTodoBinding
    private lateinit var viewModel: TodoViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTodoBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        initViews()
    }

    private fun initViews() {
        (activity as MainActivity).setSupportActionBar(binding.toolbar);
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true);
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}