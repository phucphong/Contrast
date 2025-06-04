package com.contrast.Contrast.presentation.features.contact.viewModel



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.*
import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.contact.ContactUiState

import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.contact.ContactListUseCase


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: ContactListUseCase,
    private val stringProvider: StringProvider,

    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(ContactUiState())
    val state: StateFlow<ContactUiState> = _state
    private var currentUserInfo: CurrentUserInfo? = null
    private var allContacts: List<Contact> = emptyList()
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
                        employeeId = user.employeeId,
                        customerId = user.customerId,
                        admin = user.admin,
                        adminRoot = user.adminRoot,
                        permissionMobile = user.permissionMobile,
                        showEmailKH = user.showEmailKH.toBoolean(),
                        showPhoneKH = user.showPhoneKH.toBoolean(),
                        showAddressKH = user.showAddressKH.toBoolean(),
                    )
                }
            }
        }
    }

    fun canDeleteContacts(
        item: Contact, customer: String, customerDelete: String
    ): String {

        if (customer=="1") {
            if (customerDelete == "0") {
                var error =
                    "${stringProvider.getString(R.string.youNotPermissionMobileDeleteContactsCustomer)} ${item.makhachhang?:"".lowercase()} - ${item.tenkhachhang?:"".lowercase()} ${
                        stringProvider.getString(R.string.pleaseContactToAdminPermissionMobileDeleteCustomer)
                    }"
                return error
            } else {
                return ""
            }

        } else {
            val permissions = _state.value.permissionMobile.split(",").map { it.trim() }
            val hasPermission = "lienhedelete" in permissions

            if (_state.value.adminRoot || _state.value.admin || hasPermission ) {
                return "";
            } else {
                var error = "${stringProvider.getString(R.string.youNotPermissionMobileDeleteContacts)} ${
                    stringProvider.getString(R.string.pleaseContactToAdminPermissionMobileDeleteCustomer)
                }"
                return error

            }

        }


    }

    fun setInitialContacts(Contactss: List<Contact>) {
        allContacts = Contactss
        currentPage = 1
        _state.update {
            it.copy(pageContact = Contactss.take(pageSize))
        }
    }

    fun getContactList(
        projectId: String,
    ) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update {
                it.copy(
                    isLoading = true, contactList = emptyList(), pageContact = emptyList()
                )
            }
            currentPage = 0
            allContacts = emptyList()

            useCase.getContactProjectNotImplement(
                projectId,
                user.token,

                ).collectResponse(dispatcher = dispatcher, onSuccess = { data ->
                allContacts = data
                _state.update {
                    it.copy(
                        contactList = data,
                        pageContact = data.take(pageSize),
                        isLoading = false
                    )
                }

            }, onError = { message ->
                _state.update { it.copy(isLoading = false, errorMessage = message) }
            })
        }
    }

    fun getContactByType(
        id: String,
        type: String,
    ) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            _state.update {
                it.copy(isLoading = true, contactList = emptyList(), pageContact = emptyList())
            }
            currentPage = 0
            allContacts = emptyList()

            val flow = when (type) {
                "cohoikinhdoanh" -> useCase.getContactOpportunityNotImplement(id, user.token)
                "duan"  -> useCase.getContactProjectNotImplement(id, user.token)

                else -> {useCase.getContactByCustomerId(id, user.token)}
            }

            flow.collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allContacts = data
                    _state.update {
                        it.copy(
                            contactList = data,
                            pageContact = data.take(pageSize),
                            isLoading = false
                        )
                    }
                },
                onError = { message ->
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                }
            )
        }
    }




    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = "") }
    }

    fun setErrorMessage(error: String) {
        _state.update { it.copy(errorMessage = error) }
    }

    //  obj: Contact, mamenu: String, os: String,device: String,content: String,authen: String
    fun saveContactToOpportunityProject(type: String,id: String, list: List<Contact>) {
        val user = currentUserInfo ?: return

        val  ids = useCase.idsContactChecked(list)
        if(ids.isEmpty()){
            setErrorMessage(stringProvider.getString(R.string.pleaseSelectContact))
        }else{

            val  contact = Contact( )
            contact.ids = ids

            var  menuname = ""
            if(type=="cohoikinhdoanh"){
                contact.idcohoikinhdoanh = id
                menuname = "themlienhecohoikinhdoanh"
            }else  {
                contact.idduan = id
                menuname = "themlienheduan"
            }

            viewModelScope.launch(dispatcher) {
                useCase.saveContactToOpportunityProject(
                    type = type,
                    obj = contact,
                    menuname,
                    type,
                    "android", user.device,
                    authen = user.token
                ).collectResponse(
                    onSuccess = {
                        _state.update { it.copy( isLoading = false, errorMessage = stringProvider.getString(R.string.save_success)) }
                     getContactByType(id,type)
                    },
                    onError = { error ->
                        _state.update { it.copy(errorMessage = error) }
                    },
                    dispatcher = dispatcher,
                    onLoading = {
                        _state.update {
                            it.copy(
                                isLoading = true, contactList = emptyList(), pageContact = emptyList()
                            )
                        }
                    }
                )
            }
        }


//

    }


    fun deleteContacts(
        type: String,
        obj: String,
        ids: String,
        mamenu: String,
        content: String,
        startDate: String,
        endDate: String,
        searchText: String,
        statusId: String,
        categorys: List<Contact>
    ) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update {
                it.copy(
                    isLoading = true, contactList = emptyList(), pageContact = emptyList()
                )
            }
            currentPage = 0
            allContacts = emptyList()


            useCase.deleteContacts(
                obj, "deletes", ids, mamenu, "android", user.device, content, user.token
            ).collectResponse(dispatcher = dispatcher, onSuccess = { data ->

                _state.update {
                    it.copy(
                        errorMessage = stringProvider.getString(R.string.delete_success),
                        isLoading = false
                    )
                }

//                getContacts(
//                    "cohoikinhdoanh", startDate, endDate, searchText, statusId, categorys
//                )


            }, onError = { message ->
                _state.update { it.copy(isLoading = false, errorMessage = message) }
            })
        }
    }


    fun onItemClick(
        id: String,
        customer: String,
        customerEdit: String,
    ) {
        _state.update {
            it.copy(
                navEvent = ContactNavEvent.GoToContactDetail(
                    id = id,
                    customer = customer,
                    customerEdit = customerEdit,
                )
            )
        }
    }

    fun loadNextPage() {
        if (isLoadingNextPage || currentPage * pageSize >= allContacts.size) return
        isLoadingNextPage = true
        currentPage++
        _state.update { it.copy(pageContact = allContacts.take(currentPage * pageSize)) }
        isLoadingNextPage = false
    }


    fun resetNavigation() {
        _state.update { it.copy(navEvent = ContactNavEvent.None) }
    }


}
