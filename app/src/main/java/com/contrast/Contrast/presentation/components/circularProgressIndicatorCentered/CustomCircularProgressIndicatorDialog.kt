package com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.CustomAlertDialog
import com.itechpro.domain.model.NetworkResponse
import kotlinx.coroutines.delay
@Composable
fun <T> CustomCircularProgressIndicatorDialog(
    networkState: NetworkResponse<T>,
    onDismiss: () -> Unit, // ✅ Callback khi dialog đóng
    size: Dp = 40.dp,
    strokeWidth: Dp = 5.dp
) {
    var showDialog by remember { mutableStateOf(true) } // ✅ Kiểm soát hiển thị Dialog
    var errorMessage by remember { mutableStateOf<String?>(null) } // ✅ Lưu thông báo lỗi
    val timeoutMessage = stringResource(R.string.timeout_message) // ✅ Lưu trước khi dùng

    // ✅ Timeout 30 giây nếu vẫn đang Loading
    LaunchedEffect(Unit) {
        delay(30000) // 30 giây
        if (networkState is NetworkResponse.Loading) {
            showDialog = false
            errorMessage = timeoutMessage

        // ✅ Lỗi timeout
        }
    }

    // ✅ Theo dõi trạng thái NetworkResponse để tự động ẩn Dialog & hiển thị lỗi nếu có
    LaunchedEffect(networkState) {
        when (networkState) {
            is NetworkResponse.Success, is NetworkResponse.Error -> {
                showDialog = false // ✅ Ẩn dialog khi thành công hoặc lỗi
                if (networkState is NetworkResponse.Error) {
                    errorMessage = networkState.message // ✅ Lưu lỗi để hiển thị Dialog lỗi
                }
            }
            is NetworkResponse.Loading -> {
                showDialog = true // ✅ Hiển thị Dialog khi đang tải
            }
        }
    }

    // ✅ Hiển thị Dialog chứa Loading Indicator nếu đang tải
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() }, // ✅ Gọi callback khi đóng
            properties = DialogProperties(usePlatformDefaultWidth = false), // ✅ Tùy chỉnh kích thước Dialog
            modifier = Modifier.width(90.dp).height(90.dp), // ✅ Set kích thước cố định
            text = {
                Box(
                    modifier = Modifier.fillMaxSize(), // ✅ Đảm bảo loading nằm giữa
                    contentAlignment = Alignment.Center
                ) {
                    CustomCircularProgressIndicator(size = size, strokeWidth = strokeWidth)
                }
            },
            confirmButton = {} // ✅ Không cần nút bấm
        )
    }

    // ✅ Hiển thị Dialog nếu có lỗi hoặc timeout
    errorMessage?.let { message ->
        CustomAlertDialog(
            message = message,
            onDismiss = { errorMessage = null } // ✅ Đóng Dialog khi bấm OK
        )
    }
}
