package com.contrast.Contrast.presentation.features.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.convertDateToYYYYMMDD
import com.contrast.Contrast.presentation.mapper.ValidationErrorMapper
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.data.config.AppConfig
import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.enumApp.ValidationErrorType
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.Login
import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase

import com.itechpro.domain.usecase.login.LoginInputValidator
import com.itechpro.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val appConfig: AppConfig,
    private val validator: LoginInputValidator,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _registerState = MutableStateFlow<NetworkResponse<List<Login>>>(NetworkResponse.Loading)
    val registerState: StateFlow<NetworkResponse<List<Login>>> = _registerState

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError



    private val _domainLogin = MutableStateFlow("")
    val domainLogin: StateFlow<String> = _domainLogin

    private lateinit var currentUserInfo: CurrentUserInfo

    init {
        viewModelScope.launch {
            currentUserInfo = getCurrentUserUseCase()

            _domainLogin.value = currentUserInfo.domainCustomer
        }
    }

    fun validateAndLogin(
        account: String,
        password: String,

    ) {
        val validationResult = validator.validateAll(account,password)
        if (!validationResult.success) {
            handleValidationError(validationResult.message)
            return
        }

        val obj = buildLogin(
            account,
            password,

        )

       login(obj)
    }

    private fun buildLogin(
        account: String,
        password: String,

    ): Login {

        return Login(
            Username = account,
            Password = password,
            mamenu = "login",
            hanhdong = "login",
            noidungchinh = account,
            device = currentUserInfo.device
        )
    }

    private fun handleValidationError(errorCode: String?) {
        val error = ValidationErrorType.fromCode(errorCode)
        _validationError.value = stringProvider.getString(ValidationErrorMapper.toMessageResId(error))
    }


 
  
    private fun login( Login: Login) {
        viewModelScope.launch(dispatcher) {
            _registerState.value = NetworkResponse.Loading
            try {
              
                val result = loginUseCase.invoke( Login)

//                saveLoginOptions(result)
                _registerState.value = result

            } catch (e: Exception) {
                _registerState.value = NetworkResponse.Error(stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}")
            }
        }
    }
    fun saveLoginOptions(result: Login) {
        appConfig.setToken(result.token?:"")
        appConfig.setIdEmployee(result.idnhanvien?:"")
        appConfig.setEmployeeName(result.hoten?:"")
        appConfig.setTypeAccount(result.loaikh?:"")
        appConfig.setPermissionMobile(result.permissionmobile?:"")
        appConfig.  setSalesPointId(result.iddiembanle?:"")
        appConfig.  setSalesPointName(result.tendiambanle?:"")
        appConfig. setAdmin(result.isadmincoso?:false)
        appConfig.setAdminRoot(result.isadmin?:false)
    }

    fun clearValidationError() {
        _validationError.value = null
    }
}