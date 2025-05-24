package com.contrast.Contrast.presentation.features.report.viewmodel






import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.navigationEvent.NavEvent

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase

import com.itechpro.domain.usecase.product.ProductUseCase
import android.content.Context
import com.itechpro.domain.model.LikeProductService
import com.itechpro.domain.model.product.ProductDetailUiState
import com.itechpro.domain.model.report.ReportProduct
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ReportProductViewModel @Inject constructor(private val context: Context,
                                           private val getCurrentUserUseCase: GetCurrentUserUseCase,
                                           private val useCase: ProductUseCase,
                                           private val stringProvider: StringProvider,
                                           @IoDispatcher private val dispatcher: CoroutineDispatcher,) : ViewModel() {

    private val _suggestReports = MutableStateFlow<List<ReportProduct>>(emptyList())
    val suggestReports: StateFlow<List<ReportProduct>> = _suggestReports

    private val _validationError = MutableStateFlow<String>("")
    val validationError: StateFlow<String> = _validationError

    private var currentUserInfo: CurrentUserInfo? = null
    var user: CurrentUserInfo? = null
    private val _navigationEvent = MutableStateFlow<NavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent
    private var countdownJob: Job? = null

    private val _notificationToast = MutableSharedFlow<String>()
    val notificationToast = _notificationToast.asSharedFlow()

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState
    suspend fun showNotificationToast(message: String) {
        _notificationToast.emit(message)
    }


    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }



    // Sau khi navigate xong, reset lại state
    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }

    init {
        viewModelScope.launch(dispatcher) {
        currentUserInfo = getCurrentUserUseCase()


    }
    }

    fun getTypeReport() {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {

            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getTypeReport(user.token)
                    .collect { result ->
                        when (result) {
                            is NetworkResponse.Loading -> {
                            }
                            is NetworkResponse.Success -> {
                                _suggestReports.value = result.data
                            }
                            is NetworkResponse.Error -> {
                                showNotificationToast(result.message)
                            }
                        }
                    }
            } catch (e: Exception) {


                showNotificationToast(stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}")

            }
        }
    }

    fun addEditLikeReport( url:String,idProduct: String, idUnit: String, type: String, idReson:String, content:String) {
        val user = currentUserInfo ?: return
        val obj = LikeProductService()
        obj.idsanpham =idProduct
        obj.iddonvi=idUnit
        if(type=="report"){
            obj.idlydobaocao=idReson
            obj.noidung=content
        }

        obj.loaitk=user.typeAccount
        obj.mamenu="yeuthich"
        obj.os="android"
        obj.device=user.device
        obj.hanhdong = "add"
        viewModelScope.launch(dispatcher) {
            useCase.addEditLikeReport(url, obj, user.token).collect { result ->
                if (result is NetworkResponse.Success) {
                    if(type=="report"){
                        showNotificationToast(stringProvider.getString(R.string.report_product_success))
                    }else{
                        showNotificationToast(stringProvider.getString(R.string.like_product))
                    }
                }else if (result is NetworkResponse.Error) {
                    showNotificationToast(result.message)

                }
            }
        }
    }




}
