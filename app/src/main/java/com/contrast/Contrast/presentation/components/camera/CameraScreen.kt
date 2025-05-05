package com.contrast.Contrast.presentation.components.camera



import android.Manifest
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun CameraScreen(
    onCaptureCompleted: (Uri) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var videoCapture: VideoCapture<Recorder>? by remember { mutableStateOf(null) }
    var recording: Recording? by remember { mutableStateOf(null) }
    var isVideoMode by remember { mutableStateOf(false) }

    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    DisposableEffect(Unit) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = androidx.camera.core.Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder().build()
        val recorder = Recorder.Builder().setQualitySelector(QualitySelector.from(Quality.HIGHEST)).build()
        videoCapture = VideoCapture.withOutput(recorder)

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                *(if (isVideoMode) arrayOf(videoCapture) else arrayOf(imageCapture))
            )
        } catch (exc: Exception) {
            exc.printStackTrace()
        }

        onDispose {
            cameraProvider.unbindAll()
            cameraExecutor.shutdown()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("ẢNH", color = if (!isVideoMode) Color.White else Color.Gray,
                modifier = Modifier.clickable { isVideoMode = false }.padding(16.dp))

            Button(onClick = {
                val file = File(
                    context.getExternalFilesDir(null),
                    (if (isVideoMode) "VID" else "IMG") + "_${System.currentTimeMillis()}.mp4"
                )

                if (isVideoMode) {
                    val outputOptions = FileOutputOptions.Builder(file).build()
                    val activeRecording = videoCapture?.output?.prepareRecording(context, outputOptions)
                        ?.withAudioEnabled()?.start(ContextCompat.getMainExecutor(context)) { recordEvent ->
                            if (recordEvent is VideoRecordEvent.Finalize) {
                                recordEvent.outputResults.outputUri?.let { onCaptureCompleted(it) }
                            }
                        }
                    recording = activeRecording
                } else {
                    imageCapture?.takePicture(
                        ImageCapture.OutputFileOptions.Builder(file).build(),
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onError(exception: ImageCaptureException) {
                                exception.printStackTrace()
                            }

                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                output.savedUri?.let { onCaptureCompleted(it) }
                            }
                        }
                    )
                }
            }) {
                Text(if (isVideoMode) "Ghi" else "Chụp")
            }

            Text("VIDEO", color = if (isVideoMode) Color.White else Color.Gray,
                modifier = Modifier.clickable { isVideoMode = true }.padding(16.dp))
        }
    }
}
