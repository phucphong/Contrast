package com.contrast.Contrast.presentation.features.splas

import android.content.Intent
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.module.app.AppModule
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.presentation.base.BaseViewModel
import com.contrast.Contrast.utils.NetworkChecker
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.data.config.AppConfig
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.setting.SettingUseCase
import com.itechpro.domain.usecase.share.HandleShareIntentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val stringProvider: StringProvider,
    private val handleShareIntentUseCase: HandleShareIntentUseCase,
    private val useCase: SettingUseCase,
    private val appConfig: AppConfig,
    private val networkChecker: NetworkChecker,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel(networkChecker, dispatcher) {

    private var currentUserInfo: CurrentUserInfo? = null

    private val _validationError = MutableStateFlow("")
    val validationError: StateFlow<String> = _validationError


    private val _navigationEvent = MutableStateFlow<NavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent

    private val _domain = MutableStateFlow("")
    val domain: StateFlow<String> = _domain

    init {
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                _domain.value = currentUserInfo?.domain.orEmpty()
            } catch (e: Exception) {
                _validationError.value =
                    stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun handleShareIntent(intent: Intent) {
        getAppType(intent)

    }

    fun  intentProductDetail(intent: Intent){
        val result = handleShareIntentUseCase.execute(intent) ?: return
        if (result.domain.isNotEmpty() && result.domain != _domain.value) {

            appConfig.setDomain(result.domain)
            _navigationEvent.value = SplashNaEvent.ShowAffiliateInfo(
                id = result.id,
                idUnit = result.idUnit,
                introducerId = result.introducerId,
                domain = result.domain
            )

        } else {
            _navigationEvent.value = SplashNaEvent.GoToMain(
                id = result.id,
                idUnit = result.idUnit,
                introducerId = result.introducerId,
            )


        }
    }

    fun checkLoginState() {
        viewModelScope.launch {

            getAppType(null)

        }
    }
    fun resetNavigation() {
        _navigationEvent.value =SplashNaEvent.None
    }
    private fun getSettingDisplay() {
        viewModelScope.launch(dispatcher) {
            try {
                useCase.getSettingViewOff("laydulieu", "cauhinhhienthi").collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {}
                        is NetworkResponse.Success -> {
                            appConfig.setDisplayProduct(result.data?.bansanpham?:"")
                            appConfig.setDisplayService(result.data?.bandichvu?:"")
                            appConfig.setDisplayPriority(result.data?.uutienhienthisanpham?:"")

                            if(result.data!=null){
                                _navigationEvent.value = SplashNaEvent.GoToMain("0", "0", "0")
                            }


                        }

                        is NetworkResponse.Error -> {
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _validationError.value =
                    stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    private fun getAppType(intent: Intent?) {
        viewModelScope.launch(dispatcher) {
            try {
                useCase.getAppType("loaiapp").collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {}
                        is NetworkResponse.Success -> {
                            //tmdt

                            val status = result.data.trangthai ?: ""
                            appConfig.setAppType(status)
                            if(intent!=null){
                                intentProductDetail(intent)

                            }else{
                                val isLoggedIn = getCurrentUserUseCase.isLoggedIn()
                                val isDomain = getCurrentUserUseCase.isDomain()
                                if (!isDomain) {
                                    _navigationEvent.value = SplashNaEvent.GoToDomain
                                } else if (isLoggedIn) {
                                    if(status=="tmdt"){
                                        getSettingDisplay()
                                    }else{
                                        // thay vào main nhân viên
                                        _navigationEvent.value = SplashNaEvent.GoToMain("0", "0", "0")
                                    }
                                } else {

                                    if(status=="tmdt"){
                                        getSettingDisplay()
                                    }else{
                                        _navigationEvent.value = SplashNaEvent.GoToLogIn("0")
                                    }

                                }
                            }
                        }
                        is NetworkResponse.Error -> {
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _validationError.value =
                    stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }
    private fun getVerificationCodes() {
        viewModelScope.launch(dispatcher) {
            try {
                useCase.getVerificationCodes().collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {}
                        is NetworkResponse.Success -> {
                            //tmdt




                        }
                        is NetworkResponse.Error -> {
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _validationError.value =
                    stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }
}
