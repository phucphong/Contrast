package com.contrast.Contrast.presentation.features.payment.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FFAFAFAF
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.contrast.Contrast.presentation.theme.FCFCFC

@Composable
fun LockVerificationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {

        var otpCode by remember { mutableStateOf("") }

        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false) // Full-width Dialog

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        FCFCFC,
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { onDismiss()

                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(id = R.string.close),
                                tint = Color.Black
                            )
                        }
                    }

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(id = R.string.logo),
                        modifier = Modifier.size(150.dp)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = stringResource(id = R.string.enter_unlock_code),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 100.dp)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    // Nhập mã OTP (4 ô nhập)
                    val otpLength = 4
                    var otpText by remember { mutableStateOf("") }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(otpLength) { index ->
                            val hasFocus = remember { mutableStateOf(false) }
                            TextField(
                                value = otpText.getOrNull(index)?.toString() ?: "",
                                onValueChange = { input ->
                                    if (input.length <= 1) {
                                        otpText = (otpText.take(index) + input + otpText.drop(index + 1)).take(otpLength)
                                    }
                                },
                                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp)
                                    .onFocusChanged { focusState ->
                                        hasFocus.value = focusState.isFocused
                                    },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                visualTransformation = VisualTransformation.None,
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = FFD91E18, // Chỉ có line ở bottom khi chọn
                                    unfocusedIndicatorColor = if (hasFocus.value) FFD91E18 else if (otpText.getOrNull(index)?.isDigit() == true) FFD91E18 else Color.Gray, // Viền mỏng hơn nếu có dữ liệu nhưng không focus
                                    cursorColor = Color.White, // Màu con trỏ nhập liệu
                                    textColor = FFD91E18 // Màu chữ nhập vào
                                )
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(60.dp))

                    Text(
                        text = stringResource(id = R.string.forgot_password_title),

                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF31C440),
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline,
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.pin_description),
                        fontSize = 14.sp,
                        color = FFAFAFAF,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
