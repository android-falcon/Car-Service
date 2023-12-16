package com.example.carservice.feature.employee.domain.repository

import android.util.Log
import com.example.carservice.core.service.RetrofitClient.apiService
import com.example.carservice.feature.addTicket.domain.model.StatusModel
import com.example.carservice.feature.employee.domain.model.EmployeeModel

class EmployeeRepository {

    suspend fun getAllEmployee(
        companyNumber: String,
        phase: String,
        onSuccess: (List<EmployeeModel>) -> Unit,
        onError: (String) -> Unit,
    ) {
        try{
            val response= apiService.getEmployee(companyNumber,phase)
            if(response.isSuccessful){
                onSuccess(response.body()!!)
                Log.d("TAGgetAllEmployeeRepo", "getAllEmployee: ${response.body()!!}")
            }
            else{
                onError("Error is ${response.errorBody()}")
            }
        }catch (e:Exception){
            onError("Error is ${e.localizedMessage}")
        }
    }
    suspend fun startTicket(
        companyNumber: String,
        voucherNumber:String,
        team:String,
        phase: String,
        onSuccess: (StatusModel) -> Unit,
        onError: (String) -> Unit,

        ){
        try{
            val response= apiService.startTicket( cono=companyNumber,team=team,voucherNumber=voucherNumber, phase =phase )
            if(response.isSuccessful){
                onSuccess(response.body()!!)
            }
            else{
                onError("error code is "+response.errorBody().toString())
            }
        }catch (e:Exception){
            onError(e.localizedMessage)
        }

    }
}