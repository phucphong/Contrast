package com.contrast.Contrast.presentation.features.domain

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.copyrightInfoSection.CopyrightInfoSection
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes
import com.contrast.Contrast.presentation.navigator.routers.MainRoutes
import com.itechpro.domain.model.navigationEvent.SplashNaEvent

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DomainScreen(navHostController: NavHostController, viewModel: DomainViewModel = hiltViewModel()) {

    var isRegisterButton by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    var connectCode by remember { mutableStateOf(state.domain) }


    // Xử lý navigation event
    if (state.errorMessage.isNotEmpty()&&isRegisterButton) {
        CustomOkAlertDialog(message = state.errorMessage, onDismiss = {
            isRegisterButton = false
        })
    }

    LaunchedEffect(state.navEvent) {
        when (val event =state. navEvent) {

            is SplashNaEvent.GoToLogIn -> {
                navHostController.navigate(
                    AuthRoutes.Login.withArgs(
                        isClose = event.isClose,
                    )
                )  {
                    popUpTo(0) { inclusive = true } // reset hoàn toàn
                }
                viewModel.resetNavigation()
            }
            is SplashNaEvent.GoToMain -> {
                navHostController.navigate(
                    MainRoutes.Main.withArgs(
                        id = event.id, idUnit = event.idUnit, introducerId = event.introducerId
                    )
                )
                viewModel.resetNavigation()
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

        if(state.isLoading){
            CustomCircularProgressIndicator(contentAlignment = Alignment.Center)
        }

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
                  isRegisterButton = true
                  viewModel.registerVerificationCodes(connectCode,context)
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
                  textAlign = TextAlign.Center,

              )
          }

          Spacer(modifier = Modifier.height(24.dp))

          // Đăng ký với QR Code
          CustomText(
              text = stringResource(R.string.register_qr),
              color = Color(0xFF00AA88),
              fontSize = 13.sp,
              textAlign = TextAlign.Center,

              modifier = Modifier.noRippleClickableComposable {
                viewModel.registerAccount()
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
