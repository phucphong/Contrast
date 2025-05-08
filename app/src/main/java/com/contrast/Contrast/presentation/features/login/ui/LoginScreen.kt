package com.contrast.Contrast.presentation.features.login.ui


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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldPassword
import com.contrast.Contrast.presentation.components.PasswordRequirements
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.checkbox.BorderedCheckBox
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliateModel
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.login.LoginViewModel
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FF000000
import com.contrast.Contrast.presentation.theme.PlaceholderGray
import com.itechpro.domain.model.navigationEvent.SplashNaEvent

@Preview(showBackground = true)

@Composable
fun LoginScreen(navHostController: NavHostController,
                viewModel: LoginViewModel = hiltViewModel(), ) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var isRegisterButton by remember { mutableStateOf(false) }
    val navEvent by viewModel.navigationEvent.collectAsState()
    val validationError by viewModel.validationError.collectAsState()

    if (validationError != null) {
        CustomOkAlertDialog(message = validationError!!, onDismiss = {
            viewModel.clearValidationError()
            isRegisterButton = false
        })
    }

    // Xử lý navigation event
    LaunchedEffect(navEvent) {
        when (val event = navEvent) {


            is SplashNaEvent.GoToMain -> {
                navHostController.navigate(
                    NavRoutes.Main.withArgs(
                        id = "0",
                        idUnit = "0",
                        introducerId = "0"
                    )
                )
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
                value = username,
                onValueChange = { username = it },
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
            viewModel.validateAndLogin(username, password)
        },
        modifier = Modifier
            .fillMaxWidth().weight(1f)
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00AA88))
    ) {
        Text(text = stringResource(R.string.login), fontSize = 16.sp, color = Color.White)
    }

    Image(
        painter = painterResource(id = R.drawable.fingerprintscan),
        contentDescription = "EZMAX Logo",
        modifier = Modifier
            .height(35.dp).padding(start = 15.dp)
            .noRippleClickableComposable {  }


    ) }

            Spacer(modifier = Modifier.size(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BorderedCheckBox(
                    size=20.dp,
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Text(text = stringResource(R.string.remember_me),
                        color = FF000000,
                     modifier = Modifier.padding(10.dp,2.dp,2.dp,2.dp))

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.forgot_password),
                    color = FF000000,
                    modifier = Modifier.padding(5.dp,2.dp,2.dp,2.dp).clickable {

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
                    text = stringResource(R.string.register),
                    color = Color(0xFF00AA88),
                    modifier = Modifier.clickable {
//                        navController.navigate("register")
                    }
                )
                Text(
                    text = stringResource(R.string.connect_code),
                    color = Color(0xFF00AA88),
                    modifier = Modifier.clickable {
//                        navController.navigate("connectCode")
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
