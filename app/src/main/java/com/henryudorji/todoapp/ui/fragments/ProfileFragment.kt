package com.henryudorji.todoapp.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.databinding.FragmentProfileBinding
import com.henryudorji.todoapp.ui.MainActivity
import com.henryudorji.todoapp.ui.TodoViewModel
import com.henryudorji.todoapp.utils.*
import com.henryudorji.todoapp.utils.Constants.IMAGE_REQUEST_CODE
import com.henryudorji.todoapp.utils.Constants.PASS_CODE_IS_SET
import com.henryudorji.todoapp.utils.Constants.PROFILE_SETUP_IS_DONE
import com.henryudorji.todoapp.utils.Constants.TODO_FILE_DIR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

//
// Created by hash on 4/23/2021.
//
class ProfileFragment : Fragment(R.layout.fragment_profile){
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: TodoViewModel
    private var bitmap: Bitmap? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        initViews()
    }

    private fun initViews() {
        (activity as MainActivity).setSupportActionBar(binding.toolbar);
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true);
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        if (requireActivity().getBooleanFromPref(PROFILE_SETUP_IS_DONE)) {
            viewModel.getProfile().observe(viewLifecycleOwner, Observer {

                CoroutineScope(Dispatchers.IO).launch {
                    val imageBitmap = it.imageName.let { imageName ->
                        FileStorageManager.getImageFromInternalStorage(requireContext(), imageName)
                    }
                    withContext(Dispatchers.Main) {
                        binding.profileImage.loadImage(imageBitmap)
                    }
                }
                binding.username.setText(it.username)
            })
        }

        binding.profileImage.setOnClickListener {
            openImagePicker()
        }

        binding.updateBtn.setOnClickListener {
            val username = binding.username.text.toString()
            bitmap = binding.profileImage.drawable.toBitmap()
            viewModel.validateUserInput(bitmap!!, username)


            viewModel.profileResponse.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        if (!requireContext().getBooleanFromPref(PROFILE_SETUP_IS_DONE)) {
                            context?.putBooleanInPref(PROFILE_SETUP_IS_DONE, true)
                            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                        }else {
                            binding.root.showSnackBar("Profile update successful")
                        }
                    }
                    is Resource.Error -> {
                        response.message?.let { binding.root.showSnackBar(it) }
                    }
                    is Resource.Loading -> {
                        binding.root.showSnackBar("Loading")
                    }
                }
            })
        }
    }

    private fun openImagePicker() {
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, IMAGE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {
            data?.data?.let {
                bitmap = BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(it))
                binding.profileImage.loadImage(bitmap)
            }

        }
    }
}