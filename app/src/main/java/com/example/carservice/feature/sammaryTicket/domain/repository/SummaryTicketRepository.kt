package com.example.carservice.feature.sammaryTicket.domain.repository

import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.feature.home.domain.model.TicketResponse


class SummaryTicketRepository {

    suspend fun getTicketByData(
        companyNumber: String,
        date: String,
        onSuccess: (List<TicketResponse>?) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = apiService.getTicketsByDate(companyNumber, date)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    onSuccess(response.body()!!)
                }
            } else {
                onError("Error no ticket at this date")
            }
        } catch (e: Exception) {
            onError("Error no ticket at this date")
        }


    }
}