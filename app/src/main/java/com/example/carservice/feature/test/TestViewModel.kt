package com.example.carservice.feature.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carservice.feature.addTicket.domain.model.StatusModel
import com.example.carservice.feature.services.domain.model.ServiceBodyModel
import com.example.carservice.feature.services.domain.model.ServiceDtlModel
import com.example.carservice.feature.services.domain.model.ServiceModel
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    private val repo = TestRepo()
    private val _serviceListLiveData = MutableLiveData<StatusModel>()
    val ticketListLiveData: LiveData<StatusModel> = _serviceListLiveData
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData
    suspend fun saveTicket(
        cono: String,
        phoneNumber: String,
        customerName: String,
        customerType: String,
        carId: String,
        carType: String,
        carColor: String,
        howMany: String,
        note: String,
        userNumber: String,
        service: String,
        carModel: String
    ) {
        viewModelScope.launch {
            try {
                repo.saveTicket(
                    cono,
                    phoneNumber,
                    customerName,
                    customerType,
                    carId,
                    carType,
                    carColor,
                    howMany,
                    note,
                    userNumber,
                    service,
                    carModel,
                    onSuccess = {
                        if (it != null) {
                            _serviceListLiveData.value = it
                            Log.d("TAGSaveTicket", "saveTicket:${it} ")
                        } else {
                            _errorLiveData.value = "Data IsEmpty"
                        }
                    },
                    onError = {
                        _errorLiveData.value = it
                    }
                )
            } catch (e: Exception) {
                _errorLiveData.value = e.localizedMessage
                Log.d("TAG", "saveTicket in Catch: ")
            }
        }
    }
}