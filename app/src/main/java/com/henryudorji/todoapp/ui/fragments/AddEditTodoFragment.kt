package com.henryudorji.todoapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.data.model.Category
import com.henryudorji.todoapp.data.model.Priority
import com.henryudorji.todoapp.data.model.Todo
import com.henryudorji.todoapp.databinding.FragmentAddEditTodoBinding
import com.henryudorji.todoapp.ui.MainActivity
import com.henryudorji.todoapp.ui.TodoViewModel
import com.henryudorji.todoapp.utils.Resource
import com.henryudorji.todoapp.utils.hide
import com.henryudorji.todoapp.utils.show
import com.henryudorji.todoapp.utils.showSnackBar
import java.util.*

//
// Created by hash on 4/23/2021.
//
class AddEditTodoFragment : Fragment(R.layout.fragment_add_edit_todo){
    private val TAG = "AddTodoFragment"
    private lateinit var binding: FragmentAddEditTodoBinding
    private lateinit var viewModel: TodoViewModel
    private var updateTodo: Todo? = null
    private val args: AddEditTodoFragmentArgs by navArgs()

    private var todoTitle: String = ""
    private var date: Long = 0L
    private var time: Long = 0L
    private var remindMe = false
    private var category = Category.Work
    private var priority = Priority.Low


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddEditTodoBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        updateTodo = args.todo
        if (updateTodo != null) {
            binding.createTaskText.text = "Update Task"
            binding.addTodoBtn.text = "Update Task"
        }

        initViews()
    }


    private fun initViews() {
        (activity as MainActivity).setSupportActionBar(binding.toolbar);
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true);
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.dateText.setOnClickListener {
            showDatePicker()
        }
        binding.calenderImage.setOnClickListener {
            showDatePicker()
        }

        setCheckedButton(priority.name)
        setCheckedButton(category.name)
        showAlarmText(remindMe)

        binding.remindMeSwitch.setOnCheckedChangeListener { _, isChecked ->
            remindMe = isChecked
            showAlarmText(remindMe)
        }

        binding.addTodoBtn.setOnClickListener {
            viewModel.validateTodoInput(todoTitle, remindMe, date, time, category, priority, updateTodo)

            viewModel.validateTodoInput.observe(viewLifecycleOwner, { input ->
                when(input) {
                    is Resource.Error -> binding.root.showSnackBar(input.message!!)
                    else -> findNavController().navigate(R.id.action_addTodoFragment_to_homeFragment)
                }
            })
        }

        binding.apply {
            title.setText(updateTodo?.title ?: "")
            viewModel.onDateSelected(updateTodo?.date)
            viewModel.convertMillisToHoursAndMinutes(updateTodo?.alarmTime)
            remindMeSwitch.isChecked = updateTodo?.remindMe == true
            setCheckedButton(updateTodo?.priority?.name)
            setCheckedButton(updateTodo?.category?.name)
        }

        viewModel.dateSelected.observe(viewLifecycleOwner, {
            binding.dateText.text = it ?: "Date"
        })

        viewModel.millisToHours.observe(viewLifecycleOwner, {
            binding.alarmText.text = it ?: "Alarm"
        })

    }


    private fun setCheckedButton(name: String?) {
        when(name) {
            "Work" -> category = Category.Work
            "Fun" -> category = Category.Fun
            "Study" -> category = Category.Study
            "Low" -> priority = Priority.Low
            "Medium" -> priority = Priority.Medium
            "High" -> priority = Priority.High
        }
    }

    private fun validateInput(): Boolean {
        todoTitle = binding.title.text.toString()

        return if (remindMe) {
            if (todoTitle.isEmpty() || time == 0L || date == 0L) {
                binding.root.showSnackBar("Title, Date and Alarm should all be set")
                false
            } else true
        }else {
            if (todoTitle.isEmpty() || date == 0L) {
                binding.root.showSnackBar("Title and Date should all be set")
                false
            } else true
        }
    }

    private fun showTimePicker() {
        val uses24HourFormat = is24HourFormat(requireContext())
        val timeFormat = if (uses24HourFormat) {
            TimeFormat.CLOCK_24H
        }else {
            TimeFormat.CLOCK_12H
        }

        MaterialTimePicker.Builder()
                .setTimeFormat(timeFormat)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Select time")
                .build()
                .apply {
                    show(this@AddEditTodoFragment.requireActivity().supportFragmentManager, "AddTodo")
                    addOnPositiveButtonClickListener {
                        time = viewModel.convertHoursAndMinutesToMillis(this.hour, this.minute)
                        viewModel.onTimeSelected(this.hour, this.minute)
                    }
                }
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
                .apply{
                    show(this@AddEditTodoFragment.requireActivity().supportFragmentManager, "AddTodo")
                    addOnPositiveButtonClickListener {
                        date = it
                        viewModel.onDateSelected(it)
                    }
                }
    }

    private fun showAlarmText(remindMe: Boolean) {
        if (remindMe) {
            binding.alarmText.show()
            binding.alarmImage.show()

            binding.alarmText.setOnClickListener {
                showTimePicker()
            }
            binding.alarmImage.setOnClickListener {
                showTimePicker()
            }

        }else {
            binding.alarmText.hide()
            binding.alarmImage.hide()
        }
    }

}