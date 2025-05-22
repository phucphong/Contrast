package com.contrast.Contrast.presentation.features.order.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.*

import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.oder.Order
import com.itechpro.domain.model.oder.OdersUiState
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.oder.OderDetailUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: OderDetailUserCase,

    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(OdersUiState())
    val state: StateFlow<OdersUiState> = _state
    private var currentUserInfo: CurrentUserInfo? = null


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


    fun getOderById(type: String, ido: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }

            var obj = "";
            var mode = "";
            if (type == "donhang") {
                obj = "laychitietdonhang"
                mode = "modelaychitietdonhang"
            } else {
                obj = "laychitietdonhangdathang"
                mode = "laychitietdonhangdathang"
            }


            useCase.getOderById(
                obj, mode, ido, user.token
            ).collectResponse(dispatcher = dispatcher, onSuccess = { data ->
                if (data != null) {
                    _state.update {
                        it.copy(
                            order = data.oderInfo, orders = data.items, isLoading = false
                        )
                    }
                }

            }, onError = { message ->
                _state.update { it.copy(isLoading = false, error = message) }
            })
        }
    }


    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }


}
