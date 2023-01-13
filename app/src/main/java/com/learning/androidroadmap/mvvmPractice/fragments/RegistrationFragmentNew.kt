package com.learning.androidroadmap.mvvmPractice.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.learning.androidroadmap.R
import com.learning.androidroadmap.databinding.FragmentRegistrationBinding
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
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        binding.apply {
            userEmailSignUp.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //No-op
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.checkEmail(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                    //No-op
                }
            })
        }

        viewModel.emailValidChecker.observe(viewLifecycleOwner){ isValid ->
            binding.userEmailSignUp.apply {
                error = if(!isValid){
                    getString(R.string.email_not_valid)
                }else{
                    null
                }
            }
        }
    }
}