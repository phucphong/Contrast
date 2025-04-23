package com.contrast.Contrast.presentation.features.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.*
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.cart.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: CartUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _carts = MutableStateFlow<List<CartItem>>(emptyList())
    val carts: StateFlow<List<CartItem>> = _carts

    private val _cartTable = MutableStateFlow<Cart?>(null)
    val cartTable: StateFlow<Cart?> = _cartTable

    private val _validationError = MutableStateFlow("")
    val validationError: StateFlow<String> = _validationError

    private val _domain = MutableStateFlow("")
    val domain: StateFlow<String> = _domain

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _totalCartItems = MutableStateFlow(0)
    val totalCartItems: StateFlow<Int> = _totalCartItems
    private val _totalNotificationItems = MutableStateFlow(0)
    val totalNotificationItems: StateFlow<Int> = _totalNotificationItems

    private val _navigationEvent = MutableStateFlow<ProductNavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<ProductNavEvent> = _navigationEvent

    private var currentUserInfo: CurrentUserInfo? = null

    init {
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                _domain.value = currentUserInfo?.domain.orEmpty()

                val cartsDeferred = async { getCarts() }
                cartsDeferred.await()


            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage.orEmpty()}"
            }
        }
    }
    fun onTabSelected(index: Int, category: Category) {
        _navigationEvent.value = ProductNavEvent.GoToProductsCategory(category.id.orEmpty())
    }
    fun getCarts() {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCarts(user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> _isLoading.value = true
                    is NetworkResponse.Success -> {
                        _isLoading.value = false
                        _carts.value = result.data.items
                        _totalCartItems.value = result.data.totalCount
                    }
                    is NetworkResponse.Error -> {
                        _isLoading.value = false
                        _validationError.value = result.message
                    }
                }
            }
        }
    }

    fun getCheckOder(ids: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCheckOder(ids, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Success) _statusMessage.value = result.data
                if (result is NetworkResponse.Error) _validationError.value = result.message
            }
        }
    }

    fun getDeleteCart(ids: String, content: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getDeleteCart(ids, user.device, content, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Success) _statusMessage.value = result.data
                if (result is NetworkResponse.Error) _validationError.value = result.message
            }
        }
    }

    fun getCheckProduct(ids: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCheckProduct(ids, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Success) _statusMessage.value = result.data
                if (result is NetworkResponse.Error) _validationError.value = result.message
            }
        }
    }

    fun addEditCart(url: String, obj: CartItem) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            useCase.addEditCart(url, obj, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Error) {
                    _validationError.value = result.message
                }
            }
        }
    }


    fun addOder(url: String, obj: CartItem) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            useCase.addOder(url, obj, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Error) {
                    _validationError.value = result.message
                }
            }
        }
    }




    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }
}
