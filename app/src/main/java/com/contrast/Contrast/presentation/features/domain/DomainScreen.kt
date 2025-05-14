package com.contrast.Contrast.presentation.features.domain

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.copyrightInfoSection.CopyrightInfoSection
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.features.splas.SplashViewModel
import com.contrast.Contrast.presentation.navigator.router.routes.AuthRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.MainRoutes
import com.itechpro.domain.model.navigationEvent.SplashNaEvent

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DomainScreen(navHostController: NavHostController, viewModel: DomainViewModel = hiltViewModel()) {
    var connectCode by remember { mutableStateOf("https://spa.ezmax.vn") }
    val navEvent by viewModel.navigationEvent.collectAsState()

    // Xử lý navigation event
    LaunchedEffect(navEvent) {
        when (val event = navEvent) {


            is SplashNaEvent.GoToLogIn -> {
                navHostController.navigate(
                    AuthRoutes.Login.withArgs(
                        isClose = event.isClose,
                    )
                )  {
                    popUpTo(0) { inclusive = true } // reset hoàn toàn
                }

            }

            else -> Unit

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

      Column (Modifier.weight(1f),  horizontalAlignment = Alignment.CenterHorizontally){   // Logo + slogan
          Image(
              painter = painterResource(id = R.drawable.logo), // Thay logo thực tế
              contentDescription = "EZMAX Logo",
              modifier = Modifier
                  .height(150.dp)
                  .padding(top = 32.dp)
          )

          Spacer(modifier = Modifier.height(48.dp))

          // Label: Mã kết nối
          CustomText(
              text = stringResource(R.string.connect_code_label),
              fontSize = 12.sp,
              color = Color.Gray,
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 4.dp)
          )




          CustomTextField(
              value = connectCode,
              onValueChange =  { connectCode = it },
              placeholder = stringResource(id = R.string.connect_code_placeholder),
          )

          Spacer(modifier = Modifier.height(24.dp))

          // Nút Tiếp tục
          Button(
              onClick = {
//                navController.navigate("login")
              },
              modifier = Modifier
                  .fillMaxWidth()
                  .height(48.dp),
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00AA88))
          ) {
              CustomText(
                  text = stringResource(R.string.continue_button),
                  color = Color.White,
                  fontSize = 13.sp,
                  textAlign = TextAlign.Center
              )
          }

          Spacer(modifier = Modifier.height(24.dp))

          // Đăng ký với QR Code
          CustomText(
              text = stringResource(R.string.register_qr),
              color = Color(0xFF00AA88),
              fontSize = 13.sp,
              textAlign = TextAlign.Center,

              modifier = Modifier.clickable {
                viewModel.registerVerificationCodes(connectCode)
              }
          )
 }

        CopyrightInfoSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
}
