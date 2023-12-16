package com.example.carservice.feature.techincalSetting.presintation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.databinding.FragmentTechnicalSetttingBinding


class TechnicalSettingFragment : Fragment() {
    private lateinit var binding: FragmentTechnicalSetttingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTechnicalSetttingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        onClickSave()
    }

    private fun getData() {
        if (TechnicalData.COMPANY_NUMBER.isNotEmpty() || TechnicalData.BASE_URL.isNotEmpty() || TechnicalData.COMPANY_NAME.isNotEmpty()) {
            binding.etUrl.setText(TechnicalData.BASE_URL)
            binding.etCompanyName.setText(TechnicalData.COMPANY_NAME)
            binding.etCompanyNumber.setText(TechnicalData.COMPANY_NUMBER)
            binding.etDll.setText(TechnicalData.DLL_NAME)
        }
    }

    private fun onClickSave() {
        binding.btnSave.setOnClickListener {
            if (binding.etUrl.text.isNotEmpty() && binding.etCompanyName.text.isNotEmpty() && binding.etCompanyNumber.text.isNotEmpty()) {
                TechnicalData.BASE_URL = binding.etUrl.text.toString()
                TechnicalData.COMPANY_NAME = binding.etCompanyName.text.toString()
                TechnicalData.COMPANY_NUMBER = binding.etCompanyNumber.text.toString()
                TechnicalData.DLL_NAME = binding.etDll.text.toString()
                TechnicalData.saveData(requireContext())
                findNavController().navigateUp()
            }
        }
    }


}