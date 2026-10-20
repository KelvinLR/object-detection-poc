package com.example.object_detection_poc.feature.detection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetectionScreen() {
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            CameraPreview()
            Greetings()
        }
    }
}

@Composable
fun CameraPreview() {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(Color.Red)
    )
}

@Composable
fun Greetings() {
    Text("Aponte a câmera para um objeto")
}