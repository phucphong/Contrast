package com.contrast.Contrast.presentation.features.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.presentation.mapper.ValidationErrorMapper
import com.contrast.Contrast.utils.Common
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.usecase.checkphoneEmail.CheckEmailUseCase
import com.itechpro.domain.usecase.checkphoneEmail.CheckPhoneUseCase
import com.itechpro.domain.usecase.register.RegisterAccountUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import com.contrast.Contrast.R
import com.itechpro.domain.usecase.register.UserInputValidator
import com.itechpro.domain.enumApp.ValidationErrorType

@HiltViewModel
class RegisterAccountViewModel @Inject constructor(
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val checkPhoneUseCase: CheckPhoneUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
    private val validator: UserInputValidator,
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
    var dateOfBirtday = ""

    fun validateAndRegister(
        phone: String, fullName: String, password: String, confirmPassword: String,
        email: String?, selectedDay: String, selectedMonth: String, selectedYear: String,
        typeCheck: String
    ) {
        val result = validator.validateAll(phone, fullName, password, confirmPassword, selectedDay, selectedMonth, selectedYear)
        if (!result.success) {
            val error = ValidationErrorType.fromCode(result.message)
            _validationError.value = stringProvider.getString(ValidationErrorMapper.toMessageResId(error))
            return
        }
        dateOfBirtday = "$selectedYear-$selectedMonth-$selectedDay"
        checkPhone(phone, email, fullName, password, typeCheck)
    }

    private fun checkPhone(phone: String, email: String?, fullName: String, password: String, typeCheck: String) {
        viewModelScope.launch(dispatcher) {
            try {
                val token = if (!isOffLine) Common.key else authen
                checkPhoneUseCase.execute("dailyaf", "checkdt", phone, idEmployee, token, typeCheck).collect { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            if (!result.data) {
                                if (!email.isNullOrEmpty()) {
                                    checkEmail(email, fullName, phone, password, typeCheck)
                                } else {
                                    registerAccount(fullName, phone, null, password, dateOfBirtday)
                                }
                            } else {
                                _validationError.value = stringProvider.getString(R.string.registered_phone)
                            }
                        }
                        is NetworkResponse.Error -> _validationError.value = result.message
                        NetworkResponse.Loading -> {}
                    }
                }
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    private fun checkEmail(email: String, fullName: String, phone: String, password: String, typeCheck: String) {
        viewModelScope.launch(dispatcher) {
            try {
                val token = if (!isOffLine) Common.key else authen
                checkEmailUseCase.execute("dailyaf", "checkemail", email, idEmployee, token, typeCheck).collect { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            if (!result.data) {
                                registerAccount(fullName, phone, null, password, dateOfBirtday)
                            } else {
                                _validationError.value = stringProvider.getString(R.string.registered_email)
                            }
                        }
                        is NetworkResponse.Error -> _validationError.value = result.message
                        NetworkResponse.Loading -> {}
                    }
                }
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun registerAccount(fullName: String, phone: String, email: String?, password: String, dateOfBirtday: String) {
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

    fun clearValidationError() {
        _validationError.value = null
    }
}
