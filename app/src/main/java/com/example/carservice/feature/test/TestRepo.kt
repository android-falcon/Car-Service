package com.example.carservice.feature.test

import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.feature.addTicket.domain.model.StatusModel
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.services.domain.model.ServiceBodyModel
import com.example.carservice.feature.services.domain.model.ServiceDtlModel

class TestRepo {
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
        carModel:String,
        onSuccess: (StatusModel) -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val response = apiService.saveTicket(
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
                carModel
            )
            if (response.isSuccessful) {
                if (response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    onError("The Data is Empty")
                }

            } else {
                onError(response.errorBody().toString())
            }
        } catch (e: Exception) {
            onError(e.localizedMessage)
        }
    }
}