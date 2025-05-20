package com.contrast.Contrast.presentation.features.domain


import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.module.app.AppModule
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.presentation.base.BaseViewModel
import com.contrast.Contrast.utils.Common
import com.contrast.Contrast.utils.NetworkChecker
import com.contrast.Contrast.utils.StringProvider
import com.contrast.Contrast.utils.Util
import com.itechpro.data.api.RetrofitArrayAPI2
import com.itechpro.data.config.AppConfig
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.Setting
import com.itechpro.domain.model.customer.Customer
import com.itechpro.domain.model.domain.DomainUiState
import com.itechpro.domain.model.login.LoginUiState
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.setting.SettingUseCase
import com.itechpro.domain.usecase.share.HandleShareIntentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DomainViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val stringProvider: StringProvider,

    private val useCase: SettingUseCase,
    private val appConfig: AppConfig,
    private val networkChecker: NetworkChecker,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel(networkChecker, dispatcher) {

    private lateinit var currentUserInfo: CurrentUserInfo

    private val _state = MutableStateFlow(DomainUiState())
    val state: StateFlow<DomainUiState> = _state
    init {
        viewModelScope.launch {
            currentUserInfo = getCurrentUserUseCase()
            _state.update {
                it.copy(
                    domain = currentUserInfo.domain,
                )
            }
        }
    }
    fun registerAccount() {
        _state.update {
            it.copy(
                navEvent = SplashNaEvent.GoToRegister
            )
        }
    }

    fun registerVerificationCodes(code: String, context: Context) {
        if (code.isEmpty()) {


            _state.update {
                it.copy(
                    errorMessage = stringProvider.getString(R.string.emtry_connection)
                )
            }
        } else {
            if (code.lowercase(Locale.getDefault()).contains("http")) {
                appConfig.setDomain(code)

                getVerificationCodes(code, context)
            } else {
                getVerificationCodesOnITP(code, context)
            }
        }
    }
    fun resetNavigation() {
        _state.update { it.copy(navEvent = SplashNaEvent.None) }
    }

    private fun getVerificationCodesOnITP(code: String, context: Context) {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        val retrofit: Retrofit = Util.initRetrofit("https://itp.ezmax.vn", context)
        val service: RetrofitArrayAPI2 = retrofit.create(RetrofitArrayAPI2::class.java)
        val call: Call<List<Setting>> = service.getVerificationCodesOnITP(
            Common.key, code
        )
        call.enqueue(object : Callback<List<Setting>> {

            override fun onFailure(call: Call<List<Setting>>, t: Throwable) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = stringProvider.getString(R.string.error_code_connection)
                    )
                }
            }

            override fun onResponse(
                call: Call<List<Setting>>, response: Response<List<Setting>>
            ) {
                try {

                    var listData: List<Setting> = listOf()
                    if (response.body() != null) {
                        listData = response.body()!!


                    }
                    val verificationCodes = listData[0].tenmien ?: ""
                    if (TextUtils.isEmpty(verificationCodes)) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = stringProvider.getString(R.string.error_code_connection)
                            )
                        }

                    } else {

                        appConfig.setDomain(code)

                        getVerificationCodes(verificationCodes, context)
                    }

                } catch (e: java.lang.Exception) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = stringProvider.getString(R.string.error_code_connection)
                        )
                    }
                }
            }
        })
    }

    private fun getVerificationCodes(code: String, context: Context) {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        val retrofit: Retrofit = Util.initRetrofit(code, context)
        val service: RetrofitArrayAPI2 = retrofit.create(RetrofitArrayAPI2::class.java)
        val call: Call<List<Setting>> = service.getVerificationCodesOnITP(
            Common.key, code
        )
        call.enqueue(object : Callback<List<Setting>> {

            override fun onFailure(call: Call<List<Setting>>, t: Throwable) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = stringProvider.getString(R.string.error_code_connection)
                    )
                }
            }

            override fun onResponse(
                call: Call<List<Setting>>, response: Response<List<Setting>>
            ) {
                try {

                    appConfig.setDomain(code)
// Delay nhẹ hoặc đảm bảo set xong mới gọi API
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(100) // Hoặc đảm bảo Prefs đã commit
                        withContext(Dispatchers.Main) {
                            getAppType()
                        }
                    }


                } catch (e: java.lang.Exception) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = stringProvider.getString(R.string.error_code_connection)
                        )
                    }
                }
            }
        })
    }

    private fun getSettingViewOff() {
        viewModelScope.launch(dispatcher) {
            try {
                useCase.getSettingViewOff("laydulieu", "cauhinhhienthi").collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {}
                        is NetworkResponse.Success -> {
                            appConfig.setDisplayProduct(result.data?.bansanpham ?: "")
                            appConfig.setDisplayService(result.data?.bandichvu ?: "")
                            appConfig.setDisplayPriority(result.data?.uutienhienthisanpham ?: "")

                            if (result.data != null) {


                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            errorMessage = "",
                                            navEvent = SplashNaEvent.GoToMain(
                                                id = "0",
                                                idUnit = "0",
                                                introducerId = "0",
                                            )

                                        )
                                    }
                            }


                        }

                        is NetworkResponse.Error -> {

                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message,

                                    )
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = stringProvider.getString(R.string.error_connection),

                        )
                }
            }
        }
    }


    private fun getAppType() {
        viewModelScope.launch(dispatcher) {
            try {
                useCase.getAppType("loaiapp").collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {}
                        is NetworkResponse.Success -> {
                            //tmdt

                            val status = result.data.trangthai ?: ""

                            appConfig.setAppType(status)
                            if (status == "tmdt") {
//                                getSettingViewOff()

                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "",
                                        navEvent =SplashNaEvent.GoToMain("0", "0", "0")

                                    )
                                }
                            } else {
                                _state.update {
                                    it.copy(
                                        isLoading = false, navEvent = SplashNaEvent.GoToLogIn("0")
                                    )
                                }
                            }
                        }

                        is NetworkResponse.Error -> {


                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message,

                                    )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = stringProvider.getString(R.string.error_connection),

                        )
                }


            }
        }
    }


}
