package com.learning.androidroadmap.navcomponent.fragments

import android.os.Binder
import android.os.Bundle
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
    lateinit var email : String
    lateinit var passWord : String
    lateinit var confirmPassWord : String
    lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding.registerButton.setOnClickListener {
            binding.apply {
                email = emailEditText.text.toString()
                passWord = passwordEditText.text.toString()
                confirmPassWord = confirmPasswordEditText.text.toString()
            }
            if(email.isNotEmpty() && email.contains("@", true) &&
                !email.startsWith("@") && !email.endsWith("@")){
                if(passWord.isNotEmpty()){
                    if(passWord == confirmPassWord){
                        if (!checkForAlreadyRegistered(email))
                        {
                            Toast.makeText(context,"Email already exists!",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context,"User registered!",Toast.LENGTH_SHORT).show()
                            saveDataToSharedPreferences(email, passWord)
                            Navigation.findNavController(it).navigate(R.id.action_registrationFragment_to_loginFragment)
                        }
                    }else{
                        if(confirmPassWord.isEmpty()){
                            binding.confirmPasswordEditText.error="Cant be empty"
                        }
                        else{
                            binding.confirmPasswordEditText.error="Passwords don't match"
                        }
                    }
                }
                else {
                    binding.passwordEditText.error ="Password cannot be empty"
                }
            }
            else {
                binding.emailEditText.error="Invalid email"
            }
        }
        return binding.root
    }

    private fun checkForAlreadyRegistered(email: String): Boolean {
        val data = getEncryptedPreferences(requireContext()).getString(email,null)
        return data.isNullOrEmpty()
    }

    private fun saveDataToSharedPreferences(email: String, passWord: String) {
        val sharedPrefs = getEncryptedPreferences(requireContext())
        sharedPrefs.edit().apply{
            putString(email,passWord)
        }
            .apply()
    }
}