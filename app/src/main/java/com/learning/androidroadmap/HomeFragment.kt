package com.learning.androidroadmap

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.learning.androidroadmap.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        activity?.supportFragmentManager?.popBackStack()
        val sharedPreferences = setEncryptedPreferences(requireContext())
        binding.addButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, FormFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        val hashMap = getDataFromSharedPreferences(sharedPreferences)
        val empData = convertHashMapToList(hashMap)
        if (empData.isEmpty()) {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show()
        } else {
            val nameList = extractNames(empData)
            setupRecyclerView(nameList, sharedPreferences)
        }
        return binding.root
    }

    private fun convertHashMapToList(hashMap: MutableMap<String, *>?): MutableList<String> {
        val empDetails = mutableListOf<String>()
        for (keys in hashMap?.keys!!) {
            empDetails.add(hashMap[keys].toString())
        }
        return empDetails
    }

    private fun setupRecyclerView(
        nameList: MutableList<Employee>,
        sharedPreferences: SharedPreferences
    ) {
        binding.rview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rview.adapter = FragmentAdapter(nameList) { it, _ , delete->
            if(!delete) {
            val bundle = Bundle()
            bundle.putParcelable("fetch", it)
            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, detailsFragment)
                ?.addToBackStack(null)
                ?.commit()
        }else{sharedPreferences.edit().remove(it.uid).apply()
            }
        }
    }

    private fun extractNames(empData: MutableList<String>): MutableList<Employee> {
        val fieldNames = mutableListOf<Employee>()
        for (i in 0 until (empData.size)) {
            val name = empData[i]
            val fs: Employee = GsonBuilder().create().fromJson(name, Employee::class.java)
            fieldNames.add(fs)
        }
        return fieldNames
    }

    private fun getDataFromSharedPreferences(encryptedPreferences: SharedPreferences): MutableMap<String, *> {
        return encryptedPreferences.all
    }
}