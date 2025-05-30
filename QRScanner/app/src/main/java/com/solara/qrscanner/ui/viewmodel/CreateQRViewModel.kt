package com.solara.qrscanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solara.core.onError
import com.solara.core.onSuccess
import com.solara.domain.usecases.GenerateSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
internal class CreateQRViewModel @Inject constructor(
    private val generateSeedUseCase: GenerateSeedUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<CreateQRUiState>(CreateQRUiState.Loading)
    val uiState: StateFlow<CreateQRUiState> = _uiState


    fun generateNewSeed() {
        viewModelScope.launch {
            generateSeedUseCase.invoke().onSuccess { seed ->
                _uiState.value = CreateQRUiState.Success(seed)
            }.onError { error ->
                _uiState.value = CreateQRUiState.Error(error)
            }
        }

    }

}

sealed interface CreateQRUiState {
    data object Loading : CreateQRUiState
    data class Error(val message: String) : CreateQRUiState
    data class Success(val value: String) : CreateQRUiState
}
