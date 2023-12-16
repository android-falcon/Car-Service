package com.example.carservice.feature.sammaryTicket.presentaion.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.sammaryTicket.domain.repository.SummaryTicketRepository
import kotlinx.coroutines.launch

class SummaryTicketViewModel : ViewModel() {
    private val repository = SummaryTicketRepository()
    private val _ticketListLiveData = MutableLiveData<List<TicketResponse>>()
    val ticketListLiveData: LiveData<List<TicketResponse>> = _ticketListLiveData
    val errorLiveData = MutableLiveData<String>()
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    suspend fun getTicketsByDate(date: String, companyNumber: String) {
        try{
            viewModelScope.launch {
                _loadingLiveData.value = true

                repository.getTicketByData(
                    companyNumber,
                    date,
                    onSuccess = { tickets ->
                        _ticketListLiveData.value = tickets
                    },
                    onError = { error ->
                        errorLiveData.value = error
                    }

                )
                _loadingLiveData.value = false

            }
        } catch (e:Exception){
            errorLiveData.value ="Error no ticket at this date"
        }
    }

}

