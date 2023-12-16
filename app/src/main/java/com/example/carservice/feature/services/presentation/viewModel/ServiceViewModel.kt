package com.example.carservice.feature.services.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carservice.feature.services.domain.model.ServiceModel
import com.example.carservice.feature.services.domain.repository.ServiceRepository

class ServiceViewModel : ViewModel() {
    private val repository = ServiceRepository()
    private val _serviceListLiveData = MutableLiveData<List<ServiceModel>>()
    val ticketListLiveData: LiveData<List<ServiceModel>> = _serviceListLiveData
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    suspend fun getService(companyNumber: String) {
        try {
            _loadingLiveData.value = true
            repository.getService(companyNumber, onSuccess = {
                _serviceListLiveData.value = it
            }, onError = {
                _errorLiveData.value = it
            })
            _loadingLiveData.value = false
        } catch (e: Exception) {
            _errorLiveData.value = e.localizedMessage

        }

    }
}