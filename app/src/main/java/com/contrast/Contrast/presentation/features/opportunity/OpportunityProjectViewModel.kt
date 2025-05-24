package com.contrast.Contrast.presentation.features.opportunity




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.extensions.formatToYYYYMMDD
import com.itechpro.domain.model.*

import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.opportunity_project.OpportunityProject
import com.itechpro.domain.model.opportunity_project.OpportunityProjectUiState
import com.itechpro.domain.model.order.Order
import com.itechpro.domain.model.order.OrdersUiState
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.oder.OdersUserCase
import com.itechpro.domain.usecase.opportunity_project.OpportunityProjectUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OpportunityProjectViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: OpportunityProjectUserCase,

    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(OpportunityProjectUiState())
    val state: StateFlow<OpportunityProjectUiState> = _state
    private var currentUserInfo: CurrentUserInfo? = null
    private var allOders: List<OpportunityProject> = emptyList()
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


    fun setInitialOders(oders: List<OpportunityProject>) {
        allOders = oders
        currentPage = 1
        _state.update {
            it.copy(pagedOpportunityProjects = oders.take(pageSize))
        }
    }


    fun getOpportunitiesProject(obj: String,startDate: String, endDate: String, searchKey: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, opportunityProjects = emptyList(), pagedOpportunityProjects = emptyList()) }
            currentPage = 0
            allOders = emptyList()

            useCase.getOpportunitiesProject( obj, formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate),searchKey, user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allOders = data
                    _state.update {
                        it.copy(
                            opportunityProjects = data,
                            pagedOpportunityProjects = data.take(pageSize),
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

    fun getOpportunitiesProjectByIDCustomer(obj: String,mode: String,customerId: String,  searchKey: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, opportunityProjects = emptyList(), pagedOpportunityProjects = emptyList()) }
            currentPage = 0
            allOders = emptyList()

            useCase.getOpportunitiesProjectByIDCustomer( obj, mode,
                customerId,searchKey, user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allOders = data
                    _state.update {
                        it.copy(
                            opportunityProjects = data,
                            pagedOpportunityProjects = data.take(pageSize),
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
        _state.update { it.copy(pagedOpportunityProjects = allOders.take(currentPage * pageSize)) }
        isLoadingNextPage = false
    }


    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }


}
