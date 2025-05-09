package com.contrast.Contrast.presentation.components.profile.viewModel


import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.model.Video
import com.itechpro.domain.model.cart.CartUiState
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.HomeNavEvent
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
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {


    private val _obj = MutableStateFlow<Video?>(null)
    val obj: StateFlow<Video?> = _obj


    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState
    private val _navigationEvent = MutableStateFlow<NavEvent>(CartNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private var currentUserInfo: CurrentUserInfo? = null


    init {
        viewModelScope.launch(dispatcher) {
            runCatching { getCurrentUserUseCase() }.onSuccess { user ->
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


            }.onFailure {
                _uiState.update {
                    it.copy(validationError = stringProvider.getString(R.string.error_connection))
                }
            }
        }
    }

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }
    fun onLoginClick() {
        viewModelScope.launch {
            _navigationEvent.value = SplashNaEvent.GoToLogIn
        }
    }
    fun onRegisterClick() {
        viewModelScope.launch {
            _navigationEvent.value = SplashNaEvent.GoToLogout
        }
    }

    // Sau khi navigate xong, reset lại state
    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }

    fun getQrCodeEmployee() {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                useCase.getQrCodeEmployee(user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }

                        is NetworkResponse.Success -> {
                            _uiState.update {
                                it.copy(qrCode = result.data)
                            }
                        }

                        is NetworkResponse.Error -> {
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value =
                    stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }


    fun getQrCodeCustomer() {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                useCase.getQrCodeCustomer(user.customerId, user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }

                        is NetworkResponse.Success -> {
                            _uiState.update {
                                it.copy(qrCode = result.data)
                            }
                        }

                        is NetworkResponse.Error -> {
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value =
                    stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun getInfoAccount(idCustomer: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            try {
                useCase.getInfoAccount(idCustomer, user.typeAccount, user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }

                        is NetworkResponse.Success -> {


                            _uiState.update {
                                it.copy(
                                    avartar = "${uiState.value.domain}/${result.data.anhdaidientxt ?: ""}",
                                    fullName = result.data.hoten ?: "",
                                    phone = result.data.dienthoai ?: "",
                                    agencyName = result.data.tencapdaily ?: "",
                                    address = result.data.thuongtrusonha ?: "",
                                    discount = result.data.phantramchietkhau ?: 0.0,
                                    isLogin = if(user.token.isNotEmpty()) true else false,

                                    )
                            }
                        }

                        is NetworkResponse.Error -> {
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value =
                    stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun getMenuApp() {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                useCase.getMenuApp(user.typeAccount, user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {

                        }

                        is NetworkResponse.Success -> {

                            val allCategories = buildList {
                                addAll(result.data.oders)
                                addAll(result.data.categorys)

                            }

                            setIcon(allCategories)

                            _uiState.update {
                                it.copy(
                                    oders = result.data.oders, categorys = result.data.categorys
                                )
                            }

                        }

                        is NetworkResponse.Error -> {
                            _isLoading.value = false
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value =
                    stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    private fun setIcon(datas: List<Category>) {

        for (obj in datas) {
            val key = obj.ma ?: ""


            when (key) {
                "donhangchoxacnhan" -> {
                    obj.icon = R.drawable.oderwaiting
                }

                "donhangdaxacnhan" -> {
                    obj.icon = R.drawable.booking
                }

                "donhang" -> {
                    obj.icon = R.drawable.purchaseorder
                }


                "cupons" -> {
                    obj.icon = R.drawable.coupon
                }

                "setting" -> {
                    obj.icon = R.drawable.settings
                }

                "sanphamdaxem" -> {
                    obj.icon = R.drawable.clock_aff
                }

                "sanphamdaluu" -> {
                    obj.icon = R.drawable.heart_aff
                }

                "hangthe" -> {
                    obj.icon = R.drawable.process
                }

                "lieutrinhdangthuchien" -> {
                    obj.icon = R.drawable.process
                }

                "lichthuchiendichvu" -> {
                    obj.icon = R.drawable.appointmentbook
                }

                "phantramchietkhau" -> {
                    obj.icon = R.drawable.discount
                }

                "lichsuthanhtoan" -> {
                    obj.icon = R.drawable.history
                }

                "huongdanspatainha" -> {
                    obj.icon = R.drawable.manual
                }

                "dailycapduoi" -> {
                    obj.icon = R.drawable.hierarchy
                }

                "trothanhdaily" -> {
                    obj.icon = R.drawable.agency
                }

                "cantuvan" -> {
                    obj.icon = R.drawable.opportunity
                }

                "doanhsotieudungcanhan" -> {
                    obj.icon = R.drawable.consumer
                }

                "baocaodoanhsotheotungdaily" -> {
                    obj.icon = R.drawable.topthree
                }

                "baocaohoahongthudong" -> {
                    obj.icon = R.drawable.levelup
                }

                "baocaothuongthangcap" -> {
                    obj.icon = R.drawable.progressreport
                }

                "baocaothuongthangcapcanhan" -> {
                    obj.icon = R.drawable.progressreport
                }

                "khoahoccuatoi" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "coachinh121" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "lichcoaching" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "hoidap" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "sanphamquantam" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "banquantam" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "khoahocdadangky" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "kehoachcoaching" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "videoyeuthich" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "tiendokehoachcoaching" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "thanhtoankhachhang" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "thoigianthanhtoan" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "baocaothanhtoankh" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "doimatkhau" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "switchApp" -> {
                    obj.icon = R.drawable.usertranfer
                }

                "qrcodegiothieu" -> {
                    obj.icon = R.drawable.qrcodegiothieu
                }

                "nangcapthanhdaily" -> {
                    obj.icon = R.drawable.agency
                }

                "thunhap" -> {
                    obj.icon = R.drawable.commission
                }

                "tiepthilienket" -> {
                    obj.icon = R.drawable.governance
                }

                else -> {

                }

            }
        }


    }


}
