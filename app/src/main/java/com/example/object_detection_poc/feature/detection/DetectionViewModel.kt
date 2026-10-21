package com.example.object_detection_poc.feature.detection

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetectionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DetectionState())
    val uiState: StateFlow<DetectionState> = _uiState.asStateFlow()

    fun onPermissionResult(granted: Boolean) {
        _uiState.update { it.copy(hasCameraPermission = granted) }
    }

    fun onChangeModel() {
        _uiState.update { current ->
            val models = current.availableModels
            val currentIndex = models.indexOf(current.currentModel)
            val nextIndex = (currentIndex + 1) % models.size
            current.copy(currentModel = models[nextIndex])
        }
    }

    fun updateDetectionResults(fps: Int, objectCount: Int) {
        _uiState.update { it.copy(fps = fps, detectedObjects = objectCount) }
    }

    fun setDetecting(detecting: Boolean) {
        _uiState.update { it.copy(isDetecting = detecting) }
    }
}