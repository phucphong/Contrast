package com.contrast.Contrast.presentation.features.register.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.ValidationResult
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
import com.itechpro.domain.model.Column1
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

    /**
     * ✅ 1. Kiểm tra validate đầu vào, nếu hợp lệ thì tiếp tục kiểm tra số điện thoại
     */
    fun validateAndRegister(phone: String, fullName: String, password: String, confirmPassword: String, email: String?, typeCheck: String) {
        val error = validateInputs(phone, fullName, password, confirmPassword)
        if (error != null) {
            _validationError.value = error
            return
        }
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
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: "Không xác định"}"
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
                                } else {
                                    _validationError.value = stringProvider.getString(R.string.registered_email)
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
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: "Không xác định"}"
            }
        }
    }

    /**
     * ✅ 4. Gửi yêu cầu đăng ký tài khoản
     */
    fun registerAccount(fullName: String, phone: String, email: String?, password: String) {
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
                    key = Common.key,
                    mamenu = "dangkytaikhoan",
                    hanhdong = "$phone - $fullName",
                    noidungchinh = "add",
                    device = deviceActive
                )

                val result = registerAccountUseCase.invoke("/ex/apiaffiliate/dangkytaikhoan", account)
                _registerState.value = result

            } catch (e: Exception) {
                _registerState.value = NetworkResponse.Error(stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: "Không xác định"}")
            }
        }
    }

    /**
     * ✅ Xóa lỗi Validate khi người dùng nhập lại thông tin
     */
    fun clearValidationError() {
        _validationError.value = null
    }
    fun validateInputs(phone: String, fullName: String, password: String, confirmPassword: String): String? {
        // ✅ Kiểm tra số điện thoại
        val phoneValidation = validateRegisterUseCase.validatePhone(phone)
        if (!phoneValidation.success) {
            return when (phoneValidation.message) {
                "EMPTY_PHONE" -> stringProvider.getString(R.string.error_empty_phone)
                "INVALID_PHONE" -> stringProvider.getString(R.string.error_invalid_phone)
                else -> stringProvider.getString(R.string.error_unknown)
            }
        }

        // ✅ Kiểm tra họ tên
        val nameValidation = validateRegisterUseCase.validateFullName(fullName)
        if (!nameValidation.success) {
            return when (nameValidation.message) {
                "EMPTY_NAME" -> stringProvider.getString(R.string.error_empty_fullname)
                else -> stringProvider.getString(R.string.error_unknown)
            }
        }

        // ✅ Kiểm tra mật khẩu
        val passwordValidation = validateRegisterUseCase.validateNewPassWord(password)
        if (!passwordValidation.success) {
            return when (passwordValidation.message) {
                "EMPTY_PASSWORD" -> stringProvider.getString(R.string.error_empty_password)
                else -> stringProvider.getString(R.string.error_unknown)
            }
        }

        // ✅ Kiểm tra nhập lại mật khẩu
        val rePassWordValidation = validateRegisterUseCase.validateRePassWord(confirmPassword)
        if (!rePassWordValidation.success) {
            return when (rePassWordValidation.message) {
                "EMPTY_RE_PASSWORD" -> stringProvider.getString(R.string.error_empty_re_password)
                else -> stringProvider.getString(R.string.error_empty_phone)
            }
        }

        // ✅ Kiểm tra mật khẩu có đủ mạnh không và có khớp với `confirmPassword` không
        val finalPasswordValidation = validateRegisterUseCase.validatePassword(password, confirmPassword)
        if (!finalPasswordValidation.success) {
            return when (finalPasswordValidation.message) {
                "INVALID_LENGTH" -> stringProvider.getString(R.string.error_invalid_length)
                "MISSING_NUMBER" -> stringProvider.getString(R.string.error_missing_number)
                "MISSING_UPPERCASE" -> stringProvider.getString(R.string.error_missing_uppercase)
                "MISSING_LOWERCASE" -> stringProvider.getString(R.string.error_missing_lowercase)
                "MISSING_SPECIAL_CHAR" -> stringProvider.getString(R.string.error_missing_special_char)
                "PASSWORD_MISMATCH" -> stringProvider.getString(R.string.error_password_mismatch)
                else -> stringProvider.getString(R.string.error_unknown)
            }
        }

        return null // ✅ Nếu tất cả đều hợp lệ, trả về `null`
    }

}
