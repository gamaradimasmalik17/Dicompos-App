import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ParameterViewModel : ViewModel() {

    private val _targetTemperatureMin = MutableStateFlow("")
    val targetTemperatureMin: StateFlow<String> = _targetTemperatureMin

    private val _targetTemperatureMax = MutableStateFlow("")
    val targetTemperatureMax: StateFlow<String> = _targetTemperatureMax

    private val _targetPhMin = MutableStateFlow("")
    val targetPhMin: StateFlow<String> = _targetPhMin

    private val _targetPhMax = MutableStateFlow("")
    val targetPhMax: StateFlow<String> = _targetPhMax

    fun setTargetTemperatureMin(value: String) {
        viewModelScope.launch {
            _targetTemperatureMin.value = value
        }
    }

    fun setTargetTemperatureMax(value: String) {
        viewModelScope.launch {
            _targetTemperatureMax.value = value
        }
    }

    fun setTargetPhMin(value: String) {
        viewModelScope.launch {
            _targetPhMin.value = value
        }
    }

    fun setTargetPhMax(value: String) {
        viewModelScope.launch {
            _targetPhMax.value = value
        }
    }
}
