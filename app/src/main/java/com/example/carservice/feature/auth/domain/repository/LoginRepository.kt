package com.example.carservice.feature.auth.domain.repository

import android.util.Log
import com.example.carservice.core.service.RetrofitClient
import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.feature.auth.domain.model.UserModel
import com.example.carservice.feature.employee.domain.model.EmployeeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    suspend fun getLogin(
        companyNumber: String,
        userNumber: String,
        onSuccess: (List<UserModel>) -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val response = apiService.getUserInfo(companyNumber, userNumber)
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
                Log.d(
                    "TAGonResponseGetLogin",
                    "onResponse:${response.body()} code is = ${response.code()} "
                )
            } else {
                onError(response.errorBody().toString())
                Log.d(
                    "TAGonResponseGetLogin",
                    "onResponse:${response.errorBody()} code is = ${response.code()} "
                )
            }
        } catch (e: Exception) {
            onError(e.message.toString())
            Log.d(
                "TAGonResponseGetLogin",
                "onResponse:${e.message}  "
            )
        }


    }
}