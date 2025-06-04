package com.contrast.Contrast.presentation.features.paymentProduct



import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.*
import com.itechpro.domain.model.navigationEvent.HomeNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.payment.PaymentUiState
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase

import com.itechpro.domain.usecase.dowloadFile.DownloadUseCase
import com.itechpro.domain.usecase.payment.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: PaymentUseCase,
    private val downloadUseCase: DownloadUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex
    private val _selectedCategoryName = MutableStateFlow("")
    val selectedCategoryName: StateFlow<String> = _selectedCategoryName

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState
    private val _navigationEvent = MutableStateFlow<NavEvent>(HomeNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent
    private val _notificationToast = MutableSharedFlow<String>()
    val notificationToast = _notificationToast.asSharedFlow()

    private var currentUserInfo: CurrentUserInfo? = null

    init {
        viewModelScope.launch(dispatcher) {
            runCatching { getCurrentUserUseCase() }
                .onSuccess { user ->
                    currentUserInfo = user
                    _uiState.update { it.copy(domain = user.domain.orEmpty(), device = user.device.orEmpty()) }


                }
                .onFailure {
                    _uiState.update {
                        it.copy(validationError = stringProvider.getString(R.string.error_connection))
                    }
                }
        }
    }

    fun generateCategory(isOpportunity: Boolean) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {

            _uiState.update { it.copy(tabs = useCase.generateCategory(isOpportunity)) }
            updateCategoryName(_selectedTabIndex.value)
        }
    }
    fun updateSelectedTab(index: Int) {
        _selectedTabIndex.value = index
        updateCategoryName(_selectedTabIndex.value)
    }
 fun updateCategoryName(index: Int) {
      _selectedCategoryName.value=uiState.value.tabs.getOrNull(index)?.code.orEmpty()
    }

    fun getInfoPayment(money: String, oderKey:String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getInfoPayment(money, oderKey, user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is NetworkResponse.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                qrCodes = result.data,

                            )
                        }
                    }
                    is NetworkResponse.Error -> _uiState.update {
                        it.copy(isLoading = false, validationError = result.message)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveBase64Image(base64: String, context: Context) {
        viewModelScope.launch(dispatcher) {
            val success = downloadUseCase.saveBase64ImageToGallery(context, base64)
            _notificationToast.emit(if (success)stringProvider.getString(R.string.save_image_to_gallery) else stringProvider.getString(R.string.save_image_to_gallery_error) )
        }
    }


    fun getUpdateOder(idCustomer: String, idOder:String, ghichu:String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getUpdateOder(idCustomer, idOder, ghichu,user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is NetworkResponse.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                qrCodes = result.data,
                            )
                        }
                    }
                    is NetworkResponse.Error -> _uiState.update {
                        it.copy(isLoading = false, validationError = result.message)
                    }
                }
            }
        }
    }





    fun resetNavigation() {
        _navigationEvent.value = HomeNavEvent.None
    }

    fun gotoHome() {
        _navigationEvent.value = SplashNaEvent.GoToMain("0", "0", "0")
    }
}

