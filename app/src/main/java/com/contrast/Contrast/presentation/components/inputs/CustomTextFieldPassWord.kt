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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Composable
fun CustomTextFieldPassword(
    value: String,  // Giá trị nhập vào
    onValueChange: (String) -> Unit,  // Hàm cập nhật giá trị nhập vào
    placeholder: String,  // Placeholder cho TextField
    keyboardType: KeyboardType = KeyboardType.Password,  // Kiểu bàn phím là mật khẩu
    padding: Dp = 0.dp  // Padding mặc định
) {
    // State để kiểm soát việc hiển thị mật khẩu
    var isPasswordVisible by remember { mutableStateOf(false) }

    val customFontFamily = FontFamily(
        Font(R.font.inter_18pt_medium), // Font thường
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
                onValueChange = onValueChange,  // Cập nhật giá trị khi người dùng nhập dữ liệu
                placeholder = { Text(placeholder,
                    color = Color(0xFFD7D7D7)) }, // Placeholder cho TextField
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),  // Kiểu bàn phím
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),  // Hiển thị hoặc ẩn mật khẩu
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp) // Chiều cao của TextField
                    .padding(padding),  // Padding
                textStyle = TextStyle(color = Color.Black, fontSize = 14.sp,fontFamily = customFontFamily), // Màu văn bản là đen
                singleLine = true,  // Đảm bảo chỉ một dòng văn bản
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,  // Màu nền của TextField
                    focusedIndicatorColor = Color.Transparent,  // Loại bỏ gạch chân khi focus
                    unfocusedIndicatorColor = Color.Transparent  // Loại bỏ gạch chân khi không focus
                ),
                trailingIcon = {
                    // Kiểm tra và hiển thị biểu tượng từ drawable
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible } ,
                        // Đảo ngược trạng thái hiển thị mật khẩu
                    ) {
                        // Thay đổi biểu tượng khi nhấn vào (Hiển thị mắt hoặc mắt có gạch chéo)
                        val icon = if (isPasswordVisible) R.drawable.show_password else R.drawable.show_password
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = "Toggle password visibility",
                            modifier = Modifier.size(18.dp) // Điều chỉnh kích thước nếu cần
                        )

                    }
                }
            )
        }
    }
}
