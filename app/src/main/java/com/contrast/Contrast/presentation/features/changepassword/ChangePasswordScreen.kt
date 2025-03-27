package com.contrast.Contrast.presentation.features.changepassword



import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.R

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.contrast.Contrast.presentation.components.dropdown.CustomDropdown
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldPassword
import com.contrast.Contrast.presentation.components.PasswordRequirements
import com.contrast.Contrast.presentation.components.alertDialog.CustomAlertDialog

import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.topAppBar.CustomTitleBack


import com.contrast.Contrast.presentation.features.register.viewmodel.RegisterAccountViewModel
import com.contrast.Contrast.utils.Common

import com.itechpro.domain.model.Account
import com.itechpro.domain.model.NetworkResponse


@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)


//@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun ChangePasswordScreenPreview() {
    val navController = rememberNavController()  // Tạo NavController giả lập cho Preview
    ChangePasswordScreen(navController = navController)
}

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: RegisterAccountViewModel = hiltViewModel() // ✅ Inject ViewModel
) {
    val registerState by viewModel.registerState.collectAsState()

    var type by remember { mutableStateOf("off") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var occupation by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Thêm ngày sinh
    var selectedDay by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }
    val validationError by viewModel.validationError.collectAsState()
//    var validationError by remember { mutableStateOf<String?>(null) } // ✅ Sử dụng trạng thái có thể thay đổi

    var showLoadingDialog by remember { mutableStateOf(false) } // ✅ Kiểm soát hiển thị Dialog


    // Theo dõi trạng thái để tự động đóng Dialog khi có kết quả



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(18.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Thanh tiêu đề với nút đóng
        CustomTitleBack(
            title = stringResource(R.string.forgot_password_title),
            onBackPress = { /* Xử lý quay lại */ }
        )

        Spacer(modifier = Modifier.height(40.dp))


        CustomText(text = stringResource(id = R.string.forgot_password_title))

        Spacer(modifier = Modifier.height(40.dp))




        // Mật khẩu mới
        CustomText(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.password_new))
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            }.toString()
        )

        CustomTextFieldPassword(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(id = R.string.password_placeholder),
            keyboardType = KeyboardType.Password
        )

        // Chỉ hiển thị điều kiện mật khẩu khi có dữ liệu nhập vào
        if (password.isNotEmpty()) {
            PasswordRequirements(password = password)
        }

        // Xác nhận mật khẩu mới
        CustomText(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.confirm_password))
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            }.toString()
        )

        CustomTextFieldPassword(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = stringResource(id = R.string.confirm_password_placeholder),
            keyboardType = KeyboardType.Password
        )




        Spacer(modifier = Modifier.height(24.dp))

        // Nút tạo tài khoản
        Button(
            onClick = {

                viewModel.validateAndRegister(phoneNumber, fullName, password, confirmPassword, email)
                showLoadingDialog = true


            },

            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = stringResource(id = R.string.create_account_button), color = Color.White, fontSize = 16.sp)
        }




        // ✅ Hiển thị Dialog nếu có lỗi
        if (validationError != null ) {

            CustomAlertDialog(
                message = validationError!!,
                onDismiss = { } // ✅ Đóng Dialog khi bấm OK
            )
            showLoadingDialog = false
        }else{
            val account = Account(
                id = "0",
                ido = "0",
                hoten = fullName,
                dienthoai = phoneNumber,
                email = email,
                username = phoneNumber,
                password = password,
                key = Common.key,
                mamenu = "dangkytaikhoan",
                hanhdong = "$phoneNumber - $fullName",
                noidungchinh = "add",
                device = viewModel.deviceActive,
            )



            // Giả định có constructor phù hợp
//            viewModel.registerNewAccount("/ex/apiaffiliate/dangkytaikhoan", account)
        }

        LaunchedEffect(registerState) {
            when (registerState) {
                is NetworkResponse.Success->{

                }
                is NetworkResponse.Error -> {

                }
                is NetworkResponse.Loading -> {

                }
            }
        }
        // ✅ Hiển thị Dialog khi đang tải
        if(showLoadingDialog){
            CustomCircularProgressIndicatorDialog(
                networkState = registerState,
                onDismiss = {  showLoadingDialog = false}
            )

        }
        Spacer(modifier = Modifier.height(24.dp))
        // Hiển thị trạng thái đăng ký


    }
}

@Composable
fun CustomTextField(value: String, onValueChange: () -> Unit, placeholder: String, keyboardType: KeyboardType) {

}
