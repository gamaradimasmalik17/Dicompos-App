package com.example.compost.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compost.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _fullName = MutableStateFlow<String?>(null)
    val fullName: StateFlow<String?> = _fullName

    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> = _email


    fun fetchUserFullName(username: String) {
        viewModelScope.launch {
            val user = userRepository.getCurrentUser(username = username)
            _fullName.value = user?.fullName
            _email.value = user?.email
        }
    }

}
