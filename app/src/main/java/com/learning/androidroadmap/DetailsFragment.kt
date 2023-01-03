package com.learning.androidroadmap

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.learning.androidroadmap.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val data = readDataFromSelectedSharedPref()
        showData(data)


        return binding.root
    }

    private fun showData(data: String) {

        val f : Employee = GsonBuilder().create().fromJson(data, Employee::class.java)

        binding.name.text = f.fullName
        binding.uid.text = f.uid
        binding.pname.text = f.parentName
        binding.pno.text = f.phoneNum
        binding.pcode.text = f.postalCode
    }

    private fun readDataFromSelectedSharedPref(): String {
        val sharedPref = this.activity?.getSharedPreferences("employee_details", Context.MODE_PRIVATE)
        val data = sharedPref?.getString("fetch", null)
        return data?:""
    }
}


