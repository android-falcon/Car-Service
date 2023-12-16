package com.example.carservice.feature.detail.presintation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carservice.feature.addTicket.domain.model.StatusModel
import com.example.carservice.feature.detail.domain.model.ServiceResponse
import com.example.carservice.feature.detail.domain.model.TimeModel
import com.example.carservice.feature.detail.domain.repository.GetServiceByVoucherNumberRepository
import com.example.carservice.feature.sammaryTicket.domain.model.EmployeeResponse
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val repository = GetServiceByVoucherNumberRepository()
    private val _serviceListLiveData = MutableLiveData<List<ServiceResponse>>()
    val serviceListLiveData: LiveData<List<ServiceResponse>> = _serviceListLiveData
    private val _employeeListLiveData= MutableLiveData<List<EmployeeResponse>> ()
    val employeeListLiveData: LiveData<List<EmployeeResponse>> = _employeeListLiveData
    private val _endStatusLiveData = MutableLiveData<StatusModel>()
    val endStatusLiveData: LiveData<StatusModel> = _endStatusLiveData
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    private val _timeLiveData = MutableLiveData<List<TimeModel>>()
    val timeLiveData: LiveData<List<TimeModel>> = _timeLiveData
    fun getService(
        cono: String,
        voucherNumber: String
    ) {
        viewModelScope.launch {
            _loadingLiveData.value = true
            try {
                repository.getServiceByVoucherNumber(cono, voucherNumber, onSuccess = {
                    it.let {
                        _serviceListLiveData.value = it
                    }
                }, onError = {
                    _errorLiveData.value = it
                })
            } catch (e: Exception) {
                _errorLiveData.value = e.localizedMessage
            }
        }
    }

    fun endService(
        cono: String,
        voucherNumber: String
    ) {
        viewModelScope.launch {
            _loadingLiveData.value = true
            try {
                repository.endTicket(cono, voucherNumber,
                    onSuccess = {
                        it?.let {
                            _endStatusLiveData.value = it
                        }
                    },
                    onError = {
                        _errorLiveData.value = it
                    }
                )
                _loadingLiveData.value = false

            } catch (e: Exception) {

                _errorLiveData.value = e.localizedMessage
            }


        }
    }
    fun cancelService(
        cono: String,
        voucherNumber: String,
        note:String
    ) {
        viewModelScope.launch {
            _loadingLiveData.value = true
            try {
                repository.cancelTicket(cono, voucherNumber,note,
                    onSuccess = {
                        it?.let {
                            _endStatusLiveData.value = it
                        }
                    },
                    onError = {
                        _errorLiveData.value = it
                    }
                )

            } catch (e: Exception) {

                _errorLiveData.value = e.localizedMessage
            }


        }
    }
    fun getEmployee(cono:String,voucherNumber: String){
        viewModelScope.launch {
            _loadingLiveData.value = true
            try {
                repository.getEmployee(cono, voucherNumber, onSuccess = {
                    it.let {
                        _employeeListLiveData.value = it
                    }
                }, onError = {
                    _errorLiveData.value = it
                })
            } catch (e: Exception) {
                _errorLiveData.value = e.localizedMessage
            }
        }

    }

    fun getTime(cono:String,voucherNumber: String){
        viewModelScope.launch {
            _loadingLiveData.value=true
            try {
                repository.getTime(cono,voucherNumber,
                    onError = {
                        _errorLiveData.value=it
                    },
                    onSuccess = {
                        _timeLiveData.value=it
                    }
                )
            }catch (e:Exception){
                _errorLiveData.value=e.localizedMessage
            }
        }
    }
}