package com.contrast.Contrast.presentation.features.register.ui.info

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.components.dropdown.CustomDropdown
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldPassword
import com.contrast.Contrast.presentation.components.PasswordRequirements
import com.contrast.Contrast.presentation.components.alertDialog.CustomAlertDialog

import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog

import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack

import com.contrast.Contrast.presentation.features.register.viewmodel.RegisterAccountViewModel
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.contrast.Contrast.utils.Common

import com.itechpro.domain.model.Account
import com.itechpro.domain.model.NetworkResponse

@Preview(showBackground = true)
@Preview(name = "Light Mode", showBackground = true)



//@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun RegisterAccountScreenPreview() {
    val navController = rememberNavController()  // Tạo NavController giả lập cho Preview
    RegisterAccountScreen(navController = navController)
}

@Composable
fun RegisterAccountScreen(
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
           .background(FCFCFC)

   )
   {  Column(
       modifier = Modifier
           .fillMaxSize()
           .background(FCFCFC)
           .padding(18.dp)
           .verticalScroll(rememberScrollState())
   ) {
       Spacer(modifier = Modifier.height(16.dp))

       CustomTopAppBarTittleBack(
           title = "",
           FFD91E18,
           onBackClick = { navController.popBackStack() }
       )

       Spacer(modifier = Modifier.height(16.dp))

       // Tiêu đề
       Text(
           text = stringResource(id = R.string.register_account),
           style = MaterialTheme.typography.titleLarge,
           fontWeight = FontWeight.Bold
       )



       Spacer(modifier = Modifier.height(16.dp))

       // Thông tin đăng nhập
       CustomText(text = stringResource(id = R.string.phone_number))


       // CustomTextField with phone number
       CustomTextField(
           value = phoneNumber,
           onValueChange = { phoneNumber = it },
           placeholder = stringResource(id = R.string.phone_placeholder),  // Use string resource for placeholders
           keyboardType = KeyboardType.Phone
       )



       // Mật khẩu mới
       CustomText(
           text = buildAnnotatedString {
               append(stringResource(id = R.string.password_new))
               withStyle(style = SpanStyle(color = FFD91E18)) {
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
               withStyle(style = SpanStyle(color = FFD91E18)) {
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


       Spacer(modifier = Modifier.height(10.dp))
       // Thông tin cá nhân

       Text(
           text = stringResource(id = R.string.info_account),
           style = MaterialTheme.typography.titleMedium,
           fontWeight = FontWeight.Bold
       )
       Spacer(modifier = Modifier.height(10.dp))

       // họ tên
       CustomText(text = stringResource(id = R.string.full_name))

       CustomTextField(
           value = fullName,
           onValueChange = { fullName = it },
           placeholder = stringResource(id = R.string.ex_full_name),
           keyboardType = KeyboardType.Text
       )


       // Nghề nghiệp
       CustomText(text = stringResource(id = R.string.occupation))

       CustomTextField(
           value = occupation,
           onValueChange = { occupation = it },
           placeholder = stringResource(id = R.string.ex_occupation),
           keyboardType = KeyboardType.Text
       )
       Spacer(modifier = Modifier.height(10.dp))

       // Tiêu đề "Sinh nhật"
       Text(
           text = stringResource(id = R.string.birthday), // Hỗ trợ đa ngôn ngữ
           style = MaterialTheme.typography.titleMedium,
           fontWeight = FontWeight.Bold
       )

       Spacer(modifier = Modifier.height(4.dp))

       // Mô tả sinh nhật
       Text(
           text = stringResource(id = R.string.birthday_description), // Hỗ trợ đa ngôn ngữ
           style = MaterialTheme.typography.bodyMedium,
           color = Color.Gray
       )

       Spacer(modifier = Modifier.height(12.dp))

       // Row chứa Dropdown ngày, tháng, năm
       Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
           CustomDropdown(
               options = (1..31).map { it.toString() },
               selectedOption = selectedDay,
               onOptionSelected = { selectedDay = it },
               placeholder = stringResource(id = R.string.day_placeholder), // Hỗ trợ đa ngôn ngữ
               modifier = Modifier.weight(1f)
           )

           CustomDropdown(
               options = (1..12).map { it.toString() },
               selectedOption = selectedMonth,
               onOptionSelected = { selectedMonth = it },
               placeholder = stringResource(id = R.string.month_placeholder),
               modifier = Modifier.weight(1f)// Hỗ trợ đa ngôn ngữ
           )

           CustomDropdown(
               options = (1950..2024).map { it.toString() },
               selectedOption = selectedYear,
               onOptionSelected = { selectedYear = it },
               placeholder = stringResource(id = R.string.year_placeholder), // Hỗ trợ đa ngôn ngữ
               modifier = Modifier.weight(1f)
           )
       }





       // Tiêu đề "Thông tin liên hệ khác"
       Text(
           text =  stringResource(id = R.string.contact_info),
           style = MaterialTheme.typography.titleMedium,
           fontWeight = FontWeight.Bold,
           modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
       )

       // Email
       CustomText(text = stringResource(id = R.string.email))

       CustomTextField(
           value = email,
           onValueChange = { email = it },
           placeholder =  stringResource(id = R.string.email_placeholder),
           keyboardType = KeyboardType.Email
       )

       Spacer(modifier = Modifier.height(16.dp))

       // Hộp cảnh báo màu đỏ
       Box(
           modifier = Modifier
               .fillMaxWidth()
               .background(Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp))
               .padding(16.dp)
       ) {
           Column {
               Row(verticalAlignment = Alignment.CenterVertically) {
                   Image(
                       painter = painterResource(id = R.drawable.warning), // Thay bằng ảnh trong drawable
                       contentDescription = "Warning",
                       modifier = Modifier.size(20.dp) // Điều chỉnh kích thước
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



       Spacer(modifier = Modifier.height(24.dp))

       // Nút tạo tài khoản
       Button(
           onClick = {

               viewModel.validateAndRegister(phoneNumber, fullName, password, confirmPassword, email)
               showLoadingDialog = true


           },

           colors = ButtonDefaults.buttonColors(containerColor = FFD91E18),
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


   } }
}

@Composable
fun CustomTextField(value: String, onValueChange: () -> Unit, placeholder: String, keyboardType: KeyboardType) {

}
