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
        showData(getDataFromBundle())
        return binding.root
    }

    private fun getDataFromBundle(): Employee? {
        return arguments?.getParcelable(KEY)
    }

    private fun showData(data: Employee?) {
        binding.apply {
            name.text = data?.fullName
            uid.text = data?.uid
            pname.text = data?.parentName
            pno.text = data?.phoneNum
            pcode.text = data?.postalCode
        }
    }
    companion object {
        const val KEY = "fetch"
    }
}


