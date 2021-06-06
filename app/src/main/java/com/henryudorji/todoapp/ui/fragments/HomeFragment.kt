package com.henryudorji.todoapp.ui.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.henryudorji.todoapp.R
import com.henryudorji.todoapp.adapters.HomeAdapter
import com.henryudorji.todoapp.databinding.FragmentHomeBinding
import com.henryudorji.todoapp.databinding.ProfilePromptDialogBinding
import com.henryudorji.todoapp.ui.MainActivity
import com.henryudorji.todoapp.ui.TodoViewModel
import com.henryudorji.todoapp.utils.*
import com.henryudorji.todoapp.utils.Constants.TODO
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


//
// Created by hash on 4/23/2021.
//
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: TodoViewModel
    private lateinit var homeAdapter: HomeAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

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
        }
    }

    private fun initViews() {
        viewModel.getUserImage()
        viewModel.userImageData.observe(viewLifecycleOwner, {
            when(it) {
                is Resource.Success -> binding.profileImage.loadImage(it.data)
                is Resource.Error -> {
                    /**
                     * Even though the data from the Livedata is null
                     * the extension function @loadImage knows how to
                     * handle null bitmap
                     */
                    binding.profileImage.loadImage(it.data)
                    binding.root.showSnackBar(it.message!!)
                }
                else -> binding.root.showSnackBar("Loading")
            }
        })

        binding.dateText.text = "Today, ${getTodayDate()}."

        binding.profileImage.setOnClickListener {
            openImagePicker()
        }

        binding.moreOptions.setOnClickListener {
            //@todo change profile name
            showDialog()
        }

        binding.bottomNavFab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTodoFragment)
        }


        homeAdapter = HomeAdapter(viewModel)
        binding.recyclerView.apply {
            adapter = homeAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getAllTodo().observe(viewLifecycleOwner, {
            homeAdapter.differ.submitList(it)
        })
        homeAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putParcelable(TODO, it) }
            findNavController().navigate(
                    R.id.action_homeFragment_to_addTodoFragment,
                    bundle
            )
        }
    }

    private fun getTodayDate(): CharSequence? {
        val currentTimeMillis = System.currentTimeMillis()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTimeMillis), ZoneId.systemDefault())
            dateTime.format(DateTimeFormatter.ofPattern("dd MMM,EEE"))
        }else {
            val dateTime = SimpleDateFormat("dd MMM,EEE")
            dateTime.format(Date(currentTimeMillis))
        }
    }


    private fun openImagePicker() {
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, Constants.IMAGE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.IMAGE_REQUEST_CODE) {
            data?.data?.let {
                val bitmap = BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(it))
                binding.profileImage.loadImage(bitmap)
                viewModel.saveUserImage(bitmap)
            }
        }
    }
}