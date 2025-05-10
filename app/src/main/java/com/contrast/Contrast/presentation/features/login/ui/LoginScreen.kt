package com.contrast.Contrast.presentation.features.login.ui

import android.util.Log
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.findActivity
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldPassword
import com.contrast.Contrast.presentation.components.PasswordRequirements
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.checkbox.BorderedCheckBox
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.features.login.BiometricAuthenticator
import com.contrast.Contrast.presentation.features.login.LoginViewModel
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FF000000
import com.contrast.Contrast.presentation.theme.FFD9D9D9
import com.contrast.Contrast.presentation.theme.PlaceholderGray
import com.itechpro.domain.model.navigationEvent.SplashNaEvent

@Composable
fun LoginScreen(navHostController: NavHostController,
                viewModel: LoginViewModel = hiltViewModel(), ) {

    val uiState by viewModel.uiState.collectAsState()
    var account by remember { mutableStateOf(uiState.account) }
    var passwordBiometricAuthen by remember { mutableStateOf(uiState.passwordBiometricAuthen) }
    var password by remember { mutableStateOf(uiState.password) }
    var rememberMe by remember { mutableStateOf(uiState.rememberPassword) }
    var isRegisterButton by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Log.d("CheckContext", "context = $context")
    val activity = context as? FragmentActivity
    // Khởi tạo biometricAuthenticator nếu activity không null
    val biometricAuthenticator = remember(activity) {
        activity?.let { BiometricAuthenticator(it) }
    }
    Log.d("BiometricTest", "activity = $activity")
    Log.d("BiometricTest", "canAuthenticate = ${biometricAuthenticator?.canAuthenticate()}")
    if (uiState.validationError != null) {
        CustomOkAlertDialog(message = uiState.validationError!!, onDismiss = {
            viewModel.clearValidationError()
            isRegisterButton = false
        })
    }

    // Xử lý navigation event
    LaunchedEffect(uiState.navigationEvent) {
        when (val event = uiState.navigationEvent) {


            is SplashNaEvent.GoToMain -> {
                navHostController.navigate(
                    NavRoutes.Main.withArgs(
                        id = "0",
                        idUnit = "0",
                        introducerId = "0"
                    )
                )
            }

            is SplashNaEvent.GoToRegister -> {
                navHostController.navigate(NavRoutes.Register.route)

            }
            is SplashNaEvent.GoToDomain -> {
                navHostController.navigate(NavRoutes.Domain.route)

            }
            is SplashNaEvent.GoToForgotPassword -> {
                navHostController.navigate(NavRoutes.ForgotPassword.route)

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

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
          Row {

              Box(Modifier.weight(1f))
              Image(
                  painter = painterResource(id = R.drawable.close),
                  contentDescription = "close",
                  modifier = Modifier
                      .size(30.dp).noRippleClickableComposable {
                          navHostController.popBackStack()

                      }


              )

          }


            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "EZMAX Logo",
                modifier = Modifier
                    .height(100.dp)
                    .scale(2f).padding(top = 20.dp)

            )


            Spacer(modifier = Modifier.height(36.dp))

            CustomText(text = stringResource(R.string.username_label))
            CustomTextField(
                value = account,
                onValueChange = { account = it },
                placeholder = stringResource(R.string.username_placeholder),
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomText(text = stringResource(R.string.password_label))
            CustomTextFieldPassword(
                value = password,
                onValueChange = { password = it },
                placeholder = stringResource(R.string.password_placeholder),
                keyboardType = KeyboardType.Password
            )

            Spacer(modifier = Modifier.height(16.dp))



            Spacer(modifier = Modifier.height(24.dp))
Row (verticalAlignment = Alignment.CenterVertically){
    Button(
        onClick = {
            isRegisterButton = true
            viewModel.validateAndLogin(account, password)
        },
        modifier = Modifier
            .fillMaxWidth().weight(1f)
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00AA88))
    ) {
        Text(text = stringResource(R.string.login), fontSize = 16.sp, color = Color.White)
    }

  Box( modifier = Modifier.wrapContentSize().padding(start = 15.dp).background(FFD9D9D9).clip(
      RoundedCornerShape(8.dp)
  )){
      Image(
          painter = painterResource(id = R.drawable.fingerprintscan),
          contentDescription = "EZMAX Logo",
          modifier = Modifier
              .height(40.dp).padding(8.dp)
              .noRippleClickableComposable {

                  biometricAuthenticator?.authenticate(


                      onSuccess = {

                          viewModel.loginBiometricAuthenticator(account, password)},
                      onError = { error ->Log.e("BiometricTest",error) }
                  )
              }


      )
  }

}

            Spacer(modifier = Modifier.size(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BorderedCheckBox(
                    size=20.dp,
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it
                        viewModel.rememberPassword(it)

                    }
                )
                Text(text = stringResource(R.string.remember_me),
                        color = FF000000,
                     modifier = Modifier.padding(10.dp,2.dp,2.dp,2.dp))

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.forgot_password),
                    color = FF000000,
                    modifier = Modifier.padding(5.dp,2.dp,2.dp,2.dp).noRippleClickableComposable {
                        viewModel.forgotPassword()

                    }
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            CustomDividerColor()
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.registerAccount),
                    color = Color(0xFF00AA88),
                    modifier = Modifier.clickable {

                        viewModel.registerAccount()

                    }
                )
                Text(
                    text = stringResource(R.string.connect_code),
                    color = Color(0xFF00AA88),
                    modifier = Modifier.clickable {
                        viewModel.domain()

                    }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }

        Text(
            text = stringResource(R.string.copyright),
            color = Color(0xFF00AA88),
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = stringResource(R.string.version),
            color = Color.Gray,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.size(10.dp))
    }
}
