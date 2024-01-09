package com.example.carservice.feature.addTicket.presintation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.carservice.R
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.databinding.FragmentCheckPhoneNumberBinding
import com.example.carservice.feature.addTicket.domain.model.CarResponse
import com.example.carservice.feature.addTicket.presintation.adapter.CarAdapter
import com.example.carservice.feature.addTicket.presintation.viewModel.AddTicketViewModel
import kotlinx.coroutines.launch


class CheckPhoneNumberFragment : Fragment() {
    private lateinit var binding: FragmentCheckPhoneNumberBinding
    private val viewModel: AddTicketViewModel by viewModels()
    private val carList: ArrayList<CarResponse> = ArrayList()
    private lateinit var adapter: CarAdapter
    private var phoneNumber: String = ""
    private val bundle = Bundle()
    private var key = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckPhoneNumberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(arguments)
        initObserver()

    }

    private fun initObserver() {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getCustomerByPhoneNumber(TechnicalData.COMPANY_NUMBER, phoneNumber)
                viewModel.carListLiveData.observe(viewLifecycleOwner) {
                    if (it != null) {
                        initAdapter(it)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                        if (it) binding.brGif.visibility = View.VISIBLE
                        else binding.brGif.visibility = View.GONE
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.errorLiveData.observe(viewLifecycleOwner) {
                            if (it != null) {
                                showErrorSweet(it)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            showErrorSweet(e.localizedMessage)
        }

    }

    private fun getData(arguments: Bundle?) {
        val bundle = arguments
        if (bundle != null) {
            phoneNumber = bundle.getString("phoneNumber", "")}

    }
    private fun initAdapter(carResponses: List<CarResponse>) {
        carList.addAll(carResponses)
        adapter = CarAdapter(carList, ::goToAddTicketWithData)
        binding.rvTickets.adapter = adapter
        adapter.notifyDataSetChanged()
        binding.tvLastVisit.text = "Last Visit: ${carResponses.get(0).lastVisitDate}"
        binding.tvNumVisit.text = "Number of Visit: ${carResponses.get(0).numberOfVisit}"

    }
    private fun goToAddTicketWithData(carResponse: CarResponse) {
        bundle.putParcelable("carResponse", carResponse)

        Log.d("TAGetDataFromBundle", "goToAddTicketWithData:$carResponse ")
        val addTicketFragment = AddTicketFragment()
        addTicketFragment.arguments = bundle
        findNavController().navigate(R.id.addTicketFromPhoneNumberFragment, bundle)

    }
    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }


}