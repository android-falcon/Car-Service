package com.example.carservice.feature.detail.domain.model

import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    @SerializedName("VHFNO")
    val voucherNumber: String,
    @SerializedName("VSERIAL")
    val vSerial: String,
    @SerializedName("ITEMCODE")
    val itemCode: String,
    @SerializedName("ITEMNAME")
    val itemName: String,

    )