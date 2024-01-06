package com.example.carservice.feature.detail.presintation.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.carservice.R
import com.example.carservice.core.constant.NoteDialog
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.core.constant.carImg.CarGenerate
import com.example.carservice.databinding.FragmentDetailsBinding
import com.example.carservice.feature.detail.domain.model.ServiceResponse
import com.example.carservice.feature.detail.domain.model.TimeModel
import com.example.carservice.feature.detail.presintation.viewModel.DetailViewModel
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.sammaryTicket.domain.model.EmployeeResponse
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var ticket: TicketResponse
    private val viewModel: DetailViewModel by viewModels()
    private var voucherNumber: String = ""
    private var employeeList = ArrayList<EmployeeResponse>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTicket(arguments)
        initObserver()
        initObserverTeamMember()
        showTime()
        onClickStart()
        onClickStartPhase2()
        onClickStartPhase3()
        showData()
        onClickEndTicket()
        refreshLayout()


    }

    private fun refreshLayout() {
        binding.layoutRefresh.setOnRefreshListener {
            initObserverTime()
            binding.layoutRefresh.isRefreshing = false
        }
    }

    private fun initObserverTime() {
        if (ticket.status.equals("0")) {
            return
        } else if (ticket.status.equals("1")) {
            try {
                viewModel.getTime(TechnicalData.COMPANY_NUMBER, voucherNumber)
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.timeLiveData.observe(viewLifecycleOwner) {
                        showTime(it.get(0))
                    }
                }
            } catch (e: Exception) {

            }

        } else {
            return
        }

    }

    private fun showData() {
        binding.model = ticket
    }

    private fun showTime() {
        if (ticket.phase.equals("1")) {
            binding.tvPhase1Co.setTextColor(Color.rgb(124, 187, 237))
            val t = ticket.time.toInt()
            val hours = t / 60
            val minutes = t % 60
            val time = "${hours}:${minutes}"
            binding.tvTimePhase1.text = time
            binding.tvTimePhase1.setTextColor(Color.rgb(124, 187, 237))
        } else if (ticket.phase.equals("2")) {
            val time1 = ticket.timePhase1.toInt()
            val hours1 = time1 / 60
            val minutes1 = time1 % 60
            binding.tvTimePhase1.text = "${hours1}:${minutes1}"
            binding.tvTimePhase1.setTextColor(Color.rgb(123, 176, 58))
            var time2T = ticket.time.toInt() - ticket.timePhase1.toInt()
            val hours = time2T / 60
            val minutes = time2T % 60
            val time2 = "${hours}:${minutes}"
            binding.tvTimePhase2.text = time2
            binding.tvTimePhase1.setTextColor(Color.rgb(123, 176, 58))
            binding.tvTimePhase2.setTextColor(Color.rgb(124, 187, 237))
            binding.tvPhase1Co.setTextColor((Color.rgb(123, 176, 58)))
            binding.tvPhase2Co.setTextColor(Color.rgb(124, 187, 237))
            binding.tvTimePhase1.setBackgroundResource(R.drawable.bc_complted_time)

        } else if (ticket.phase.equals("3")) {
            binding.tvTimePhase1.setBackgroundResource(R.drawable.bc_complted_time)
            binding.tvTimePhase2.setBackgroundResource(R.drawable.bc_complted_time)
            binding.tvTimePhase1.setTextColor(Color.rgb(123, 176, 58))
            binding.tvTimePhase2.setTextColor(Color.rgb(123, 176, 58))
            binding.tvPhase1Co.setTextColor((Color.rgb(123, 176, 58)))
            binding.tvPhase2Co.setTextColor(Color.rgb(123, 176, 58))
            binding.tvTimePhase3.setTextColor(Color.rgb(124, 187, 237))
            binding.tvPhase3Co.setTextColor(Color.rgb(124, 187, 237))
            val time1 = ticket.timePhase1.toInt()
            val hours1 = time1 / 60
            val minutes1 = time1 % 60
            binding.tvTimePhase1.text = "${hours1}:${minutes1}"
            Log.d("TAGshowTime", "showTime phase1:${hours1}:${minutes1} ")
            val time2 = ticket.timePhase2.toInt()
            val hours = time2 / 60
            val minutes = (time2 % 60)
            val time = "${hours}:${minutes}"
            binding.tvTimePhase2.text = time
            Log.d("TAGshowTime", "showTime phase2:${time2} ")

            var time3T =
                ticket.time.toInt() - (ticket.timePhase2.toInt() + ticket.timePhase1.toInt())
            Log.d("TAGshowTime", "time:${time3T} ")
            Log.d("TAGshowTime", "showTime phase 3: ${time3T} ")
            val hours3 = time3T / 60
            val minutes3 = time3T % 60
            binding.tvTimePhase3.text = "${hours3}:${minutes3}"
            Log.d("TAGshowTime", "showTime phase1:${hours3}:${minutes3} ")


        }
    }

    private fun showTime(time: TimeModel) {
        if (ticket.phase.equals("1")) {
            binding.tvPhase1Co.setTextColor(Color.rgb(124, 187, 237))
            val t = time.time.toInt()
            val hours = t / 60
            val minutes = t % 60
            val time1 = "${hours}:${minutes}"
            binding.tvTimePhase1.text = time1
            binding.tvTimePhase1.setTextColor(Color.rgb(124, 187, 237))
        } else if (ticket.phase.equals("2")) {
            val time1 = time.timePhase1.toInt()
            val hours1 = time1 / 60
            val minutes1 = time1 % 60
            binding.tvTimePhase1.text = "${hours1}:${minutes1}"
            binding.tvTimePhase1.setTextColor(Color.rgb(123, 176, 58))
            var time2T = time.time.toInt() - time.timePhase1.toInt()
            val hours = time2T / 60
            val minutes = time2T % 60
            val time2 = "${hours}:${minutes}"
            binding.tvTimePhase2.text = time2
            binding.tvTimePhase1.setTextColor(Color.rgb(123, 176, 58))
            binding.tvTimePhase2.setTextColor(Color.rgb(124, 187, 237))
            binding.tvPhase1Co.setTextColor((Color.rgb(123, 176, 58)))
            binding.tvPhase2Co.setTextColor(Color.rgb(124, 187, 237))
            binding.tvTimePhase1.setBackgroundResource(R.drawable.bc_complted_time)

        } else if (ticket.phase.equals("3")) {
            binding.tvTimePhase1.setBackgroundResource(R.drawable.bc_complted_time)
            binding.tvTimePhase2.setBackgroundResource(R.drawable.bc_complted_time)
            binding.tvTimePhase1.setTextColor(Color.rgb(123, 176, 58))
            binding.tvTimePhase2.setTextColor(Color.rgb(123, 176, 58))
            binding.tvPhase1Co.setTextColor((Color.rgb(123, 176, 58)))
            binding.tvPhase2Co.setTextColor(Color.rgb(123, 176, 58))
            binding.tvTimePhase3.setTextColor(Color.rgb(124, 187, 237))
            binding.tvPhase3Co.setTextColor(Color.rgb(124, 187, 237))
            val time1 = time.timePhase1.toInt()
            val hours1 = time1 / 60
            val minutes1 = time1 % 60
            binding.tvTimePhase1.text = "${hours1}:${minutes1}"
            Log.d("TAGshowTime", "showTime phase1:${hours1}:${minutes1} ")
            val time2 = time.timePhase2.toInt()
            val hours = time2 / 60
            val minutes = (time2 % 60)
            val timeStr = "${hours}:${minutes}"
            binding.tvTimePhase2.text = timeStr
            Log.d("TAGshowTime", "showTime phase2:${time2} ")

            var time3T =
                time.time.toInt() - (time.timePhase2.toInt() + time.timePhase1.toInt())
            Log.d("TAGshowTime", "time:${time3T} ")
            Log.d("TAGshowTime", "showTime phase 3: ${time3T} ")
            val hours3 = time3T / 60
            val minutes3 = time3T % 60
            binding.tvTimePhase3.text = "${hours3}:${minutes3}"
            Log.d("TAGshowTime", "showTime phase1:${hours3}:${minutes3} ")


        }
    }

    private fun initObserverTeamMember() {
        if (ticket.status.equals("0")) {

            return
        } else {
            try {
                viewModel.getEmployee(TechnicalData.COMPANY_NUMBER, voucherNumber)
                viewModel.employeeListLiveData.observe(viewLifecycleOwner) {
                    employeeList.addAll(it)
                    insertEmployeeToUI(it)
                    Log.d("InitObserverTeamMember", "initObserverTeamMember:${it} ")

                }

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.errorLiveData.observe(viewLifecycleOwner) {
                        Log.d("initObserverTeamMember", "initObserverTeamMember:${it}")

                    }
                }
            } catch (e: Exception) {
                Log.d("initObserverTeamMember", "initObserverTeamMember:${e.message}")
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun insertEmployeeToUI(it: List<EmployeeResponse>?) {
        if (it != null) {
            for (item in it) {
                if (item.phase.equals("1")) {
                    Log.d("insertEmployeeToUI", "insertEmployeeToUI:${item.name} ")
                    binding.tvPhase1Co.setText("${binding.tvPhase1Co.text}\n${item.name}")
                } else if (item.phase.equals("2")) {
                    Log.d("insertEmployeeToUI2", "insertEmployeeToUI:${item.name} ")

                    binding.tvPhase2Co.setText("${binding.tvPhase2Co.text}\n${item.name}")
                } else if (item.phase.equals("3")) {
                    Log.d("insertEmployeeToUI3", "insertEmployeeToUI:${item.name} ")

                    binding.tvPhase3Co.setText("${binding.tvPhase3Co.text}\n${item.name}")

                }
            }
        }

    }

    private fun onClickStart() {
        binding.btnStartTicket.setOnClickListener {
            var bundle = saveDataToBundle(ticket)
            findNavController().navigate(R.id.employeeListFragment, bundle)

        }
    }

    private fun onClickStartPhase2() {
        binding.btnStartPhase2.setOnClickListener {
            var bundle = saveDataToBundle(ticket)
            findNavController().navigate(R.id.employeeListFragment, bundle)
        }
    }

    private fun onClickStartPhase3() {
        binding.btnStartPhase3.setOnClickListener {
            var bundle = saveDataToBundle(ticket)
            findNavController().navigate(R.id.employeeListFragment, bundle)
        }
    }

    private fun onClickEndTicket() {
        binding.btnEndTicket.setOnClickListener {
            initObserverEndTicket()

        }
    }

    private fun initObserverEndTicket() {
        try {
            viewModel.endService(TechnicalData.COMPANY_NUMBER, voucherNumber)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.endStatusLiveData.observe(viewLifecycleOwner) {
                    if (it.errorDescription.equals("Saved Successfully")) {
                        showSuccessesDialog(it.errorDescription)
                        goBack()
                        Log.d("initObserverEndTicket", "initObserverEndTicket:$it ")
                    } else {
                        showErrorSweet(it.errorDescription)
                        Log.d("initObserverEndTicket", "initObserverEndTicket:$it ")

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


        } catch (e: Exception) {
            showErrorSweet("${e.localizedMessage}")
            Log.d("initObserverEndTicket", "initObserverEndTicket:${e.localizedMessage} ")

        }


    }

    @SuppressLint("LongLogTag")
    private fun initObserverCancelTicket(note: String) {

        try {
            viewModel.cancelService(TechnicalData.COMPANY_NUMBER, voucherNumber, note)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.endStatusLiveData.observe(viewLifecycleOwner) {
                    if (it.errorCode.equals("0")) {
                        showSuccessesDialog(it.errorDescription)
                        goBack()
                        Log.d("initObserverEndTicket", "initObserverEndTicket:$it ")
                    } else {
                        showErrorSweet(it.errorDescription)
                        Log.d("initObserverEndTicket", "initObserverEndTicket:$it ")

                    }
                }
            }

        } catch (e: Exception) {
            showErrorSweet("${e.localizedMessage}")
            Log.d("initObserverEndTicket", "initObserverEndTicket:${e.localizedMessage} ")


        }


    }

    private fun goBack() {
        val navController = findNavController()
        val homeFragment = R.id.homeFragment
        navController.popBackStack(homeFragment, false)
    }

    private fun initObserver() {
        try {

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getService(TechnicalData.COMPANY_NUMBER, ticket.voucherNumber)
                viewModel.serviceListLiveData.observe(viewLifecycleOwner) { service ->
                    service?.let {
                        binding.tvServiceCv.text = makeServiceView(service)
                        Log.d("TAGetServices", "getServices: $service")

                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.errorLiveData.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        showErrorSweet(it.toString())
                    }
                }
            }


        } catch (e: Exception) {
            showErrorSweet(e.localizedMessage)
        }
        binding.brGif.visibility = View.GONE

    }

    private fun makeServiceView(services: List<ServiceResponse>): String {
        var serviceText = ""
        var lineCount = 0
        for (service in services) {
            lineCount++
            if (lineCount == 3) {
                serviceText += "\n${service.itemName},"
                lineCount = 0
            } else {
                serviceText += "${service.itemName},"
            }
            Log.d("TAGMakeServiceView", "makeServiceView:${serviceText} ")

        }
        return serviceText
    }


    private fun getTicket(arguments: Bundle?) {

        var bundle = arguments
        if (bundle != null) {
            val carColor = bundle.getString("carColor")
            val carId = bundle.getString("carId")
            val carType = bundle.getString("carType")
            var carImg = bundle.getString("carImg")
            val personName = bundle.getString("customerName")
            val serviceNumber = bundle.getString("serviceNumber")
            val phoneNumber = bundle.getString("phoneNumber")
            val teamNumber = bundle.getString("teamNumber")
            voucherNumber = bundle.getString("voucherNumber")!!
            val status = bundle.getString("status")
            val insDate = bundle.getString("insDate")
            val insTime = bundle.getString("insTime")
            val startDate = bundle.getString("startDate")
            val startTime = bundle.getString("startTime")
            val endDate = bundle.getString("endDate")
            val endTime = bundle.getString("endTime")
            val customerType = bundle.getString("customerType")
            val phase = bundle.getString("phase")
            val time = bundle.getString("time")
            val timePhase1 = bundle.getString("timePhase1")
            val timePhase2 = bundle.getString("timePhase2")
            val timePhase3 = bundle.getString("timePhase2")
            val holdNumber = bundle.getString("holdNumber")
            ticket = TicketResponse(
                carColor = carColor ?: "",
                carId = carId ?: "",
                carType = carType ?: "",
                customerName = personName ?: "",
                serviceNumber = serviceNumber ?: "",
                phoneNumber = phoneNumber ?: "",
                teamNumber = teamNumber ?: "",
                voucherNumber = voucherNumber ?: "",
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
                timePhase3 = timePhase3 ?: "",
                timePhase1 = timePhase1 ?: "",
                timePhase2 = timePhase2 ?: "",
                timePhase1ForCV = "",
                timePhase2ForCV = "",
                timePhase3ForCV = "",
                holdNumber = holdNumber.toString()
            )
        }
        checkPhase()
        if (ticket.carModel.equals("-1")) {
            binding.ivCarModel.setImageResource(R.drawable.img_car)
        } else {
            val image: Int = CarGenerate.carList().get(ticket.carModel.toInt()).img
            binding.ivCarModel.setImageResource(image)
        }


    }

    private fun checkPhase() {
        if (ticket.phase.equals("0")) {
            binding.btnStartPhase2.visibility = View.GONE
            binding.btnStartPhase3.visibility = View.GONE
            binding.btnEndTicket.visibility = View.GONE
            binding.btnStartTicket.visibility = View.VISIBLE
            binding.layoutPhases.visibility = View.GONE


        } else if (ticket.phase.equals("1")) {
            binding.layoutPhases.visibility = View.VISIBLE
            binding.btnStartPhase2.visibility = View.VISIBLE
            binding.btnStartPhase3.visibility = View.GONE
            binding.btnEndTicket.visibility = View.GONE
            binding.btnStartTicket.visibility = View.GONE
            binding.tvPhase2Co.visibility = View.GONE
            binding.tvPhase3Co.visibility = View.GONE
            binding.tvTimePhase3.visibility = View.GONE
            binding.tvTimePhase2.visibility = View.GONE
        } else if (ticket.phase.equals("2")) {
            binding.layoutPhases.visibility = View.VISIBLE
            binding.btnStartPhase2.visibility = View.GONE
            binding.btnStartPhase3.visibility = View.VISIBLE
            binding.btnEndTicket.visibility = View.GONE
            binding.btnStartTicket.visibility = View.GONE
            binding.tvPhase2Co.visibility = View.VISIBLE
            binding.tvPhase3Co.visibility = View.GONE
            binding.tvTimePhase3.visibility = View.GONE
            binding.tvTimePhase2.visibility = View.VISIBLE
        } else if (ticket.phase.equals("3")) {
            binding.layoutPhases.visibility = View.VISIBLE
            binding.btnStartPhase2.visibility = View.GONE
            binding.btnStartPhase3.visibility = View.GONE
            binding.btnEndTicket.visibility = View.VISIBLE
            binding.btnStartTicket.visibility = View.GONE
            binding.tvPhase2Co.visibility = View.VISIBLE
            binding.tvPhase3Co.visibility = View.VISIBLE
            binding.tvTimePhase3.visibility = View.VISIBLE
            binding.tvTimePhase2.visibility = View.VISIBLE
        }


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
        val phase = ticketResponse.phase

        bundle.putString("carColor", carColor)
        bundle.putString("carId", carId)
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
        bundle.putString("phase", phase)
        return bundle
    }

    private fun showSuccessesDialog(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.SUCCESS_TYPE
        ).setTitleText("Done").setContentText(message).show()
    }

    private fun showWarningDialog() {
        SweetAlertDialog(
            requireContext(),
            SweetAlertDialog.WARNING_TYPE
        ).setTitleText("Are you sure?").setContentText("This action can't be undone.")
            .setCancelText("Cancel").setConfirmText("OK").setCancelClickListener { sDialog ->
                sDialog.cancel()
            }.setConfirmClickListener { sDialog ->
                sDialog.dismissWithAnimation()
                showNoteDialog()


            }.show()

    }

    private fun showNoteDialog() {
        val myDialog = NoteDialog(requireContext(), ::onClickAdd).showDialog()
        myDialog

    }

    private fun onClickAdd(note: String) {
        if (!note.equals("")) {
            initObserverCancelTicket(note)

        } else {
            showErrorSweet("you must add note")
        }
    }
}