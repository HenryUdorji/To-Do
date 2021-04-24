package com.henryudorji.todoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.databinding.ActivityMainBinding
import com.henryudorji.todoapp.utils.Constants.PROFILE_SETUP_IS_DONE
import com.henryudorji.todoapp.utils.SharedPrefUtil

class MainActivity : AppCompatActivity() {
    //fb827c7470c2b05072c9106ba63ddbf4
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TodoApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.bottom_nav_graph)
        navHostFragment.navController.graph = graph
        //val navController = navHostFragment.navController

        /**
         * At the very first launch of this app the user is prompted
         * to setup their profile, after this setup this setup fragment
         * is not shown to the user again on startup.
         */
        if (!SharedPrefUtil.getBooleanFromPref(PROFILE_SETUP_IS_DONE)) {
            graph.startDestination = R.id.setupFragment
        }else {
            graph.startDestination = R.id.homeFragment
        }


    }

}