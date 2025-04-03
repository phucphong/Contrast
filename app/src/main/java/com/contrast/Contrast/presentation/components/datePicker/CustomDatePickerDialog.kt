package com.contrast.Contrast.presentation.components.datePicker

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
fun CustomDatePickerDialog(
    initialDate: String? = null,  // Chuỗi định dạng "dd/MM/yyyy"
    minDate: String? = null,  // Chuỗi định dạng "dd/MM/yyyy"
    maxDate: String? = null,  // Chuỗi định dạng "dd/MM/yyyy"
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
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
                    .navigationBarsPadding()
                    .padding(),
                color = Color.White
            ) {
                IOSStyleWheelDatePicker(
                    initialDate = initialDate,
                    minDate = minDate,
                    maxDate = maxDate,
                    onDateSelected = {
                        onDateSelected(it)
                        onDismiss()
                    }
                )
            }
        }
    }
}
