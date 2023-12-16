package com.example.carservice.core.constant

class ApiException(val errorCode: String, errorMessage: String) : Exception(errorMessage)
