package com.example.carservice.feature.addTicket.domain.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.feature.addTicket.domain.model.CarResponse
import com.example.carservice.feature.addTicket.domain.model.StatusModel

class AddTicketRepository {
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
        carModel: String,
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
                carModel,
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

    @SuppressLint("LongLogTag")
    suspend fun getCustomerByPhoneNumber(
        cono: String,
        phoneNumber: String,
        onSuccess: (List<CarResponse>) -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val response = apiService.getCustomerByPhoneNumber(
                cono,
                phoneNumber,

            )
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
                Log.d("TAGetCustomerByPhoneNumber", "getCustomerByPhoneNumber:${response.body()} ")
            } else {
                onError("no Data for this number ")
                Log.d(
                    "TAGetCustomerByPhoneNumber",
                    "getCustomerByPhoneNumber:${response.errorBody()}"
                )

            }

        } catch (e: Exception) {

            onError("no Data for this number")
            Log.d("TAGetCustomerByPhoneNumber", "getCustomerByPhoneNumber:${e.localizedMessage} ")


        }

    }
    @SuppressLint("LongLogTag")
    suspend fun getCustomerByPlateNumber(
        cono: String,
        plateNumber: String,
        onSuccess: (List<CarResponse>) -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val response = apiService.getCustomerByPlateNumber(
                cono,
                plateNumber
            )
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
                Log.d("TAGetCustomerByPhoneNumber", "getCustomerByPhoneNumber:${response.body()} ")
            } else {
                onError("no Data for this number ")
                Log.d(
                    "TAGetCustomerByPhoneNumber",
                    "getCustomerByPhoneNumber:${response.errorBody()}"
                )

            }

        } catch (e: Exception) {

            onError("no Data for this number")
            Log.d("TAGetCustomerByPhoneNumber", "getCustomerByPhoneNumber:${e.localizedMessage} ")


        }

    }
}