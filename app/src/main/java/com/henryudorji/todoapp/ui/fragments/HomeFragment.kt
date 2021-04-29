package com.henryudorji.todoapp.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.databinding.FragmentHomeBinding
import com.henryudorji.todoapp.databinding.ProfilePromptDialogBinding
import com.henryudorji.todoapp.utils.Constants
import com.henryudorji.todoapp.utils.Constants.APP_HAS_LAUNCHED_BEFORE
import com.henryudorji.todoapp.utils.getBooleanFromPref


//
// Created by hash on 4/23/2021.
//
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        /**
         * At the very first launch of this app the user is prompted
         * to setup their profile, after this setup this setup fragment
         * is not shown to the user again on startup.
         */
        if (!requireActivity().getBooleanFromPref(Constants.PROFILE_SETUP_IS_DONE)) {
            val dialogBinding = ProfilePromptDialogBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding.root)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //dialog.setCanceledOnTouchOutside(false)
            dialog.show()

            dialogBinding.continueBtn.setOnClickListener {
                dialog.dismiss()
                findNavController().navigate(R.id.action_to_profile_fragment)
            }

        }

        initViews()
    }

    private fun initViews() {
        binding.bottomNavFab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTodoFragment)
        }
    }
}