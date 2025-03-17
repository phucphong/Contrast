package com.contrast.Contrast.presentation.features.account



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.NetworkResponse

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import com.contrast.Contrast.utils.StringProvider
import com.contrast.Contrast.R
import com.itechpro.domain.usecase.account.AccountUseCase
import com.itechpro.domain.usecase.account.ValidateAccountUseCase

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val validateAccountUseCase: ValidateAccountUseCase,
    private val accountUseCase: AccountUseCase,
    private val stringProvider: StringProvider, // ✅ Inject StringProvider để lấy string từ resource
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @Named("deviceActive") var deviceActive: String
) : ViewModel() {

    private val _registerState = MutableStateFlow<NetworkResponse<List<Account>>>(NetworkResponse.Loading)
    val registerState: StateFlow<NetworkResponse<List<Account>>> = _registerState



    fun updateAccount(account: Account, authToken: String?, type: String) {
        viewModelScope.launch(dispatcher) {
            _registerState.value = NetworkResponse.Loading
            val result = try {
                accountUseCase.execute(account, authToken, type)
            } catch (e: Exception) {
                NetworkResponse.Error(stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: "Không xác định"}")
            }
            _registerState.value = result
        }
    }

    fun validateInputs(phone: String, fullName: String, password: String, confirmPassword: String): String? {


        // ✅ Kiểm tra số điện thoại
        val phoneValidation = validateAccountUseCase.validatePhone(phone)
        if (!phoneValidation.success) {
            return when (phoneValidation.message) {
                "EMPTY_PHONE" -> stringProvider.getString(R.string.error_empty_phone)
                else -> null
            }
        }



        // ✅ Kiểm tra mật khẩu
        val passwordValidation = validateAccountUseCase.validateNewPassWord(password) // Fix lỗi truyền sai tham số
        if (!passwordValidation.success) {
            return when (passwordValidation.message) {
                "EMPTY_PASSWORD" -> stringProvider.getString(R.string.error_empty_password)
                else -> null
            }
        }

        // ✅ Kiểm tra nhập lại mật khẩu
        val rePassWordValidation = validateAccountUseCase.validateRePassWord(confirmPassword)
        if (!rePassWordValidation.success) {
            return when (rePassWordValidation.message) {
                "EMPTY_RE_PASSWORD" -> stringProvider.getString(R.string.error_empty_re_password)
                else -> null
            }
        }

        // ✅ Kiểm tra quy tắc mật khẩu + khớp mật khẩu
        val finalPasswordValidation = validateAccountUseCase.validatePassword(password, confirmPassword)
        if (!finalPasswordValidation.success) {
            return when (finalPasswordValidation.message) {
                "INVALID_LENGTH" -> stringProvider.getString(R.string.error_invalid_length)
                "MISSING_NUMBER" -> stringProvider.getString(R.string.error_missing_number)
                "MISSING_UPPERCASE" -> stringProvider.getString(R.string.error_missing_uppercase)
                "MISSING_LOWERCASE" -> stringProvider.getString(R.string.error_missing_lowercase)
                "MISSING_SPECIAL_CHAR" -> stringProvider.getString(R.string.error_missing_special_char)
                "PASSWORD_MISMATCH" -> stringProvider.getString(R.string.error_password_mismatch)
                else -> null
            }
        }
        // ✅ Kiểm tra tên đầy đủ
        val nameValidation = validateAccountUseCase.validateFullName(fullName)
        if (!nameValidation.success) {
            return when (nameValidation.message) {
                "EMPTY_NAME" -> stringProvider.getString(R.string.error_empty_fullname)
                else -> null
            }
        }

        return null // ✅ Nếu không có lỗi thì trả về null
    }

}
