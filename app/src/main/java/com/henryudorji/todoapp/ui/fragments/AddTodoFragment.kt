package com.henryudorji.todoapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.data.model.Category
import com.henryudorji.todoapp.data.model.Priority
import com.henryudorji.todoapp.data.model.TaskState
import com.henryudorji.todoapp.data.model.Todo
import com.henryudorji.todoapp.databinding.FragmentAddTodoBinding
import com.henryudorji.todoapp.ui.MainActivity
import com.henryudorji.todoapp.ui.TodoViewModel
import com.henryudorji.todoapp.utils.showSnackBar
import java.util.*

//
// Created by hash on 4/23/2021.
//
class AddTodoFragment : Fragment(R.layout.fragment_add_todo){
    private val TAG = "AddTodoFragment"
    private lateinit var binding: FragmentAddTodoBinding
    private lateinit var viewModel: TodoViewModel
    private var updateTodoId: Int = 0
    private val args: AddTodoFragmentArgs by navArgs()

    private var todoTitle: String? = null
    private var date: Long? = null
    private var time: Long? = null
    private var remindMe = false
    private var category = Category.Work
    private var priority = Priority.Low
    private var taskState = TaskState.Pending


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTodoBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        updateTodoId = args.id
        if (updateTodoId != 0) {
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
            findNavController().apply {
                navigateUp()
                popBackStack()
            }
        }

        binding.dateText.setOnClickListener {
            showDatePicker()
        }
        binding.calenderImage.setOnClickListener {
            showDatePicker()
        }

        binding.alarmText.setOnClickListener {
            showTimePicker()
        }
        binding.alarmImage.setOnClickListener {
            showTimePicker()
        }

        binding.remindMeSwitch.setOnCheckedChangeListener { _, isChecked ->
            remindMe = isChecked
        }

        binding.toggleGroupCat.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                binding.workBtn.id -> {
                    category = Category.Work
                    binding.workBtn.isChecked = isChecked
                }
                binding.funBtn.id -> {
                    category = Category.Fun
                    binding.funBtn.isChecked = isChecked
                }
                binding.sportsBtn.id -> {
                    category = Category.Sports
                    binding.sportsBtn.isChecked = isChecked
                }
                binding.studyBtn.id -> {
                    category = Category.Study
                    binding.studyBtn.isChecked = isChecked
                }
            }
        }

        binding.toggleGroupPriority.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                binding.lowBtn.id -> {
                    priority = Priority.Low
                    binding.lowBtn.isChecked = isChecked
                }
                binding.mediumBtn.id -> {
                    priority = Priority.Medium
                    binding.mediumBtn.isChecked = isChecked
                }
                binding.highBtn.id -> {
                    priority = Priority.High
                    binding.highBtn.isChecked = isChecked
                }
            }
        }

        binding.addTodoBtn.setOnClickListener {
            if (validateInput()) {
                val todo = Todo(todoTitle, date, time, remindMe, category, priority, taskState)

                if (updateTodoId == 0) {
                    viewModel.insertTodo(todo)
                    Log.d(TAG, "validateTodo: INSERTION")
                } else {
                    todo.id = updateTodoId
                    viewModel.updateTodo(todo)
                    Log.d(TAG, "validateTodo: UPDATE")
                }
                findNavController().apply {
                    navigateUp()
                    popBackStack()
                }

            }
        }

        //OBSERVERS
        viewModel.dateSelected.observe(viewLifecycleOwner, { date ->
            if (date.data.isNullOrEmpty()) {
                binding.dateText.text = "Date"
            }else {
                binding.dateText.text = date.data
            }
        })

        viewModel.millisToHours.observe(viewLifecycleOwner, { time ->
            if (time.data.isNullOrEmpty()) {
                binding.alarmText.text = "Alarm"
            }else {
                binding.alarmText.text = time.data
            }
        })

        viewModel.getTodo(updateTodoId).observe(viewLifecycleOwner, {
            if (it != null) {
                binding.title.setText(it.title)
                viewModel.onDateSelected(it.date)
                viewModel.convertMillisToHoursAndMinutes(it.alarmTime!!)
                binding.remindMeSwitch.isChecked = it.remindMe == true
                setCheckedButton(it.priority.name)
                setCheckedButton(it.category.name)
            }
        })

    }

    private fun setCheckedButton(name: String?) {
        when(name) {
            "WORK" -> binding.workBtn.isChecked = true
            "FUN" -> binding.funBtn.isChecked = true
            "SPORTS" -> binding.sportsBtn.isChecked = true
            "STUDY" -> binding.studyBtn.isChecked = true
            "LOW" -> binding.lowBtn.isChecked = true
            "MEDIUM" -> binding.mediumBtn.isChecked = true
            "HIGH" -> binding.highBtn.isChecked = true
        }
    }

    private fun validateInput(): Boolean {
        todoTitle = binding.title.text.toString()

        return if (todoTitle.isNullOrEmpty() || binding.alarmText.text.equals("Alarm") ||
                binding.dateText.text.equals("Date")) {
            binding.root.showSnackBar("Title, Date and Alarm should all be set")
            false
        }else true
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
                show(this@AddTodoFragment.requireActivity().supportFragmentManager, "AddTodo")
                addOnPositiveButtonClickListener {
                    time = convertHoursAndMinutesToMillis(this.hour, this.minute)
                    binding.alarmText.text = onTimeSelected(this.hour, this.minute)
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
                show(this@AddTodoFragment.requireActivity().supportFragmentManager, "AddTodo")
                addOnPositiveButtonClickListener {
                    date = it
                    viewModel.onDateSelected(it)
                }
            }
    }

    private fun onTimeSelected(hour: Int, minute: Int): String {
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute
        return "$hourAsText:$minuteAsText"
    }

    private fun convertHoursAndMinutesToMillis(hour: Int, minute: Int): Long {
        //val minuteInMillis = minute * 60000
        val hoursInMillis = hour * 3600
        return hoursInMillis.toLong()
    }
}