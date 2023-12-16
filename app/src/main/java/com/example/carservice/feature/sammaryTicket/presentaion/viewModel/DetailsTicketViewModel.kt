package com.example.carservice.feature.sammaryTicket.presentaion.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carservice.feature.addTicket.domain.model.StatusModel
import com.example.carservice.feature.detail.domain.model.ServiceResponse
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.sammaryTicket.domain.model.EmployeeResponse
import com.example.carservice.feature.sammaryTicket.domain.repository.DetailsTicketRepository
import com.example.carservice.feature.sammaryTicket.domain.repository.SummaryTicketRepository
import kotlinx.coroutines.launch

class DetailsTicketViewModel : ViewModel() {

    private val repository = DetailsTicketRepository()
    private val _serviceListLiveData = MutableLiveData<List<ServiceResponse>?>()
    val serviceListLiveData: LiveData<List<ServiceResponse>?> = _serviceListLiveData
    private val _teamListLiveData = MutableLiveData<List<EmployeeResponse>?>()
    val teamListLiveData: LiveData<List<EmployeeResponse>?> = _teamListLiveData

    val errorLiveData = MutableLiveData<String>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getService(
        cono: String, voucherNumber: String
    ) {


        try {

            viewModelScope.launch {
                loadingLiveData.value = true
                repository.getServiceByVoucherNumber(cono, voucherNumber, onSuccess = {
                    it.let {
                        _serviceListLiveData.value = it
                    }
                }, onError = {
                    errorLiveData.value = it
                })
            }
        } catch (e: Exception) {
            errorLiveData.value = e.localizedMessage
        }
        loadingLiveData.value = false
    }

    fun getEmployee(
        cono: String, voucherNumber: String
    ) {
        loadingLiveData.value = true
        try {
            viewModelScope.launch {
                repository.getEmployeeTeam(cono, voucherNumber, onSuccess = {
                    _teamListLiveData.value = it
                }, onError = {
                    errorLiveData.value = it
                })
            }

        } catch (e: Exception) {
            errorLiveData.value = e.localizedMessage
        }
        loadingLiveData.value = false
    }
}