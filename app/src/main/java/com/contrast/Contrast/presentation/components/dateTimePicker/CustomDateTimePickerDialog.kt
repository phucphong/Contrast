package com.contrast.Contrast.presentation.components.dateTimePicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun CustomDateTimePickerDialog(
    initialDateTime: String? = null,  // Chuỗi định dạng "dd/MM/yyyy HH:mm"
    minDateTime: String? = null,  // Chuỗi định dạng "dd/MM/yyyy HH:mm"
    maxDateTime: String? = null,  // Chuỗi định dạng "dd/MM/yyyy HH:mm"
    onDismiss: () -> Unit,
    onDateTimeSelected: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .navigationBarsPadding().padding(),

                color = Color.White
            ) {
                IOSStyleWheelDateTimePicker(
                    initialDateTime=initialDateTime,
                    minDateTime=minDateTime,
                    maxDateTime =maxDateTime,
                onDateTimeSelected = {
                    onDateTimeSelected(it)
                    onDismiss()
                }
            )
        }
    }}
}
