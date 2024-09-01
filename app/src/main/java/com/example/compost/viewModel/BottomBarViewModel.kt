package com.example.compost.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compost.data.repository.ControlRepository
import kotlinx.coroutines.launch

class BottomBarViewModel(private val controlRepository: ControlRepository) : ViewModel() {

    fun togglePowerState(isPowerIcon: Boolean, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = try {
                if (!isPowerIcon) {
                    controlRepository.deactivate()
                } else {
                    controlRepository.activate()
                }
                true // If no exception, return success as true
            } catch (e: Exception) {
                false // If there’s an exception, return success as false
            }
            onSuccess(success)
        }
    }

}


