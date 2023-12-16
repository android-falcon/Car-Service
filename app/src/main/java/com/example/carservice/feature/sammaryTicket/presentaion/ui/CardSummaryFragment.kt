package com.example.carservice.feature.sammaryTicket.presentaion.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.carservice.R
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.core.constant.carImg.CarGenerate
import com.example.carservice.databinding.FragmentCardSammaryBinding
import com.example.carservice.feature.detail.domain.model.ServiceResponse
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.sammaryTicket.domain.model.EmployeeResponse
import com.example.carservice.feature.sammaryTicket.presentaion.viewModel.DetailsTicketViewModel
import kotlinx.coroutines.launch

class CardSummaryFragment : Fragment() {
    private lateinit var binding: FragmentCardSammaryBinding
    private var voucherNumber = ""
    private lateinit var ticket: TicketResponse
    private val viewModel: DetailsTicketViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentCardSammaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(arguments)
        initObserverService(voucherNumber)
        initObserverEmployeeList(voucherNumber)
        showData()
    }

    private fun showData() {
        if (ticket != null) {
            binding.tvColorCv.setText(ticket.carColor)
            binding.tvPlateNumber.setText(ticket.carId)
            binding.tvNameCv.setText(ticket.customerName)
            binding.tvModelCv.setText(ticket.carType)
            binding.tvEndTime.setText(ticket.endTimeForCV())
            binding.tvInTime.setText(ticket.insTimeForCV())
            binding.tvStartTime.setText(ticket.startTimeForCV())
            binding.tvTimePhase1.text = ticket.startTime
            binding.tvTimePhase2.text = ticket.timePhase2ForCV
            binding.tvTimePhase3.text = ticket.timePhase3ForCV
            binding.tvEntryTimeCv.text = ticket.insTime
            Log.d(
                "TAGShowDataIn",
                "showData:phase1 ${ticket.startTime} phase2 ${ticket.timePhase2} phase3 ${ticket.timePhase3} "
            )

            if (ticket.carModel.equals("-1")) {
                binding.ivCarModel.setImageResource(R.drawable.img_car)
            } else {
                val img = CarGenerate.carList().get(ticket.carModel.toInt()).img
                binding.ivCarModel.setImageResource(img)
            }
            Log.d("TAGshowData", "showData:$ticket ")
        }
    }

    @SuppressLint("LongLogTag")
    private fun initObserverEmployeeList(voucherNumber: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getEmployee(
                TechnicalData.COMPANY_NUMBER, voucherNumber
            )
            viewModel.teamListLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    createPhasesTime(it)
                }
                Log.d("initObserverEmployeeList", "initObserverEmployeeList:$it ")
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    showErrorSweet("this ticket was canceled ")
                    viewModel.errorLiveData.value = ""
                }
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if (it) binding.brGif.visibility = View.VISIBLE
            else binding.brGif.visibility = View.GONE
        }
    }

    private fun createPhasesTime(employeeResponseList: List<EmployeeResponse>) {
        var phase1 = binding.tvPhase1Co.text.toString()
        var phase2 = binding.tvPhase2Co.text.toString()
        var phase3 = binding.tvPhase3Co.text.toString()
        for (item in employeeResponseList) {
            if (item.phase.equals("1")) {
                phase1 += "\n${item.name}"
            } else if (item.phase.equals("2")) {
                phase2 += "\n${item.name}"
            } else if (item.phase.equals("3")) {
                phase3 += "\n${item.name}"
            }
        }
        binding.tvPhase1Co.text = phase1
        binding.tvPhase2Co.text = phase2
        binding.tvPhase3Co.text = phase3

    }

    @SuppressLint("LongLogTag")
    private fun initObserverService(voucherNumber: String) {
        binding.brGif.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getService(
                TechnicalData.COMPANY_NUMBER, voucherNumber
            )
            viewModel.serviceListLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.tvServiceCv.setText(createServiceData(it))
                }
                Log.d("initObserverEmployeeList", "createServiceData:$it ")

            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if (it) binding.brGif.visibility = View.VISIBLE

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    showErrorSweet(it)
                }
            }

        }
    }

    private fun createServiceData(services: List<ServiceResponse>): String {
        var serviceText = ""
        for (service in services) {
            serviceText += "${service.itemName},"
            Log.d("TAGMakeServiceView", "makeServiceView:${serviceText} ")
        }
        return serviceText
    }

    private fun createTeamView(it: List<EmployeeResponse>): String {
        var teamString = ""

        for (item in it) {
            teamString += "${item.name}\n "

        }
        return teamString

    }

    private fun getData(arguments: Bundle?) {
        if (arguments != null) {
            voucherNumber = arguments.getString("voucherNumber").toString()
            val carColor = arguments.getString("carColor")
            val carId = arguments.getString("carId")
            val carType = arguments.getString("carType")
            val carImg = arguments.getString("carImg")
            val personName = arguments.getString("customerName")
            val serviceNumber = arguments.getString("serviceNumber")
            val phoneNumber = arguments.getString("phoneNumber")
            val teamNumber = arguments.getString("teamNumber")
            val status = arguments.getString("status")
            val insDate = arguments.getString("insDate")
            val insTime = arguments.getString("insTime")
            val startDate = arguments.getString("startDate")
            val startTime = arguments.getString("startTime")
            val endDate = arguments.getString("endDate")
            val endTime = arguments.getString("endTime")
            val customerType = arguments.getString("customerType")
            val phase = arguments.getString("phase")
            val time = arguments.getString("time")
            val timePhase1 = arguments.getString("timePhase1")
            val timePhase2 = arguments.getString("timePhase2")
            val timePhase3 = arguments.getString("timePhase3")
            ticket = TicketResponse(
                carColor = carColor ?: "",
                carId = carId ?: "",
                carType = carType ?: "",
                customerName = personName ?: "",
                serviceNumber = serviceNumber ?: "",
                phoneNumber = phoneNumber ?: "",
                teamNumber = teamNumber ?: "",
                voucherNumber = voucherNumber,
                status = status ?: "",
                insDate = insDate ?: "",
                insTime = insTime ?: "",
                startDate = startDate ?: "",
                startTime = startTime ?: "",
                endDate = endDate ?: "",
                endTime = endTime ?: "",
                customerType = customerType ?: "",
                carModel = carImg ?: "",
                phase = phase ?: "",
                time = time ?: "",
                timePhase2ForCV = timePhase2 ?: "",
                timePhase1ForCV = timePhase1 ?: "",
                timePhase3ForCV = timePhase3 ?: "",
                timePhase3 = "",
                timePhase1 = "",
                timePhase2 = ""
            )
        }

    }

    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }


}