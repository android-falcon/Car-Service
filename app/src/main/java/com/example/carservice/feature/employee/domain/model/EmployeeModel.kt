package com.example.carservice.feature.employee.domain.model

import com.google.gson.annotations.SerializedName

data class EmployeeModel(
    @SerializedName("CODE")
    val code: String,
    @SerializedName("NAME")
    val name: String,
    @SerializedName("PHONE")
    val phone: String,
    @SerializedName("ADDRES")
    val address: String,


    )