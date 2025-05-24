package com.contrast.Contrast.presentation.components.profile.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.presentation.mapper.withUiIcon
import com.contrast.Contrast.utils.StringProvider
import com.contrast.Contrast.utils.Util
import com.itechpro.data.config.AppConfig
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.category.Category

import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.HomeNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.OpportunityNavEvent
import com.itechpro.domain.model.navigationEvent.OrderNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.navigationEvent.ReportPersonalSalesNaEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.model.profile.ProfileNaEvent
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


    private val _state = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state

    private var currentUserInfo: CurrentUserInfo? = null

    init {
        viewModelScope.launch { initUser() }
    }

    fun loadCategory() {


        viewModelScope.launch(dispatcher) {
            try {
                _state.update {
                    it.copy(
                        tabs = useCase.generateCategory(),
                    )
                }
            } catch (e: Exception) {

            }
        }
    }

    fun onCategorySelected(index: Int, tabs: List<Category>) {
        if (state.value.selectedTab != index) {
            val code = tabs.getOrNull(index)?.code.orEmpty()
            _state.update { it.copy(selectedTab = index) }
            var content = ""
            if (code == "personal") {
                val url = Util.extractAfterScheme(currentUserInfo?.domain ?: "")
                val http = Util.extractBeforeScheme(currentUserInfo?.domain ?: "")
                content =
                    "${currentUserInfo?.employeeId}-${currentUserInfo?.employeeName}-$url-$http://"

            } else if (code == "ios") {
                content = "https://apps.apple.com/vn/app/ezmax-crm/id1435470585"
            } else {
                content = "https://play.google.com/store/apps/details?id=com.itechpro.ezmax"
            }
            getQrCodeContent(content)
        }
    }

    private suspend fun initUser() {
        runCatching { getCurrentUserUseCase() }.onSuccess { user ->
            currentUserInfo = user
            _state.update {
                it.copy(
                    domain = user.domain,
                    device = user.device,
                    typeAccount = user.typeAccount,
                    customerId = user.customerId,
                    employeeId = user.employeeId,
                    employeeName = user.employeeName
                )
            }
        }.onFailure {
            _state.update {
                it.copy(validationError = stringProvider.getString(R.string.error_connection))
            }
        }
    }

    fun gotoHome() {

        _state.update {
            it.copy(navEvent = SplashNaEvent.GoToMain("0", "0", "0"))
        }
    }

    fun getQrCodeEmployee() {
        val user = currentUserInfo ?: return
        viewModelScope.launch {
            useCase.getQrCodeEmployee(user.token).collectResponse(dispatcher = dispatcher,
                onSuccess = { data -> _state.update { it.copy(qrCode = data) } },
                onError = { it })
        }
    }

    fun getQrCodeCustomer() {
        val user = currentUserInfo ?: return
        viewModelScope.launch {
            useCase.getQrCodeCustomer(user.customerId, user.token)
                .collectResponse(dispatcher = dispatcher,
                    onSuccess = { data -> _state.update { it.copy(qrCode = data) } },
                    onError = { it })
        }
    }

    fun getQrCodeContent(content: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch {
            useCase.getQrCodeContent(content, user.token).collectResponse(dispatcher = dispatcher,
                onSuccess = { data -> _state.update { it.copy(qrCode = data) } },
                onError = { it })
        }
    }

    fun getInfoAccount(idCustomer: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch {
            useCase.getInfoAccount(idCustomer, user.typeAccount, user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { data ->
                    _state.update {
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
                }, onError = { it })
        }
    }

    fun onLoginClick() {


        _state.update {
            it.copy(
                navEvent = SplashNaEvent.GoToLogIn("1")
            )
        }
    }

    fun onProfileClick(obj: Category) {

        val code = obj.ma ?: ""
        val title = obj.ten ?: ""
        Log.e("onProfileClick", code)








        _state.update {
            it.copy(
                navEvent = SplashNaEvent.GoToLogIn("1")
            )
        }


        when (code) {

            "qrcodegiothieu" -> {
                _state.update { it.copy(navEvent = ProfileNaEvent.GoToShareQrcode(title)) }
            }


            "tiepthilienket" -> {
                _state.update { it.copy(navEvent = ProfileNaEvent.GoToShareProduct(title)) }
            }

            "thunhap" -> {
                _state.update { it.copy(navEvent = ProfileNaEvent.GoToInCome(title)) }
            }

            "sanphamdaxem" -> {
                _state.update {
                    it.copy(
                        navEvent = ProfileNaEvent.GoToProductViewSave(
                            "sanphamdaxem", title
                        )
                    )
                }
            }

            "sanphamdaluu" -> {
                _state.update {
                    it.copy(
                        navEvent = ProfileNaEvent.GoToProductViewSave(
                            "sanphamdaluu", title
                        )
                    )
                }
            }

            "lieutrinhdangthuchien" -> {
                _state.update { it.copy(navEvent = ProfileNaEvent.GoToServiceProgress(title)) }
            }

            "lichthuchiendichvu" -> {
                _state.update { it.copy(navEvent = ProfileNaEvent.GoToServiceCalendar(title)) }
            }

            "donhangchoxacnhan" -> {
                _state.update {
                    it.copy(
                        navEvent = OrderNavEvent.GoToOderType(
                            "donhangchoxacnhan", title
                        )
                    )
                }
            }

            "donhangdaxacnhan" -> {
                _state.update {
                    it.copy(
                        navEvent = OrderNavEvent.GoToOderType(
                            "donhangdaxacnhan", title
                        )
                    )
                }
            }

            "donhang" -> {
                _state.update { it.copy(navEvent = OrderNavEvent.GoToOderType("donhang", title)) }
            }

            "huongdanspatainha" -> {
                _state.update { it.copy(navEvent = ProfileNaEvent.GoToSpaAtHome(title)) }
            }

            "dailycapduoi" -> {
                _state.update { it.copy(navEvent = ProfileNaEvent.GoToAgency(title)) }
            }

            "cantuvan" -> {
                _state.update { it.copy(navEvent = OpportunityNavEvent.GoToOpportunity("cantuvan",title)) }
            }

            "doanhsotieudungcanhan" -> {
                _state.update { it.copy(navEvent = ReportPersonalSalesNaEvent.GoToPersonalConsumptionSales(title)) }
            }
  "baocaothuongthangcap" -> {
                _state.update { it.copy(navEvent = ReportPersonalSalesNaEvent.GoToUpToLevelSales("baocaothuongthangcap",title)) }
            }
  "baocaothuongthangcapcanhan" -> {
                _state.update { it.copy(navEvent = ReportPersonalSalesNaEvent.GoToUpToLevelSales("baocaothuongthangcapcanhan",title)) }
            }

            "baocaohoahongthudong" -> {
                _state.update { it.copy(navEvent = ReportPersonalSalesNaEvent.GoToPassiveCommissionReport(title)) }
            }

            "baocaodoanhsotheotungdaily" -> {
                _state.update { it.copy(navEvent = ReportPersonalSalesNaEvent.GoToSalesReportByAgency(title)) }
            }

            "baocaothuongthangcap" -> {
                _state.update {
                    it.copy(
                        navEvent = ReportPersonalSalesNaEvent.GoToRankAdvancementBonusReport(
                            "daily", title
                        )
                    )
                }
            }

            "baocaothuongthangcapcanhan" -> {
                _state.update {
                    it.copy(
                        navEvent = ReportPersonalSalesNaEvent.GoToRankAdvancementBonusReport(
                            "canhan", title
                        )
                    )
                }
            }

        }
//        _navigationEvent.value = SplashNaEvent.GoToLogIn("1")
    }

    fun onRegisterClick() {
        _state.update { it.copy(navEvent = SplashNaEvent.GoToRegister) }

    }

    fun resetNavigation() {

        _state.update { it.copy(navEvent = ProfileNaEvent.None) }
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

        _state.update { it.copy(navEvent = SplashNaEvent.GoToLogIn("0")) }


    }

    fun getMenuApp() {
        val user = currentUserInfo ?: return
        viewModelScope.launch {
            useCase.getMenuApp(user.typeAccount, user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { data ->
                    val allCategories = (data.oders + data.categorys).withUiIcon()
                    _state.update {
                        it.copy(
                            oders = data.oders, categorys = data.categorys
                        )
                    }
                }, onError = { it })
        }
    }
}
