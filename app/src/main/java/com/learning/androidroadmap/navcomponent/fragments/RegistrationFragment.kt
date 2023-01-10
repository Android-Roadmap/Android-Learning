package com.learning.androidroadmap.navcomponent.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.learning.androidroadmap.R
import com.learning.androidroadmap.databinding.FragmentRegistrationBinding
import com.learning.androidroadmap.navcomponent.getEncryptedPreferences


class RegistrationFragment : Fragment() {
    lateinit var email: String
    lateinit var passWord: String
    lateinit var confirmPassWord: String
    lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding.userRegisterButton.setOnClickListener {
            binding.apply {
                email = userEmailSignUp.text.toString()
                passWord = userPasswordSignUp.text.toString()
                confirmPassWord = userConfirmPassword.text.toString()
            }
            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (passWord.isNotEmpty()) {
                    if (passwordValidation(passWord)) {
                        if (passWord == confirmPassWord) {
                            if (!checkForAlreadyRegistered(email)) {
                                Toast.makeText(context, "Email already exists!", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context, "User registered!", Toast.LENGTH_SHORT)
                                    .show()
                                saveDataToSharedPreferences(email, passWord)
                                Navigation.findNavController(it)
                                    .navigate(R.id.action_registrationFragment_to_loginFragment)
                            }
                        } else {
                            binding.userConfirmPassword.error = "Passwords don't match"
                        }
                    } else {
                        if (confirmPassWord.isEmpty()) {
                            binding.userConfirmPassword.error = "Cant be empty"
                        }
                    }
                } else {
                    binding.userPasswordSignUp.error = "Password cannot be empty"
                }
            } else {
                binding.userEmailSignUp.error = "Invalid email"
            }
        }
        return binding.root
    }

    private fun passwordValidation(passWord: String): Boolean {
        var lowerCaseAlphabetCheck = false
        var upperCaseAlphabetCheck = false
        var numberCheck = false
        var specialCharacterCheck = false
        if (passWord.length >= 8) {
            for (ch in passWord) {
                if ("!@#$%&*?".contains(ch, false)) {
                    specialCharacterCheck = true
                }
                if ("abcdefghijklmnopqrstuvwxyz".contains(ch, false)) {
                    lowerCaseAlphabetCheck = true
                }
                if ("1234567890".contains(ch, false)) {
                    numberCheck = true
                }
                if ("abcdefghijklmnopqrstuvwxyz".uppercase().contains(ch, false)) {
                    upperCaseAlphabetCheck = true
                }
            }
            if (specialCharacterCheck) {
                if (lowerCaseAlphabetCheck) {
                    if (upperCaseAlphabetCheck) {
                        if (numberCheck) {
                            return true
                        } else {
                            binding.userPasswordSignUp.error =
                                "Password must contain atleast one number"
                        }
                    } else {
                        binding.userPasswordSignUp.error =
                            "Password must contain atleast one Uppercase alphabet"
                    }
                } else {
                    binding.userPasswordSignUp.error =
                        "Password must contain atleast one Lowercase alphabet"
                }
            } else {
                binding.userPasswordSignUp.error = "Password must contain one special character"
            }
        } else {
            binding.userPasswordSignUp.error = "Passowrd length is less than 8"
        }
        return false
    }

    private fun checkForAlreadyRegistered(email: String): Boolean {
        val data = getEncryptedPreferences(requireContext()).getString(email, null)
        return data.isNullOrEmpty()
    }

    private fun saveDataToSharedPreferences(email: String, passWord: String) {
        val sharedPrefs = getEncryptedPreferences(requireContext())
        sharedPrefs.edit().apply {
            putString(email, passWord)
        }
            .apply()
    }
}