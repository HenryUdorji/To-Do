package com.henryudorji.todoapp.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.data.TodoDatabase
import com.henryudorji.todoapp.data.TodoRepository
import com.henryudorji.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: TodoViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TodoApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = TodoDatabase(this)
        val repository = TodoRepository(db)
        val factory = TodoViewModelFactory(repository, application)
        viewModel = ViewModelProvider(this, factory).get(TodoViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.bottom_nav_graph)
        navController = navHostFragment.navController

        /*if (getBooleanFromPref(PROFILE_SETUP_IS_DONE)) {
            graph.startDestination = R.id.homeFragment
        }else {
            graph.startDestination = R.id.profileFragment
        }
        navHostFragment.navController.graph = graph*/
    }


}