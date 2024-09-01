package com.example.compost.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compost.data.model.RegisterData
import com.example.compost.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registerState = MutableStateFlow<RegistrationState>(RegistrationState.Initial)
    val registerState: StateFlow<RegistrationState> = _registerState

    fun registerUser(email: String, username: String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegistrationState.Loading
            try {
                val response = userRepository.registerAccount(email, username, password)
                if (response.status == 200 && response.data != null && response.data.isNotEmpty()) {
                    val registerData = response.data[0]
                    _registerState.value = RegistrationState.Success(registerData, response.message ?: "Registration successful")
                } else {
                    _registerState.value = RegistrationState.Error(response.message!!)
                }

            } catch (e: Exception) {
                // Log the exception for debugging purposes
                _registerState.value = RegistrationState.Error("Registration failed")
            }
        }
    }
}

sealed class RegistrationState {
    data object Initial : RegistrationState()
    data object Loading : RegistrationState()
    data class Success(val data: RegisterData, val message: String) : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}
