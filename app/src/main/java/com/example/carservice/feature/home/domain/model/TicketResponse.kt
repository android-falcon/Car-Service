package com.example.carservice.feature.home.domain.model

import com.google.gson.annotations.SerializedName

data class TicketResponse(
    @SerializedName("VHFNO")
    val voucherNumber: String,

    @SerializedName("PHONE_NUMBER")
    val phoneNumber: String,

    @SerializedName("CUSTOMER_NAME")
    val customerName: String,

    @SerializedName("CUSTOMER_TYPE")
    val customerType: String,

    @SerializedName("CAR_ID")
    val carId: String,

    @SerializedName("CAR_TYPE")
    val carType: String,

    @SerializedName("CAR_MODEL")
    val carModel: String,

    @SerializedName("CAR_COLOR")
    val carColor: String,

    @SerializedName("HOWMANY")
    val serviceNumber: String,

    @SerializedName("TEAM_NO")
    val teamNumber: String,

    @SerializedName("STATEUS")
    val status: String,

    @SerializedName("INS_DATE")
    val insDate: String,

    @SerializedName("INS_TIME")
    val insTime: String,

    @SerializedName("START_DATE")
    val startDate: String,

    @SerializedName("START_TIME")
    val startTime: String,

    @SerializedName("END_DATE")
    val endDate: String,

    @SerializedName("END_TIME")
    val endTime: String,

    @SerializedName("PHASE")
    val phase: String,

    @SerializedName("DURATIONTIME")
    val time: String,

    @SerializedName("PHASE1TIME")
    val timePhase1: String,

    @SerializedName("PHASE2TIME")
    val timePhase2: String,

    @SerializedName("PHASE3TIME")
    val timePhase3: String ,
    @SerializedName("PHASE1_TIME")
    val timePhase1ForCV: String,

    @SerializedName("PHASE2_TIME")
    val timePhase2ForCV: String,

    @SerializedName("PHASE3_TIME")
    val timePhase3ForCV: String


) {
    fun titleForCV() = "${carType} ${carColor}"
    fun insTimeForCV() = "Entry Time : ${insTime}"
    fun startTimeForCV() = "Start Time  : ${startTime}"
    fun endTimeForCV() = "End Time  : ${endTime}"


}