package com.contrast.Contrast.presentation.features.register.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.usecase.register.RegisterAccountUseCase
import com.itechpro.domain.usecase.register.ValidateRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import com.contrast.Contrast.utils.StringProvider
import com.contrast.Contrast.R
import com.contrast.Contrast.utils.Common
import com.itechpro.domain.model.validation.ValidationErrorType
import com.itechpro.domain.usecase.checkphoneEmail.CheckEmailUseCase
import com.itechpro.domain.usecase.checkphoneEmail.CheckPhoneUseCase
@HiltViewModel
class RegisterAccountViewModel @Inject constructor(
    private val validateRegisterUseCase: ValidateRegisterUseCase,
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val checkPhoneUseCase: CheckPhoneUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @Named("deviceActive") var deviceActive: String,
    @Named("authen") private val authen: String,
    @Named("idEmployee") var idEmployee: String,
    @Named("isOffLine") var isOffLine: Boolean
) : ViewModel() {

    private val _registerState = MutableStateFlow<NetworkResponse<List<Account>>>(NetworkResponse.Loading)
    val registerState: StateFlow<NetworkResponse<List<Account>>> = _registerState


    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError
    var  dateOfBirtday = ""

    /**
     * ✅ 1. Kiểm tra validate đầu vào, nếu hợp lệ thì tiếp tục kiểm tra số điện thoại
     */
    fun validateAndRegister(phone: String, fullName: String, password: String, confirmPassword: String, email: String?,

                            selectedDay: String,  selectedMonth: String,  selectedYear: String, typeCheck: String) {
        val error = validateInputs(phone, fullName, password, confirmPassword,selectedDay,selectedMonth,selectedYear)
        if (error != null) {
            _validationError.value = error
            return
        }

          dateOfBirtday = "$selectedYear-$selectedMonth-$selectedDay"
        checkPhone(phone, email, fullName, password,typeCheck)
    }

    /**
     * ✅ 2. Kiểm tra số điện thoại
     */
    private fun checkPhone(phone: String, email: String?, fullName: String, password: String, typeCheck: String) {
        viewModelScope.launch(dispatcher) {
            try {
                val token = if (!isOffLine) Common.key else authen

                checkPhoneUseCase.execute("dailyaf", "checkdt", phone, idEmployee, token, typeCheck)
                    .collect { result ->
                        when (result) {
                            is NetworkResponse.Success -> {
                                if (!result.data) {
                                    if (!email.isNullOrEmpty()) {
                                        checkEmail(email, fullName, phone, password, typeCheck)
                                    }else{

                                        registerAccount(fullName, phone, null, password,dateOfBirtday)
                                    }
                                } else {
                                    _validationError.value = stringProvider.getString(R.string.registered_phone)
                                }


                            }
                            is NetworkResponse.Error -> {
                                _validationError.value = result.message
                                Log.e("_validationError", result.message)
                            }

                            NetworkResponse.Loading -> {

                            }
                        }
                    }
            } catch (e: Exception) {
                 _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }


    /**
     * ✅ 3. Kiểm tra email (nếu có)
     */
    private fun checkEmail(email: String, fullName: String, phone: String, password: String, typeCheck: String) {
        viewModelScope.launch(dispatcher) {

            try {
                val token = if (!isOffLine) Common.key else authen
                checkEmailUseCase.execute("dailyaf", "checkemail", email, idEmployee, token, typeCheck)
                    .collect { result ->
                        when (result) {
                            is NetworkResponse.Success -> {
                                if (!result.data) {
                                    registerAccount(fullName, phone, null, password,dateOfBirtday)
                                } else {
                                    _validationError.value = stringProvider.getString(R.string.registered_email)
                                }
                            }
                            is NetworkResponse.Error -> {
                                _validationError.value = result.message

                            }
                            NetworkResponse.Loading -> {

                            }
                        }
                    }
            } catch (e: Exception) {
                 _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    /**
     * ✅ 4. Gửi yêu cầu đăng ký tài khoản
     */
    fun registerAccount(fullName: String, phone: String, email: String?, password: String,dateOfBirtday:String) {


        viewModelScope.launch(dispatcher) {
            _registerState.value = NetworkResponse.Loading
            try {
                val account = Account(
                    id = "0",
                    ido = "0",
                    hoten = fullName,
                    dienthoai = phone,
                    email = email ?: "",
                    username = phone,
                    password = password,
                    ngaysinh = dateOfBirtday,
                    key = Common.key,
                    mamenu = "dangkytaikhoan",
                    hanhdong = "$phone - $fullName",
                    noidungchinh = "add",
                    device = deviceActive
                )

                val result = registerAccountUseCase.invoke("/ex/apiaffiliate/dangkytaikhoan", account)
                _registerState.value = result

            } catch (e: Exception) {
                _registerState.value = NetworkResponse.Error(stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}")
            }
        }
    }

    /**
     * ✅ Xóa lỗi Validate khi người dùng nhập lại thông tin
     */
    fun clearValidationError() {
        _validationError.value = null
    }

    private fun getValidationErrorMessage(error: ValidationErrorType): String {
        return stringProvider.getString(
            when (error) {
                ValidationErrorType.EMPTY_PHONE -> R.string.error_empty_phone
                ValidationErrorType.INVALID_PHONE -> R.string.error_invalid_phone
                ValidationErrorType.EMPTY_NAME -> R.string.error_empty_fullname
                ValidationErrorType.EMPTY_PASSWORD -> R.string.error_empty_password
                ValidationErrorType.EMPTY_RE_PASSWORD -> R.string.error_empty_re_password
                ValidationErrorType.INVALID_LENGTH -> R.string.error_invalid_length
                ValidationErrorType.MISSING_NUMBER -> R.string.error_missing_number
                ValidationErrorType.MISSING_UPPERCASE -> R.string.error_missing_uppercase
                ValidationErrorType.MISSING_LOWERCASE -> R.string.error_missing_lowercase
                ValidationErrorType.MISSING_SPECIAL_CHAR -> R.string.error_missing_special_char
                ValidationErrorType.PASSWORD_MISMATCH -> R.string.error_password_mismatch
                ValidationErrorType.EMPTY_DAY -> R.string.error_empty_dob_day
                ValidationErrorType.EMPTY_MONTH -> R.string.error_empty_dob_month
                ValidationErrorType.EMPTY_YEAR -> R.string.error_empty_dob_year
                ValidationErrorType.UNKNOWN -> R.string.error_unknown
            }
        )
    }


    fun validateInputs(phone: String, fullName: String, password: String, confirmPassword: String,
                       selectedDay: String, selectedMonth: String, selectedYear: String): String? {
        // ✅ Kiểm tra số điện thoại
        // ✅ Validate phone
        val phoneValidation = validateRegisterUseCase.validatePhone(phone)
        if (!phoneValidation.success) {
            val error = ValidationErrorType.fromCode(phoneValidation.message)
            return getValidationErrorMessage(error)
        }

        // ✅ Validate full name
        val nameValidation = validateRegisterUseCase.validateFullName(fullName)
        if (!nameValidation.success) {
            val error = ValidationErrorType.fromCode(nameValidation.message)
            return getValidationErrorMessage(error)
        }

        // ✅ Validate password
        val passwordValidation = validateRegisterUseCase.validateNewPassWord(password)
        if (!passwordValidation.success) {
            val error = ValidationErrorType.fromCode(passwordValidation.message)
            return getValidationErrorMessage(error)
        }

        // ✅ Validate re-password
        val rePassWordValidation = validateRegisterUseCase.validateRePassWord(confirmPassword)
        if (!rePassWordValidation.success) {
            val error = ValidationErrorType.fromCode(rePassWordValidation.message)
            return getValidationErrorMessage(error)
        }

        // ✅ Kiểm tra mật khẩu có đủ mạnh không và có khớp với `confirmPassword` không
        val finalPasswordValidation = validateRegisterUseCase.validatePassword(password, confirmPassword)
        if (!finalPasswordValidation.success) {
            val error = ValidationErrorType.fromCode(finalPasswordValidation.message)
            return getValidationErrorMessage(error)
        }


        // ✅ Validate ngày sinh
        if (selectedDay.isNotEmpty() ||selectedMonth.isNotEmpty()||selectedYear.isNotEmpty()) {
        val selectedDayValidation = validateRegisterUseCase.validateDayOfBirth(selectedDay)
            if (!selectedDayValidation.success) {
                val error = ValidationErrorType.fromCode(selectedDayValidation.message)
                return getValidationErrorMessage(error)
            }

            val selectedMonthValidation = validateRegisterUseCase.validateMonthOfBirth(selectedMonth)
            if (!selectedMonthValidation.success) {
                val error = ValidationErrorType.fromCode(selectedMonthValidation.message) // ✅ fix chỗ này
                return getValidationErrorMessage(error)
            }

            val selectedYearValidation = validateRegisterUseCase.validateYearOfBirth(selectedYear)
            if (!selectedYearValidation.success) {
                val error = ValidationErrorType.fromCode(selectedYearValidation.message)
                return getValidationErrorMessage(error)
            }






        }


        return null // ✅ Nếu tất cả đều hợp lệ, trả về `null`
    }

}
