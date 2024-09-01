package com.example.compost.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compost.data.model.UserData
import com.example.compost.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = userRepository.login(email, password)
                if (response.status == 200) {
                    _loginState.value = LoginState.Success(response.data)
                } else {
                    _loginState.value = LoginState.Error(response.message!!)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Login failed")
            }
        }
    }

    fun logout() {
        userRepository.logout()
        // Additional cleanup if necessary
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: UserData?) : LoginState()
    data class Error(val message: String) : LoginState()
}
