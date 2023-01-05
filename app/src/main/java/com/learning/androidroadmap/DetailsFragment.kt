package com.learning.androidroadmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.androidroadmap.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val empdData = getDataFromBundle()
        showData(empdData)
        return binding.root
    }

    private fun getDataFromBundle(): Employee {
        val data = arguments?.getParcelable<Employee>("fetch")
        return data!!
    }

    private fun showData(data: Employee) {
        binding.name.text = data.fullName
        binding.uid.text = data.uid
        binding.pname.text = data.parentName
        binding.pno.text = data.phoneNum
        binding.pcode.text = data.postalCode
    }
}


