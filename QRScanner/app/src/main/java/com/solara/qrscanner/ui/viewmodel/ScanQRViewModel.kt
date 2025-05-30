package com.solara.qrscanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
internal class ScanQRViewModel @Inject constructor(
    private val stringMapper: StringMapper,
) : ViewModel() {

    companion object {
        private const val TAG = "ScanQRViewModel"

    }

    private val _uiState = MutableStateFlow<ScanQRUiState>(ScanQRUiState.Scan)
    val uiState: StateFlow<ScanQRUiState> = _uiState.asStateFlow()


}

sealed interface ScanQRUiState {
    data object Scan : ScanQRUiState
    data object Loading : ScanQRUiState
    data class Error(val message: String) : ScanQRUiState
    data class Success(val isValid: Boolean) : ScanQRUiState
}
