package com.learning.androidroadmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.learning.androidroadmap.databinding.FragmentFormBinding

class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding
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
                binding.nameEt.error="Name field can't be empty"
            }
            else if(uid.isEmpty()) {
                binding.uniqueId.error="Uid field can't be empty"
            }
            else if(parentsName.isEmpty()) {
                binding.ParentsNameET.error="Parent's name field can't be empty"
            }
            else if(postalCode.isEmpty() || postalCode.length<6) {
                binding.postalCodeEt.error="Postal code field can't be empty"
            }
            else if(phoneNum.isEmpty() || phoneNum.length<10) {
                binding.PhoneNumEt.error="Phone no. can't be empty"
            }
            else {
                val fields = Employee(name, uid, parentsName, phoneNum, postalCode)
                val bundle = Bundle()
                bundle.putParcelable("employeeDetails",fields)
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                val detailsConfirmFragment = DetailsConfirmFragment()
                detailsConfirmFragment.arguments = bundle
                transaction?.replace(R.id.fragmentContainerView, detailsConfirmFragment)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
        }
        return binding.root
    }
}