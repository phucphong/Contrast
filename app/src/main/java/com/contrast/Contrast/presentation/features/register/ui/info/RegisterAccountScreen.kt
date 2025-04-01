package com.contrast.Contrast.presentation.features.register.ui.info

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.*
import com.contrast.Contrast.presentation.components.alertDialog.CustomAlertDialog
import com.contrast.Contrast.presentation.components.button.ButtonRed
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.dropdown.CustomDropdown
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldPassword
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.features.register.viewmodel.RegisterAccountViewModel
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.itechpro.domain.model.NetworkResponse
@Preview(showBackground = true)
@Composable
fun RegisterAccountScreen(
    navController: NavController,
    viewModel: RegisterAccountViewModel = hiltViewModel()
) {
    val registerState by viewModel.registerState.collectAsState()
    val validationError by viewModel.validationError.collectAsState()

    var isAlertDialogVisible by remember { mutableStateOf(false) }
    var isRegisterButton by remember { mutableStateOf(false) }

    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var occupation by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }



    LaunchedEffect(registerState) {
        when (registerState) {
            is NetworkResponse.Success -> {
                isAlertDialogVisible = false
                isRegisterButton = false
            }
            is NetworkResponse.Error -> {
                isAlertDialogVisible = false
                isRegisterButton = false
            }
            is NetworkResponse.Loading -> {
                isAlertDialogVisible = true
            }
        }
    }

    if (validationError != null) {
        CustomAlertDialog(
            message = validationError!!,
            onDismiss = {
                viewModel.clearValidationError()
                isAlertDialogVisible = false
                isRegisterButton = false
            }
        )
    }

    if (isAlertDialogVisible && isRegisterButton && validationError == null) {
        CustomCircularProgressIndicatorDialog(
            networkState = registerState,
            onDismiss = {
                isAlertDialogVisible = false
                isRegisterButton = false
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(FCFCFC)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            RegisterHeader(navController)
        }

        item {
            RegisterInputFields(
                phoneNumber, { phoneNumber = it },
                password, { password = it },
                confirmPassword, { confirmPassword = it },
                fullName, { fullName = it },
                occupation, { occupation = it },
                email, { email = it },
                selectedDay, { selectedDay = it },
                selectedMonth, { selectedMonth = it },
                selectedYear, { selectedYear = it }
            )
        }

        item {
            RegisterWarningBox()
        }

        item {
            ButtonRed(
                text = stringResource(id = R.string.create_account_button),
                onClick = {
                    isRegisterButton = true
                    viewModel.validateAndRegister(
                        phoneNumber,
                        fullName,
                        password,
                        confirmPassword,
                        email,
                        selectedDay,selectedMonth,selectedYear,
                        "off"
                    )
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
fun RegisterWarningBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.warning),
                    contentDescription = "Warning",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Lưu ý:",
                    color = FFD91E18,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Bạn sẽ không thể thay đổi thông tin cá nhân & sinh nhật sau khi đăng ký thành công.\n\nVì vậy, hãy chắc chắn các thông tin đã chính xác.",
                color = FFD91E18,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
private fun RegisterHeader(navController: NavController) {
    Spacer(modifier = Modifier.height(16.dp))
    CustomTopAppBarBackTitle(
        title = stringResource(id = R.string.register_account),
        titleColor = FFD91E18,
        onBackClick = { navController.popBackStack() }
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun RegisterInputFields(
    phoneNumber: String, onPhoneChange: (String) -> Unit,
    password: String, onPasswordChange: (String) -> Unit,
    confirmPassword: String, onConfirmPasswordChange: (String) -> Unit,
    fullName: String, onFullNameChange: (String) -> Unit,
    occupation: String, onOccupationChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    selectedDay: String, onDayChange: (String) -> Unit,
    selectedMonth: String, onMonthChange: (String) -> Unit,
    selectedYear: String, onYearChange: (String) -> Unit
) {
    CustomText(text = stringResource(id = R.string.phone_number))
    CustomTextField(
        value = phoneNumber,
        onValueChange = onPhoneChange,
        placeholder = stringResource(id = R.string.phone_placeholder),
        keyboardType = KeyboardType.Phone
    )

    CustomText(
        text = buildAnnotatedString {
        append(stringResource(id = R.string.password_new))
        withStyle(style = SpanStyle(color = FFD91E18)) { append("*") }
    }
    )
    CustomTextFieldPassword(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = stringResource(id = R.string.password_placeholder),
        keyboardType = KeyboardType.Password
    )
    if (password.isNotEmpty()) {
        PasswordRequirements(password = password)
    }

    CustomText(
        text = buildAnnotatedString {
        append(stringResource(id = R.string.password_new))
        withStyle(style = SpanStyle(color = FFD91E18)) { append("*") }
    }
    )
    CustomTextFieldPassword(
        value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        placeholder = stringResource(id = R.string.confirm_password_placeholder),
        keyboardType = KeyboardType.Password
    )

    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.info_account),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(10.dp))

    CustomText(text = stringResource(id = R.string.full_name))
    CustomTextField(
        value = fullName,
        onValueChange = onFullNameChange,
        placeholder = stringResource(id = R.string.ex_full_name),
        keyboardType = KeyboardType.Text
    )

    CustomText(text = stringResource(id = R.string.occupation))
    CustomTextField(
        value = occupation,
        onValueChange = onOccupationChange,
        placeholder = stringResource(id = R.string.ex_occupation),
        keyboardType = KeyboardType.Text
    )

    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.birthday),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = stringResource(id = R.string.birthday_description),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(12.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        CustomDropdown(
            options = (1..31).map { it.toString() },
            selectedOption = selectedDay,
            onOptionSelected = onDayChange,
            placeholder = stringResource(id = R.string.day_placeholder),
            modifier = Modifier.weight(1f)
            ,
        )
        CustomDropdown(
            options = (1..12).map { it.toString() },
            selectedOption = selectedMonth,
            onOptionSelected = onMonthChange,
            placeholder = stringResource(id = R.string.month_placeholder),
            modifier = Modifier.weight(1f)
        )
        CustomDropdown(
            options = (1950..2024).map { it.toString() },
            selectedOption = selectedYear,
            onOptionSelected = onYearChange,
            placeholder = stringResource(id = R.string.year_placeholder),
            modifier = Modifier.weight(1f)
        )
    }

    Text(
        text = stringResource(id = R.string.contact_info),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
    CustomText(text = stringResource(id = R.string.email))
    CustomTextField(
        value = email,
        onValueChange = onEmailChange,
        placeholder = stringResource(id = R.string.email_placeholder),
        keyboardType = KeyboardType.Email
    )
}

