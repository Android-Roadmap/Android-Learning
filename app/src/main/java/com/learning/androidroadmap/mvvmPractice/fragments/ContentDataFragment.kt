package com.learning.androidroadmap.mvvmPractice.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.androidroadmap.R
import com.learning.androidroadmap.databinding.FragmentContentDataBinding
import com.learning.androidroadmap.mvvmPractice.adapter.AdapterMvvm
import com.learning.androidroadmap.mvvmPractice.getViewModelFactory
import com.learning.androidroadmap.mvvmPractice.viewModels.ContentDataViewModel


class ContentDataFragment : Fragment() {
    lateinit var binding : FragmentContentDataBinding
    private lateinit var postAdapter: AdapterMvvm
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = viewModelInstanceGenerator()
        postAdapter = AdapterMvvm()
        binding.recyclerViewMvvm.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMvvm.adapter = postAdapter
//        viewModel.getDataFromRepository()
        viewModel.getDataFromAPIViaFlow()

        viewModel.data.observe(viewLifecycleOwner){
            postAdapter.collectData(it)
        }

        viewModel.statusCode.observe(viewLifecycleOwner){
            binding.progressBar.visibility = View.GONE
            Toast.makeText(context, "Data successfully loaded", Toast.LENGTH_SHORT).show()
        }

        viewModel.statusCodeNotFound.observe(viewLifecycleOwner){
            Toast.makeText(context,"No internet connection", Toast.LENGTH_SHORT).show()
            viewModel.checkForCache()
            viewModel.cache.observe(viewLifecycleOwner){
                if (it.isNotEmpty()){
                    binding.progressBar.visibility = View.GONE
                    postAdapter.collectData(it)
                }
                else{
                    binding.errorTextView.text = getString(R.string.offline)
                    binding.errorTextView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun viewModelInstanceGenerator(): ContentDataViewModel {
        return ViewModelProvider(this, getViewModelFactory
        { ContentDataViewModel() })[ContentDataViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.menu_action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_logout ->{
            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
            viewModelInstanceGenerator().logout()
            findNavController().navigate(R.id.action_fragment_content_to_loginFragmentNew)
            true
        }
        else ->{
            super.onOptionsItemSelected(item)
        }
    }
}