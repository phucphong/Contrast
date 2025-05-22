package com.contrast.Contrast.presentation.features.login

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.presentation.mapper.ValidationErrorMapper
import com.contrast.Contrast.utils.Common
import com.contrast.Contrast.utils.StringProvider
import com.contrast.Contrast.utils.Util
import com.itechpro.data.api.RetrofitArrayAPI2
import com.itechpro.data.config.AppConfig
import com.itechpro.domain.enumApp.ValidationErrorType
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.Setting
import com.itechpro.domain.model.login.Login
import com.itechpro.domain.model.login.LoginUiState
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.login.LoginInputValidator
import com.itechpro.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
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

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    private lateinit var currentUserInfo: CurrentUserInfo

    init {
        viewModelScope.launch {
            currentUserInfo = getCurrentUserUseCase()
            _state.update {
                it.copy(
                    domainLogin = currentUserInfo.domain,
                    rememberPassword = currentUserInfo.rememberPassword,
                    account =currentUserInfo.account ,
                    password = if( currentUserInfo.rememberPassword)  currentUserInfo.password else   currentUserInfo.password,
                    passwordBiometricAuthen = currentUserInfo.password,

                )
            }
        }
    }


    fun validateAndLogin(account: String, password: String) {
        val validationResult = validator.validateAll(account, password)
        if (!validationResult.success) {
            val error = ValidationErrorType.fromCode(validationResult.message)
            _state.update {
                it.copy(errorMessage = stringProvider.getString(ValidationErrorMapper.toMessageResId(error)))
            }
            return
        }

        val loginData = buildLogin(account, password)
        appConfig.setAccount(account)
        appConfig.setPassword(password)
        if (_state.value.rememberPassword) {
            appConfig.setPassword(password)

        }


        login(loginData, password)
    }
    fun loginBiometricAuthenticator(account: String, password: String) {


        val loginData = buildLogin(account, password)
        appConfig.setAccount(account)
        if (_state.value.rememberPassword) {
            appConfig.setPassword(password)
        }
        login(loginData, password)
    }

    fun rememberPassword(checked: Boolean) {
        appConfig.setRememberPassword(checked)
        _state.update { it.copy(rememberPassword = checked) }
    }
    fun autoLoginFromFingerprint(account:String, password:String) {


        if (account.isNullOrEmpty() || password.isNullOrEmpty()) {
            _state.update {
                it.copy(errorMessage = "Không tìm thấy tài khoản đã lưu để đăng nhập.")
            }
            return
        }
        loginBiometricAuthenticator(account, password)
    }
    // Sau khi navigate xong, reset lại state

    fun registerAccount() {
        _state.update {
            it.copy(
                navEvent = SplashNaEvent.GoToRegister
            )
        }
    }
    fun domain() {
        _state.update {
            it.copy(
                navEvent = SplashNaEvent.GoToDomain
            )
        }
    }
   fun forgotPassword() {
        _state.update {
            it.copy(
                navEvent = SplashNaEvent.GoToForgotPassword
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
            _state.update { it.copy(isLoading = true, errorMessage = "") }
            try {
                when (val result = loginUseCase(login)) {
                    is NetworkResponse.Success -> {
                        saveLoginOptions(result.data, password)
                        delay(1000)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                loginResult = result.data,
                                errorMessage = if(result.data==null) "Mật khẩu hoặc pass đang sai" else "",
                                navEvent = SplashNaEvent.GoToMain("0", "0", "0")
                            )
                        }
                    }

                    is NetworkResponse.Error -> {
                        _state.update { it.copy(isLoading = false, errorMessage = stringProvider.getString(R.string.account_password_error)) }
                    }
                    else -> {
                        _state.update {
                            it.copy(isLoading = false, errorMessage = stringProvider.getString(R.string.error_unknown))
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage}"
                    )
                }
            }
        }
    }
    private fun checkCustomerActiveContract(domainCustomer: String, context: Context) {
        val retrofit: Retrofit = Util.initRetrofit("https://itp.ezmax.vn", context)
        val service: RetrofitArrayAPI2 = retrofit.create(RetrofitArrayAPI2::class.java)
        val call: Call<List<Setting>> = service.checkCustomerActiveContract(
            Common.key, domainCustomer
        )
        call.enqueue(object : Callback<List<Setting>> {

            override fun onFailure(call: Call<List<Setting>>, t: Throwable) {

                _state.update {
                    it.copy(isLoading = false,
                        errorMessage = stringProvider.getString(R.string.error_code_connection)
                    )
                }
            }
            override fun onResponse(
                call: Call<List<Setting>>,
                response: Response<List<Setting>>
            ) {
                try {
                    _state.update {
                        it.copy(isLoading = true,
                            errorMessage = ""
                        )
                    }

                    if (response.body() != null) {


                        val connection: Setting = response.body()!![0]
                        var lydotamdung = connection.lydotamdung?:""
                        var noidungbaotruoc =  connection.noidungbaotruoc?:""

                        if (lydotamdung.isNotEmpty()) {
                            _state.update {
                                it.copy(isLoading = false,
                                    errorMessage =lydotamdung
                                )
                            }
//                            AppConfig.setngayhientai(this@LoginAcitivity, Util.ngayhomngay())
                        } else if (!TextUtils.isEmpty(noidungbaotruoc)) {
                            _state.update {
                                it.copy(isLoading = false,
                                    errorMessage =noidungbaotruoc
                                )
                            }
                        } else {
                            _state.update {
                                it.copy(isLoading = false,
                                    navEvent = SplashNaEvent.GoToMain("0", "0", "0")
                                )
                            }
                        }
                    }




                } catch (e: java.lang.Exception) {
                    Log.e("Exception", e.toString())
                }
            }
        })
    }
    private fun saveLoginOptions(result: Login, password: String) {

        Log.e("getProductsByIdParent",result.token ?: "")
        appConfig.setToken(result.token ?: "")
        appConfig.setEmployeeId(result.idnhanvien ?: "")
        appConfig.setCustomerId(result.idkh ?: "")
        appConfig.setEmployeeName(result.hoten ?: "")
        appConfig.setTypeAccount(result.loaitk ?: "")
        appConfig.setPermissionMobile(result.permissionmobile ?: "")
        appConfig.setSalesPointId(result.iddiembanle ?: "")
        appConfig.setSalesPointName(result.tendiambanle ?: "")
        appConfig.setAdmin((result.isadmincoso ?: "False").toBoolean())
        appConfig.setAdminRoot((result.isadmin ?: "False").toBoolean())

    }

    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = "") }
    }

    fun resetNavigation() {
        _state.update { it.copy(navEvent = SplashNaEvent.None) }
    }
}
