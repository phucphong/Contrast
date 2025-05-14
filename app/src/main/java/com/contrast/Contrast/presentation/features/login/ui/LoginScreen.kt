package com.contrast.Contrast.presentation.features.login.ui

import android.os.Build
import android.util.Log
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldPassword
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.checkbox.BorderedCheckBox
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.copyrightInfoSection.CopyrightInfoSection
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.features.login.BiometricAuthenticator
import com.contrast.Contrast.presentation.features.login.LoginViewModel
import com.contrast.Contrast.presentation.navigator.router.routes.AffiliateRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.AuthRoutes
import com.contrast.Contrast.presentation.theme.FF000000
import com.contrast.Contrast.presentation.theme.FFD9D9D9
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(navHostController: NavHostController,
                isClose: String,
                viewModel: LoginViewModel = hiltViewModel(), ) {

    val state by viewModel.state.collectAsState()
    var account by remember { mutableStateOf(state.account) }

    var password by remember { mutableStateOf(state.password) }
    var rememberMe by remember { mutableStateOf(state.rememberPassword) }
    var isRegisterButton by remember { mutableStateOf(false) }
    var isBiometricAuthenticator by remember { mutableStateOf(false) }
    var biometricAuthenticatorError by remember { mutableStateOf("") }
    val context = LocalContext.current

    val activity = context as? FragmentActivity
    // Khởi tạo biometricAuthenticator nếu activity không null
    val biometricAuthenticator = remember(activity) {
        activity?.let { BiometricAuthenticator(it) }
    }
    if (state.errorMessage.isNotEmpty()&&isRegisterButton) {
        CustomOkAlertDialog(message = state.errorMessage, onDismiss = {
            viewModel.clearValidationError()
            isRegisterButton = false
        })
    }


    if (isBiometricAuthenticator) {
        CustomOkAlertDialog(message = stringResource(R.string.biometricCredentials), onDismiss = {
            isBiometricAuthenticator = false
        })
    }

    // Xử lý navigation event
    LaunchedEffect(state.navigationEvent) {
        when (val event = state.navigationEvent) {
            is SplashNaEvent.GoToMain -> {
                delay(200)
                navHostController.navigate(
                    AffiliateRoutes.AffiliateHome.route
                ) {
                    popUpTo(0) { inclusive = true } // reset hoàn toàn
                }
                viewModel.resetNavigation()
            }

            is SplashNaEvent.GoToRegister -> {
                navHostController.navigate(AuthRoutes.Register.route)
                viewModel.resetNavigation()
            }
            is SplashNaEvent.GoToDomain -> {
                navHostController.navigate(AuthRoutes.Domain.route)
                viewModel.resetNavigation()
            }
            is SplashNaEvent.GoToForgotPassword -> {
                navHostController.navigate(AuthRoutes.ForgotPassword.route)
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

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            if(isClose=="1") {

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

                                        viewModel.autoLoginFromFingerprint(account, password)},
                                    onError = { error ->Log.e("BiometricTest",error) }
                                )


                                if (biometricAuthenticator != null) {
                                    if (biometricAuthenticator.isBiometricSupported()) {
                                        biometricAuthenticator.authenticate(
                                            onSuccess = {
                                                viewModel.autoLoginFromFingerprint(account, password)

                                            },
                                            onError = { biometricAuthenticatorError = it}
                                        )
                                    } else {
                                        // Hiển thị UI khác vì thiết bị không hỗ trợ
                                        isBiometricAuthenticator = true
                                    }
                                }
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
                CustomText(text = stringResource(R.string.remember_me),
                    color = FF000000,
                    modifier = Modifier.padding(10.dp,2.dp,2.dp,2.dp))

                Spacer(modifier = Modifier.weight(1f))

                CustomText(
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
                CustomText(
                    text = stringResource(R.string.registerAccount),
                    color = TealGreen,
                    modifier = Modifier.clickable {

                        viewModel.registerAccount()

                    }
                )
                CustomText(
                    text = stringResource(R.string.connect_code),
                    color = TealGreen,
                    modifier = Modifier.clickable {
                        viewModel.domain()

                    }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }

      CopyrightInfoSection(
          modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 16.dp)
      )
    }
        if(state.isLoading){
            CustomCircularProgressIndicator(contentAlignment = Alignment.Center)
        }


}
