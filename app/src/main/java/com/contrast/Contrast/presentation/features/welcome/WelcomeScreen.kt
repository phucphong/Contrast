package com.contrast.Contrast.presentation.features.welcome

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.core.FFFCFCFC

@Preview(showBackground = true)
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
  Column(
      modifier = Modifier
          .fillMaxSize()
           .background(FFFCFCFC),


  )  {

      Column(
          modifier = Modifier
              .fillMaxSize()
              .padding(16.dp)  .background(FFFCFCFC),

          horizontalAlignment = Alignment.CenterHorizontally
      ) {
          Spacer(modifier = Modifier.height(100.dp))

          // Hình ảnh minh họa
          Image(
              painter = painterResource(id = R.drawable.welcome), // Thay bằng hình ảnh thực tế
              contentDescription = "Welcome Image",
              modifier = Modifier
                  .fillMaxWidth()
                  .height(200.dp)
          )

          Spacer(modifier = Modifier.height(24.dp))

          // Tiêu đề
          Text(
              text = stringResource(R.string.welcome_subtitle),
              fontSize = 16.sp,
              color = Color.Black
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
              text =  stringResource(R.string.welcome_subtitle),
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
          )

          // Đẩy nội dung bên trên lên, giữ hai nút ở cuối màn hình
          Spacer(modifier = Modifier.weight(1f))

          Column(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 20.dp), // Khoảng cách 20dp từ bottom
              horizontalAlignment = Alignment.CenterHorizontally
          ) {
              // Nút Đăng nhập
              Button(
                  onClick = onLoginClick,
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(50.dp),
                  colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                  shape = RoundedCornerShape(8.dp)
              ) {
                  Text(text = stringResource(R.string.login), color = Color.White, fontSize = 16.sp)
              }

              Spacer(modifier = Modifier.height(16.dp))

              // Nút Tạo tài khoản (viền đỏ, nền trắng)
              OutlinedButton(
                  onClick = onRegisterClick,
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(50.dp),
                  border = BorderStroke(1.dp, Color.Red),
                  shape = RoundedCornerShape(8.dp),
                  colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White)
              ) {
                  Text(text = stringResource(R.string.createAccount), color = Color.Red, fontSize = 16.sp)
              }
          }
      }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen(
        onLoginClick = {},
        onRegisterClick = {}
    )
}
