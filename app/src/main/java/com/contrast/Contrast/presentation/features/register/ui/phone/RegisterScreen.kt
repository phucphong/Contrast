package com.contrast.Contrast.presentation.features.auth

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.core.FFFCFCFC
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack


@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun RegisterScreen(onBackPress: () -> Unit, onConfirm: (String) -> Unit) {

    var phoneNumber by remember { mutableStateOf("") }

  Column (
      modifier = Modifier
          .fillMaxSize()

          .background(FFFCFCFC)
  ){   Column(
      modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
          .background(FFFCFCFC)
  ) {

      Spacer(modifier = Modifier.height(16.dp))
      // Nút quay lại

      CustomTopAppBarTittleBack(
          title = "",
          Color.Red,
          Color.White,
          onBackClick = {  }
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Tiêu đề
      Text(
          text = stringResource(R.string.register_title),
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.align(Alignment.Start)
      )

      Spacer(modifier = Modifier.height(30.dp))

      // Nhập số điện thoại
      CustomText(
          text = stringResource(R.string.phone_label),
          fontSize = 16.sp,
          fontWeight = FontWeight.Medium,

          )
      Spacer(modifier = Modifier.height(8.dp))


      // CustomTextField with phone number
      CustomTextField(
          value = phoneNumber,
          onValueChange = { phoneNumber = it },
          placeholder = stringResource(id = R.string.phone_placeholder),  // Use string resource for placeholders
          keyboardType = KeyboardType.Phone
      )



      // Spacer để đẩy nút xuống cuối
      Spacer(modifier = Modifier.weight(1f))

      Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
      ) {
          // Ghi chú chính sách
          Text(
              text = stringResource(R.string.policy_text),
              fontSize = 12.sp,
              color = Color.Gray,
              textAlign = TextAlign.Center,
              modifier = Modifier.padding(horizontal = 16.dp)
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Nút xác nhận
          Button(
              onClick = { onConfirm(phoneNumber) },
              enabled = phoneNumber.isNotEmpty(),
              modifier = Modifier
                  .fillMaxWidth()
                  .height(50.dp),
              colors = ButtonDefaults.buttonColors(
                  backgroundColor = if (phoneNumber.isNotEmpty()) Color.Red else Color(0xFFFFC0C0),
                  disabledBackgroundColor = Color(0xFFFFC0C0)
              ),
              shape = RoundedCornerShape(8.dp)
          ) {
              Text(text = stringResource(R.string.confirm_button), color = Color.White, fontSize = 16.sp)
          }

          Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách để tránh che nút với navigation bar
      }
  } }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(onBackPress = {}, onConfirm = {})
}
