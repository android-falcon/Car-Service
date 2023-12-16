package com.example.carservice.feature.home.domain.repository

import com.example.carservice.core.service.NoInternetException
import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.feature.home.domain.model.TicketResponse

class TicketRepository {


    suspend fun getAllTicket(
        companyNumber: String,
        status: String,
        onSuccess: (List<TicketResponse>) -> Unit,
        onError: (String) -> Unit,

        ) {

        try {
            val response = apiService.getTicketByStatus(companyNumber, status)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val data = response.body()
                    onSuccess(data!!)
                }
            } else {
                onError(response.errorBody().toString())
            }
        } catch (e: NoInternetException) {
          onError( e.message.toString())
        } catch (e: Exception) {
            onError( "No Ticket found")
        }

    }
}

