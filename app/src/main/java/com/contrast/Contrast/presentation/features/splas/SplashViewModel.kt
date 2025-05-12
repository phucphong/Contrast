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
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun handleShareIntent(intent: Intent) {
        val result = handleShareIntentUseCase.execute(intent) ?: return


            if (result.domain.isNotEmpty() && result.domain != _domain.value) {
                AppModule.updateBaseUrl(result.domain)
                appConfig.setDomain(result.domain)
                _navigationEvent.value = SplashNaEvent.ShowAffiliateInfo(
                    id = result.id,
                    idUnit = result.idUnit,
                    introducerId = result.introducerId,
                    domain = result.domain
                )

            } else {

                Log.e("_navigationEvent", "${result.id} ${result.idUnit}")
                _navigationEvent.value = SplashNaEvent.GoToMain(
                    id = result.id,
                    idUnit = result.idUnit,
                    introducerId = result.introducerId,
                )




        }
    }

    fun checkLoginState() {
        viewModelScope.launch {
            val isLoggedIn = getCurrentUserUseCase.isLoggedIn()
            if (isLoggedIn) {
                getSettingViewOff()
            } else {
                _navigationEvent.value = SplashNaEvent.GoToLogIn
            }
        }
    }

    private fun getSettingViewOff() {
        viewModelScope.launch(dispatcher) {
            try {
                useCase.getSettingViewOff("laydulieu", "cauhinhhienthi").collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {}
                        is NetworkResponse.Success -> {
                            _navigationEvent.value = SplashNaEvent.GoToMain(
                                id = "0",
                                idUnit = "0",
                                introducerId = "0",
                            )
                        }
                        is NetworkResponse.Error -> {
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }
}
