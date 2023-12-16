package com.example.carservice.feature.services.domain.repository

import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.services.domain.model.ServiceModel

class ServiceRepository {

    suspend fun getService(
        companyNumber: String,
        onSuccess: (List<ServiceModel>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = apiService.getService(companyNumber)
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onError(response.errorBody().toString())
            }
        } catch (e: Exception) {
            onError(e.localizedMessage)
        }

    }
}