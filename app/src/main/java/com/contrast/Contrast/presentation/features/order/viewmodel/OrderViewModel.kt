package com.contrast.Contrast.presentation.features.order.viewmodel



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.extensions.formatToYYYYMMDD
import com.itechpro.domain.model.*

import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.order.Order
import com.itechpro.domain.model.order.OrdersUiState
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.oder.OdersUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: OdersUserCase,

    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(OrdersUiState())
    val state: StateFlow<OrdersUiState> = _state
    private var currentUserInfo: CurrentUserInfo? = null
    private var allOders: List<Order> = emptyList()
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
                        pointAffiliate = user.pointAffiliate,
                        employeeId = user.employeeId,
                        customerId = user.customerId,
                    )
                }
            }
        }
    }


    fun setInitialOders(oders: List<Order>) {
        allOders = oders
        currentPage = 1
        _state.update {
            it.copy(pagedOrders = oders.take(pageSize))
        }
    }


    fun getOderByConfirm(startDate: String, endDate: String, confirm: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, orders = emptyList(), pagedOrders = emptyList()) }
            currentPage = 0
            allOders = emptyList()

            useCase.getOderByConfirm(  formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate),confirm,user.typeAccount, user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allOders = data
                    _state.update {
                        it.copy(
                            orders = data,
                            pagedOrders = data.take(pageSize),
                            isLoading = false
                        )
                    }
                 
                },
                onError = { message ->
                    _state.update { it.copy(isLoading = false, error = message) }
                }
            )
        }
    }


    fun getOderByTypeAccount(startDate: String, endDate: String, customerId: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, orders = emptyList(), pagedOrders = emptyList()) }
            currentPage = 0
            allOders = emptyList()

            useCase.getOderByTypeAccount(  formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate),customerId, user.typeAccount,user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allOders = data
                    _state.update {
                        it.copy(
                            orders = data,
                            pagedOrders = data.take(pageSize),
                            isLoading = false
                        )
                    }

                },
                onError = { message ->
                    _state.update { it.copy(isLoading = false, error = message) }
                }
            )
        }
    }

    fun onItemClick(id: String, type:String) {
        _state.update {
            it.copy(navEvent = OrderNavEvent.GoToOderDetail(
                id = id,
                type = type,

            ))
        }
    }

    fun loadNextPage() {
        if (isLoadingNextPage || currentPage * pageSize >= allOders.size) return
        isLoadingNextPage = true
        currentPage++
        _state.update { it.copy(pagedOrders = allOders.take(currentPage * pageSize)) }
        isLoadingNextPage = false
    }

    

    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }


}
