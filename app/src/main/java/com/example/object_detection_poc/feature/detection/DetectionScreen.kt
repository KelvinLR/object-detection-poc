package com.example.object_detection_poc.feature.detection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun DetectionScreen() {
    Scaffold(
        topBar = { CustomAppBar() }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            CameraPreview()
            Instructions()
            Status()
            ChangeModelButton()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar() {
    TopAppBar(
        title = { Text("Detecção de Objetos") }
    )
}

@Composable
fun CameraPreview() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    Box(
        Modifier
            .size(
                width = ((358f / 390f) * screenWidth).dp,
                height = ((318f / 844f) * screenHeight).dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFFBFCABA), androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {

        }
    }
}

@Composable
fun Instructions() {
    Text("Aponte a câmera para um objeto")
}

@Composable
fun Status() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Framerate: %FPS%")
        Text("Objects: %OBJECTS%")
        Text("Model: %MODEL%")
    }
}

@Composable
fun ChangeModelButton() {
    Button(onClick = {}) {
        Text("Mudar modelo")
    }
}