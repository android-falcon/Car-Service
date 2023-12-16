package com.example.carservice.feature.services.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.databinding.FragmentServiceBinding
import com.example.carservice.feature.addTicket.presintation.viewModel.AddTicketViewModel
import com.example.carservice.feature.home.domain.model.TicketBody
import com.example.carservice.feature.services.domain.model.ServiceBodyModel
import com.example.carservice.feature.services.domain.model.ServiceDtlModel
import com.example.carservice.feature.services.domain.model.ServiceModel
import com.example.carservice.feature.services.presentation.adapter.NewServiceAdapter
import com.example.carservice.feature.services.presentation.viewModel.ServiceViewModel
import kotlinx.coroutines.launch


class ServiceFragment : Fragment() {
    private lateinit var binding: FragmentServiceBinding
    private val viewModel: ServiceViewModel by viewModels()
    private lateinit var serviceAdapter: NewServiceAdapter
    private var serviceList = ArrayList<ServiceModel>()
    private val addTicketViewMode: AddTicketViewModel by viewModels()
    private lateinit var ticketBody: TicketBody
    private var selectedList = ArrayList<ServiceBodyModel>()
    private var userName: String? = null
    private var userType: String? = null
    private var password: String? = null
    private var userNumber: String? = null
    private var serviceString: MutableLiveData<String>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        getTicketInfo()
        onClickCreateTicke()
    }

    private fun onClickCreateTicke() {


        binding.btnAddInQueue.setOnClickListener {
            if (checkAllFiled()) {
                try {
                    binding.brGif.visibility = View.VISIBLE
                    viewLifecycleOwner.lifecycleScope.launch {
                        addTicketViewMode.saveTicket(
                            TechnicalData.COMPANY_NUMBER, carId = ticketBody.carId,
                            userNumber = ticketBody.serviceNumber,
                            carType = ticketBody.carType,
                            customerName = ticketBody.customerName,
                            customerType = ticketBody.customerType,
                            phoneNumber = ticketBody.phoneNumber,
                            carColor = ticketBody.carColor,
                            howMany = ticketBody.serviceNumber,
                            note = ticketBody.note,
                            service = serviceString?.value!!,
                            carModel = ticketBody.carModel,

                            )
                        addTicketViewMode.ticketListLiveData.observe(viewLifecycleOwner) { status ->
                            status.let {
                                if (status.errorCode.equals("0")) {
                                    showSuccessesDialog(status.errorDescription)
                                } else if (!status.errorCode.equals("0")) {
                                    showErrorSweet(status.errorDescription)
                                }
                            }
                        }
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        addTicketViewMode.errorLiveData.observe(viewLifecycleOwner) {
                            if (!it.isEmpty()) {
                                showErrorSweet(it.toString())
                            }

                        }
                    }

                } catch (e: Exception) {
                    showErrorSweet(e.localizedMessage)
                }
                binding.brGif.visibility = View.GONE

            }
        }
    }

    private fun getServiceEncode(list: ArrayList<ServiceBodyModel>): MutableLiveData<String> {
        // val servicesJson = "{\"DTL\":[{\"ITEMCODE\":\"10006\",\"ITEMNAME\":\"Wash\"}]}"
        val inString: String = "{\"DTL\":["
        val firstString: String = "{\"ITEMCODE:\""
        val secondString: String = "\",\"ITEMNAME\":\""
        val endObjectString: String = "\"}"
        val endString: String = "\"}]}"
        var outputString = MutableLiveData<String>()
        outputString.value = inString
        if (!list.isEmpty()) {
            Log.d("TAGetServiceEncode", "getServiceEncode:${list}")

            if (list.size == 0) {
                outputString.value =
                    "${firstString}${list[1].itemCode}${secondString}${list[1].itemName}" +
                            "${endObjectString}${endString}"
            } else {
                for (item in list) {
                    outputString.value += "${firstString}${item.itemCode}${secondString}${item.itemName}" +
                            "${endObjectString},"
                    Log.d("TAGetServiceEncode", "getServiceEncode:${outputString}")

                }
            }


            outputString.value += endString
        }
        Log.d("TAGetServiceEncode", "getServiceEncode:${outputString}")
        return outputString

    }

    private fun showSuccessesDialog(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.SUCCESS_TYPE
        ).setTitleText("Error").setContentText(message).show()
    }

    private fun checkAllFiled(): Boolean {
        if (selectedList.isEmpty()) {
            showErrorSweet("Choose one service At least")
            return false
        } else if (ticketBody == null) {
            showErrorSweet("You doesn't login Something wrong  ")
            return false
        }
        return true
    }

    private fun getTicketInfo() {
        var sharedPreferences = context?.getSharedPreferences("User", Context.MODE_PRIVATE)

        userName = sharedPreferences?.getString("userName", "")
        userType = sharedPreferences?.getString("userType", "")
        password = sharedPreferences?.getString("password", "")
        userNumber = sharedPreferences?.getString("userNumber", "")

        var bundle = arguments
        if (bundle != null) {
            val carColor = bundle.getString("carColor")
            val carId = bundle.getString("carId")
            val carType = bundle.getString("carType")
            val personName = bundle.getString("customerName")
            val phoneNumber = bundle.getString("phoneNumber")
            val customerType = bundle.getString("customerType")
            val note = bundle.getString("note")
            val carImg = bundle.getString("carImg")
            val list = ArrayList<ServiceBodyModel>()
            list.add(ServiceBodyModel(itemCode = "10003", itemName = "wash"))
            val serviceDtlModel = ServiceDtlModel(dtl = list)
            Log.d("TAGlist", "getTicketInfo:${serviceDtlModel} ")

            ticketBody = TicketBody(
                phoneNumber = phoneNumber!!,
                carColor = carColor!!,
                customerType = customerType!!,
                customerName = personName!!,
                note = note!!,
                services = serviceDtlModel,
                carType = carType!!,
                carId = carId!!,
                serviceNumber = selectedList.size.toString(),
                userNumber = userNumber!!,
                carModel = carImg!!
            )
            Log.d("TAGetTicketInfo", "getTicketInfo:${ticketBody} ")
        }

    }

    private fun initObserver() {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getService(TechnicalData.COMPANY_NUMBER)
                viewModel.ticketListLiveData.observe(viewLifecycleOwner) { service ->
                    service.let {
                        initAdapter(service)
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
                viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
                    if (error != null) {
                        showErrorSweet(error)

                    }
                }
            }
        } catch (e: Exception) {
            showErrorSweet(e.localizedMessage)
        }
    }

    private fun initAdapter(service: List<ServiceModel>?) {
        if (service != null) {
            serviceList.clear()
            serviceList.addAll(service)
            serviceAdapter = NewServiceAdapter(serviceList) { service, isChecked ->

                if (isChecked) {
                    selectedList.add(service)
                } else {
                    selectedList.remove(service)
                }
                Log.d("TAGInitAdapter", "initAdapter:${selectedList} ")
            }
            serviceString = getServiceEncode(selectedList)
            Log.d("TAGInitAdapter", "serviceString:${serviceString} ")
            serviceAdapter.notifyDataSetChanged()
            binding.rvService.adapter = serviceAdapter


        }
    }


    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }


}