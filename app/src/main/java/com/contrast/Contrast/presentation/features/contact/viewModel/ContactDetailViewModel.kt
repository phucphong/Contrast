package com.contrast.Contrast.presentation.features.contact.viewModel




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.extensions.formatFloat
import com.contrast.Contrast.extensions.formatToYYYYMMDD
import com.contrast.Contrast.utils.StringProvider
import com.contrast.Contrast.utils.Util
import com.itechpro.domain.model.*
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.contact.ContactDetailUiState
import com.itechpro.domain.model.customer.Customer

import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.opportunity.OpportunityDetailUiState
import com.itechpro.domain.model.product.InfoDetail
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.contact.ContactDetailUseCase
import com.itechpro.domain.usecase.dowloadFile.DownloadUseCase
import com.itechpro.domain.usecase.opportunity.OpportunityDetailUseCase
import com.itechpro.domain.usecase.opportunity.OpportunityListUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: ContactDetailUseCase,
    private val stringProvider: StringProvider,
    private val downloadUseCase: DownloadUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(ContactDetailUiState())
    val state: StateFlow<ContactDetailUiState> = _state
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
            _state.update { it.copy(navEvent = ContactNavEvent.GoToEdit(id)) }
        }
    }


    fun permissionMobileEditOpportunity(
        item: Contact, customer: String, customerDelete: String
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
            val hasPermission = "lienheedit" in permissions

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






    val bottomActions = listOf(
        BottomActionItem(
            id = "0", iconRes = R.drawable.ic_edit, label = stringProvider.getString(R.string.edit)
        )

    )

    fun setErrorMessage(error: String) {
        _state.update { it.copy(errorMessage = error) }
    }

    fun onBottomActionClick(
        id: String, item: Contact, customer: String, customerDelete: String
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

        }
    }

    private fun mapToInfoDetail(obj: Contact): List<InfoDetail> {

        val  phone=obj.dienthoai?:""
        val  phone1=obj.dienthoai1?:""
        val  email=obj.email?:""
        val  email1=obj.email1?:""
        var  fullPhone = "";
        var  fullEmail = "";

        if (phone1.isNotEmpty()) {
            fullPhone = phone1
        }else{
            if (phone.isNotEmpty()) {
                fullPhone = phone
            }
        }

        if (email1.isNotEmpty()) {
            fullEmail = email1
        }else{
            if (email.isNotEmpty()) {
                fullEmail = email
            }
        }

        return listOf(

            InfoDetail(
                label = stringProvider.getString(R.string.customer),
                value = obj.tenkhachhang ?: "",
                customerId = obj.idkhachhang ?: "",
                customertype = obj.loaikhachhang ?: "",
                highlight = true
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.pronoun),
                value = obj.xungho ?: ""
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.full_name),
                value = obj.hoten ?: ""
            ),

            InfoDetail(
                label = stringProvider.getString(R.string.date_of_birtd),
                value = Util.ddMMYYYHHMM(obj.ngaysinh ?: "")
            ),

            InfoDetail(
                label = stringProvider.getString(R.string.phone),
                value = if(_state.value.showPhoneKH) fullEmail else Util.hide4PhoneEnd(fullPhone)
            ),

            InfoDetail(
                label = stringProvider.getString(R.string.phone_house),
                value = if(_state.value.showPhoneKH) fullEmail else Util.hide4PhoneEnd(obj.dienthoainharieng?:"")
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.address),
                value = if(_state.value.showAddressKH) fullEmail else Util.hide4PhoneEnd(obj.diachi?:"")
            ),
            InfoDetail(
                label = stringProvider.getString(R.string.email),
                value = if(_state.value.showEmailKH) fullEmail else Util.hideEmail(fullEmail)
            ),

            InfoDetail(
                label = stringProvider.getString(R.string.pause),
                value = (obj.lienhechinh ?: false).toString(),
                isChecked = true
            ),
            )
    }

    fun loadDefaultTabs() {
        _state.update {
            it.copy(tabs = useCase.getDefaultAffiliateTabs())
        }
    }

    fun getContactDetail(id: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }
            currentPage = 0
            useCase.getContactDetail(id, user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { obj ->

                    _state.update {
                        it.copy(
                            contact = obj,
                            objInfoList = mapToInfoDetail(obj!!),
                            contactName ="${ obj.xungho ?: ""} - ${ obj.hoten ?: ""}",
                            isLoading = false
                        )
                    }

                }, onError = { message ->
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                })
        }
    }

    fun getEmailByContact(id: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }
            currentPage = 0
            useCase.getEmailByContact(id,  user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { data ->

                    val newTabs = _state.value.tabs.toMutableList()
                    newTabs[1].count = data.totalCount
                    _state.update {
                        it.copy(
                            contactList = data.items,
                            isLoading = false,
                            tabs = newTabs,

                            )
                    }
                }, onError = { message ->
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                })
        }
    }


    fun getCallByContact(id: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }
            currentPage = 0
            useCase.getCallByContact(id, user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { data ->
                    val newTabs = _state.value.tabs.toMutableList()
                    newTabs[2].count = data.totalCount

                    _state.update {
                        it.copy(
                            contactList = data.items, isLoading = false, tabs = newTabs
                        )
                    }
                }, onError = { message ->
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                })
        }
    }

    fun getSMSByContact(id: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }
            currentPage = 0
            useCase.getSMSByContact(id,  user.token)
                .collectResponse(dispatcher = dispatcher, onSuccess = { data ->

                    val newTabs = _state.value.tabs.toMutableList()
                    newTabs[3].count = data.totalCount
                    _state.update {
                        it.copy(
                            contactList = data.items!!,
                            isLoading = false,
                            tabs = newTabs,

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
