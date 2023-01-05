package com.learning.androidroadmap

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.GsonBuilder
import com.learning.androidroadmap.databinding.FragmentDetailsConfirmBinding


class DetailsConfirmFragment : Fragment() {
    private var empDetailsFromForm : Employee? = null
    private lateinit var binding: FragmentDetailsConfirmBinding
    private var employeeDetails = arrayListOf<String>()
    private lateinit var decryptedData : MutableMap<String, *>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsConfirmBinding.inflate(inflater,container,false)
        empDetailsFromForm = arguments?.getParcelable(GET_DATA)
        setFields()
        binding.saveButton.setOnClickListener {
            val sharedPreferences = getEncryptedSharedPreferences()
            empDetailsFromForm?.let {
                saveEmployee(it.uid, it, sharedPreferences )
                }
            convertToJson(decryptedData)
            val bundle = Bundle()
            bundle.putStringArrayList(EMP_LIST,employeeDetails)
            val homeFragment = HomeFragment()
            homeFragment.arguments = bundle
            activity?.supportFragmentManager?.popBackStack("FormFragment",1)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, homeFragment)
                ?.commit()
            Toast.makeText(context,"Details saved", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun convertToJson(hashMapData: MutableMap<String, *>) {
        for(keys in hashMapData.keys)
            employeeDetails.add(hashMapData[keys].toString())
    }

    private fun getEncryptedSharedPreferences(): SharedPreferences {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            EMP_DATA_FILE, masterKey,
            requireContext(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

     private fun saveEmployee(
         employeeId: String,
         employeeDetails: Employee,
         sharedPref: SharedPreferences
     ) {
         val sharedPrefHome = setEncryptedPreferences(requireContext())
        sharedPrefHome.edit().apply{
            putString(employeeId, GsonBuilder().create().toJson(employeeDetails))
        }
            .apply()
        decryptedData = sharedPref.all
    }

    private fun setFields() {
        binding.apply {
            nameTextView.text = empDetailsFromForm?.fullName
            ParentNameTextView.text = empDetailsFromForm?.parentName
            PhoneNumberTextView.text = empDetailsFromForm?.phoneNum
            PostalCodeTextView.text = empDetailsFromForm?.postalCode
            UidTextView.text = empDetailsFromForm?.uid
        }
    }
    companion object{
        const val GET_DATA = "employeeDetails"
        const val EMP_LIST = "employeeData"
        const val EMP_DATA_FILE = "employeeDetails"

    }
}