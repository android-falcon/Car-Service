package com.example.carservice.feature.services.domain.model

import com.google.gson.annotations.SerializedName

data class ServiceBodyModel(
    @SerializedName("ITEMCODE")
    var itemCode: String,
    @SerializedName("ITEMNAME")
    var itemName: String,

    )
