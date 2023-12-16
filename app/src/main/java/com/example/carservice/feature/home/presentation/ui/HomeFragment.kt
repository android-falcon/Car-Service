package com.example.carservice.feature.home.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.carservice.R
import com.example.carservice.core.constant.ConstantValues.IN_QUEUE_STATUS
import com.example.carservice.core.constant.ConstantValues.NEW_STATUS
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.databinding.FragmentHomeBinding
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.home.presentation.adapter.TicketAdapter
import com.example.carservice.feature.home.presentation.viewModel.HomeViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var ticketAdapterStatus0: TicketAdapter
    private lateinit var ticketAdapterStatus1: TicketAdapter
    private lateinit var binding: FragmentHomeBinding
    private var ticketListStatus0 = ArrayList<TicketResponse>()
    private var ticketListStatus1 = ArrayList<TicketResponse>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvMessage.visibility = View.GONE
        binding.rdbNew.isChecked = true

        binding.rvTicketsStatus1.visibility = View.GONE
        checkRbGroup()
        refreshLayout()
        searchingInList()
        onClickSummary()


    }

    private fun checkRbGroup() {
        if (binding.rdbNew.isChecked) {
            binding.rdbNew.isChecked = true
            binding.rdbProcess.isChecked = false
            binding.rvTicketsStatus1.visibility = View.GONE
            binding.rvTickets0.visibility = View.VISIBLE
            initObserverTicketStatus0()

        } else if (binding.rdbProcess.isChecked) {
            binding.rdbNew.isChecked = false
            binding.rdbProcess.isChecked = true
            binding.rvTicketsStatus1.visibility = View.VISIBLE
            binding.rvTickets0.visibility = View.GONE
            initObserverTicketStatus1()
        }
        binding.rdbNew.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.rdbNew.isChecked = true
                binding.rdbProcess.isChecked = false
                binding.rvTicketsStatus1.visibility = View.GONE
                binding.rvTickets0.visibility = View.VISIBLE

                Log.d("TAGcheckRbGroup", "checkRbGroup:${isChecked} ")
                initObserverTicketStatus0()
            }

        }
        binding.rdbProcess.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.rdbNew.isChecked = false
                binding.rdbProcess.isChecked = true
                binding.rvTicketsStatus1.visibility = View.VISIBLE
                binding.rvTickets0.visibility = View.GONE
                binding.rvTicketsStatus1.visibility = View.VISIBLE
                initObserverTicketStatus1()
                checkNewTicket()
            }

        }
    }

    private fun checkNewTicket() {
        if (viewModel.ticketListLiveData.value?.size != ticketListStatus0.size) {
            binding.ivNoti.visibility = View.VISIBLE
        } else {
            binding.ivNoti.visibility = View.GONE

        }

    }


    private fun initObserverTicketStatus1() {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getTicketsByStatus(IN_QUEUE_STATUS, TechnicalData.COMPANY_NUMBER)
                viewModel.ticketListLiveData.observe(viewLifecycleOwner) { tickets ->
                    // Update the UI with the list of tickets
                    tickets?.let {
                        initAdapterStatus1(it)
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it) binding.brGif.visibility = View.VISIBLE
                    else binding.brGif.visibility = View.GONE

                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel._errorLiveData.observe(viewLifecycleOwner) { error ->
                    // Handle the error
                    if (error != null) {
                        binding.tvMessage.visibility = View.VISIBLE
                        Log.d("TAGStatus2", "Error:${error} ")
                        viewModel._errorLiveData.value = null

                    }
                }
            }
        } catch (e: Exception) {
            binding.tvMessage.visibility = View.VISIBLE

        }
    }

    private fun onClickSummary() {
        binding.btnSummary.setOnClickListener {
            findNavController().navigate(R.id.summary_ticket)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun searchingInList() {
        binding.etSearch.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        binding.etSearch.setOnCloseListener {
            binding.rvTickets0.adapter = ticketAdapterStatus0
            ticketAdapterStatus0.notifyDataSetChanged()
            false
        }

    }


    private fun filterList(newText: String?) {
        if (newText != null) {
            val filterList = ArrayList<TicketResponse>()
            for (i in ticketListStatus0) {
                if (i.carColor.toLowerCase().contains(newText) || i.carType.toLowerCase()
                        .contains(newText) || i.carModel.toLowerCase()
                        .contains(newText) || i.customerName.toLowerCase().contains(newText) ||
                    i.carId.toLowerCase().contains(newText)
                ) {
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()

            } else {
                ticketAdapterStatus0.setFilteredList(filterList)
            }

        } else {
            binding.rvTickets0.adapter = ticketAdapterStatus0
        }
    }


    private fun refreshLayout() {
        binding.rfLayout.setOnRefreshListener {
            if (binding.rdbNew.isChecked) {
                initObserverTicketStatus0()
                binding.ivNoti.visibility = View.GONE

            } else if (binding.rdbProcess.isChecked) {
                initObserverTicketStatus1()
            }
            binding.rfLayout.isRefreshing = false
        }
    }

    @SuppressLint("LongLogTag")
    private fun initObserverTicketStatus0() {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getTicketsByStatus(NEW_STATUS, TechnicalData.COMPANY_NUMBER)
                viewModel.ticketListLiveData.observe(viewLifecycleOwner) { tickets ->
                    tickets?.let {
                        initAdapterStatus0(it)
                        Log.d("TAGinitObserverTicketStatus0", "initObserverTicketStatus0:${it} ")
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it) binding.brGif.visibility = View.VISIBLE
                    else binding.brGif.visibility = View.GONE

                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel._errorLiveData.observe(viewLifecycleOwner) { error ->
                    // Handle the error
                    if (error != null) {
                        binding.rvTickets0.visibility = View.GONE
                        binding.tvMessage.visibility = View.VISIBLE
                        viewModel._errorLiveData.value = null

                    }
                }
            }
        } catch (e: Exception) {
            binding.tvMessage.visibility = View.VISIBLE

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapterStatus1(list: List<TicketResponse>) {
        ticketListStatus1.clear()
        ticketListStatus1.addAll(list)
        ticketAdapterStatus1 = TicketAdapter(ticketListStatus1, ::onClickTicket)
        ticketAdapterStatus1.notifyDataSetChanged()
        binding.rvTicketsStatus1.adapter = ticketAdapterStatus1
        Log.d("TAGsetAdapterData", "setAdapterData:${list}")

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapterStatus0(list: List<TicketResponse>) {

        ticketListStatus0.clear()
        ticketListStatus0.addAll(list)
        ticketAdapterStatus0 = TicketAdapter(ticketListStatus0, ::onClickTicket)
        ticketAdapterStatus0.notifyDataSetChanged()
        binding.rvTickets0.adapter = ticketAdapterStatus0
        Log.d("TAGsetAdapterData", "setAdapterData:${list}")

    }

    private fun onClickTicket(ticketResponse: TicketResponse) {
        val bundle = saveDataToBundle(ticketResponse)

        findNavController().navigate(R.id.detailsFragment, bundle)
    }

    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }

    private fun saveDataToBundle(ticketResponse: TicketResponse): Bundle {
        val bundle = Bundle()
        val carColor = ticketResponse.carColor
        val carId = ticketResponse.carId
        val carType = ticketResponse.carType
        val personName = ticketResponse.customerName
        val serviceNumber = ticketResponse.serviceNumber
        val phoneNumber = ticketResponse.phoneNumber
        val teamNumber = ticketResponse.teamNumber
        val voucherNumber = ticketResponse.voucherNumber
        val status = ticketResponse.status
        val insDate = ticketResponse.insDate
        val insTime = ticketResponse.insTime
        val startDate = ticketResponse.startDate
        val startTime = ticketResponse.startTime
        val endDate = ticketResponse.endDate
        val endTime = ticketResponse.endTime
        val customerType = ticketResponse.customerType
        val carImg = ticketResponse.carModel
        val phase = ticketResponse.phase
        val time = ticketResponse.time
        val timePhase1 = ticketResponse.timePhase1
        val timePhase2 = ticketResponse.timePhase2
        val timePhase3 = ticketResponse.timePhase2

        bundle.putString("carColor", carColor)
        bundle.putString("carId", carId)
        bundle.putString("carType", carType)
        bundle.putString("carType", carType)
        bundle.putString("customerName", personName)
        bundle.putString("serviceNumber", serviceNumber)
        bundle.putString("phoneNumber", phoneNumber)
        bundle.putString("teamNumber", teamNumber)
        bundle.putString("voucherNumber", voucherNumber)
        bundle.putString("status", status)
        bundle.putString("insDate", insDate)
        bundle.putString("insTime", insTime)
        bundle.putString("startDate", startDate)
        bundle.putString("startTime", startTime)
        bundle.putString("endDate", endDate)
        bundle.putString("endTime", endTime)
        bundle.putString("endTime", endTime)
        bundle.putString("customerType", customerType)
        bundle.putString("carImg", carImg)
        bundle.putString("phase", phase)
        bundle.putString("time", time)
        if (timePhase1.equals("")) {
            bundle.putString("timePhase1", "")
        } else {
            bundle.putString("timePhase1", timePhase1)
        }
        if (timePhase2.equals("")) {
            bundle.putString("timePhase2", "")

        } else {
            bundle.putString("timePhase2", timePhase2)
        }
        if (timePhase3.equals("")) {
            bundle.putString("timePhase2", "")

        } else {
            bundle.putString("timePhase2", timePhase3)

        }
        return bundle
    }

}