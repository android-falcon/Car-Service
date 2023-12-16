package com.example.carservice.feature.auth.domain.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("USER_NO")
    val userNumber: String,
    @SerializedName("USER_NAME")
    val userName: String,
    @SerializedName("USER_TYPE")
    val userType: String,
    @SerializedName("PASSWORD")
    val password: String,
)
