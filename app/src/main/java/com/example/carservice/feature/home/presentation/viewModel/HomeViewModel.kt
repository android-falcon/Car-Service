package com.example.carservice.feature.home.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carservice.core.service.NoInternetException
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.home.domain.repository.TicketRepository
import kotlinx.coroutines.launch


class HomeViewModel() : ViewModel() {
    private val repository = TicketRepository()
    private val _ticketListLiveData = MutableLiveData<List<TicketResponse>>()
    val ticketListLiveData: LiveData<List<TicketResponse>> = _ticketListLiveData
    val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData


    suspend fun getTicketsByStatus(status: String, companyNumber: String) {
        try {
            viewModelScope.launch {
                _loadingLiveData.value = true

                repository.getAllTicket(
                    companyNumber,
                    status,
                    onSuccess = { tickets ->
                        _ticketListLiveData.value = tickets
                    },
                    onError = { error ->
                        _errorLiveData.value = error

                    }

                )
                _loadingLiveData.value = false
            }
            } catch (e: NoInternetException) {
                _errorLiveData.value = e.message
            } catch (e: Exception) {
                _errorLiveData.value = "No Ticket found"
            }
    }


}

