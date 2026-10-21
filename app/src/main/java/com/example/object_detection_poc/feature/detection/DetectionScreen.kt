package com.example.object_detection_poc.feature.detection

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetectionScreen(
    viewModel: DetectionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onPermissionResult(isGranted)
    }

    // Check initial permission and request if needed
    LaunchedEffect(Unit) {
        val alreadyGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (alreadyGranted) {
            viewModel.onPermissionResult(true)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

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
            CameraPreview(hasCameraPermission = uiState.hasCameraPermission)
            Instructions()
            Status(
                framerate = uiState.fps,
                objects = uiState.detectedObjects,
                model = uiState.currentModel
            )
            ChangeModelButton(onClick = viewModel::onChangeModel)
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
fun CameraPreview(hasCameraPermission: Boolean) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val shape = RoundedCornerShape(10.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(
                width = ((358f / 390f) * screenWidth).dp,
                height = ((318f / 844f) * screenHeight).dp,
            )
            .clip(shape)
            .border(1.dp, Color(0xFFBFCABA), shape)
    ) {
        if (hasCameraPermission) {
            CameraViewFinder()
        } else {
            Text(
                text = "Permissão de câmera necessária",
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CameraViewFinder() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    )

    LaunchedEffect(lifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview
                )
            } catch (e: Exception) {
                Log.e("CameraPreview", "Falha ao iniciar câmera", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }
}

@Composable
fun Instructions() {
    Text("Aponte a câmera para um objeto")
}

@Composable
fun Status(framerate: Int, objects: Int, model: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("FPS: $framerate")
        Text("Objects: $objects")
        Text("Model: $model")
    }
}

@Composable
fun ChangeModelButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Mudar modelo")
    }
}