package com.contrast.Contrast.presentation.components.inputs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.TextField

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
@Composable
fun CustomTextFieldUpDown(
    value: String,  // Giá trị nhập vào
    onValueChange: (String) -> Unit,  // Hàm cập nhật giá trị nhập vào
    placeholder: String,  // Placeholder cho TextField
    keyboardType: KeyboardType = KeyboardType.Number,  // Mặc định là bàn phím số
    padding: Dp = 0.dp  // Padding mặc định
) {
    val customFontFamily = FontFamily(
        Font(R.font.inter), // Font thường
        Font(R.font.inter_18pt_bold, FontWeight.Bold),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 5.dp) // Padding cho TextField
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp) // Rounded corners
            )
            .border(
                width = 1.dp,
                color = Color(0xFFD7D7D7),
                shape = RoundedCornerShape(8.dp) // Border với các góc bo tròn
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = value,  // Giá trị của TextField
                onValueChange = { newValue ->
                    // Chỉ chấp nhận số (Loại bỏ ký tự không phải số)
                    val filteredValue = newValue.filter { it.isDigit() }
                    onValueChange(filteredValue)
                },
                placeholder = {
                    Text(
                        placeholder,
                        color = Color(0xFFD7D7D7)
                    )
                }, // Placeholder cho TextField
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),  // Mặc định là số
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp) // Chiều cao của TextField
                    .padding(padding),  // Padding
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = customFontFamily
                ), // Màu văn bản là đen
                singleLine = true,  // Đảm bảo chỉ một dòng văn bản
                colors = TextFieldDefaults.textFieldColors(
                     Color.White,  // Màu nền của TextField
                    focusedIndicatorColor = Color.Transparent,  // Loại bỏ gạch chân khi focus
                    unfocusedIndicatorColor = Color.Transparent  // Loại bỏ gạch chân khi không focus
                ),
                trailingIcon = {
                    // Nút tăng giảm số
                    Column {
                        IconButton(
                            onClick = {
                                val newValue = (value.toIntOrNull() ?: 0) + 1000
                                onValueChange(newValue.toString())
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.down),
                                contentDescription = "Increase value",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                val newValue = (value.toIntOrNull() ?: 0) - 1000
                                if (newValue >= 0) {
                                    onValueChange(newValue.toString())
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.down),
                                contentDescription = "Decrease value",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}
