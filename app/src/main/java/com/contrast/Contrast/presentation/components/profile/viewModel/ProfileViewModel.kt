package com.contrast.Contrast.presentation.components.profile.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.presentation.mapper.withUiIcon
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.data.config.AppConfig
import com.itechpro.domain.model.CurrentUserInfo

import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.model.profile.ProfileUiState
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.profile.ProfileUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: ProfileUseCase,
    private val appConfig: AppConfig,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {




    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState
    private val _navigationEvent = MutableStateFlow<NavEvent>(CartNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent
    private var currentUserInfo: CurrentUserInfo? = null

    init {
        viewModelScope.launch { initUser() }
    }

    private suspend fun initUser() {
        runCatching { getCurrentUserUseCase() }
            .onSuccess { user ->
                currentUserInfo = user
                _uiState.update {
                    it.copy(
                        domain = user.domain.orEmpty(),
                        device = user.device.orEmpty(),
                        typeAccount = user.typeAccount.orEmpty(),
                        customerId = user.customerId.orEmpty(),
                        employeeId = user.employeeId.orEmpty()
                    )
                }
            }
            .onFailure {
                _uiState.update {
                    it.copy(validationError = stringProvider.getString(R.string.error_connection))
                }
            }
    }



    fun getQrCodeEmployee() {
        val user = currentUserInfo ?: return
        viewModelScope.launch {
            useCase.getQrCodeEmployee(user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data -> _uiState.update { it.copy(qrCode = data) } },
                onError = {  it }
            )
        }
    }

    fun getQrCodeCustomer() {
        val user = currentUserInfo ?: return
        viewModelScope.launch {
            useCase.getQrCodeCustomer(user.customerId, user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data -> _uiState.update { it.copy(qrCode = data) } },
                onError = { it }
            )
        }
    }

    fun getInfoAccount(idCustomer: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch {
            useCase.getInfoAccount(idCustomer, user.typeAccount, user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    _uiState.update {
                        it.copy(
                            avartar = "${it.domain}/${data.anhdaidientxt.orEmpty()}",
                            fullName = data.hoten.orEmpty(),
                            phone = data.dienthoai.orEmpty(),
                            agencyName = data.tencapdaily.orEmpty(),
                            address = data.thuongtrusonha.orEmpty(),
                            discount = data.phantramchietkhau ?: 0.0,
                            isLogin = user.token.isNotEmpty()
                        )
                    }
                },
                onError = { it }
            )
        }
    }
    fun onLoginClick() {
        _navigationEvent.value = SplashNaEvent.GoToLogIn("1")
    }

    fun onRegisterClick() {
        _navigationEvent.value = SplashNaEvent.GoToRegister
    }

    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }
    fun onLogoutClick() {
        saveLogOutOptions()
    }

    fun saveLogOutOptions() {
        appConfig.setToken("")
        appConfig.setEmployeeId("")
        appConfig.setCustomerId("")
        appConfig.setEmployeeName("")
        appConfig.setPermissionMobile("")

        _navigationEvent.value = SplashNaEvent.GoToLogIn("0")
    }

    fun getMenuApp() {
        val user = currentUserInfo ?: return
        viewModelScope.launch {
            useCase.getMenuApp(user.typeAccount, user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    val allCategories = (data.oders + data.categorys).withUiIcon()
                    _uiState.update {
                        it.copy(
                            oders = data.oders,
                            categorys = data.categorys
                        )
                    }
                },
                onError = { it }
            )
        }
    }
}
