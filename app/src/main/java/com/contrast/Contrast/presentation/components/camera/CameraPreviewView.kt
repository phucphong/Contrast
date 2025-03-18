package com.contrast.Contrast.presentation.components.camera

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@Composable
fun CameraPreviewView(
    context: Context,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    cameraExecutor: java.util.concurrent.ExecutorService
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray), // Màu nền xám
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                val previewView = androidx.camera.view.PreviewView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                cameraProviderFuture.addListener({
                    Handler(Looper.getMainLooper()).post {
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val imageAnalyzer = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                            .also {
                                it.setAnalyzer(cameraExecutor) { imageProxy ->
                                    processBarcodeImage(imageProxy, context)
                                }
                            }

                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageAnalyzer
                            )
                        } catch (e: Exception) {
                            Log.e("CameraX", "Use case binding failed", e)
                        }
                    }
                }, cameraExecutor)

                previewView
            }
        )

        // Khung quét QR với 4 góc 1/2 hình vuông
        Box(
            modifier = Modifier
                .size(250.dp, 250.dp) // Kích thước giống SwiftUI
                .background(Color.White.copy(alpha = 0.2f)) // Màu nền trắng với opacity 20%
        ) {
            Canvas(
                modifier = Modifier
                    .size(250.dp, 250.dp)
                    .background(Color.Transparent)
            ) {
                val strokeWidth = 2.dp.toPx()
                val cornerLength = 25.dp.toPx()
                val color = Color.White

                // Góc trên trái
                drawLine(color, Offset(0f, 0f), Offset(cornerLength, 0f), strokeWidth)
                drawLine(color, Offset(0f, 0f), Offset(0f, cornerLength), strokeWidth)

                // Góc trên phải
                drawLine(color, Offset(size.width, 0f), Offset(size.width - cornerLength, 0f), strokeWidth)
                drawLine(color, Offset(size.width, 0f), Offset(size.width, cornerLength), strokeWidth)

                // Góc dưới trái
                drawLine(color, Offset(0f, size.height), Offset(cornerLength, size.height), strokeWidth)
                drawLine(color, Offset(0f, size.height), Offset(0f, size.height - cornerLength), strokeWidth)

                // Góc dưới phải
                drawLine(color, Offset(size.width, size.height), Offset(size.width - cornerLength, size.height), strokeWidth)
                drawLine(color, Offset(size.width, size.height), Offset(size.width, size.height - cornerLength), strokeWidth)
            }
        }
    }
}

@OptIn(ExperimentalGetImage::class)
fun processBarcodeImage(imageProxy: ImageProxy, context: Context) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val scanner = BarcodeScanning.getClient()

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    Log.d("QRScanner", "Barcode detected: ${barcode.rawValue}")
                }
            }
            .addOnFailureListener { e ->
                Log.e("QRScanner", "Barcode scanning failed", e)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}