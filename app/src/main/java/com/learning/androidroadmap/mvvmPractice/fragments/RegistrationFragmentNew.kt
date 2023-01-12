package com.learning.androidroadmap.mvvmPractice.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.learning.androidroadmap.databinding.FragmentRegistrationBinding
import com.learning.androidroadmap.mvvmPractice.viewModels.ViewModelRegistrationFragment


class RegistrationFragmentNew : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding.userRegisterButton.setOnClickListener {
            val viewModel = ViewModelProvider(this)[ViewModelRegistrationFragment::class.java]
            binding.userRegisterButton.setOnClickListener {
                viewModel.setContext(requireContext())
                val email = binding.userEmailSignUp.text.toString()
                val password = binding.userPasswordSignUp.text.toString()
                val confirmPassword = binding.userConfirmPassword.text.toString()
                viewModel.getCredentials(email, password, confirmPassword)
                viewModel._emailAlreadyExists.observe(viewLifecycleOwner){
                    Toast.makeText(context,"Email already exists", Toast.LENGTH_SHORT).show()
                }
                viewModel.CanInsert.observe(viewLifecycleOwner){
                    Toast.makeText(context,"Successfully Registered!", Toast.LENGTH_SHORT).show()
                }
            }
            viewModel._passwordLengthCheck.observe(viewLifecycleOwner){
                binding.userPasswordSignUp.error = "Password length can't be less than 8"
            }
            viewModel._passwordNumberCheck.observe(viewLifecycleOwner){
                binding.userPasswordSignUp.error = "Password must contain atleast one number"
            }
            viewModel._passwordLowerCaseCheck.observe(viewLifecycleOwner){
                binding.userPasswordSignUp.error = "Password must contain atleast one lower-case character"
            }
            viewModel._passwordUpperCaseCheck.observe(viewLifecycleOwner){
                binding.userPasswordSignUp.error = "Password must contain atleast one upper-case character"
            }
            viewModel._passwordSpecialCharacterCheck.observe(viewLifecycleOwner){
                binding.userPasswordSignUp.error = "Password must contain atleast one special character"
            }
            viewModel._passwordMatchCheck.observe(viewLifecycleOwner){
                binding.userPasswordSignUp.error = "Passwords don't match"
                binding.userConfirmPassword.error = "Passwords don't match"
            }
            viewModel._passwordEmptyCheck.observe(viewLifecycleOwner){
                binding.userPasswordSignUp.error = "Password can't be empty"
            }
            viewModel._emailValidChecker.observe(viewLifecycleOwner){
                binding.userEmailSignUp.error = "Invalid email"
            }

            binding.apply {
                userEmailSignUp.addTextChangedListener(object: TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        TODO("Not yet implemented")
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                       viewModel.checkEmail(p0.toString())
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
        return binding.root
    }
}