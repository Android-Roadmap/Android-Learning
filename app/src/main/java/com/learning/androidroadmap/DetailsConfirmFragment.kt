package com.learning.androidroadmap

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.learning.androidroadmap.databinding.FragmentDetailsConfirmBinding


class DetailsConfirmFragment : Fragment() {


    lateinit var binding: FragmentDetailsConfirmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsConfirmBinding.inflate(inflater,container,false)
        val jsonString = readDataFromSharedPreferences()
        val fields : Employee = GsonBuilder().create().fromJson(jsonString, Employee::class.java)
        setFields(fields)


        binding.saveButton.setOnClickListener {
            saveDataToSharedPreferences(jsonString)
            activity?.supportFragmentManager?.popBackStack() //Pop FormFragment
            activity?.supportFragmentManager?.popBackStack() //Pop HomeFragment
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, HomeFragment())
                ?.commit()
            Toast.makeText(context,"Details saved", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun saveDataToSharedPreferences(jsonString: String) {
        val sharedPrefs = activity?.getSharedPreferences("Database", Context.MODE_PRIVATE)
        val editor = sharedPrefs?.edit()
        editor?.apply {
            putString(jsonString,jsonString)
            apply()
        }
    }

    private fun setFields(fields: Employee) {
        binding.nameTextView.text = fields.fullName
        binding.ParentNameTextView.text = fields.parentName
        binding.PhoneNumberTextView.text = fields.phoneNum
        binding.PostalCodeTextView.text = fields.postalCode
        binding.UidTextView.text = fields.uid
    }

    private fun readDataFromSharedPreferences(): String {
        val sharedPrefs = activity?.getSharedPreferences("confirm_details", Context.MODE_PRIVATE)
        val data = sharedPrefs?.getString("confirmation",null)
        return data?:""
    }


}