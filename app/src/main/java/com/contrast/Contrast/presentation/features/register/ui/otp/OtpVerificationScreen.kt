package com.contrast.Contrast.presentation.features.register.ui.otp


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.FFD91E18
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Preview(showBackground = true)


@Composable
fun OtpVerificationScreen(phoneNumber: String, onBackPress: () -> Unit, onOtpSubmit: (String) -> Unit, onResendOtp: () -> Unit) {
    var otpCode by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(60) } // Đếm ngược 60 giây
    val coroutineScope = rememberCoroutineScope()

    // Bắt đầu đếm ngược khi màn hình mở
    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

  Column (
      modifier = Modifier
          .fillMaxSize()

          .background(FCFCFC)
  ){
      Column(
          modifier = Modifier
              .fillMaxSize()
              .padding(16.dp)
              .background(FCFCFC)
      ) {
          // Nút quay lại
          Spacer(modifier = Modifier.height(40.dp))
          Image(
              painter = painterResource(id = R.drawable.back),
              contentDescription = stringResource(id = R.string.app_name),  // Example for app name or other description
              modifier = Modifier
                  .size(30.dp)
                  .clickable(onClick = {
                      // Your onClick logic goes here
                  })
          )

          Spacer(modifier = Modifier.height(32.dp))

          // Thông báo mã OTP đã gửi
          CustomText(
              text = "Mã xác nhận đã được gửi vào số $phoneNumber",
              fontSize = 14.sp,
              color = Color.Gray,
              textAlign = TextAlign.Center,

              )

          Spacer(modifier = Modifier.height(16.dp))

          // Tiêu đề "Xác nhận OTP"
          CustomText(
              text = "Xác nhận OTP",
              fontSize = 24.sp,
              fontWeight = FontWeight.Bold,

              )

          Spacer(modifier = Modifier.height(24.dp))

          // Nhập OTP (6 ô nhập)
          Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly
          ) {
              repeat(6) { index ->
                  TextField(
                      value = otpCode.getOrNull(index)?.toString() ?: "",
                      onValueChange = {
                          if (it.length <= 1) {
                              otpCode = otpCode.take(index) + it + otpCode.drop(index + 1)
                          }
                      },
                      textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                      modifier = Modifier
                          .width(50.dp)
                          .height(50.dp),
                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                      visualTransformation = VisualTransformation.None,
                      singleLine = true,
                      shape = RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp), // Bo góc chỉ ở cạnh dưới
                      colors = TextFieldDefaults.textFieldColors(
                          backgroundColor = Color(0xFCFCFC), // Đặt màu nền cho giống OutlinedTextField
                          focusedIndicatorColor = Color.Gray, // Màu viền khi chọn
                          unfocusedIndicatorColor = Color.Gray, // Màu viền khi chưa chọn
                          cursorColor = Color.Gray, // Màu con trỏ nhập liệu
                          textColor = Color.Black // Màu chữ nhập vào
                      )
                  )
              }
          }



          Spacer(modifier = Modifier.height(30.dp))

          // Bộ đếm thời gian
          Text(
              text = "Có hiệu lực trong ${timeLeft / 60}:${String.format("%02d", timeLeft % 60)}",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = Color.Black
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Nút "Gửi lại" mã OTP
          Row {
              Text(
                  text = "Bạn chưa nhận được mã?",
                  fontSize = 14.sp,

                  color =  Color.Gray,

                  )
              Text(
                  text = " Gửi lại",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (timeLeft == 0) FFD91E18 else Color.Black,
                  modifier = Modifier.clickable(enabled = timeLeft == 0) {
                      if (timeLeft == 0) {
                          onResendOtp()
                          timeLeft = 60
                          coroutineScope.launch {
                              while (timeLeft > 0) {
                                  delay(1000L)
                                  timeLeft--
                              }
                          }
                      }
                  }
              )
          }
      }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewOtpVerificationScreen() {
    OtpVerificationScreen(
        phoneNumber = "091****678",
        onBackPress = {},
        onOtpSubmit = {},
        onResendOtp = {}
    )
}
