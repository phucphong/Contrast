package com.contrast.Contrast.presentation.features.splas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.presentation.base.BaseViewModel
import com.contrast.Contrast.presentation.features.login.ui.LoginActivity
import com.contrast.Contrast.presentation.features.main.ContrastActivity
import com.contrast.Contrast.utils.NetworkChecker
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.home.HomeAffiliateUseCase
import com.itechpro.domain.usecase.setting.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val useCase: SettingUseCase,
    private val networkChecker: NetworkChecker,
) : BaseViewModel(networkChecker, dispatcher) {

    private val _validationError = MutableStateFlow<String>("")
    val validationError: StateFlow<String> = _validationError
    private val _navigateTo = MutableStateFlow<Class<*>?>(null)
    val navigateTo: StateFlow<Class<*>?> = _navigateTo

    init {
        viewModelScope.launch(dispatcher) {
            try {




            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }


    fun checkLoginState() {
        viewModelScope.launch {
            val isLoggedIn = getCurrentUserUseCase.isLoggedIn()
            if(isLoggedIn){
                getSettingViewOff()
            }else{
                _navigateTo.value=LoginActivity::class.java
            }

        }
    }


    fun getSettingViewOff() {

        viewModelScope.launch(dispatcher) {

            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getSettingViewOff( "laydulieu","cauhinhhienthi").collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
                          if(result.data!=null){}
                            _navigateTo.value=ContrastActivity::class.java

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
