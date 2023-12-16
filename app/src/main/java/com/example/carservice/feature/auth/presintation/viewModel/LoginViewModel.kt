package com.example.carservice.feature.auth.presintation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carservice.feature.auth.domain.model.UserModel
import com.example.carservice.feature.auth.domain.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()
    private val _userListLiveData = MutableLiveData<List<UserModel>>()
    val userListLiveData: LiveData<List<UserModel>> = _userListLiveData
     val errorLiveData = MutableLiveData<String>()

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    fun loginUser(companyNumber: String, userNumber: String) {
        viewModelScope.launch {
            _loadingLiveData.value = true
            try {
                repository.getLogin(
                    companyNumber = companyNumber,
                    userNumber=userNumber,
                    onSuccess = {
                        _userListLiveData.value = it
                        Log.d("TAGLoginViewModel", "loginUser:${it} ")
                    },
                    onError = {
                        errorLiveData.value = it
                        Log.d("TAGLoginViewModel", "Error:${it} ")
                    }

                )
            } catch (e: Exception) {
                Log.d("TAGLoginViewModel", "Error:${e.localizedMessage} ")
                errorLiveData.value = e.localizedMessage

            }
            _loadingLiveData.value=false


        }

    }
}