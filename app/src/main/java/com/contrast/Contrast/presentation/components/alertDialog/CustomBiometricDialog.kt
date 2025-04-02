package com.contrast.Contrast.presentation.components.alertDialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.checkbox.BorderedCheckBox

@Preview(showBackground = true)
// thông báo đăng nhập faceid
@Composable

fun CustomBiometricDialog(

    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var doNotShowAgain by remember { mutableStateOf(false) }

    Dialog(  onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),

            ) {
                // Tiêu đề
                Text(
                    text = stringResource(id = R.string.biometric_title),
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),

                )

                Spacer(modifier = Modifier.height(16.dp))

                // Icon + message
                Row(

                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.faceid),
                        contentDescription = "Biometric Icon",
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.biometric_setup_description),

                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF7C7C7C),

                            )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Checkbox
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BorderedCheckBox(
                        checked = doNotShowAgain,
                        onCheckedChange = { doNotShowAgain = it }
                    )

                    Text(
                        text = stringResource(id = R.string.do_not_show_again),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF7C7C7C),
                        )
                        , modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Confirm button
                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.activate_biometric),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 17.5.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),

                            textAlign = TextAlign.Center,
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Cancel button
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 1.dp,
                        brush = SolidColor(Color.Red)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 17.5.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFD91E18),

                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }
}
