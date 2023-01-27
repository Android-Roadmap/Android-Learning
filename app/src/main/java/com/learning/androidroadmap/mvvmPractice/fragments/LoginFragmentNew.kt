package com.learning.androidroadmap.mvvmPractice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.learning.androidroadmap.R
import com.learning.androidroadmap.databinding.FragmentLoginBinding
import com.learning.androidroadmap.mvvmPractice.getViewModelFactory
import com.learning.androidroadmap.mvvmPractice.viewModels.LoginViewModel

class LoginFragmentNew : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this, getViewModelFactory { LoginViewModel(requireContext()) })[LoginViewModel::class.java]

        viewModel.checkSavedState()

        viewModel.checkStateSaved.observe(viewLifecycleOwner){
            if (it!= "No data"){
                binding.or.visibility = View.VISIBLE
                binding.savedLoginButton.visibility = View.VISIBLE
                binding.savedLoginButton.text = "Login using $it"
            }
        }

        binding.savedLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragmentNew_to_contentData2)
        }

        binding.userLoginButton.setOnClickListener {
            val name = binding.userEmailLogin.text.toString()
            val password = binding.userPasswordLogin.text.toString()
            val checkSave = binding.rememberMeCheck.isChecked
            viewModel.getDetails(name, password, checkSave)
        }
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragmentNew_to_registrationFragmentNew)
        }

        viewModel.alreadyExists.observe(viewLifecycleOwner){
            if(it == false){
                binding.userEmailLogin.error = "Email not registered"
            }
        }

        viewModel.correctPassword.observe(viewLifecycleOwner){
            if(it == false){
                binding.userPasswordLogin.error = "Incorrect password"
            }
            else if(it == true){
                findNavController().navigate(R.id.action_loginFragmentNew_to_contentData2)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }


}