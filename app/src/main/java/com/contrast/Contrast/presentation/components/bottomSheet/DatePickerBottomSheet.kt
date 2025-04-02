package com.contrast.Contrast.presentation.components.bottomSheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.material3.ModalBottomSheetDefaults
import com.contrast.Contrast.extensions.IOSStyleDatePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {}, // ✅ Không tự đóng khi click outside hoặc back
        sheetState = sheetState,

    ) {
        IOSStyleDatePicker(
            onDateSelected = { date ->
                onDateSelected(date)
            },
            onDone = {
                onDismiss() // Gọi hide() và cập nhật lại state ở ngoài
            }
        )
    }
}
