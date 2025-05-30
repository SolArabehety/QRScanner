package com.solara.qrscanner.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solara.core.onError
import com.solara.core.onSuccess
import com.solara.core.utils.QrCodeGenerator
import com.solara.domain.model.SeedError
import com.solara.domain.usecases.GenerateSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
internal class CreateQRViewModel @Inject constructor(
    private val stringMapper: StringMapper,
    private val qrCodeGenerator: QrCodeGenerator,
    private val generateSeedUseCase: GenerateSeedUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<CreateQRUiState>(CreateQRUiState.Loading)
    val uiState: StateFlow<CreateQRUiState> = _uiState

    fun generateNewSeed() {
        viewModelScope.launch {
            generateSeedUseCase.invoke().onSuccess { seed ->
                val image = qrCodeGenerator.generate(seed.value, 500)

                if (image != null) {
                    _uiState.value =
                        CreateQRUiState.Success(image, seed.value, seed.getSecondsRemaining())

                } else {
                    _uiState.value =
                        CreateQRUiState.Error(stringMapper.mapErrorString(SeedError.InvalidQRGeneration))
                }
            }.onError { error ->
                val message = stringMapper.mapErrorString(error)
                _uiState.value = CreateQRUiState.Error(message)
            }
        }
    }

}

sealed interface CreateQRUiState {
    data object Loading : CreateQRUiState
    data class Error(val message: String) : CreateQRUiState
    data class Success(val qrImage: Bitmap, val qrValue: String, val expiresInSeconds: Int) :
        CreateQRUiState
}
