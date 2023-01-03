package com.learning.androidroadmap

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.learning.androidroadmap.databinding.FragmentFormBinding


class FormFragment : Fragment() {
    lateinit var binding: FragmentFormBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        binding.nextButton.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val uid = binding.uniqueId.text.toString() //only numeric
            val parentsName = binding.ParentsNameET.text.toString()
            val phoneNum = binding.PhoneNumEt.text.toString() //10 digits and num
            val postalCode = binding.postalCodeEt.text.toString() //6

            if(name.isEmpty()) {
                Toast.makeText(context,"Name field can't be empty",Toast.LENGTH_SHORT).show()
            }
            if(uid.isEmpty()) {
                Toast.makeText(context,"Uid field can't be empty",Toast.LENGTH_SHORT).show()
            }
            if(parentsName.isEmpty()) {
                Toast.makeText(context,"Parent's name field can't be empty",Toast.LENGTH_SHORT).show()
            }
            if(phoneNum.isEmpty() || phoneNum.length<10) {
                Toast.makeText(context,"Phone number field can't be empty",Toast.LENGTH_SHORT).show()
            }
            if(postalCode.isEmpty() || postalCode.length<6) {
                Toast.makeText(context,"Postal Code field can't be empty",Toast.LENGTH_SHORT).show()
            }
            else {
                val fields = Employee(name, uid, parentsName, phoneNum, postalCode)
                val jsonString = GsonBuilder().create().toJson(fields)
                writeNameIntoSharedPreferences(jsonString)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView, DetailsConfirmFragment())
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
        return binding.root
    }

    private fun writeNameIntoSharedPreferences(name: String) {
        val sharedPrefs = this.activity?.getSharedPreferences("confirm_details", Context.MODE_PRIVATE)
        val editor = sharedPrefs?.edit()
        editor?.apply {
            //use employee ID as key
            putString("confirmation",name) //encryption
            apply()
        }
    }
}