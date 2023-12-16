package com.example.carservice.feature.detail.domain.model

import com.google.gson.annotations.SerializedName

data class TimeModel (
    @SerializedName("DURATIONTIME")
    var time:String,
    @SerializedName("PHASE1TIME")
    var timePhase1:String,
    @SerializedName("PHASE2TIME")
    var timePhase2:String,
    @SerializedName("PHASE3TIME")
    var timePhase3:String,

)