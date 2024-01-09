package com.example.carservice.feature.services.presentation.ui

import android.content.Context
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
import com.example.carservice.core.constant.JsonHelper
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.databinding.FragmentCreateTicketBinding
import com.example.carservice.feature.addTicket.presintation.viewModel.AddTicketViewModel
import com.example.carservice.feature.home.domain.model.TicketBody
import com.example.carservice.feature.services.domain.model.ServiceBodyModel
import com.example.carservice.feature.services.domain.model.ServiceDtlModel
import com.example.carservice.feature.services.domain.model.ServiceModel
import com.example.carservice.feature.services.presentation.adapter.NewServiceAdapter
import com.example.carservice.feature.services.presentation.viewModel.ServiceViewModel
import kotlinx.coroutines.launch


class CreateTicketFragment : Fragment() {
    private lateinit var binding: FragmentCreateTicketBinding
    private val serviceViewModel: ServiceViewModel by viewModels()
    private lateinit var serviceAdapter: NewServiceAdapter
    private var serviceList = ArrayList<ServiceModel>()
    private val COMPANY_NUMBER = TechnicalData.COMPANY_NUMBER
    private var serviceString: String = ""
    private var userName: String? = null
    private var userType: String? = null
    private var password: String? = null
    private var userNumber: String? = null
    private lateinit var ticketBody: TicketBody
    private var carModel: String = ""
    private var selectedCount = 0
    private val addTicketViewMode: AddTicketViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTicketBinding.inflate(layoutInflater)
        binding.brGif.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserverServicesList()
        getTicketInfo()
        onClickCreateTicket()
    }

    private fun initAdapter(data: List<ServiceModel>?) {
        serviceList.clear()
        if (data != null) {
            serviceList.addAll(data)
            serviceAdapter = NewServiceAdapter(serviceList, ::onSelectedItem)
            binding.rvService.adapter = serviceAdapter
        }
    }

    private fun onSelectedItem(serviceBodyModel: ServiceBodyModel, checked: Boolean) {


        if (checked) {
            // Add the serviceBodyModel to the JSON
            serviceString = JsonHelper.addServiceBodyModelToDtlJson(serviceString, serviceBodyModel)
            selectedCount++
            // Save or update the JSON in your data source
            // For example, if you are using a ViewModel, you might call a function like viewModel.updateJson(updatedJson)
            Log.d("UpdatedJson", "add:" + serviceString)
        } else {
            // Delete the serviceBodyModel from the JSON
            serviceString = JsonHelper.deleteServiceBodyModelFromDtlJson(
                serviceString, serviceBodyModel.itemCode

            )
            selectedCount--

            // Save or update the JSON in your data source
            // For example, if you are using a ViewModel, you might call a function like viewModel.updateJson(updatedJson)
            Log.d("UpdatedJson", "delete" + serviceString)
        }
        Log.d("UpdatedJson", "end" + serviceString)


    }


    private fun initObserverServicesList() {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                serviceViewModel.getService(COMPANY_NUMBER)
                serviceViewModel.ticketListLiveData.observe(viewLifecycleOwner) { data ->
                    data.let {
                        initAdapter(data)
                        Log.d("TAGgetService", "initObserverServicesList: $data")
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                serviceViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
                    showErrorSweet(error)
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                serviceViewModel.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it) binding.brGif.visibility = View.VISIBLE
                    else binding.brGif.visibility = View.GONE

                }
            }

        } catch (e: Exception) {
            showErrorSweet(e.localizedMessage)

        }
    }

    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }

    private fun checkAllFiled(): Boolean {
        if (serviceString.isEmpty()) {
            showErrorSweet("Choose one service At least")
            return false
        }
        return true
    }

    private fun onClickCreateTicket() {


        binding.btnAddInQueue.setOnClickListener {

            if (checkAllFiled()) {
                binding.btnAddInQueue.isEnabled = false
                try {
                    binding.brGif.visibility = View.VISIBLE
                    viewLifecycleOwner.lifecycleScope.launch {
                        addTicketViewMode.saveTicket(
                            TechnicalData.COMPANY_NUMBER,
                            carId = ticketBody.carId,
                            userNumber = ticketBody.serviceNumber,
                            carType = ticketBody.carType,
                            customerName = ticketBody.customerName,
                            customerType = ticketBody.customerType,
                            phoneNumber = ticketBody.phoneNumber,
                            carColor = ticketBody.carColor,
                            howMany = ticketBody.serviceNumber,
                            note = ticketBody.note,
                            service = serviceString,
                            carModel = ticketBody.carModel
                        )
                        addTicketViewMode.ticketListLiveData.observe(viewLifecycleOwner) { status ->
                            status.let {
                                if (status.errorCode.equals("0")) {
                                    binding.brGif.visibility = View.GONE
                                    showSuccessesDialog(status.errorDescription)
                                    backToMainPage()
                                } else if (!status.errorCode.equals("0")) {
                                    binding.brGif.visibility = View.GONE

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
                    viewLifecycleOwner.lifecycleScope.launch {
                        addTicketViewMode.loadingLiveData.observe(viewLifecycleOwner) {
                            if (it) {
                                binding.brGif.visibility = View.VISIBLE
                            } else {
                                binding.brGif.visibility = View.GONE

                            }
                        }
                    }

                } catch (e: Exception) {
                    showErrorSweet(e.localizedMessage)
                    Log.d("TAGshowErrorSweet", "onClickCreateTicket:${e.localizedMessage} ")
                }
                binding.brGif.visibility = View.GONE
                binding.btnAddInQueue.isEnabled = false


            }
        }
    }

    private fun backToMainPage() {
        if (userType.equals("0")) {
            val navController = findNavController()
            val summaryTicketFragmentId = R.id.homeFragment
            navController.popBackStack(summaryTicketFragmentId, false)
        } else {
            val navController = findNavController()
            val summaryTicketFragmentId = R.id.addTicketFragment
            navController.popBackStack(summaryTicketFragmentId, false)
        }

    }

    private fun showProgressDialog(status: Boolean) {
        val dialog = SweetAlertDialog(
            requireContext(), SweetAlertDialog.PROGRESS_TYPE
        ).setTitleText("").setContentText("loading..")
        if (status) {
            dialog.show()
        } else {
            dialog.cancel()
        }
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
            val carId = bundle.getString("plateNumber")
            val carType = bundle.getString("carType")
            val personName = bundle.getString("customerName")
            val phoneNumber = bundle.getString("phoneNumber")
            val customerType = bundle.getString("customerType")
            val carImg = bundle.getString("carImg")
            val note = bundle.getString("note")
            val list = ArrayList<ServiceBodyModel>()
//            list.add(ServiceBodyModel(itemCode = "10003", itemName = "wash"))
            val serviceDtlModel = ServiceDtlModel(dtl = list)
            Log.d("TAGlist", "getTicketInfo:${serviceDtlModel} ")
            ticketBody = TicketBody(
                phoneNumber = formatPhoneNumber(phoneNumber!!),
                carColor = carColor!!,
                customerType = customerType!!,
                customerName = personName!!,
                note = note!!,
                services = serviceDtlModel,
                carType = carType!!,
                carId = carId!!,
                serviceNumber = selectedCount.toString(),
                userNumber = userNumber!!,
                carModel = carImg!!

            )
            Log.d("TAGPhoneNumber", "phone: ${formatPhoneNumber(phoneNumber)} ")

            Log.d("TAGetTicketInfo", "getTicketInfo:${ticketBody} ")
        }

    }

    private fun showSuccessesDialog(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.SUCCESS_TYPE
        ).setTitleText("Done").setContentText(message).show()
    }
    fun formatPhoneNumber(inputNumber: String): String {
        // Check if the number starts with "07" and is 10 characters long
        if (inputNumber.length == 10 && inputNumber.startsWith("07")) {
            // Add "962" at the beginning and remove the leading "0"
            return "962${inputNumber.substring(1)}"
        }

        // If the number doesn't match the above condition, return it as is
        return inputNumber
    }

}