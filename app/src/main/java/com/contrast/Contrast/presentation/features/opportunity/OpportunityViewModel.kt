package com.contrast.Contrast.presentation.features.opportunity




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.extensions.formatToYYYYMMDD
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.*
import com.itechpro.domain.model.category.Category

import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.opportunity.OpportunityUiState
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.opportunity.OpportunitysUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OpportunityViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: OpportunitysUserCase,
    private val stringProvider: StringProvider,

    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(OpportunityUiState())
    val state: StateFlow<OpportunityUiState> = _state
    private var currentUserInfo: CurrentUserInfo? = null
    private var allOders: List<Opportunity> = emptyList()
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


    fun setInitialOders(oders: List<Opportunity>) {
        allOders = oders
        currentPage = 1
        _state.update {
            it.copy(pagedOpportunityProjects = oders.take(pageSize))
        }
    }
    fun getOpportunityProcess(type:String,startDate: String, endDate: String, searchText: String, statusId: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getOpportunityProcess(user.token)
                .collectResponse(
                    dispatcher = dispatcher,
                    onSuccess = { categorys ->
                        val categoryListWithAll = listOf(
                            Category(id = "-1", code = "all", ten = "Tất cả", quantity = 0)
                        ) + categorys
                        _state.update { it.copy(categorys = categoryListWithAll) }

                        if (type == "cohoikinhdoanh") {
                           getOpportunity("cohoikinhdoanh",startDate, endDate, searchText, statusId,categoryListWithAll)
                        } else if (type == "cantuvan") {
                           getOpportunity("cohoikinhdoanh","", "", searchText,statusId,categoryListWithAll)
                        }

                    },
                    onError = { message ->
                        _state.update { it.copy(errorMessage = message) }
                    }
                )
        }
    }
    fun onCategorySelected(index: Int, categorys: List<Category>, type: String, startDate: String, endDate: String, searchText: String, ) {
        _state.update { it.copy(selectedTab = index,categoryCode = categorys.getOrNull(index)?.id.orEmpty()) }


        if (type == "cohoikinhdoanh") {
            getOpportunity("cohoikinhdoanh",startDate, endDate, searchText, categorys.getOrNull(index)?.id.orEmpty(),categorys)
        } else if (type == "cantuvan") {
            getOpportunity("cohoikinhdoanh","", "", searchText,categorys.getOrNull(index)?.id.orEmpty(),categorys)
        }
    }
    fun getOpportunity(obj: String,startDate: String, endDate: String, searchText: String, statusId: String, categorys: List<Category>) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, opportunityProjects = emptyList(), pagedOpportunityProjects = emptyList()) }
            currentPage = 0
            allOders = emptyList()

            useCase.getOpportunity( obj, formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate),searchText, user.token,statusId, categorys).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allOders = data.items
                    _state.update {
                        it.copy(
                            opportunityProjects = data.items,
                            pagedOpportunityProjects = data.items.take(pageSize),
                            categorys = data.categories,
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

    fun getOpportunityByIDCustomer(obj: String,mode: String,customerId: String,  searchText: String, statusId: String, categorys: List<Category>) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, opportunityProjects = emptyList(), pagedOpportunityProjects = emptyList()) }
            currentPage = 0
            allOders = emptyList()

            useCase.getOpportunityByIDCustomer( obj, mode,
                customerId,searchText, user.token,statusId, categorys).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allOders = data.items
                    _state.update {
                        it.copy(
                            opportunityProjects = data.items,
                            pagedOpportunityProjects = data.items.take(pageSize),
                            categorys = data.categories,
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
   fun deleteOpportunity(type: String,obj: String,ids: String,  mamenu: String, content: String, startDate: String, endDate: String,
                         searchText: String,  statusId: String, categorys: List<Category>) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, opportunityProjects = emptyList(), pagedOpportunityProjects = emptyList()) }
            currentPage = 0
            allOders = emptyList()


            useCase.deleteOpportunity( obj, "deletes",
                ids,mamenu, "android",user.device,content, user.token ).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->

                    _state.update {
                        it.copy(
                            errorMessage = stringProvider.getString(R.string.delete_success),
                            isLoading = false
                        )
                    }

                    if (type == "cohoikinhdoanh") {
                        getOpportunity("cohoikinhdoanh",startDate, endDate, searchText, statusId,categorys)
                    } else if (type == "cantuvan") {
                        getOpportunity("cohoikinhdoanh","", "", searchText, statusId,categorys)
                    }

                },
                onError = { message ->
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
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
