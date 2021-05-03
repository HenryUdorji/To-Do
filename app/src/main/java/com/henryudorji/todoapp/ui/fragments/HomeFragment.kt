package com.henryudorji.todoapp.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.databinding.FragmentHomeBinding
import com.henryudorji.todoapp.databinding.ProfilePromptDialogBinding
import com.henryudorji.todoapp.ui.MainActivity
import com.henryudorji.todoapp.ui.TodoViewModel
import com.henryudorji.todoapp.utils.Constants
import com.henryudorji.todoapp.utils.Constants.PROFILE_SETUP_IS_DONE
import com.henryudorji.todoapp.utils.FileStorageManager
import com.henryudorji.todoapp.utils.getBooleanFromPref
import com.henryudorji.todoapp.utils.loadImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//
// Created by hash on 4/23/2021.
//
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: TodoViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        /*if (!requireActivity().getBooleanFromPref(Constants.PROFILE_SETUP_IS_DONE)) {
            showDialog()
        }*/

        initViews()
    }

    private fun showDialog() {
        val dialogBinding = ProfilePromptDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        dialogBinding.continueBtn.setOnClickListener {
            dialog.dismiss()
            Log.d(TAG, "showDialog: ${dialog.isShowing}")
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun initViews() {
        if (requireActivity().getBooleanFromPref(PROFILE_SETUP_IS_DONE)) {
            viewModel.getProfile().observe(viewLifecycleOwner, Observer {

                if (it != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val imageBitmap = it.imageName.let { imageName ->
                            FileStorageManager.getImageFromInternalStorage(requireContext(), imageName)
                        }
                        withContext(Dispatchers.Main) {
                            binding.profileImage.loadImage(imageBitmap)
                        }
                    }
                    binding.username.text = "Hello ${it.username}!"
                }
            })
        }
        binding.profileImage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        binding.bottomNavFab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTodoFragment)
        }
    }
}