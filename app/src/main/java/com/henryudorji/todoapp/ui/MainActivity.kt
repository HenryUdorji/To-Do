package com.henryudorji.todoapp.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.data.TodoDatabase
import com.henryudorji.todoapp.data.TodoRepository
import com.henryudorji.todoapp.databinding.ActivityMainBinding
import com.henryudorji.todoapp.utils.Constants.APP_HAS_LAUNCHED_BEFORE
import com.henryudorji.todoapp.utils.getBooleanFromPref

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TodoApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = TodoDatabase(this)
        val repository = TodoRepository(db)
        val factory = TodoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(TodoViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.bottom_nav_graph)
        navHostFragment.navController.graph = graph
        //val navController = navHostFragment.navController



        //todo: 4/28/2021 show fingerPrint or passcode pop up if it is set any time the app is
        //todo destroyed and relaunched


    }
}