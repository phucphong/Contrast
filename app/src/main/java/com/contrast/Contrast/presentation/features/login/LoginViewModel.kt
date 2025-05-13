package com.contrast.Contrast.presentation.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.presentation.mapper.ValidationErrorMapper
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.data.config.AppConfig
import com.itechpro.domain.enumApp.ValidationErrorType
import com.itechpro.domain.model.CurrentUserInfo

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.login.Login
import com.itechpro.domain.model.login.LoginUiState
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.login.LoginInputValidator
import com.itechpro.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private lateinit var currentUserInfo: CurrentUserInfo

    init {
        viewModelScope.launch {
            currentUserInfo = getCurrentUserUseCase()
            _uiState.update {
                it.copy(
                    domainLogin = currentUserInfo.domainCustomer,
                    rememberPassword = currentUserInfo.rememberPassword,
                    account =currentUserInfo.account ,
                    password = if( currentUserInfo.rememberPassword)  currentUserInfo.password else "",
                    passwordBiometricAuthen = currentUserInfo.password,

                )
            }
        }
    }


    fun validateAndLogin(account: String, password: String) {
        val validationResult = validator.validateAll(account, password)
        if (!validationResult.success) {
            val error = ValidationErrorType.fromCode(validationResult.message)
            _uiState.update {
                it.copy(errorMessage = stringProvider.getString(ValidationErrorMapper.toMessageResId(error)))
            }
            return
        }

        val loginData = buildLogin(account, password)
        appConfig.setAccount(account)
        appConfig.setPassword(password)
        if (_uiState.value.rememberPassword) {
            appConfig.setPassword(password)

        }


        login(loginData, password)
    }
    fun loginBiometricAuthenticator(account: String, password: String) {


        val loginData = buildLogin(account, password)
        appConfig.setAccount(account)
        if (_uiState.value.rememberPassword) {
            appConfig.setPassword(password)

        }


        login(loginData, password)
    }

    fun rememberPassword(checked: Boolean) {
        appConfig.setRememberPassword(checked)
        _uiState.update { it.copy(rememberPassword = checked) }
    }
    fun autoLoginFromFingerprint(account:String, password:String) {


        if (account.isNullOrEmpty() || password.isNullOrEmpty()) {
            _uiState.update {
                it.copy(errorMessage = "Không tìm thấy tài khoản đã lưu để đăng nhập.")
            }
            return
        }

        loginBiometricAuthenticator(account, password)
    }

    fun registerAccount() {
        _uiState.update {
            it.copy(
                navigationEvent = SplashNaEvent.GoToRegister
            )
        }
    }
    fun domain() {
        _uiState.update {
            it.copy(
                navigationEvent = SplashNaEvent.GoToDomain
            )
        }
    }
   fun forgotPassword() {
        _uiState.update {
            it.copy(
                navigationEvent = SplashNaEvent.GoToDomain
            )
        }
    }

     fun buildLogin(account: String, password: String): Login {
        return Login(
            Username = account,
            Password = password,
            mamenu = "login",
            hanhdong = "login",
            noidungchinh = account,
            device = currentUserInfo.device
        )
    }

    private fun login(login: Login, password:String) {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                when (val result = loginUseCase(login)) {
                    is NetworkResponse.Success -> {
                        saveLoginOptions(result.data, password)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                loginResult = result.data,
                                navigationEvent = SplashNaEvent.GoToMain("0", "0", "0")
                            )
                        }


                    }

                    is NetworkResponse.Error -> {
                        _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                    }

                    else -> {
                        _uiState.update {
                            it.copy(isLoading = false, errorMessage = stringProvider.getString(R.string.error_unknown))
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    private fun saveLoginOptions(result: Login, password: String) {
        appConfig.setToken(result.token ?: "")
        appConfig.setEmployeeId(result.idnhanvien ?: "")
        appConfig.setCustomerId(result.idkh ?: "")
        appConfig.setEmployeeName(result.hoten ?: "")
        appConfig.setTypeAccount(result.loaikh ?: "")
        appConfig.setPermissionMobile(result.permissionmobile ?: "")
        appConfig.setSalesPointId(result.iddiembanle ?: "")
        appConfig.setSalesPointName(result.tendiambanle ?: "")
        appConfig.setAdmin((result.isadmincoso ?: "False").toBoolean())
        appConfig.setAdminRoot((result.isadmin ?: "False").toBoolean())


    }

    fun clearValidationError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun resetNavigation() {
        _uiState.update { it.copy(navigationEvent = SplashNaEvent.None) }
    }
}
