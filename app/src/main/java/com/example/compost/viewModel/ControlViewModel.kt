package com.example.compost.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compost.data.model.CustomLineData
import com.example.compost.data.model.Realtime
import com.example.compost.data.repository.IndicatorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ControlViewModel(private val repository: IndicatorRepository) : ViewModel() {
    private val _indicatorInfoState: MutableStateFlow<Realtime?> = MutableStateFlow(null)
    val indicatorInfoState: StateFlow<Realtime?> = _indicatorInfoState

    private val _lineDataState = MutableStateFlow<List<CustomLineData>>(emptyList())
    val lineDataState: StateFlow<List<CustomLineData>> = _lineDataState

    fun fetchIndicatorInfo() {
        // Panggil repository untuk mendapatkan informasi indikator
        viewModelScope.launch {
            val info = repository.fetchIndicatorInfo()
            _indicatorInfoState.value=info
        }
    }
    fun fetchDataChart(nameIndicator: String) {
        viewModelScope.launch {
            val lineData = repository.fetchRecordsData(nameIndicator)
            _lineDataState.value = lineData
        }
    }
}