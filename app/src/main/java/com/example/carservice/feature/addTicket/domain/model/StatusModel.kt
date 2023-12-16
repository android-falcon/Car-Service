package com.example.carservice.feature.addTicket.domain.model

import com.google.gson.annotations.SerializedName

data class StatusModel(
    @SerializedName("ErrorCode")
    var errorCode: String,

    @SerializedName("ErrorDesc")
    var errorDescription: String
)