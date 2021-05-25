package com.henryudorji.todoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.henryudorji.todoapp.data.model.Todo
import com.henryudorji.todoapp.databinding.SingleTodoLayoutBinding
import com.henryudorji.todoapp.ui.TodoViewModel
import com.henryudorji.todoapp.utils.Resource
import java.util.*

//
// Created by hash on 5/5/2021.
//
class HomeAdapter(private val viewModel: TodoViewModel): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(val binding: SingleTodoLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object: DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = SingleTodoLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val todo = differ.currentList[position]

        holder.binding.apply {
            priority.text = todo.priority.name
            //viewModel.convertMillisToHoursAndMinutes(todo?.alarmTime!!)
            taskTime.text = convertMillisToHoursAndMinutes(todo?.alarmTime!!)
            taskTitle.text = todo.title
            category.text = todo.category.name
            //taskDate.text = todo?.date.toString()

            this.root.setOnClickListener {
                onItemClickListener?.let { todo.id?.let { it1 -> it(it1) } }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((id: Int) -> Unit) ? = null

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        onItemClickListener = listener
    }

    private fun convertMillisToHoursAndMinutes(millis: Long): String {
        val hour = millis / 3600
        val minute = millis / 60000
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute
        return "$hourAsText:$minuteAsText"
    }
}