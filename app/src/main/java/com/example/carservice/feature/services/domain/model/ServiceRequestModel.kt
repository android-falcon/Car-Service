package com.example.carservice.feature.services.domain.model

import com.google.gson.annotations.SerializedName



    data class ServiceDtlModel(
    @SerializedName("DTL")
    val dtl: List<ServiceBodyModel>
)

