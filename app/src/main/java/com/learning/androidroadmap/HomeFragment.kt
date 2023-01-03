package com.learning.androidroadmap

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.learning.androidroadmap.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        activity?.supportFragmentManager?.popBackStack()

        binding.addButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, FormFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        val data = getDataFromSharedPreferences()
        if (data != "") {
            val nameList = extractNames(data)
            println("The names are : $nameList")
            setupRecyclerView(nameList)
        }


        return binding.root
    }

    private fun setupRecyclerView(nameList: List<Employee>) {
        binding.rview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rview.adapter = FragmentAdapter(nameList) { it, _ ->
            writeDataIntoSelectedSharedPreferences(it)
            //TODO : Switch to new Fragment
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, DetailsFragment())
                ?.addToBackStack(null)
                ?.commit()

        }
    }

    private fun writeDataIntoSelectedSharedPreferences(data: Employee) {
        val sharedPreferences =
            this.activity?.getSharedPreferences("employee_details", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply {
            val fetchedJson = GsonBuilder().create().toJson(data)
            putString("fetch", fetchedJson)
            apply()
        }
    }

    private fun extractNames(data: Any): List<Employee> {
        val jsonNames = mutableListOf<String>()
        val hp: HashMap<String, String> =
            data as HashMap<String, String> /* = java.util.HashMap<kotlin.String, kotlin.String> */
        for (keys in hp.keys) {
            jsonNames.add(hp[keys]!!)
        }

        val fieldNames = mutableListOf<Employee>()
        for(i in 0 until jsonNames.size)
        {
            val fs : Employee = GsonBuilder().create().fromJson(jsonNames[i], Employee::class.java)
            fieldNames.add(fs)
        }


        return fieldNames


    }

    private fun getDataFromSharedPreferences(): Any {
        val sharedPrefs = this.activity?.getSharedPreferences("Database", Context.MODE_PRIVATE)
        val data = sharedPrefs?.all
        return data ?: ""

    }

}