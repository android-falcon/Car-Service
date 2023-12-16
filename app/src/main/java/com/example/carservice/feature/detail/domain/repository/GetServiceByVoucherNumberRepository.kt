package com.example.carservice.feature.detail.domain.repository

import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.feature.addTicket.domain.model.StatusModel
import com.example.carservice.feature.auth.domain.model.UserModel
import com.example.carservice.feature.detail.domain.model.ServiceResponse
import com.example.carservice.feature.detail.domain.model.TimeModel
import com.example.carservice.feature.sammaryTicket.domain.model.EmployeeResponse

class GetServiceByVoucherNumberRepository {
    suspend fun getServiceByVoucherNumber(
        cono: String,
        voucherNumber: String,
        onSuccess: (List<ServiceResponse>) -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val response = apiService.getServiceByVoucherNumber(
                cono, voucherNumber
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

    suspend fun endTicket(
        cono: String,
        voucherNumber: String,
        onSuccess: (StatusModel) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = apiService.endTicket(
                cono, voucherNumber
            )
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onError("Error code is ${response.errorBody()}")
            }

        } catch (e: Exception) {
            error(e.localizedMessage)

        }

    }

    suspend fun cancelTicket(
        cono: String,
        voucherNumber: String,
        note: String,
        onSuccess: (StatusModel) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = apiService.cancelTicket(
                cono, voucherNumber, note
            )
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onError("Error code is ${response.errorBody()}")
            }

        } catch (e: Exception) {
            error(e.localizedMessage)

        }

    }

    suspend fun getEmployee(
        cono: String,
        voucherNumber: String,
        onSuccess: (List<EmployeeResponse>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = apiService.getTeamList(
                cono, voucherNumber
            )
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onError("error from DataResponse:${response.errorBody()}")
            }
        } catch (e: Exception) {
            onError("Something Error  :${e.message}")


        }

    }
    suspend fun getTime(
        cono:String,
        voucherNumber: String,
        onError:(String) ->Unit,
        onSuccess: (List<TimeModel>) -> Unit
    ){
        try {
            val response= apiService.getTime(cono,voucherNumber)
            if(response.isSuccessful){
                onSuccess(response.body()!!)
            }
            else {
                onError(response.message())
            }
        }catch (e:Exception){
            error(e.localizedMessage)
        }
    }
}