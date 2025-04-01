package com.contrast.Contrast.presentation.features.security




import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.BiometricDisableDialog
import com.contrast.Contrast.presentation.components.alertDialog.CustomBiometricDialog

import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.switch_custom.IOSSwitch
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.theme.FFD91E18
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SecurityScreen(
    isNotificationEnabled: Boolean,
    isFaceIDEnabled: Boolean,
    onBackPressed: () -> Unit,
    onToggleNotification: (Boolean) -> Unit,
    onToggleFaceID: (Boolean) -> Unit
) {
    var showDialog by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().background(FCFCFC).padding(top = 30.dp)) {
        // Top Bar

        CustomTopAppBarBackTitle(
            title = stringResource(R.string.security),
            FFD91E18,
            onBackClick = { }
        )


        // Notification toggle row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.change_password),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )

            IOSSwitch(
                checked = isNotificationEnabled,
                onCheckedChange = { onToggleNotification(it) },

                )


        }
        CustomDividerColor()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.lock_with_face_id),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )

            IOSSwitch(
                checked = isFaceIDEnabled,
                onCheckedChange = { onToggleNotification(it) },

                )


        }

        CustomDividerColor()
// thông báo nếu chưa có quyền truy cập thông báo

//        FaceIDPermissionDialog(
//            onConfirm = { showDialog = false /* xử lý tiếp */ },
//            onDismiss = { showDialog = false }
//        )


//đăng xuất khi quên mật khẩu
      //  LogoutConfirmDialog({},{})

        //bât sinh trắc học
       // BiometricDisableDialog({},{})
  //bât sinh trắc học
        CustomBiometricDialog({},{})


    }
}
