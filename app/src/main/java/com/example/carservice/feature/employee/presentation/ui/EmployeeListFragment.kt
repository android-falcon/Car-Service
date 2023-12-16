package com.example.carservice.feature.employee.presentation.ui

import android.annotation.SuppressLint
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
import com.example.carservice.core.constant.JsonHelper
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.core.service.NoInternetException
import com.example.carservice.databinding.FragmentEmployeeListBinding
import com.example.carservice.feature.employee.domain.model.EmployeeModel
import com.example.carservice.feature.employee.presentation.adapter.NewEmployeeAdapter
import com.example.carservice.feature.employee.presentation.viewModel.EmployeeViewModel
import kotlinx.coroutines.launch

class EmployeeListFragment : Fragment() {
    private val viewModel: EmployeeViewModel by viewModels()
    private lateinit var binding: FragmentEmployeeListBinding
    private var listOfTeam = ArrayList<String>()
    private var countNumber = 0
    private lateinit var adapter: NewEmployeeAdapter
    private var voucherNumber: String? = ""
    private var phase: String? = ""
    private var serviceString: String = ""
    private var selectedCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvMessage.visibility = View.GONE
        getData(arguments)
        initObserverEmployeeList()
        onClickAddInQueue()

    }

    private fun onClickAddInQueue() {
        binding.btnAddInQueue.setOnClickListener {
            binding.btnAddInQueue.isEnabled = false
            if (!serviceString.isEmpty()) {
                initObserverStartTicket()
            } else if (serviceString.isEmpty()) {
                showErrorSweet("Choose at lest one employee")
            } else {
                showErrorSweet("Something error try again")
            }
        }
    }

    private fun initObserverStartTicket() {
        try {
            viewModel.startTicket(
                TechnicalData.COMPANY_NUMBER,
                team = serviceString,
                voucherNumber = voucherNumber!!,
                phase = phase!!
            )
            lifecycleScope.launch {
                viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it) binding.brGif.visibility = View.VISIBLE
                    else binding.brGif.visibility = View.GONE
                }
            }
            lifecycleScope.launch {
                viewModel.statusLiveData.observe(viewLifecycleOwner) { status ->
                    status.let {
                        if (it.errorCode.equals("0")) {
                            showSuccessesDialog(it.errorDescription)
                            backToMainPage()
                        }
                    }

                }
            }

        } catch (e: Exception) {
            showErrorSweet("All Employee busy")
        }
        binding.btnAddInQueue.isEnabled = true

    }

    private fun backToMainPage() {

        val navController = findNavController()
        val summaryTicketFragmentId = R.id.homeFragment
        navController.popBackStack(summaryTicketFragmentId, false)
    }


    private fun showSuccessesDialog(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.SUCCESS_TYPE
        ).setTitleText("Done").setContentText(message).show()
    }

    private fun getData(arguments: Bundle?) {
        var bundle = arguments
        if (bundle != null) {
            voucherNumber = bundle.getString("voucherNumber")
            if (bundle.getString("phase").equals("0")) {
                phase = "1"
            } else if (bundle.getString("phase").equals("1")) {
                phase = "2"
            } else if (bundle.getString("phase").equals("2")) {
                phase = "3"
            }

            Log.d("TAGetData", "getData: $voucherNumber")
            Log.d("TAGetData", "phase: $phase")
        }

    }


    @SuppressLint("LongLogTag")
    private fun initObserverEmployeeList() {
        try {
            viewModel.getEmployee(companyNumber = TechnicalData.COMPANY_NUMBER, phase = phase!!)
            lifecycleScope.launch {
                viewModel.employeeListLiveData.observe(viewLifecycleOwner) { employee ->
                    employee.let {
                        initAdapter(employee)

                    }
                }
            }
            lifecycleScope.launch {
                viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it) binding.brGif.visibility = View.VISIBLE
                    else binding.brGif.visibility = View.GONE
                }
            }
            lifecycleScope.launch {
                viewModel.errorLiveData.observe(viewLifecycleOwner) {
                    if (it != null) {
                        binding.tvMessage.visibility = View.VISIBLE
                        binding.brGif.visibility=View.GONE

                        Log.d("initObserverEmployeeList", "initObserverEmployeeList:${it} ")
                    }
                }
            }
        } catch (e: Exception) {
            binding.tvMessage.visibility = View.VISIBLE

        } catch (e: NoInternetException) {
            binding.tvMessage.visibility = View.VISIBLE
         }
    }

    private fun initAdapter(employee: List<EmployeeModel>) {
        adapter = NewEmployeeAdapter(employee, ::onSelectedEmployee)
        binding.rvTickets.adapter = adapter
        adapter.notifyDataSetChanged()
        binding.brGif.visibility = View.GONE
    }

    private fun onSelectedEmployee(employeeNumber: String, isChecked: Boolean) {
        if (isChecked) {
            serviceString = JsonHelper.addEmployeeModelToDtlJson(serviceString, employeeNumber)
            selectedCount++
            Log.d("UpdatedJson", "add:" + serviceString)

        } else {
            serviceString = JsonHelper.deleteEmployeeModelFromDtlJson(serviceString, employeeNumber)
            Log.d("UpdatedJson", "add:" + serviceString)
            selectedCount--
        }
        Log.d("UpdatedJson", "end" + serviceString)
    }


    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }


}