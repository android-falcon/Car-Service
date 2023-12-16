package com.example.carservice.feature.services.domain.model

import com.google.gson.annotations.SerializedName

data class ServiceModel(
    @SerializedName("ItemOCode")
    var itemCode: String,
    @SerializedName("ItemNameA")
    var nameInArabic: String,
    @SerializedName("ItemNameE")
    var nameEnglish: String,
    )