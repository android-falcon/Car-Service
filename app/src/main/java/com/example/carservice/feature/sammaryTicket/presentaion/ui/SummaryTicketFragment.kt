package com.example.carservice.feature.sammaryTicket.presentaion.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.carservice.R
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.databinding.FragmentSummaryTicketBinding
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.home.presentation.adapter.TicketAdapter
import com.example.carservice.feature.sammaryTicket.presentaion.viewModel.SummaryTicketViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class SummaryTicketFragment : Fragment() {
    private val viewModel: SummaryTicketViewModel by viewModels()
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var binding: FragmentSummaryTicketBinding
    private lateinit var CURRENT_DATE: String
    private var ticketList = ArrayList<TicketResponse>()
    private lateinit var selectedDate: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSummaryTicketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDateToView()
        initObserver(selectedDate)
        onClickDatePicker()
        searchingInList()
    }


    private fun initObserver(date: String) {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getTicketsByDate(date, TechnicalData.COMPANY_NUMBER)
                viewModel.ticketListLiveData.observe(viewLifecycleOwner) { tickets ->
                    tickets?.let {
                        initAdapter(it)
                        Log.d("initObserverDate", "initObserver:${it} ")
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it) {
                        binding.brGif.visibility = View.VISIBLE
                    } else {
                        binding.brGif.visibility = View.GONE
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.errorLiveData.observe(viewLifecycleOwner) {
                    if (viewModel.errorLiveData.value != null) {
                        showErrorSweet(it)
                        viewModel.errorLiveData.value = null
                    }
                }
            }

        } catch (e: Exception) {
            showErrorSweet("No Ticket in this date")
        }
    }

    private fun onClickDatePicker() {
        binding.btnShow.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setDateToView() {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var date: String = dateFormat.format(currentDate)
        binding.etDate.setText(date)
        selectedDate = date
    }


    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var date: String = ""
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDateCalendar = Calendar.getInstance()
                selectedDateCalendar.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDateCalendar.time)
                date = formattedDate
                binding.etDate.setText(date)
                initObserver(date)


            },
            year,
            month,
            day
        )

        datePickerDialog.show()
        selectedDate = date

    }

    private fun getNewTicketByDate(selectedDate: String) {
        try {
            GlobalScope.launch {
                viewModel.getTicketsByDate(selectedDate, TechnicalData.COMPANY_NUMBER)
                viewModel.ticketListLiveData.observe(viewLifecycleOwner) { tickets ->
                    tickets?.let {
                        initAdapter(it)
                        Log.d("getNewTicketByDate", "getNewTicketByDate:$it ")
                    }
                }
            }
            GlobalScope.launch {
                viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it) {
                        binding.brGif.visibility = View.VISIBLE
                    } else {
                        binding.brGif.visibility = View.GONE
                    }
                }
            }

            GlobalScope.launch {
                viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
                    // Handle the error
                    error?.let {
                        showErrorSweet("Error no ticket at this date")

                    }
                    viewModel.errorLiveData.value = null
                }
            }
        } catch (e: Exception) {
            showErrorSweet("No Ticket in this date")
        }

    }


    private fun initAdapter(list: List<TicketResponse>) {
        ticketList.clear()
        for (item in list) {
            if (item.status.equals("2")) {
                ticketList.add(item)
            }
        }
        ticketAdapter = TicketAdapter(ticketList, ::OnClick)
        ticketAdapter.notifyDataSetChanged()
        binding.rvTickets.adapter = ticketAdapter
        Log.d("TAGsetAdapterData", "setAdapterData:${list}")

    }


    private fun OnClick(ticketResponse: TicketResponse) {
        val bundle = Bundle()
        bundle.apply {
            bundle.putString("voucherNumber", ticketResponse.voucherNumber)
            bundle.putString("phoneNumber", ticketResponse.phoneNumber)
            bundle.putString("carId", ticketResponse.carId)
            bundle.putString("carType", ticketResponse.carType)
            bundle.putString("carColor", ticketResponse.carColor)
            bundle.putString("customerType", ticketResponse.customerType)
            bundle.putString("endTime", ticketResponse.endTime)
            bundle.putString("insTime", ticketResponse.insTime)
            bundle.putString("startTime", ticketResponse.startTime)
            bundle.putString("customerName", ticketResponse.customerName)
            bundle.putString("status",ticketResponse.status)
            bundle.putString("timePhase1",ticketResponse.timePhase1ForCV)
            bundle.putString("timePhase2",ticketResponse.timePhase2ForCV)
            bundle.putString("timePhase3",ticketResponse.timePhase3ForCV)
            bundle.putString("carImg",ticketResponse.carModel)
        }
        findNavController().navigate(R.id.cardSummaryFragment, bundle)
    }


    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }
    @SuppressLint("NotifyDataSetChanged")
    private fun searchingInList() {
        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        binding.etSearch.setOnCloseListener {
            binding.rvTickets.adapter = ticketAdapter
            ticketAdapter.notifyDataSetChanged()
            false
        }

    }

    private fun filterList(newText: String?) {
        if (newText != null) {
            val filterList = ArrayList<TicketResponse>()
            for (i in ticketList ) {
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
                ticketAdapter.setFilteredList(filterList)
            }

        }
    }

}