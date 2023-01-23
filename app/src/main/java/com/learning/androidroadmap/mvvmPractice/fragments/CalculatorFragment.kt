package com.learning.androidroadmap.mvvmPractice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.androidroadmap.databinding.FragmentCalculatorBinding
import com.learning.androidroadmap.mvvmPractice.applicationclass.ComponentMvvm


class CalculatorFragment : Fragment() {
    var result = 0.0
    private var componentMvvm = ComponentMvvm()
    lateinit var binding: FragmentCalculatorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculatorBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addButton.setOnClickListener {
                val num1 = binding.numberOneEditText.text.toString()
                val num2 = binding.numberTwoEditText.text.toString()
                if (num1.isNotEmpty() && num2.isNotEmpty()){
                result = componentMvvm.calculator.add(num1.toDouble(), num2.toDouble())
                resultTextView.text = result.toString()
            }}
            subtractButton.setOnClickListener {
                val num1 = binding.numberOneEditText.text.toString()
                val num2 = binding.numberTwoEditText.text.toString()
                if (num1.isNotEmpty() && num2.isNotEmpty()){
                    result = componentMvvm.calculator.subtract(num1.toDouble(), num2.toDouble())
                    resultTextView.text = result.toString()
                }
            }
        }
    }
}