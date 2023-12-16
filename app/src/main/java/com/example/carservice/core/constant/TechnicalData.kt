package com.example.carservice.core.constant

import android.content.Context
import android.content.SharedPreferences

object TechnicalData {
    lateinit var BASE_URL: String
    lateinit var COMPANY_NUMBER: String
    lateinit var COMPANY_NAME: String
    lateinit var DLL_NAME: String

    private const val PREFERENCES_NAME = "TechnicalDataPreferences"

    // Initialize SharedPreferences
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun saveData(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.putString("BASE_URL", BASE_URL)
        editor.putString("COMPANY_NUMBER", COMPANY_NUMBER)
        editor.putString("COMPANY_NAME", COMPANY_NAME)
        editor.putString("DLL_NAME", DLL_NAME)
        editor.apply()
    }

    fun loadData(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        BASE_URL = sharedPreferences.getString("BASE_URL", "") ?: ""
        COMPANY_NUMBER = sharedPreferences.getString("COMPANY_NUMBER", "") ?: ""
        COMPANY_NAME = sharedPreferences.getString("COMPANY_NAME", "") ?: ""
        DLL_NAME = sharedPreferences.getString("DLL_NAME", "") ?: ""
    }
}