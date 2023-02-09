package com.learning.androidroadmap.mvvmPractice.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.learning.androidroadmap.R
import com.learning.androidroadmap.databinding.FragmentRegistrationBinding
import com.learning.androidroadmap.mvvmPractice.getViewModelFactory
import com.learning.androidroadmap.mvvmPractice.viewModels.RegistrationViewModel

class RegistrationFragmentNew : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, getViewModelFactory { RegistrationViewModel() })[RegistrationViewModel::class.java]
        binding.apply {
            userEmailSignUp.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //No-op
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.checkEmailIsValid(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                    //No-op
                }
            })

            userPasswordSignUp.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //nothing
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.checkPasswordIsValid(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                    //nothing
                }
            })

            userConfirmPassword.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //nothing
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.checkPasswordMatches(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                    //nothing
                }
            })
        }

        viewModel.emailValidChecker.observe(viewLifecycleOwner){ isValid ->
            binding.userEmailSignUp.apply {
                error = if(isValid.equals("Invalid")){
                    getString(R.string.email_not_valid)
                }else{
                    null
                }
            }
            if(isValid.equals("Empty")){
                binding.requiredMessageEmail.text = getString(R.string.empty)
            }
            if(isValid.equals("Exists")){
                binding.requiredMessageEmail.text = getString(R.string.exists)
            }
        }

        viewModel.passwordChecker.observe(viewLifecycleOwner){isValid ->
            binding.userPasswordSignUp.apply {
                error = if(isValid.equals("Invalid")){
                    getString(R.string.password_not_valid)}
                else{
                    null
                }
            }
            if(isValid.equals("Empty")){
                binding.requiredMessagePassword.text = getString(R.string.empty)
            }
        }

        viewModel.confirmPasswordChecker.observe(viewLifecycleOwner){ isValid ->
            if (isValid == "Invalid"){
                binding.userConfirmPassword.error = "Passwords are not matching"
            }
            if(isValid.equals("Empty")){
                binding.requiredMessageConfirmPassword.text = getString(R.string.empty)
            }
        }

        binding.userRegisterButton.setOnClickListener {
            viewModel.checkForEmpty(binding.userEmailSignUp.text.toString(),
                binding.userPasswordSignUp.text.toString(), binding.userConfirmPassword.text.toString() )
        }

        viewModel.switchFragment.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigate(R.id.action_registrationFragmentNew_to_loginFragmentNew)
            }
        }
    }
}