package com.example.carservice.feature.test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.carservice.R
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.databinding.FragmentTestBinding
import com.example.carservice.feature.services.domain.model.ServiceBodyModel
import com.example.carservice.feature.services.domain.model.ServiceDtlModel
import kotlinx.coroutines.launch
import java.net.URLEncoder

class TestFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding
    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() {
        binding.btn.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val list = ArrayList<ServiceBodyModel>()
        val servicesJson = "{\"DTL\":[{\"ITEMCODE\":\"10006\",\"ITEMNAME\":\"Wash\"}]}"
        Log.d("TAGEncodedServicesJson", "saveData:${servicesJson} ")

        try {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.saveTicket(
                    cono = TechnicalData.COMPANY_NUMBER,
                    note = "ss",
                    howMany = "1",
                    carColor = "red",
                    phoneNumber = "0468218",
                    customerType = "0",
                    customerName = "hhh",
                    carId = "12-88881",
                    carType = "Mazda",
                    userNumber = "1000",
                    service = servicesJson,
                    carModel = "1"
                )
                viewModel.ticketListLiveData.observe(viewLifecycleOwner) { data ->
                    if (data != null) {
                        Log.d("TAGFragment", "${data}")

                    }
                }
                viewModel.errorLiveData.observe(viewLifecycleOwner) {
                    Log.d("TAGFragment", "on Error live data ${it}")
                }
            }
        } catch (e: Exception) {
            Log.d("TAGFragment", "on Error catch ${e.message}")

        }
    }


}