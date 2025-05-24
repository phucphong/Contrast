package com.contrast.Contrast.presentation.features.report.viewmodel





import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.extensions.formatToYYYYMMDD
import com.contrast.Contrast.utils.Util
import com.itechpro.domain.model.*
import com.itechpro.domain.model.category.Category

import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.report.Report
import com.itechpro.domain.model.report.ReportUiState
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase

import com.itechpro.domain.usecase.report.ReportPersonalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ReportPersonalViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: ReportPersonalUseCase,

    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(ReportUiState())
    val state: StateFlow<ReportUiState> = _state
    private var currentUserInfo: CurrentUserInfo? = null
    private var allReports: List<Report> = emptyList()
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
                    )
                }
            }
        }
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
    fun setInitialReports(reports: List<Report>) {
        allReports = reports
        currentPage = 1
        _state.update {
            it.copy(pagedReports = reports.take(pageSize))
        }
    }


    fun getReportPersonalSales(obj: String,mode: String,startDate: String, endDate: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, reports = emptyList(), pagedReports = emptyList()) }
            currentPage = 0
            allReports = emptyList()

            useCase.getReportPersonalSales( obj,mode, formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate), user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->



                    _state.update {
                        it.copy(
                            report = data,
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

    fun getReportSalesReportByAgentByLevel(startDate: String, endDate: String,ids: String, idsAgencyLevel: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, reports = emptyList(), pagedReports = emptyList()) }
            currentPage = 0
            allReports = emptyList()

            useCase.getReportSalesReportByAgentByLevel( formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate),ids,idsAgencyLevel,user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allReports = data
                    _state.update {
                        it.copy(
                            reports = data,
                            pagedReports = data.take(pageSize),
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
    fun getReportPassiveCommission(monthYear: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, reports = emptyList(), pagedReports = emptyList()) }
            currentPage = 0
            allReports = emptyList()

            useCase.getReportPassiveCommission( formatToYYYYMMDD("01/$monthYear"), user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allReports = data
                    _state.update {
                        it.copy(
                            reports = data,
                            pagedReports = data.take(pageSize),
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
    fun getReportUpToLevel(monthYear: String, ids: String,
                           idsAgencyLevel: String,) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, reports = emptyList(), pagedReports = emptyList()) }
            currentPage = 0
            allReports = emptyList()



            useCase.getReportUpToLevel( formatToYYYYMMDD(monthYear), ids,idsAgencyLevel ,user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allReports = data
                    _state.update {
                        it.copy(
                            reports = data,
                            pagedReports = data.take(pageSize),
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



    fun onCategorySelected(monthYear: String,index: Int, tabs: List<Category>) {
        if (state.value.selectedTab != index) {
            val code = tabs.getOrNull(index)?.code.orEmpty()
            _state.update { it.copy(selectedTab = index) }


        }
    }



    fun loadNextPage() {
        if (isLoadingNextPage || currentPage * pageSize >= allReports.size) return
        isLoadingNextPage = true
        currentPage++
        _state.update { it.copy(pagedReports = allReports.take(currentPage * pageSize)) }
        isLoadingNextPage = false
    }


    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }


}
