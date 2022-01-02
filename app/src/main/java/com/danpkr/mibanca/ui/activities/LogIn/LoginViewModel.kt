package com.danpkr.mibanca.ui.activities.LogIn

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danpkr.mibanca.app.AppConfig
import com.danpkr.mibanca.domain.UserService
import com.danpkr.mibanca.ui.models.EventType
import com.danpkr.mibanca.ui.models.UiEvent
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel: ViewModel() {
    val event = MutableLiveData<UiEvent>()

    fun LoginUser(email:String, clearPassword: String){
        viewModelScope.launch {
            try {
                AppConfig.LoggedUser = UserService.LoginUser(email,clearPassword)
                event.postValue(UiEvent(true,EventType.USER_LOGIN))
            }catch ( e: Exception){
                Log.d("loginUser", "loginUser: ${e.message}")
                event.postValue(UiEvent(false,EventType.USER_LOGIN,e.message))
            }
        }
    }

    fun RegisterNewUser(name: String, lastName: String,email:String, clearPassword: String){
        viewModelScope.launch {
            try {
                UserService.CreateNewUser(name,lastName,email,clearPassword)
                event.postValue(UiEvent(true,EventType.USER_SIGNUP))
            }catch ( e: Exception){
                Log.d("registerNewUser", "registerNewUser: ${e.message}")
                event.postValue(UiEvent(false,EventType.USER_SIGNUP,e.message))
            }
        }
    }
}