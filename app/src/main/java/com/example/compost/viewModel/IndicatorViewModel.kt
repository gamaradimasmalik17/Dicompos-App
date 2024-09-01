package com.example.compost.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compost.data.model.CustomLineData
import com.example.compost.data.model.IndicatorRecord
import com.example.compost.data.model.Realtime
import com.example.compost.data.model.dataControl
import com.example.compost.data.repository.ControlRepository
import com.example.compost.data.repository.IndicatorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IndicatorViewModel(
    private val repository: IndicatorRepository,
    private val controlRepository: ControlRepository,
    context: Context
) : ViewModel() {

    private val _indicatorInfoState: MutableStateFlow<Realtime?> = MutableStateFlow(null)
    val indicatorInfoState: StateFlow<Realtime?> = _indicatorInfoState

    private val _lineDataState = MutableStateFlow<List<CustomLineData>>(emptyList())
    val lineDataState: StateFlow<List<CustomLineData>> = _lineDataState

    private val _lineDataRecord = MutableStateFlow<List<IndicatorRecord>>(emptyList())
    val lineDataRecord: StateFlow<List<IndicatorRecord>> = _lineDataRecord

    // StateFlow untuk menyimpan data dari fetchDataControl
    private val _dataControlState: MutableStateFlow<dataControl?> = MutableStateFlow(null)
    val dataControlState: StateFlow<dataControl?> = _dataControlState

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("CompostPreferences", Context.MODE_PRIVATE)
    fun fetchIndicatorInfo() {
        viewModelScope.launch {
            try {
                val info = withContext(Dispatchers.IO) {
                    repository.fetchIndicatorInfo()
                }
                _indicatorInfoState.value = info
            } catch (e: Exception) {
                e.printStackTrace()
                _indicatorInfoState.value = null
            }
        }
    }
    fun fetchDataChart() {
        viewModelScope.launch {
            try {
                var result=   repository.fetchRecordsData()
                withContext(Dispatchers.Main) {
                    _lineDataRecord.value =result
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _lineDataRecord.value = emptyList()
            }
        }
    }

    // Mem-fetch dataControl dari repository
    fun fetchDataControl() {
        viewModelScope.launch {
            try {
                val dataControl = withContext(Dispatchers.IO) {
                    repository.fetchDataControl()
                }
                _dataControlState.value = dataControl
            } catch (e: Exception) {
                e.printStackTrace()
                _dataControlState.value = null
            }
        }
    }

    // Mengatur target suhu dan menyimpan ke SharedPreferences
    fun setTemperature(targetMin: String, targetMax: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = try {
                withContext(Dispatchers.IO) {
                    controlRepository.setTemperature(targetMin.toInt(), targetMax.toInt())
                }
                sharedPreferences.edit().apply {
                    putString("targetMinTemperature", targetMin)
                    putString("targetMaxTemperature", targetMax)
                    apply()
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
            onResult(success)
        }
    }

    // Mengatur target kelembaban dan menyimpan ke SharedPreferences
    fun setMoisture(targetMin: String, targetMax: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = try {
                withContext(Dispatchers.IO) {
                    controlRepository.setMoisture(targetMin.toInt(), targetMax.toInt())
                }
                sharedPreferences.edit().apply {
                    putString("targetMinMoisture", targetMin)
                    putString("targetMaxMoisture", targetMax)
                    apply()
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
            onResult(success)
        }
    }

    // Memuat nilai yang disimpan dari SharedPreferences saat aplikasi dimulai
    fun loadSavedValues(
        targetMinTemperature: MutableState<String>,
        targetMaxTemperature: MutableState<String>,
        targetMinMoisture: MutableState<String>,
        targetMaxMoisture: MutableState<String>
    ) {
        targetMinTemperature.value = sharedPreferences.getString("targetMinTemperature", "") ?: ""
        targetMaxTemperature.value = sharedPreferences.getString("targetMaxTemperature", "") ?: ""
        targetMinMoisture.value = sharedPreferences.getString("targetMinMoisture", "") ?: ""
        targetMaxMoisture.value = sharedPreferences.getString("targetMaxMoisture", "") ?: ""
    }
}
