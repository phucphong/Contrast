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
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.cart.CartUiState
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.payment.PaymentUiState
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductDetail
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.cart.CartUseCase
import com.itechpro.domain.usecase.dowloadFile.DownloadUseCase
import com.itechpro.domain.usecase.payment.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Base64
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val cartUseCase: PaymentUseCase,
    private val downloadUseCase: DownloadUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState
    private val _navigationEvent = MutableStateFlow<NavEvent>(CartNavEvent.None)
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

    fun getInfoPayment(money: String, oderKey:String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            cartUseCase.getInfoPayment(money, oderKey, user.token).collect { result ->
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
            cartUseCase.getUpdateOder(idCustomer, idOder, ghichu,user.token).collect { result ->
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
        _navigationEvent.value = CartNavEvent.None
    }
}

