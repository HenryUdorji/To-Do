package com.henryudorji.todoapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.databinding.FragmentSetupBinding
import com.henryudorji.todoapp.utils.Constants
import com.henryudorji.todoapp.utils.Constants.PROFILE_SETUP_IS_DONE
import com.henryudorji.todoapp.utils.SharedPrefUtil

//
// Created by hash on 4/23/2021.
//
class SetupFragment : Fragment(R.layout.fragment_setup){
    private lateinit var binding: FragmentSetupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupBinding.bind(view)


        binding.continueBtn.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_homeFragment)
            SharedPrefUtil.putBooleanInPref(PROFILE_SETUP_IS_DONE, true)
        }
    }
}