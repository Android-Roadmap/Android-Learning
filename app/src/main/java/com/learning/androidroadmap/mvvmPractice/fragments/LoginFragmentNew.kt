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
import com.learning.androidroadmap.mvvmPractice.viewModels.LoginViewModel

class LoginFragmentNew : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.userLoginButton.setOnClickListener {
//            val name = binding.userEmailLogin.text.toString()
//            val password = binding.userPasswordLogin.text.toString()
//            viewModel.getDetails(name, password)
        }
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragmentNew_to_registrationFragmentNew)
        }
        return binding.root
    }
}