package com.contrast.Contrast.presentation.components.alertDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


import androidx.compose.material3.Text

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.AEA1F27
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.TealGreen

@Composable
fun ConfirmDeleteDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String = stringResource(R.string.dialog_confirm_delete_title),
    message: String = stringResource(R.string.dialog_confirm_delete_message),
    confirmText: String = stringResource(R.string.dialog_confirm_button),
    dismissText: String = stringResource(R.string.dialog_cancel_button)
) {
    if (!show) return

    Dialog(
        onDismissRequest = {}, // Không đóng khi nhấn ra ngoài
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Bold,
                    color = FF151515,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            Text(
                text = message,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 15.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Normal,
                    color = FF151515,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomDividerColor()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = confirmText,
                    fontWeight = FontWeight.Bold,
                    color = TealGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .noRippleClickableComposable { onConfirm() }
                        .padding(vertical = 12.dp)
                )

                Box(
                    Modifier
                        .width(1.dp)
                        .height(50.dp)
                        .background(AEA1F27)
                )

                Text(
                    text = dismissText,
                    fontWeight = FontWeight.Bold,
                    color = TealGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .noRippleClickableComposable { onDismiss() }
                        .padding(vertical = 12.dp)
                )
            }
        }
    }
}
