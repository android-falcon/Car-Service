package com.example.carservice.feature.employee.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carservice.feature.addTicket.domain.model.StatusModel
import com.example.carservice.feature.employee.domain.model.EmployeeModel
import com.example.carservice.feature.employee.domain.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel : ViewModel() {
    private val repository = EmployeeRepository()
    private val _employeeListLiveData = MutableLiveData<List<EmployeeModel>>()
    val employeeListLiveData: LiveData<List<EmployeeModel>> = _employeeListLiveData
    private val _statusLiveData = MutableLiveData<StatusModel>()
    val statusLiveData: LiveData<StatusModel> = _statusLiveData
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData


    fun getEmployee(companyNumber: String, phase: String) {
        _loadingLiveData.value = true
        viewModelScope.launch {
            repository.getAllEmployee(companyNumber,
                phase,
                onSuccess = { employeeModelList ->
                    _employeeListLiveData.value = employeeModelList

                }, onError = { error ->
                    _errorLiveData.value = error
                })
        }
    }

    fun startTicket(companyNumber: String, team: String, voucherNumber: String, phase: String) {
        try {
            _loadingLiveData.value = true
            viewModelScope.launch {
                repository.startTicket(
                    companyNumber,
                    team = team,
                    voucherNumber = voucherNumber,
                    phase = phase,
                    onSuccess = {
                        _statusLiveData.value = it

                    },
                    onError = {
                        _errorLiveData.value = it
                    })
                _loadingLiveData.value = true
            }

        } catch (e: Exception) {
            _errorLiveData.value = e.localizedMessage
        }
    }

}