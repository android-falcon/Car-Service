package com.example.carservice.feature.sammaryTicket.domain.model

import com.google.gson.annotations.SerializedName


data class EmployeeResponse(
    @SerializedName("NAME")
    val name: String,
    @SerializedName("PHASE")
    val phase: String
)