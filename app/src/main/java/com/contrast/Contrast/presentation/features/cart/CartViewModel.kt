// 📦 Tên file: CartViewModel.kt (Full Refactor)

package com.contrast.Contrast.presentation.features.cart

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
import com.itechpro.domain.model.payment.OrderPayment
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductDetail
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.cart.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val cartUseCase: CartUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState
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
                    _uiState.update { it.copy(domain = user.domain.orEmpty(), device = user.device.orEmpty(),
                        typeAccount = user.typeAccount.orEmpty(),
                        customerId = user.customerId.orEmpty(),
                        employeeId = user.employeeId.orEmpty())
                    }

                }
                .onFailure {
                    _uiState.update {
                        it.copy(validationError = stringProvider.getString(R.string.error_connection))
                    }
                }
        }
    }

    fun getCarts(isTotalOrder: Boolean) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            cartUseCase.getCarts(isTotalOrder, user.typeAccount, user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is NetworkResponse.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                carts = result.data.items,
                                totalCartItems = result.data.totalCount ?: 0,
                                totalValue = result.data.totalValue ?: 0.0,
                                totalIntoMoney = result.data.totalIntoMoney ?: 0.0,
                                amountMoneyDiscount = result.data.amountMoneyDiscount ?: 0.0
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

    fun addEditCart(url: String, cartItem: CartItem, type: String, quantity: Double, isTotalOrder: Boolean, isToast: Boolean) {
        val user = currentUserInfo ?: return
        cartItem.apply {
            ido = if (type == "update") id else "0"
            soluong = quantity
            loaicapnhat = type
            mamenu = "giohang"
            hanhdong = if (type == "add") "add" else "edit"
            device = _uiState.value.device
        }

        viewModelScope.launch(dispatcher) {
            cartUseCase.addEditCart(url, cartItem, user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is NetworkResponse.Error -> _uiState.update { it.copy(validationError = result.message) }
                    is NetworkResponse.Success -> {
                        getCarts(isTotalOrder)
                        if(isToast){
                            _notificationToast.emit(stringProvider.getString(R.string.add_product_on_cart))
                        }

                    }
                }
            }
        }
    }
    fun addOrder( carts: List<CartItem> ,idCustomer: String, note: String,

                address: String, typeAccount: String,discount:String, isToast: Boolean, isOpportunity: Boolean) {
        val user = currentUserInfo ?: return
        val salesPointId = user.salesPointId




        val orderPayment = OrderPayment(
            idCustomer,
            salesPointId,
            "",
            note,
            address,
            typeAccount,
            discount,
            carts
        )
        viewModelScope.launch(dispatcher) {
            cartUseCase.addOrder(isOpportunity, orderPayment, user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is NetworkResponse.Error -> _uiState.update { it.copy(validationError = result.message) }
                    is NetworkResponse.Success -> {
                        deleteAll(carts)
                        if(isToast){
                            _notificationToast.emit(stringProvider.getString(R.string.add_oder_success))

                            _navigationEvent.value = CartNavEvent.GoToPayment(
                                totalIntoMoney = result.data.tongtien,
                                oderKey = result.data.madonhang,
                                idOder = result.data.iddonhang,
                                discount = _uiState.value.discount.toString(),
                                address = address,
                                isOpportunity = isOpportunity.toString()
                            )
                        }

                    }
                }
            }
        }
    }


    fun onItemAddCartToProductDetail(product: ProductDetail, introducerId: String) {
        val cartItem = CartItem(
            id = "0",
            idsp = product.id,
            iddonvi = product.iddonvichuan,
            idnguoigioithieu = introducerId,
            soluong = product.soluong ?: 1.0,
            dongia = product.sotien,
            ck = product.khuyenmai,
            loaicapnhat = "add",
            mamenu = "giohang",
            hanhdong = "add",
            device = _uiState.value.device
        )
        addEditCart("/ex/apiaffiliate/addgiohang", cartItem, "add", product.soluong ?: 1.0, false, true)
    }
    fun deleteAll(carts: List<CartItem>) {
        val ids = cartUseCase.idsDeleteCart(carts)
        deleteCart(ids)
    }

    fun deleteCart(ids: String, content: String = "") {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            cartUseCase.getDeleteCart(ids, user.device, content, user.token).collect { result ->
                _uiState.update {
                    it.copy(
                        isLoading = result is NetworkResponse.Loading,
                        validationError = if (result is NetworkResponse.Error) result.message else it.validationError,
                        statusMessage = if (result is NetworkResponse.Success) result.data else it.statusMessage
                    )
                }
            }
        }
    }

    fun checkProductBeforePayment(ids: String,carts: List<CartItem>, typeAccount: String,address: String,  customerId: String, note: String, isOpportunity: Boolean) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            cartUseCase.checkProductBeforePayment(ids, user.token).collect { result ->
                _uiState.update { it.copy(isLoading = result is NetworkResponse.Loading) }
                when (result) {
                    is NetworkResponse.Success -> {
                        if (result.data.isEmpty() ) {


                            addOrder( carts , customerId,note,address, typeAccount,"0", true,isOpportunity)

                        } else {



                        }
                    }
                    is NetworkResponse.Error -> _uiState.update { it.copy(validationError = result.message) }
                    NetworkResponse.Loading -> {

                    }
                }
            }
        }
    }

    fun payment(typeAccount: String,address: String,customerId: String, note: String, carts: List<CartItem>, isOpportunity: Boolean) {
        viewModelScope.launch(dispatcher) {
            if (address.isEmpty()) {
                _notificationToast.emit(stringProvider.getString(R.string.address_oder_emtry))
            } else {
                val ids = cartUseCase.idsProductCheckActive(carts)
                checkProductBeforePayment(ids,carts, typeAccount,address,customerId,note, isOpportunity)
            }
        }
    }

    fun onItemAddCart(product: Product) {
        val cartItem = CartItem(
            id = "0",
            idsp = product.id,
            iddonvi = product.iddonvichuan,
            soluong = 1.0,
            dongia = product.sotien,
            ck = product.khuyenmai,
            loaicapnhat = "add",
            mamenu = "giohang",
            hanhdong = "add",
            device = _uiState.value.device
        )
        addEditCart("/ex/apiaffiliate/addgiohang", cartItem, "add", 1.0, false, true)
    }

    fun updateCartQuantity(cartItem: CartItem, type: String, newQuantity: Double, ) {
        _uiState.update { state ->
            state.copy(carts = state.carts.map {
                if (it.idsp == cartItem.idsp) it.copy(soluong = newQuantity) else it
            })
        }
        addEditCart("/ex/apiaffiliate/addgiohang", cartItem, type, newQuantity, true, false)
    }
    fun isAllSelected(carts: List<CartItem>, isChecked: Boolean) {
        val updatedCarts = cartUseCase.onCheckedChangeAll(carts, isChecked)
        _uiState.update {
            it.copy(
                carts = updatedCarts,
                isAllSelected = isChecked
            )
        }
        updateCartTotal(updatedCarts)
    }

    fun increaseQuantity(item: CartItem, type: String) {
        val newQuantity = (item.soluong ?: 1.0) + 1
        updateCartQuantity(item, type, newQuantity)
    }
    fun onQuantityChange(cartItem: CartItem, type: String, newQuantity: Double) {
        updateCartQuantity(cartItem, type, newQuantity)
    }

    fun decreaseQuantity(item: CartItem, type: String) {
        val quantity = (item.soluong ?: 1.0)
        if (quantity >= 1.0) updateCartQuantity(item, type, quantity - 1)
    }

    fun onCheckedChangeAll(carts: List<CartItem>, isChecked: Boolean) {
        val updatedCarts = cartUseCase.onCheckedChangeAll(carts, isChecked)
        _uiState.update { it.copy(carts = updatedCarts, isAllSelected = isChecked) }
        updateCartTotal(updatedCarts)
    }

    fun updateCartTotal(carts: List<CartItem>) {
        val result = cartUseCase.calculateCartTotal(carts, false)
        _uiState.update {
            it.copy(
                totalValue = result.totalValue,
                totalIntoMoney = result.totalIntoMoney,
                amountMoneyDiscount = result.amountMoneyDiscount
            )
        }
    }

    fun resetNavigation() {
        _navigationEvent.value = CartNavEvent.None
    }
}

