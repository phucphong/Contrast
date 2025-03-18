package com.contrast.Contrast.presentation.features.qrscanner.ui


import android.content.Context

import android.util.Log

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.core.Red18
import com.contrast.Contrast.presentation.components.camera.CameraPreviewView
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTittle
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScannerScreenPreview() {
    QRScannerScreen(onBackPress = {})
}

@Composable
fun QRScannerScreen(onBackPress: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Red18), // Nền đỏ
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        CustomTopAppBarBackTittle(
            title = "Quét mã QR",
            Color.White,
            Red18,
            FontWeight.Medium,
            Color.White,
            onBackClick = { onBackPress() }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Camera Preview Box
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(500.dp)
                .clip(RoundedCornerShape(16.dp)) // Bo tròn 4 góc
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            CameraPreviewView(context, lifecycleOwner, cameraExecutor)

            Text(
                text = stringResource(id = R.string.qr_scan_instruction),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp)
            )


        }
        }

}




