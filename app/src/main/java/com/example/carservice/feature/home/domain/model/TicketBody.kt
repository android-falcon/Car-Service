package com.example.carservice.feature.home.domain.model

import com.example.carservice.feature.services.domain.model.ServiceDtlModel
import com.google.gson.annotations.SerializedName

data class TicketBody(
    @SerializedName("PHONE_NUMBER")
    val phoneNumber: String,
    @SerializedName("CUSTOMER_NAME")
    var customerName: String,
    @SerializedName("CUSTOMER_TYPE")
    var customerType: String,
    @SerializedName("CAR_ID")
    var carId: String,
    @SerializedName("CAR_TYPE")
    var carType: String,
    @SerializedName("CAR_COLOR")
    var carColor: String,
    @SerializedName("HOWMANY")
    var serviceNumber: String,
    @SerializedName("NOTE")
    var note: String,
    @SerializedName("USER_NO")
    var userNumber: String,
    @SerializedName("SERVICES")
    var services: ServiceDtlModel,
    @SerializedName("CAR_MODEL")
    var carModel:String
    )
