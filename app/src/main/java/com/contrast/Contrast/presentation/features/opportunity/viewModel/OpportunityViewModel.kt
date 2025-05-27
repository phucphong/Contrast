package com.contrast.Contrast.presentation.features.opportunity.viewModel




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
import com.itechpro.domain.usecase.opportunity.OpportunityListUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OpportunityViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: OpportunityListUserCase,
    private val stringProvider: StringProvider,

    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(OpportunityUiState())
    val state: StateFlow<OpportunityUiState> = _state
    private var currentUserInfo: CurrentUserInfo? = null
    private var allOpportunity: List<Opportunity> = emptyList()
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


    fun setInitialOpportunitys(Opportunitys: List<Opportunity>) {
        allOpportunity = Opportunitys
        currentPage = 1
        _state.update {
            it.copy(pagedOpportunitys = Opportunitys.take(pageSize))
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
                            Category(id = "-1",  ten = "Tất cả", quantity = 0)
                        ) + categorys
                        _state.update { it.copy(isLoading =true, categorys = categoryListWithAll) }

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
            _state.update { it.copy(isLoading = true, opportunitys = emptyList(), pagedOpportunitys = emptyList()) }
            currentPage = 0
            allOpportunity = emptyList()

            useCase.getOpportunity( obj, formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate),searchText, user.token,statusId, categorys).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allOpportunity = data.items
                    _state.update {
                        it.copy(
                            opportunitys = data.items,
                            pagedOpportunitys = data.items.take(pageSize),
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
    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = "") }
    }


   fun deleteOpportunity(type: String,obj: String,ids: String,  mamenu: String, content: String, startDate: String, endDate: String,
                         searchText: String,  statusId: String, categorys: List<Category>) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, opportunitys = emptyList(), pagedOpportunitys = emptyList()) }
            currentPage = 0
            allOpportunity = emptyList()


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


    fun onItemClick(id: String) {
        _state.update {
            it.copy(navEvent = OpportunityNavEvent.GoToOpportunityDetail(
                id = id))
        }
    }

    fun loadNextPage() {
        if (isLoadingNextPage || currentPage * pageSize >= allOpportunity.size) return
        isLoadingNextPage = true
        currentPage++
        _state.update { it.copy(pagedOpportunitys = allOpportunity.take(currentPage * pageSize)) }
        isLoadingNextPage = false
    }


    fun resetNavigation() {
        _state.update { it.copy(navEvent = OpportunityNavEvent.None) }
    }


}
