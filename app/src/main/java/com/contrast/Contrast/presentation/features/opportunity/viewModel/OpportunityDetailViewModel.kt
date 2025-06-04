package com.contrast.Contrast.presentation.features.opportunity.viewModel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.extensions.formatFloat

import com.contrast.Contrast.utils.StringProvider
import com.contrast.Contrast.utils.Util
import com.itechpro.domain.model.*


import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.opportunity.OpportunityDetailUiState
import com.itechpro.domain.model.product.InfoDetail
import com.itechpro.domain.model.product.ProductDetailInput
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.calculateTotalAmount.CalculateTotalAmountUseCase
import com.itechpro.domain.usecase.dowloadFile.DownloadUseCase
import com.itechpro.domain.usecase.opportunity.OpportunityDetailUseCase
import com.itechpro.domain.usecase.opportunity.OpportunityListUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OpportunityDetailViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: OpportunityDetailUseCase,
    private val calculateTotalAmountUseCase: CalculateTotalAmountUseCase,
    private val stringProvider: StringProvider,
    private val downloadUseCase: DownloadUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(OpportunityDetailUiState())
    val state: StateFlow<OpportunityDetailUiState> = _state
    private var currentUserInfo: CurrentUserInfo? = null

    private var currentPage = 0
    private val pageSize = 10
    private var isLoadingNextPage = false


    init {
        viewModelScope.launch(dispatcher) {
            val user = runCatching { getCurrentUserUseCase() }.getOrNull()
            currentUserInfo = user
            if (user != null) {
                _state.update {
                    it.copy(
                        domain = user.domain,
                        token = user.token,
                        device = user.device,
                        permissionMobile = user.permissionMobile,
                        showEmailKH = user.showEmailKH.toBoolean(),
                        showPhoneKH = user.showPhoneKH.toBoolean(),
                        showAddressKH = user.showAddressKH.toBoolean(),

                        )
                }
            }
        }
    }

    fun setSearch(text: String) {
        _state.update {
            it.copy(searchText = text)
        }
    }

    fun handleEdit(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(navEvent = OpportunityNavEvent.GoToEdit(id)) }
        }
    }

    fun updateProductDetails(unitPrice: Double, quantity: Double, vat: Double, discount: Double, isPercent: Boolean) {


        val total =calculateTotalAmountUseCase(unitPrice, quantity, vat, discount, isPercent)
        _state.update {
            it.copy(
                totalMoney = total,


            )
        }
    }


    fun permissionMobileEditOpportunity(
        item: Opportunity, customer: String, customerDelete: String
    ): String {

        if (customer == "1") {
            if (customerDelete == "0") {
                var error =
                    "${stringProvider.getString(R.string.youNotPermissionMobileEditOpportunityCustomer)} ${item.makhachhang ?: "".lowercase()} - ${item.tenkhachhang ?: "".lowercase()} ${
                        stringProvider.getString(R.string.pleaseContactToAdminPermissionMobileEditCustomer)
                    }"
                return error
            } else {
                return ""
            }

        } else {
            val permissions = _state.value.permissionMobile.split(",").map { it.trim() }
            val hasPermission = "danhsachcohoikinhdoanhedit" in permissions
            if (_state.value.adminRoot || _state.value.admin || hasPermission ) {
                return "";
            } else {
                var error =
                    "${stringProvider.getString(R.string.youNotPermissionMobileEditOpportunity)} ${
                        stringProvider.getString(R.string.pleaseContactToAdminPermissionMobileEditCustomer)
                    }"
                return error

            }

        }


    }

    fun downloadImage(url: String) {
        downloadUseCase(url, url)
    }

    fun hideAddButton(selectedTabIndex: Int) {
        if (selectedTabIndex > 0) {
            _state.update { it.copy(isShowAddButton = true, selectedTabIndex = selectedTabIndex) }
        } else {
            _state.update { it.copy(isShowAddButton = false, selectedTabIndex = selectedTabIndex) }
        }

    }
    fun onClickItem(selectedTabIndex: Int, id:String, customer:String, customerEdit:String) {

        when (selectedTabIndex) {
            1 -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(navEvent = ContactNavEvent.GoToContactDetail(id, customer,customerEdit))
                    }
                }
            }

            2 -> {

            }

            3 -> {

            }

            else-> {

            }
        }
        if (selectedTabIndex > 0) {
            _state.update { it.copy(isShowAddButton = true) }
        } else {
            _state.update { it.copy(isShowAddButton = false) }
        }

    }

    fun onClickAddButton(selectedTabIndex: Int, id:String) {

        when (selectedTabIndex) {
            1 -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(navEvent = ContactNavEvent.GoToContactOpportunityProject(id))
                    }
                }
            }

            2 -> {

            }

            3 -> {

            }

            else-> {

            }
        }
        if (selectedTabIndex > 0) {
            _state.update { it.copy(isShowAddButton = true) }
        } else {
            _state.update { it.copy(isShowAddButton = false) }
        }

    }

    fun handleCost(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(navEvent = OpportunityNavEvent.GoToEdit(id))
            }
        }
    }

    fun handleCompleted(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    navEvent = OpportunityNavEvent.GoToEdit(id),

                    )
            }
        }
    }

    fun handleOpportunity(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    navEvent = OpportunityNavEvent.GoToEdit(id),

                    )
            }
        }
    }

    fun handleTask(id: String) {
        viewModelScope.launch { _state.update { it.copy(navEvent = OpportunityNavEvent.GoToEdit(id)) } }
    }

    fun handleTransferredToProject(id: String, transferredToProject: Boolean, successRate: Float) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    navEvent = OpportunityNavEvent.GoToEdit(id),

                    )
            }
        }
    }


    val bottomActions = listOf(
        BottomActionItem(
            id = "0", iconRes = R.drawable.ic_edit, label = stringProvider.getString(R.string.edit)
        ), BottomActionItem(
            id = "1", iconRes = R.drawable.ic_task, label = stringProvider.getString(R.string.task)
        ), BottomActionItem(
            id = "2",
            iconRes = R.drawable.chiphi,
            label = stringProvider.getString(R.string.cost)
        ), BottomActionItem(
            id = "3",
            iconRes = R.drawable.processing,
            label = stringProvider.getString(R.string.procedure)
        ), BottomActionItem(
            id = "4",
            iconRes = R.drawable.transferred_to_project,
            label = stringProvider.getString(R.string.transferredToProject)
        )
    )

    fun setErrorMessage(error: String) {
        _state.update { it.copy(errorMessage = error) }
    }

    fun onBottomActionClick(
        id: String, item: Opportunity, customer: String, customerDelete: String
    ) {


        when (id) {
            "0" -> {
                val error = permissionMobileEditOpportunity(item, customer, customerDelete)
                if (error.isEmpty()) {
                    viewModelScope.launch {
                        _state.update {
                            it.copy(
                                navEvent = OpportunityNavEvent.GoToEdit(
                                    item.id ?: "0"
                                )
                            )
                        }
                    }
                } else {
                    setErrorMessage(error)
                }

            }

            "1" -> handleTask(id)
            "2" -> handleCost(id)
            "3" -> handleCompleted(id)
            "4" -> handleTransferredToProject(
                id, item.chuyensangduan ?: false, item.tylethanhcong ?: 0f
            )
        }
    }

    private fun mapToInfoDetail(obj: Opportunity): List<InfoDetail> {


        var contact_person = obj.tennhanvienphutrach ?: ""
        var startDate = Util.ddMMYYY(obj.ngaybatdau ?: "")
        var endDate = Util.ddMMYYY(obj.ngayketthuc ?: "")


        return listOf(


            InfoDetail(label = stringProvider.getString(R.string.key), value = obj.ma ?: ""),
//            InfoDetail(label = stringProvider.getString(R.string.opportunity_name), value =obj.ten?:""),
            InfoDetail(
                label = stringProvider.getString(R.string.customer),
                value = obj.tenkhachhang ?: "",
                customerId = obj.idkhachhang ?: "",
                customertype = obj.loaikhachhang ?: "",
                highlight = true
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.contact_person),
                value = if (contact_person.isNotEmpty()) obj.tennhanvienphutrach
                    ?: "" else obj.nguoitao ?: ""
            ),

            InfoDetail(
                label = stringProvider.getString(R.string.time), value = "$startDate - $endDate"
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.dateReminder),
                value = Util.ddMMYYYHHMM(obj.ngaynhacnho ?: "")
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.successRate),
                value = (obj.tylethanhcong ?: 0f).formatFloat()
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.detail), value = obj.chitiet ?: ""
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.costLimit),
                value = (obj.hanmucchiphi ?: 0.0).formatDouble()
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.informationSources),
                value = obj.tennguon ?: ""
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.priorityLevel), value = obj.tenmucdo ?: ""
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.opportunity_process),
                value = obj.tenquytrinh ?: ""
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.addressWork),
                value = obj.diachitrienkhai ?: ""
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.pause),
                value = (obj.tamdung ?: false).toString(),
                isChecked = true
            ),

            )
    }

    fun loadDefaultTabs() {
        _state.update {
            it.copy(tabs = useCase.getDefaultAffiliateTabs())
        }
    }

    fun getOpportunityDetail(id: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }
            currentPage = 0
            useCase.getOpportunityDetail(id, user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { obj ->

                    _state.update {
                        it.copy(
                            opportunity = obj,
                            objInfoList = mapToInfoDetail(obj!!),
                            opportunityName = obj.ten ?: "",
                            isLoading = false
                        )
                    }

                }, onError = { message ->
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                })
        }
    }

    fun getContactByOpportunity(id: String, searchText: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }
            currentPage = 0
            useCase.getContactByOpportunity(id, searchText, user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { data ->
                    val newTabs = _state.value.tabs.toMutableList()
                    newTabs[1].count = data.size




                    _state.update {
                        it.copy(
                            contactsList = data, isLoading = false, tabs = newTabs
                        )
                    }
                }, onError = { message ->
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                })
        }
    }

    fun getProductByOpportunity(id: String, searchText: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }
            currentPage = 0
            useCase.getProductByOpportunity(id, searchText, user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { data ->

                    val newTabs = _state.value.tabs.toMutableList()
                    newTabs[2].count = data.products!!.size
                    _state.update {
                        it.copy(
                            productList = data.products!!,
                            isLoading = false,
                            tabs = newTabs,
                            totalMoney = data.totalMoney ?: 0.0
                        )
                    }
                }, onError = { message ->
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                })
        }
    }

    fun getAttachByOpportunity(id: String, searchText: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }
            currentPage = 0
            useCase.getAttachByOpportunity(id, searchText, user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { data ->

                    val newTabs = _state.value.tabs.toMutableList()
                    newTabs[3].count = data.size
                    _state.update {
                        it.copy(
                            attachList = data, isLoading = false, tabs = newTabs
                        )
                    }
                }, onError = { message ->
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                })
        }
    }


    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = "") }
    }

    fun resetNavigation() {
        _state.update { it.copy(navEvent = OpportunityNavEvent.None) }
    }


}
