package com.example.carservice.feature.sammaryTicket.domain.repository

import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.feature.detail.domain.model.ServiceResponse
import com.example.carservice.feature.sammaryTicket.domain.model.EmployeeResponse

class DetailsTicketRepository {
    suspend fun getServiceByVoucherNumber(
        cono: String,
        voucherNumber: String,
        onSuccess: (List<ServiceResponse>) -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val response = apiService.getServiceByVoucherNumber(
                cono,
                voucherNumber
            )
            if (response.isSuccessful) {
                onSuccess(response.body()!!)

            } else {
                onError("Error ${response.errorBody()}")
            }
        } catch (e: Exception) {
            onError(e.localizedMessage)
        }
    }

    suspend fun getEmployeeTeam(
        cono: String,
        voucherNumber: String,
        onSuccess: (List<EmployeeResponse>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = apiService.getTeamList(
                cono,
                voucherNumber
            )
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onError(response.code().toString())
            }
        } catch (e: Exception) {
            onError(e.localizedMessage)
        }


    }
}