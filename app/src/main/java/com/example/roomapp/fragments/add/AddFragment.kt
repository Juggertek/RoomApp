package com.example.roomapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.sql.Date

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val firstName = addFirstName_et.text.toString()
        val lastName = addLastName_et.text.toString()
        val age = addAge_et.text.toString()
        val dob = addDob_et.text.toString()

        try {
            if (inputCheck(firstName, lastName, age, dob)) {
                // Create User Object
                val user = User(
                    0,
                    firstName,
                    lastName,
                    age.toIntOrNull(),
                    if (dob.isEmpty()) null else Date.valueOf(dob)
                )
                // Add Data to Database
                mUserViewModel.addUser(user)
                Toast.makeText(
                    requireContext(), "Successfully added!", Toast.LENGTH_LONG
                ).show()
                // Navigate Back
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                    .show()
            }
        } catch (e: IllegalArgumentException) {
            Toast.makeText(requireContext(), "Please use format: YYYY-MM-DD", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(
        firstName: String,
        lastName: String,
        age: String,
        dob: String
    ): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName))
    }

}