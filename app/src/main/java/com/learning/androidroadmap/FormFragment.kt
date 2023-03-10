package com.learning.androidroadmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.learning.androidroadmap.databinding.FragmentFormBinding

class FormFragment : Fragment() {
    private lateinit var name : String
    private lateinit var uid : String
    private lateinit var parentsName : String
    private lateinit var phoneNum : String
    private lateinit var postalCode : String
    private lateinit var binding: FragmentFormBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        binding.nextButton.setOnClickListener {
            binding.apply {
                name = nameEt.text.toString()
                uid = binding.uniqueId.text.toString()
                parentsName = binding.ParentsNameET.text.toString()
                phoneNum = binding.PhoneNumEt.text.toString()
                postalCode = binding.postalCodeEt.text.toString()
            }
            if(name.isEmpty()) {
                binding.nameEt.error=getString(R.string.warning_message_name_field)

            }
            else if(uid.isEmpty()) {
                binding.uniqueId.error=getString(R.string.warning_message_uid_field)
            }
            else if(parentsName.isEmpty()) {
                binding.ParentsNameET.error=getString(R.string.warning_message_parents_name_field)
            }
            else if(phoneNum.isEmpty() || phoneNum.length<10) {
                binding.PhoneNumEt.error=getString(R.string.warning_message_phone_number_field)
            }
            else if(postalCode.isEmpty() || postalCode.length<6) {
                binding.postalCodeEt.error=getString(R.string.warning_message_postal_code_field)
            }
            else {
                val fields = Employee(name, uid, parentsName, phoneNum, postalCode)
                val bundle = Bundle()
                bundle.putParcelable(SEND_DATA,fields)
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                val detailsConfirmFragment = DetailsConfirmFragment()
                detailsConfirmFragment.arguments = bundle
                transaction?.replace(R.id.fragmentContainerView, detailsConfirmFragment)
                transaction?.addToBackStack(FRAGMENT_NAME)
                transaction?.commit()
            }
        }
        return binding.root
    }
    companion object {
        const val FRAGMENT_NAME = "FormFragment"
        const val SEND_DATA = "employeeDetails"
    }
}