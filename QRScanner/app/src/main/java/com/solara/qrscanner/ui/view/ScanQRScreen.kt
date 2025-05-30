package com.solara.qrscanner.ui.view

import android.Manifest
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.solara.core.utils.QRCodeAnalyzer
import com.solara.qrscanner.R
import com.solara.qrscanner.ui.viewmodel.CreateQRUiState
import com.solara.qrscanner.ui.viewmodel.CreateQRViewModel
import com.solara.qrscanner.ui.viewmodel.ScanQRUiState
import com.solara.qrscanner.ui.viewmodel.ScanQRViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun ScanQRScreen(
    viewModel: ScanQRViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!cameraPermissionState.status.isGranted) {
            PermissionMessage(permissionState = cameraPermissionState)
        }

        when (uiState) {
            is ScanQRUiState.Scan -> {
                if (cameraPermissionState.status.isGranted) {
                    CameraView(onBarcodeScanned = { scannedValue ->
                        viewModel.validateQRValue(scannedValue)
                    })
                } else {
                    PermissionMessage(permissionState = cameraPermissionState)
                }
            }

            is ScanQRUiState.Loading -> LoadingScreen()
            is ScanQRUiState.Error -> ErrorScreen("error")
            is ScanQRUiState.Success -> SuccessMessage(true)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionMessage(permissionState: PermissionState) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            permissionState.status.shouldShowRationale -> {
                Text("Camera permission was permanently denied.")
            }

            else -> {
                Text("Requesting camera permission...")
            }
        }
    }
}


@Composable
private fun CameraView(onBarcodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx)

            val preview = androidx.camera.core.Preview.Builder().build().apply {
                surfaceProvider = previewView.surfaceProvider
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .apply {
                    setAnalyzer(
                        ContextCompat.getMainExecutor(ctx),
                        QRCodeAnalyzer { barcodeValue ->
                            onBarcodeScanned(barcodeValue)
                        }
                    )
                }

            runCatching {
                cameraProviderFuture.get().bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageAnalysis
                )
            }.onFailure {
                Log.e("CameraView", "Camera initialization error: ${it.localizedMessage}", it)
            }

            previewView
        }
    )
}


@Composable
private fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.il_error),
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .aspectRatio(1f)
            )
            Text(
                text = stringResource(R.string.error_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun SuccessMessage(isValid: Boolean) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Text(
            text = "QR Code validation result: $isValid",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

