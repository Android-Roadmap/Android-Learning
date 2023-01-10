package com.learning.androidroadmap.navcomponent.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.learning.androidroadmap.R
import com.learning.androidroadmap.databinding.FragmentLoginBinding
import com.learning.androidroadmap.navcomponent.getEncryptedPreferences


class LoginFragment : Fragment() {
    private lateinit var loginEmail: String
    private lateinit var loginPassword: String
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.userLoginButton.setOnClickListener {
            binding.apply {
                loginEmail = userEmailLogin.text.toString()
                loginPassword = userPasswordLogin.text.toString()
            }
            if (loginEmail.isNotEmpty()) {
                if (loginPassword.isNotEmpty()) {
                    if (checkCredentials(loginEmail, loginPassword)) {
                        Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(it)
                            .navigate(R.id.action_loginFragment_to_contentFragment)
                    } else {
                        Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    binding.userPasswordLogin.error = "Empty field not allowed"
                }

            } else {
                binding.userEmailLogin.error = "Invalid entry"
            }
        }
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        return binding.root
    }

    private fun checkCredentials(loginEmail: String, loginPassword: String): Boolean {
        val data = getEncryptedPreferences(requireContext()).all
        if (data.isNullOrEmpty()) {
            return false
        } else {
            for (keys in data.keys) {
                if (loginEmail == keys && loginPassword == data[keys]) {
                    return true
                }
            }
            return false
        }
    }
}