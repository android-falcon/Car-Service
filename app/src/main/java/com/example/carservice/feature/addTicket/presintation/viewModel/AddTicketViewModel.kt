package com.example.carservice.feature.addTicket.presintation.viewModel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carservice.feature.addTicket.domain.model.CarResponse
import com.example.carservice.feature.addTicket.domain.model.StatusModel
import com.example.carservice.feature.addTicket.domain.repository.AddTicketRepository
import com.example.carservice.feature.home.domain.model.TicketBody
import kotlinx.coroutines.launch

class AddTicketViewModel : ViewModel() {
    private val repository = AddTicketRepository()
    private val _carResult = MutableLiveData<CarResponse>()
    val carResult: LiveData<CarResponse> = _carResult

    private val _serviceListLiveData = MutableLiveData<StatusModel>()
    val ticketListLiveData: LiveData<StatusModel> = _serviceListLiveData
    private val _carListLiveData = MutableLiveData<List<CarResponse>>()
    val carListLiveData: LiveData<List<CarResponse>> = _carListLiveData
    val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

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
        carModel: String,
        userNumber: String,
        service: String
    ) {
        try {
            viewModelScope.launch {
                _loadingLiveData.value = true
                repository.saveTicket(
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
                        _serviceListLiveData.value = it
                        Log.d("TAGSaveTicket", "onSuccess:$it ")
                    }, onError = {
                        _errorLiveData.value = it
                        Log.d("TAGSaveTicket", "onError:$it ")

                    })
                _loadingLiveData.value = false

            }
        } catch (e: Exception) {
            _errorLiveData.value = e.localizedMessage
            Log.d("TAGErrorLiveData", "saveTicket:${e.localizedMessage} ")
        }
    }

    suspend fun getCustomerByPhoneNumber(
        cono: String,
        phoneNumber: String,
    ) {
        try {
            viewModelScope.launch {
                _loadingLiveData.value = true
                repository.getCustomerByPhoneNumber(
                    cono,
                    phoneNumber,
                    onSuccess = {
                        _carListLiveData.value = it
                    }, onError = {
                        _errorLiveData.value = it
                    }

                )
                _loadingLiveData.value = false

            }
        } catch (e: Exception) {
            _errorLiveData.value = "no Data for this number"
        }

    }
    suspend fun getCustomerByPlateNumber(
        cono: String,
        plateNumber: String,
    ) {
        try {
            viewModelScope.launch {
                _loadingLiveData.value = true
                repository.getCustomerByPlateNumber(
                    cono,
                    plateNumber,
                    onSuccess = {
                        _carListLiveData.value = it
                    }, onError = {
                        _errorLiveData.value = it
                    }

                )
                _loadingLiveData.value = false

            }
        } catch (e: Exception) {
            _errorLiveData.value = "no Data for this number"
        }

    }

    fun setCarResponse(carResponse: CarResponse) {
        _carResult.value = carResponse
    }


}