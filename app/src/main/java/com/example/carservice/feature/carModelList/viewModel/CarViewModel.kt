package com.example.carservice.feature.carModelList.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarViewModel:ViewModel() {
    private val name = MutableLiveData<String>()
    private val carCode = MutableLiveData<String>()
    private val plateNumber = MutableLiveData<String>()
    private val phoneNumber = MutableLiveData<String>()
    private val carType = MutableLiveData<String>()
    private val carModel = MutableLiveData<String>()
    private val colorCar = MutableLiveData<String>()
    private val note = MutableLiveData<String>()
    fun getNote() = note.value
    fun setNote(text: String) {
        note.value = text
    }

    fun setColorCar(color: String) {
        colorCar.value = color
    }

    fun getCarColor() = colorCar.value
    fun setPlateNumber(number: String) {
        plateNumber.value = number
    }

    fun setCarModel(number: String) {
        carModel.value = number
    }

    fun getCarModel() = carModel.value

    fun setCarType(type: String) {
        carType.value = type
    }

    fun getCarType() = carType.value

    fun getPhone() = phoneNumber.value
    fun setPhoneNumber(number: String) {
        phoneNumber.value = number
    }

    fun getPlateNumber() = plateNumber.value

    fun setName(newData: String) {
        name.value = newData
    }

    fun setCarCode(code: String) {
        carCode.value = code
    }

    fun getCarCode() = carCode.value

    fun getName() = name
}