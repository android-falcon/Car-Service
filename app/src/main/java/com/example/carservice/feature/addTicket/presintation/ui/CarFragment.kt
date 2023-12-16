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
import com.example.carservice.databinding.FragmentCarBinding
import com.example.carservice.feature.addTicket.domain.model.CarResponse
import com.example.carservice.feature.addTicket.presintation.adapter.CarAdapter
import com.example.carservice.feature.addTicket.presintation.viewModel.AddTicketViewModel
import kotlinx.coroutines.launch

class CarFragment : Fragment() {
    private lateinit var binding: FragmentCarBinding
    private val viewModel: AddTicketViewModel by viewModels()
    private val carList: ArrayList<CarResponse> = ArrayList()
    private lateinit var adapter: CarAdapter
    private var phoneNumber: String = ""
    private var plateNumber: String = ""
    private val bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(arguments)
        initObserver()


    }

    private fun getData(arguments: Bundle?) {
        val bundle = arguments
        if (bundle != null) {
            phoneNumber = bundle.getString("phoneNumber", "")
            plateNumber = bundle.getString("plateNumber", "")

        }

    }

    private fun initObserver() {
        if (!plateNumber.equals("")) {

            try {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getCustomerByPlateNumber(TechnicalData.COMPANY_NUMBER, plateNumber)
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

        } else if (!phoneNumber.equals("")) {

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

    }

    private fun initAdapter(carResponses: List<CarResponse>) {
        carList.addAll(carResponses)
        adapter = CarAdapter(carList, ::goToAddTicketWithData)
        binding.rvTickets.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun goToAddTicketWithData(carResponse: CarResponse) {
        bundle.putParcelable("carResponse",carResponse)
        bundle.putString("key","1")
        Log.d("TAGetDataFromBundle", "goToAddTicketWithData:$carResponse ")
        val addTicketFragment=AddTicketFragment()
        addTicketFragment.arguments=bundle
        findNavController().navigate(R.id.action_carFragment_to_addTicketFragment2, bundle)

    }

    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }

}