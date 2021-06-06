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
import com.henryudorji.todoapp.utils.hide
import com.henryudorji.todoapp.utils.show
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
            if (todo.remindMe) alarmIcon.show() else alarmIcon.hide()
            taskTitle.text = todo.title
            category.text = todo.category.name

            this.root.setOnClickListener {
                onItemClickListener?.let { it(todo) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((todo: Todo) -> Unit) ? = null

    fun setOnItemClickListener(listener: (todo: Todo) -> Unit) {
        onItemClickListener = listener
    }
}