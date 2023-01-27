package com.learning.androidroadmap.mvvmPractice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.androidroadmap.R
import com.learning.androidroadmap.databinding.FragmentContentDataBinding
import com.learning.androidroadmap.mvvmPractice.adapter.AdapteerMvvm
import com.learning.androidroadmap.mvvmPractice.getViewModelFactory
import com.learning.androidroadmap.mvvmPractice.viewModels.ContentDataViewModel


class ContentDataFragment : Fragment() {
    lateinit var binding : FragmentContentDataBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this, getViewModelFactory
        { ContentDataViewModel(requireContext()) })[ContentDataViewModel::class.java]
        viewModel.getDataFromRepository()
        viewModel.data.observe(viewLifecycleOwner){
            val data = it
            binding.recyclerViewMvvm.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerViewMvvm.adapter = AdapteerMvvm(data)
        }
        viewModel.statusCode.observe(viewLifecycleOwner){ it ->
            binding.progressBar.visibility = View.GONE
            if(it == 200)
                Toast.makeText(context, "Data successfully loaded", Toast.LENGTH_SHORT).show()
            else if(it == 404){
                Toast.makeText(context,"No internet connection", Toast.LENGTH_SHORT).show()
                viewModel.checkForCache()
                viewModel.cache.observe(viewLifecycleOwner){
                    if (it.isNotEmpty()){
                        binding.recyclerViewMvvm.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding.recyclerViewMvvm.adapter = AdapteerMvvm(it)
                    }
                    else{
                        binding.errorTextView.text = getString(R.string.offline)
                        binding.errorTextView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}