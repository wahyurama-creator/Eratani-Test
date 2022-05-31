package com.way.apigorest.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.way.apigorest.data.model.User
import com.way.apigorest.network.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {
    val user: MutableLiveData<Response<List<User>>> = MutableLiveData()
    val register: MutableLiveData<Response<User>> = MutableLiveData()

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService().getUser()
                user.postValue(response)
            } catch (e: Exception) {
                Log.e("USER", e.message.toString())
            }
        }
    }

    fun registerUser(
        name: String, email: String, gender: String, status: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService().registerUser(name, email, gender, status)
                register.postValue(response)
            } catch (e: Exception) {
                Log.e("REGISTER", e.message.toString())
            }
        }
    }
}